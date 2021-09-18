package com.nadim.jogajogapplication.CustomerCall.Others;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nadim.jogajogapplication.R;

public class NotificationMessage extends AppCompatActivity {
    TextView textView,tv_date,personNameShow,personPhoneNumberShow,callToPerson,summary,nextStep,reason,anyReminder;
    String todayDiscussion,nextDiscussion,meetingReason,reminderDate,phoneNumber,name;
    Integer empId,contactId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_message);
        textView = findViewById(R.id.tv_message);
        tv_date = findViewById(R.id.tv_date);

        Bundle bundle = getIntent().getExtras();
        textView.setText(bundle.getString("message"));
        tv_date.setText(bundle.getString("date"));

      /*  personNameShow = findViewById(R.id.personNameShow);
        personPhoneNumberShow = findViewById(R.id.personPhoneNumberShow);
        *//*callToPerson = findViewById(R.id.callToPerson);*//*
        summary = findViewById(R.id.summary);
        nextStep = findViewById(R.id.nextStep);
        reason = findViewById(R.id.reason);
        anyReminder = findViewById(R.id.anyReminder);


        Intent i=getIntent();
        todayDiscussion= i.getStringExtra("todayDiscussion");
        nextDiscussion= i.getStringExtra("nextDiscussion");
        meetingReason= i.getStringExtra("meetingReason");
        reminderDate= i.getStringExtra("meetingReason");
        phoneNumber= i.getStringExtra("phoneNumber");
        name= i.getStringExtra("name");
        empId= i.getIntExtra("empId",0);
        contactId= i.getIntExtra("contactId",0);

        personNameShow.setText(name);
        personPhoneNumberShow.setText(phoneNumber);
        summary.setText(todayDiscussion);
        nextStep.setText(nextDiscussion);
        reason.setText(meetingReason);
        anyReminder.setText(reminderDate);*/



    }
}
