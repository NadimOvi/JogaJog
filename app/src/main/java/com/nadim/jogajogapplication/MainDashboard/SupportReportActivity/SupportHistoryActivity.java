package com.nadim.jogajogapplication.MainDashboard.SupportReportActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nadim.jogajogapplication.Api;
import com.nadim.jogajogapplication.MainDashboard.Models.NotificationModel;
import com.nadim.jogajogapplication.MainDashboard.SaveContactAdapter.SaveDataAdapter;
import com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.Models.MwlDetailsModel;
import com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.Models.MwlGetByDate;
import com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.Models.MwlMainModel;
import com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.SupportAdapter.Adapter;
import com.nadim.jogajogapplication.MainDashboard.UserDashboardActivity;
import com.nadim.jogajogapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupportHistoryActivity extends AppCompatActivity {
    private String token,empCode,empName,desigName,empImage,clientName;
    private Integer empId;
    SharedPreferences sp;
    SharedPreferences.Editor sped;
    DatePickerDialog picker;
    String showFromDate,showToDate, filterShowFromDate, filterShowToDate;
    RecyclerView dateRecyclerView;
    Spinner clientSpinner;

    TextView dateTimeShow;
    EditText fromDateShow,toDateShow, filterFromEditTxt, filterToEditTxt;
    Button supportReport, selectButton, secondButton;
    LinearLayout fromDateButton, toDateButton, filterFromDate, filterToDate;
    ProgressDialog progressDialog;
    String currentDateandTime,beforedate;
    String URL = "https://www.pqstec.com/GTRJOGAJOG/API/Customer";
    Integer fieldId,field_Id;

    ArrayList<Integer> fieldArrayId;
    ArrayList<String> FieldName;

    ImageButton arrow;
    LinearLayout hiddenView;
    CardView cardView;
    String cusNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supports_history);
        initialise();
        dateTime();
        dateDirectPost();

        ///Spinner for Search
        fieldList(URL);
        clientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                clientName = clientSpinner.getItemAtPosition(clientSpinner.getSelectedItemPosition()).toString();
                fieldId = fieldArrayId.get(i);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void fieldList(String url) {
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    progressDialog.dismiss();
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("customers");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        cusNames=jsonObject1.getString("custName");

                        Toast.makeText(SupportHistoryActivity.this,cusNames, Toast.LENGTH_SHORT).show();

                        field_Id = Integer.valueOf(jsonObject1.getString("custId"));

                        FieldName.add(cusNames);

                        /*  saveCompanyName.setText(country);*/

                        fieldArrayId.add(field_Id);
                    }

                    clientSpinner.setAdapter(new ArrayAdapter<String>(SupportHistoryActivity.this, android.R.layout.simple_expandable_list_item_1, FieldName));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
            }
        }){

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> params = new HashMap<String, String>();
                /*HashMap<String, String> headers = new HashMap<String, String>();*/
                params.put("Authorization", token);
                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void initialise() {
        Intent i=getIntent();
        empId= i.getIntExtra("empId",0);
        empCode= i.getStringExtra("empCode");
        empName= i.getStringExtra("empName");
        desigName= i.getStringExtra("desigName");
        empImage= i.getStringExtra("empImage");
        token= i.getStringExtra("token");
        sp = getApplicationContext().getSharedPreferences("GTR", MODE_PRIVATE);
        sped = sp.edit();

        /////
        dateTimeShow = findViewById(R.id.dateTimeShow);
        supportReport = findViewById(R.id.supportReport);
        selectButton = findViewById(R.id.selectButton);
        fromDateButton = findViewById(R.id.fromDate);
        toDateButton = findViewById(R.id.toDate);
        fromDateShow = findViewById(R.id.fromDateShow);
        toDateShow = findViewById(R.id.toDateShow);
        dateRecyclerView = findViewById(R.id.dateRecyclerView);
        clientSpinner = findViewById(R.id.input_company);

        arrow = findViewById(R.id.arrow_button);
        hiddenView = findViewById(R.id.hidden_view);
        cardView = findViewById(R.id.base_cardview);
        cardView = findViewById(R.id.base_cardview);
        filterFromDate = findViewById(R.id.filterFromDate);
        filterToDate = findViewById(R.id.filterToDate);
        filterFromEditTxt = findViewById(R.id.filterFromEditTxt);
        filterToEditTxt = findViewById(R.id.filterToEditTxt);
        secondButton = findViewById(R.id.secondButton);


        progressDialog = new ProgressDialog(SupportHistoryActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);

        FieldName =new ArrayList<>();
        fieldArrayId=new ArrayList<>();

        dateRecyclerView=findViewById(R.id.dateRecyclerView);
        dateRecyclerView.setHasFixedSize(true);
        dateRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fromDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(SupportHistoryActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                showFromDate= year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                fromDateShow.setText(showFromDate);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        toDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(SupportHistoryActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                showToDate= year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                toDateShow.setText(showToDate);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showFromDate==null) {
                    fromDateShow.setError("From Date is Empty");
                    fromDateShow.requestFocus();
                    return;
                } else if (showToDate==null) {
                    toDateShow.setError("To date is Empty");
                    toDateShow.requestFocus();
                    return;

                }else{
                    datePost();
                }
            }
        });

        supportReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SupportHistoryActivity.this, SupportActivity.class)
                        .putExtra("empId", empId)
                        .putExtra("empCode", empCode)
                        .putExtra("custName", empName)
                        .putExtra("desigName", desigName)
                        .putExtra("empImage", empImage)
                        .putExtra("token", token));
            }
        });

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hiddenView.getVisibility() == View.VISIBLE) {

                    TransitionManager.beginDelayedTransition(cardView,
                            new AutoTransition());
                    hiddenView.setVisibility(View.GONE);
                    arrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }

                // If the CardView is not expanded, set its visibility
                // to visible and change the expand more icon to expand less.
                else {

                    TransitionManager.beginDelayedTransition(cardView,
                            new AutoTransition());
                    hiddenView.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }

            }
        });

        filterFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(SupportHistoryActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                filterShowFromDate= year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                filterFromEditTxt.setText(filterShowFromDate);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        filterToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(SupportHistoryActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                filterShowToDate= year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                filterToEditTxt.setText(filterShowToDate);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterShowFromDate!=null||filterShowToDate!=null){
                    filterPost();
                }else if(cusNames!=null){
                    filterPostWithCilent();
                }
            }
        });
    }


    private void dateTime() {

        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        currentDateandTime = sdf.format(new Date());
        Date cdate= null;

        try {
            cdate = sdf.parse(currentDateandTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar now2= Calendar.getInstance();
        now2.add(Calendar.DATE, -30);

        beforedate=now2.get(Calendar.YEAR)+"-"+(now2.get(Calendar.MONTH) + 1)+"-"+now2.get(Calendar.DATE);
        Date BeforeDate1= null;
        try {
            BeforeDate1 = sdf.parse(beforedate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String last = String.valueOf(cdate.compareTo(BeforeDate1));

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateformat = new SimpleDateFormat("EEE, dd MMM yyyy");
        String currentTime = dateformat.format(Calendar.getInstance().getTime());

        dateTimeShow.setText(currentTime);
        fromDateShow.setText(beforedate);
        toDateShow.setText(currentDateandTime);
    }

    private void dateDirectPost() {

        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<MwlMainModel> call = api.getMwlGetByDate(token,empId,beforedate,currentDateandTime);
        call.enqueue(new Callback<MwlMainModel>() {
            @Override
            public void onResponse(Call<MwlMainModel> call, Response<MwlMainModel> response) {
                progressDialog.dismiss();
                List<MwlGetByDate> mwlGetByDates = response.body().getMwl();
                if (mwlGetByDates!=null){
                    for (MwlGetByDate weatherModel :mwlGetByDates){
                        showIt(mwlGetByDates);
                    }


                }else{
                    progressDialog.dismiss();
                    Toast.makeText(SupportHistoryActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MwlMainModel> call, Throwable t) {

            }
        });
    }
    private void datePost() {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<MwlMainModel> call = api.getMwlGetByDate(token,empId,showFromDate,showToDate);
        call.enqueue(new Callback<MwlMainModel>() {
            @Override
            public void onResponse(Call<MwlMainModel> call, Response<MwlMainModel> response) {
                List<MwlGetByDate> mwlGetByDates = response.body().getMwl();
                if (mwlGetByDates!=null){
                    for (MwlGetByDate weatherModel :mwlGetByDates){
                        progressDialog.dismiss();
                        showIt(mwlGetByDates);
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(SupportHistoryActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<MwlMainModel> call, Throwable t) {
                Toast.makeText(SupportHistoryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showIt(List<MwlGetByDate> list) {
        Adapter adapter = new Adapter(list,getApplicationContext());
        dateRecyclerView.setAdapter(adapter);
    }

    private void filterPost() {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<MwlMainModel> call = api.getMwlDateWithCompanyName(token,empId,filterShowFromDate,filterShowToDate,cusNames);
        call.enqueue(new Callback<MwlMainModel>() {
            @Override
            public void onResponse(Call<MwlMainModel> call, Response<MwlMainModel> response) {
                progressDialog.dismiss();
                List<MwlGetByDate> mwlGetByDates = response.body().getMwl();
                if (mwlGetByDates!=null){
                    for (MwlGetByDate weatherModel :mwlGetByDates){
                        showIt(mwlGetByDates);
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(SupportHistoryActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MwlMainModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SupportHistoryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterPostWithCilent(){
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<MwlMainModel> call = api.getMwlDateWithCompanyName(token,empId,filterShowFromDate,filterShowToDate,cusNames);
        call.enqueue(new Callback<MwlMainModel>() {
            @Override
            public void onResponse(Call<MwlMainModel> call, Response<MwlMainModel> response) {
                progressDialog.dismiss();
                List<MwlGetByDate> mwlGetByDates = response.body().getMwl();
                if (mwlGetByDates!=null){
                    for (MwlGetByDate weatherModel :mwlGetByDates){
                        showIt(mwlGetByDates);
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(SupportHistoryActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MwlMainModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SupportHistoryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}