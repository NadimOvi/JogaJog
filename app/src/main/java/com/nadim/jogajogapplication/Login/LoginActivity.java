package com.nadim.jogajogapplication.Login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nadim.jogajogapplication.Api;
import com.nadim.jogajogapplication.Login.Model.EmpInfo;
import com.nadim.jogajogapplication.Login.Model.UserEmployeeInfo;
import com.nadim.jogajogapplication.R;
import com.nadim.jogajogapplication.MainDashboard.UserDashboardActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userNameId,passwordId;
    private CheckBox checkRemember;
    private ImageView loginButton;

    private Integer userId;

    private SharedPreferences sp;
    private SharedPreferences.Editor sped;

    private String strRemember;
    private String userName,userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (isConnected()) {
            preInitialize();
            prcGetRemember();
        } else {
            Toast.makeText(getApplicationContext(), "No internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
    private void preInitialize() {
        userNameId = findViewById(R.id.userNameId);
        passwordId = findViewById(R.id.passwordId);
        checkRemember = findViewById(R.id.checkRemember);
        findViewById(R.id.loginButton).setOnClickListener(this);


        userId = getIntent().getIntExtra("userId",0);

        sp = getApplicationContext().getSharedPreferences("GTR", MODE_PRIVATE);
        sped = sp.edit();
    }

    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else
            return false;

    }
    private void prcSetRemember() {
        strRemember = "";
        if (checkRemember.isChecked()) {
            strRemember = "Remember";
            sped.putString("UserName", userNameId.getText().toString().trim());
            sped.putString("Password", passwordId.getText().toString().trim());
            sped.putString("Remember", strRemember);
            sped.commit();
        } else {
            sped.putString("UserName", "");
            sped.putString("Password", "");
            sped.putString("Remember", "");
            sped.commit();
        }
    }
    private void prcGetRemember() {

        if (sp.contains("UserName")) {
            String strPassword = "";
            userNameId.setText(sp.getString("UserName", ""));
            passwordId.setText(sp.getString("Password", ""));
            checkRemember.setChecked(false);
            strPassword = sp.getString("Remember", "");
            if (strPassword.length() != 0) {
                checkRemember.setChecked(true);
            }
            prcValidateUser("Auto");
        }
    }
    private void prcValidateUser(String Flag) {
        /* progressDialog.show();*/
        //Validating User :: Using Async Task
        try {
            validationUser();

        } catch (Exception ex) {
            Log.d("ValUser", ex.getMessage());
        }
    }
    private void validationUser(){

        userName = userNameId.getText().toString().trim();
        userPassword = passwordId.getText().toString().trim();

        if (userName.isEmpty()) {
            userNameId.setError("Please provide user name");
            userNameId.requestFocus();
            return;
        }

        if (userPassword.isEmpty()) {
            passwordId.setError("Please provide security code");
            passwordId.requestFocus();
            return;
        }

        userLogin();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginButton) {
            validationUser();
        }
    }
    private void userLogin() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Api api = retrofit.create(Api.class);
        Call<UserEmployeeInfo> call =api.getEmployeeDataInfo(userName,userPassword);
        call.enqueue(new Callback<UserEmployeeInfo>() {
            @Override
            public void onResponse(Call<UserEmployeeInfo> call, Response<UserEmployeeInfo> response) {
                UserEmployeeInfo userEmployeeInfo= response.body();
                if (response.isSuccessful()){

                    if (userEmployeeInfo!= null){
                        EmpInfo empInfo = userEmployeeInfo.getEmpInfo();

                        Integer empId = empInfo.getEmpId();
                        String empCode = String.valueOf(empInfo.getEmpCode());
                        String empName = empInfo.getEmpName();
                        String desigName = empInfo.getDesignation().getDesigName();
                        String empImage = empInfo.getEmpImage();
                        String token = userEmployeeInfo.getToken();


                        prcSetRemember();

                        Intent intent = new Intent(LoginActivity.this, UserDashboardActivity.class);
                        intent.putExtra("empId", empId);
                        intent.putExtra("empCode", empCode);
                        intent.putExtra("empName", empName);
                        intent.putExtra("desigName", desigName);
                        intent.putExtra("empImage", empImage);
                        intent.putExtra("token", token);



                        /*JSONArray arr = new JSONArray(yourJSONresponse);*/
                    /*    List<String> list = new ArrayList<String>();
                        for(int i = 0; i < products.size(); i++){
                            list.add(String.valueOf(products));
                        }*/
                        /*intent.putExtra("productArrayList", String.valueOf(list));*/


                        /*Toast.makeText(LoginActivity.this,"Login Successful", Toast.LENGTH_LONG).show();*/
                        startActivity(intent);

                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Null", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserEmployeeInfo> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.alert_dark_frame)
                .setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }

                })
                .setNegativeButton("No",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }

                } )

                .show();
    }
}