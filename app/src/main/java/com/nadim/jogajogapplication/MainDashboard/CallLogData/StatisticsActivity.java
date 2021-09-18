package com.nadim.jogajogapplication.MainDashboard.CallLogData;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;


import com.nadim.jogajogapplication.MainDashboard.NewContact.NewContactActivity;
import com.nadim.jogajogapplication.MainDashboard.UserDashboardActivity;
import com.nadim.jogajogapplication.R;
import com.nadim.jogajogapplication.databinding.ActivityStatisticsBinding;

public class StatisticsActivity extends AppCompatActivity {

    ActivityStatisticsBinding binding;
    Button saveButton;
    ImageButton telephoneButton;
    String number,name;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private String empCode,empName,desigName,empImage,token,empIdNew;
    Integer empId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_statistics);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i=getIntent();

        empIdNew= i.getStringExtra("empId");
        empId = Integer.valueOf(empIdNew);
        empCode= i.getStringExtra("empCode");
        empName= i.getStringExtra("empName");
        desigName= i.getStringExtra("desigName");
        empImage= i.getStringExtra("empImage");
        token= i.getStringExtra("token");

        initValues();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StatisticsActivity.this, NewContactActivity.class)
                        .putExtra("name", name)
                        .putExtra("number", number)
                        .putExtra("empId", empId)
                        .putExtra("empCode", empCode)
                        .putExtra("empName", empName)
                        .putExtra("desigName", desigName)
                        .putExtra("empImage", empImage)
                        .putExtra("token", token));
                finish();
            }
        });

        telephoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StatisticsActivity.this, "Telephone", Toast.LENGTH_SHORT).show();
                callPostRequest();
            }
        });
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
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                }
            }
        }
    }

    private void callPhone() {

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            startActivity(intent);
          /*  AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setCancelable(false);
            dialog.setTitle("Customer!")

                    .setMessage("Receive your Call?")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            dialoginterface.cancel();
                        }})
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            *//*DiallerPost();*//*
                        }
                    }).show();

*/

        }


    }



    private void initValues(){
        saveButton = findViewById(R.id.saveButton);
        telephoneButton = findViewById(R.id.telephoneButton);

        number = getIntent().getStringExtra("number");
        name = getIntent().getStringExtra("name");
        if(number == null){
            finish();
            return;
        }

        CallLogUtils callLogUtils = CallLogUtils.getInstance(getApplicationContext());

    /*   String miscall= callLogUtils.getMissCallName(number);
        Toast.makeText(this, miscall, Toast.LENGTH_SHORT).show();*/

        long data[] = callLogUtils.getAllCallState(number);
        /*String call = callLogUtils.getAllCallState(number);*/

        binding.textViewCallCountTotal.setText(data[0]+" calls");
        binding.textViewCallDurationsTotal.setText(Utils.formatSeconds(data[1]));

        data = callLogUtils.getIncomingCallState(number);
        binding.textViewCallCountIncoming.setText(data[0]+" calls");
        binding.textViewCallDurationsIncoming.setText(Utils.formatSeconds(data[1]));

        data = callLogUtils.getOutgoingCallState(number);
        binding.textViewCallCountOutgoing.setText(data[0]+" calls");
        binding.textViewCallDurationsOutgoing.setText(Utils.formatSeconds(data[1]));

        int count = callLogUtils.getMissedCallState(number);
        binding.textViewCallCountMissed.setText(count+" calls");
        binding.textViewCallDurationsMissed.setText(Utils.formatSeconds(0));

        binding.textViewNumber.setText(number);
        binding.textViewName.setText(TextUtils.isEmpty(name) ? number : name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
