package com.nadim.jogajogapplication.CustomerCall;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nadim.jogajogapplication.Api;
import com.nadim.jogajogapplication.CustomerCall.Database.DatabaseClass;
import com.nadim.jogajogapplication.CustomerCall.Database.EntityClass;
import com.nadim.jogajogapplication.CustomerCall.Others.AlarmBrodcast;
import com.nadim.jogajogapplication.CustomerCall.Others.CreateEvent;
import com.nadim.jogajogapplication.Login.LoginActivity;
import com.nadim.jogajogapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CallHistoryEditActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Button btn_time, btn_date, btn_done;
    ImageView btn_record;
    EditText editext_message,callSummary;
    String timeTonotify;
    DatabaseClass databaseClass;

    private TextView companyNameShow,personNameShow,personPhoneNumberShow,callToPerson;
    private String comapnyPhoto,personName,companyName,phoneNumber,token;
    private Integer contactId, empId,callHistoryId;
    SharedPreferences sp;
    SharedPreferences.Editor sped;
    Spinner spinner;
    String meetingReason;
    String dateTime;
    String callToSummary,text,value;


    String outputDateStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_history_edit);

        btn_record = findViewById(R.id.btn_record);
        btn_time = findViewById(R.id.btn_time);
        btn_date = findViewById(R.id.btn_date);
        btn_done = findViewById(R.id.btn_done);
        editext_message = findViewById(R.id.editext_message);
        callSummary = findViewById(R.id.callSummary);
        btn_record.setOnClickListener(this);
        btn_time.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        btn_done.setOnClickListener(this);
        databaseClass = DatabaseClass.getDatabase(getApplicationContext());

        Intent i=getIntent();
        empId= i.getIntExtra("empId",0);
        contactId= i.getIntExtra("contactId",0);
        callHistoryId= i.getIntExtra("callHistoryId",0);
        personName= i.getStringExtra("personName");
        companyName= i.getStringExtra("companyName");
        token= i.getStringExtra("token");
        phoneNumber= i.getStringExtra("phoneNumber");

        sp = getApplicationContext().getSharedPreferences("GTR", MODE_PRIVATE);
        sped = sp.edit();

        spinner = findViewById(R.id.input_reasons);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.reason, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        meetingReason = parent.getItemAtPosition(position).toString();
      /*  Toast.makeText(parent.getContext(), meetingReason, Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {
        if (view == btn_record) {
            recordSpeech();
        } else if (view == btn_time) {
            selectTime();
        } else if (view == btn_date) {
            selectDate();
        } else {
            submit();
        }
    }

    private void submit() {
        text = editext_message.getText().toString().trim();
        callToSummary = callSummary.getText().toString().trim();
        if (text.isEmpty()||callToSummary.isEmpty()) {
            Toast.makeText(this, "Please record the Field", Toast.LENGTH_SHORT).show();
        } else {
            if (btn_time.getText().toString().equals("Select Time") || btn_date.getText().toString().equals("Select date")) {
                Toast.makeText(this, "Please select date and time", Toast.LENGTH_SHORT).show();
            } else {
                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), AlarmBrodcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
                am.cancel(pendingIntent);

                value = (editext_message.getText().toString().trim());
                final String date = (btn_date.getText().toString().trim());
                final String time = (btn_time.getText().toString().trim());


                try {
                    SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm a");
                    Date dates = inFormat.parse(time);
                    SimpleDateFormat outFormat = new SimpleDateFormat("hh:mm:ss");
                    String goal = outFormat.format(dates);

                 /*   Toast.makeText(this, goal, Toast.LENGTH_SHORT).show();*/

                    dateTime = date+"T"+goal;

                    EntityClass entityClass = new EntityClass();
                    entityClass.setEventdate(date);
                    entityClass.setEventname(value);
                    entityClass.setEventtime(time);
                    databaseClass.EventDao().insertAll(entityClass);

                    setAlarm(value, date, time);


                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    private void selectTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeTonotify = i + ":" + i1;
                btn_time.setText(FormatTime(i, i1));
            }
        }, hour, minute, false);
        timePickerDialog.show();

    }

    private void selectDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                btn_date.setText(day + "-" + (month + 1) + "-" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public String FormatTime(int hour, int minute) {

        String time;
        time = "";
        String formattedMinute;

        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }


        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }


        return time;
    }


    private void recordSpeech() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        try {

            startActivityForResult(intent, 1);
        } catch (Exception e) {
            Toast.makeText(this, "Your device does not support Speech recognizer", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                editext_message.setText(text.get(0));
            }
        }

    }

    private void setAlarm(String text, String date, String time) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmBrodcast.class);
        intent.putExtra("event", text);
        intent.putExtra("time", date);
        intent.putExtra("date", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String dateandtime = date + " " + timeTonotify;
        String dateandtimePost = date + "T" + timeTonotify;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        DateFormat postFormatter = new SimpleDateFormat("yyyy-M-d'T'hh:mm");

        try {
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
//////////////////////////
            Date datess = formatter.parse(dateandtime);
            outputDateStr = postFormatter.format(datess);
           /* Toast.makeText(this, outputDateStr, Toast.LENGTH_SHORT).show();*/
            post();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        finish();

    }

    private void post() {
        Gson gson = new GsonBuilder()
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
            jsonObjectName.put("CallHistoryId", callHistoryId);
            jsonObjectName.put("TodayDiscussion", callToSummary);
            jsonObjectName.put("NextDiscussion", value);
            jsonObjectName.put("MeetingReason", meetingReason);
            jsonObjectName.put("ReminderDate", outputDateStr);
            jsonObjectName.put("ContactId", contactId);
            jsonObjectName.put("EmpId", empId);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonParser jsonParser = new JsonParser();
        jsonObjectFinal = (JsonObject) jsonParser.parse(jsonObjectName.toString());
        Call<String> call = api.createNewCall(token,jsonObjectFinal);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    /*Toast.makeText(CallHistoryEditActivity.this, "Success", Toast.LENGTH_SHORT).show();*/
                    Toast.makeText(CallHistoryEditActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CallHistoryEditActivity.this, LoginActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(CallHistoryEditActivity.this, "Bad Request", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CallHistoryEditActivity.this, "False", Toast.LENGTH_SHORT).show();
            }
        });
    }
}