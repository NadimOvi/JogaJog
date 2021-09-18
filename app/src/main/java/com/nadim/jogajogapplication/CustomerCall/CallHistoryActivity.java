package com.nadim.jogajogapplication.CustomerCall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.nadim.jogajogapplication.Api;

import com.nadim.jogajogapplication.CustomerCall.Others.CreateEvent;
import com.nadim.jogajogapplication.CustomerCall.Others.MainActivity;
import com.nadim.jogajogapplication.Login.LoginActivity;
import com.nadim.jogajogapplication.MainDashboard.NewContact.CreateContactActivity;
import com.nadim.jogajogapplication.MainDashboard.UserDashboardActivity;
import com.nadim.jogajogapplication.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import softpro.naseemali.ShapedNavigationView;
import softpro.naseemali.ShapedViewSettings;

public class CallHistoryActivity extends AppCompatActivity implements CallReceiver.Message{

    private TextView companyNameShow,personNameShow,personPhoneNumberShow,callToPerson;
    private String personName,companyName,phoneNumber,token;
    private Integer contactId, empId;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    String contact;


    SharedPreferences sp;
    SharedPreferences.Editor sped;
    private ShapedNavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    private RecyclerView callHistoryRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_history);
        getInitialize();
        setInitialize();
        getCallHistory();

    }

    private void getInitialize() {
       /* Bundle bundle = getIntent().getExtras();
        String bundleMessage = bundle.getString("message");
        Toast.makeText(this, bundleMessage, Toast.LENGTH_LONG).show();
        */

        Intent i=getIntent();
        empId= i.getIntExtra("empId",0);
        contactId= i.getIntExtra("contactId",0);
        personName= i.getStringExtra("personName");
        companyName= i.getStringExtra("companyName");
        token= i.getStringExtra("token");
        phoneNumber= i.getStringExtra("phoneNumber");
        sp = getApplicationContext().getSharedPreferences("GTR", MODE_PRIVATE);
        sped = sp.edit();

    }

    private void setInitialize() {
        companyNameShow = findViewById(R.id.companyNameShow);
        companyNameShow.setText(companyName);
        personNameShow = findViewById(R.id.personNameShow);
        personNameShow.setText(personName);
        personPhoneNumberShow = findViewById(R.id.personPhoneNumberShow);
        personPhoneNumberShow.setText(phoneNumber);

        callToPerson = findViewById(R.id.callToPerson);
        callToPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPostRequest();
            }
        });
        //RecyclerView in Save Contact List
        callHistoryRecyclerAdapter = findViewById(R.id.callHistoryRecyclerAdapter);
        callHistoryRecyclerAdapter.setHasFixedSize(true);
        callHistoryRecyclerAdapter.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    private void signout() {
        sped.putString("UserName", "");
        sped.putString("Password", "");
        sped.putString("Remember", "");
        sped.commit();

        Toast.makeText(CallHistoryActivity.this,"Logout Successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(CallHistoryActivity.this, LoginActivity.class));
        finish();
    }

    private void getCallHistory() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<CallHistoryModel>> call= api.getCallHistoryList(token,empId,contactId);
        call.enqueue(new Callback<List<CallHistoryModel>>() {
            @Override
            public void onResponse(Call<List<CallHistoryModel>> call, Response<List<CallHistoryModel>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(CallHistoryActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<CallHistoryModel> callHistoryModels = response.body();

                for (CallHistoryModel ourDataSet : callHistoryModels) {

                    saveShowIt(response.body(),token);
                }
            }

            @Override
            public void onFailure(Call<List<CallHistoryModel>> call, Throwable t) {
                Toast.makeText(CallHistoryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void saveShowIt(List<CallHistoryModel> list, String token) {
        CallHistoryAdapter callHistoryAdapter = new CallHistoryAdapter(list,getApplicationContext(), this.token);

        callHistoryRecyclerAdapter.setAdapter(callHistoryAdapter);
    }

    private void callPostRequest() {
       /* int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);*/
         int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
           /* ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);*/
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            callPhone();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                }
            }
        }
    }

    private void callPhone() {
       /* if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},1);*/

      /*  Intent i = new Intent(this, CallReceiver.class);
        startActivity(i);
*/

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            startActivity(intent);
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setCancelable(false);
            dialog.setTitle("Customer!")

                    .setMessage("Receive your Call?")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            dialoginterface.cancel();
                        }})
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            DiallerPost();
                        }
                    }).show();


            /*Intent intent1 = new Intent(this,CallReceiver.class);
            intent1.putExtra("phoneNumer", phoneNumber);
            intent1.putExtra("empId", empId);
            intent1.putExtra("contactId", contactId);*/

      /* secondOne();*/

            /*Intent sendIntent = new Intent(Intent.ACTION_CALL);
            sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sendIntent.setData(Uri.parse("sms:" + phoneNumber));
            sendIntent.putExtra("phoneNumer", phoneNumber);
            sendIntent.putExtra("empId", empId);
            sendIntent.putExtra("contactId", contactId);*/
            /*startActivityForResult(intent, 1212);*/

        }


    }

    private void DiallerPost() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<String> call = api.getDialerShow(token,empId,contactId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                   /* Toast.makeText(CallHistoryActivity.this, "Success", Toast.LENGTH_SHORT).show();*/
                    newCallPost();
                }else{
                    Toast.makeText(CallHistoryActivity.this, "Some Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CallHistoryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void newCallPost() {
       /* Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        Api api = retrofit.create(Api.class);
        JsonObject jsonObjectFinal = new JsonObject();

        JSONObject jsonObjectName = new JSONObject();
        try {
            jsonObjectName.put("TodayDiscussion", name);
            jsonObjectName.put("NextDiscussion", fieldId);
            jsonObjectName.put("MeetingReason", designation);
            jsonObjectName.put("ReminderDate", email);
            jsonObjectName.put("phoneNumber", phoneNumber);
            jsonObjectName.put("image", image);
            jsonObjectName.put("addedByUserId", empId);


        } catch (JSONException e) {
            e.printStackTrace();
        }*/
       Intent intent = new Intent(this, CreateEvent.class);
       intent.putExtra("empId",empId);
       intent.putExtra("contactId",contactId);
       intent.putExtra("personName",personName);
       intent.putExtra("companyName",companyName);
       intent.putExtra("token",token);
       intent.putExtra("phoneNumber",phoneNumber);
       startActivity(intent);

    }


}