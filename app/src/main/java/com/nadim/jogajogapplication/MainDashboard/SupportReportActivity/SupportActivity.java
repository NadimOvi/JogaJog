package com.nadim.jogajogapplication.MainDashboard.SupportReportActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nadim.jogajogapplication.Api;
import com.nadim.jogajogapplication.MainDashboard.NewContact.NewContactActivity;
import com.nadim.jogajogapplication.MainDashboard.UserDashboardActivity;
import com.nadim.jogajogapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupportActivity extends AppCompatActivity {

    Spinner clientSpinner,supportNameShort,purposeNameShort,companyLocation;
    EditText currentLocation, contactName,contactDesignation,contactPhone,descriptionDetails,next_Step,withPerson;
    TextView timeDateShow,inTime,outTime;
    Button saveButton;
    String currentTime,todayDate,inFormattedTime,outFormattedTime;

    private ResultReceiver resultReceiver;
    private String locationAddress;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private double latitude,longitude;

    private ProgressBar progressBar;
    private Integer empId;
    private String token,empCode,empName,desigName,empImage,clientName,companyLocationName,shortSupportName,purposeName;
    LinearLayout inTimeButton,outTimeButton;
    SharedPreferences sp;
    SharedPreferences.Editor sped;
    ProgressDialog progressDialog;

    //set Client Name Spinner
    String URL="https://www.pqstec.com/GTRJOGAJOG/API/Customer";
    String getDropDown = "https://www.pqstec.com/GTRJOGAJOG/API/MWL/GetDropdown";
    ArrayList<String> FieldName;
    ArrayList<String> locationFieldName;
    ArrayList<String> typeFieldName;
    ArrayList<String> purposeFieldName;

    ArrayList<Integer> fieldArrayId;
    ArrayList<Integer> locationfieldArrayId;
    ArrayList<Integer> typefieldArrayId;
    ArrayList<Integer> purposefieldArrayId;

    Integer field_Id,Locationfield_Id, typefield_Id, purposefield_Id;
    Integer fieldId,LocationfieldId, typefieldId, purposefieldId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        initialise();
        getCurrentLoction();
        //set Spinner for Client
        fieldList(URL);
        clientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 clientName = clientSpinner.getItemAtPosition(clientSpinner.getSelectedItemPosition()).toString();
                /*String id=   clientSpinner.getItemAtPosition(clientSpinner.getSelectedItemPosition()).toString();*/
                fieldId = fieldArrayId.get(i);
                /* Toast.makeText(getApplicationContext(),custName,Toast.LENGTH_LONG).show();*/
                /*Toast.makeText(DashboardActivity.this, fieldId, Toast.LENGTH_SHORT).show();*/
                /*listShow();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        //set Spinner for supportNameShort
        fieldSupport(getDropDown);
        companyLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                companyLocationName = companyLocation.getItemAtPosition(companyLocation.getSelectedItemPosition()).toString();
                LocationfieldId = locationfieldArrayId.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //set Spinner for type
        typeSupport(getDropDown);
        supportNameShort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                shortSupportName = supportNameShort.getItemAtPosition(supportNameShort.getSelectedItemPosition()).toString();
                typefieldId = typefieldArrayId.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //set Spinner for purpose
        purposeSupport(getDropDown);
        purposeNameShort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                purposeName = purposeNameShort.getItemAtPosition(purposeNameShort.getSelectedItemPosition()).toString();
                purposefieldId = purposefieldArrayId.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        inTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog mTimePicker;
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                mTimePicker = new TimePickerDialog(SupportActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String time = selectedHour + ":" + selectedMinute;

                        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                        Date date = null;
                        try {
                            date = fmt.parse(time );
                        } catch (ParseException e) {

                            e.printStackTrace();
                        }

                        SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                        inFormattedTime=fmtOut.format(date);

                        inTime.setText(inFormattedTime);
                    }
                }, hour, minute, false);//No 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        outTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                mTimePicker = new TimePickerDialog(SupportActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String time = selectedHour + ":" + selectedMinute;

                        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                        Date date = null;
                        try {
                            date = fmt.parse(time );
                        } catch (ParseException e) {

                            e.printStackTrace();
                        }

                        SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                        outFormattedTime=fmtOut.format(date);

                        outTime.setText(outFormattedTime);
                    }
                }, hour, minute, false);//No 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        });

    }

    private void initialise() {
        //Initialise
        timeDateShow = findViewById(R.id.timeDateShow);
        clientSpinner = findViewById(R.id.input_company);
        contactName = findViewById(R.id.contactName);
        contactDesignation = findViewById(R.id.contactDesignation);
        contactPhone = findViewById(R.id.contactPhone);
        supportNameShort = findViewById(R.id.supportNameShort);
        purposeNameShort = findViewById(R.id.purposeNameShort);
        descriptionDetails = findViewById(R.id.descriptionDetails);
        next_Step = findViewById(R.id.next_Step);
        companyLocation = findViewById(R.id.companyLocation);
        withPerson = findViewById(R.id.withPerson);
        inTime = findViewById(R.id.inTime);
        outTime = findViewById(R.id.outTime);
        inTimeButton = findViewById(R.id.inTimeButton);
        outTimeButton = findViewById(R.id.outTimeButton);
        saveButton = findViewById(R.id.saveButton);
        currentLocation = findViewById(R.id.currentLocation);

        progressBar = findViewById(R.id.progressBar);
        progressDialog = new ProgressDialog(SupportActivity.this);
        progressDialog.setMessage("Please Wait");

        Intent i=getIntent();
        empId= i.getIntExtra("empId",0);
        empCode= i.getStringExtra("empCode");
        empName= i.getStringExtra("empName");
        desigName= i.getStringExtra("desigName");
        empImage= i.getStringExtra("empImage");
        token= i.getStringExtra("token");
        sp = getApplicationContext().getSharedPreferences("GTR", MODE_PRIVATE);
        sped = sp.edit();

        FieldName =new ArrayList<>();
        fieldArrayId=new ArrayList<>();

        locationFieldName =new ArrayList<>();
        locationfieldArrayId=new ArrayList<>();

        typeFieldName =new ArrayList<>();
        typefieldArrayId=new ArrayList<>();

        purposeFieldName =new ArrayList<>();
        purposefieldArrayId=new ArrayList<>();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateformat = new SimpleDateFormat("MMMM-yyyy");
        currentTime = dateformat.format(Calendar.getInstance().getTime());
        timeDateShow.setText(currentTime);

        resultReceiver = new AddressResultReceiver(new Handler());

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sendDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        todayDate = sendDate.format(Calendar.getInstance().getTime());

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
                        String country=jsonObject1.getString("custName");

                        field_Id = Integer.valueOf(jsonObject1.getString("custId"));

                        FieldName.add(country);
                        /*  saveCompanyName.setText(country);*/
                        fieldArrayId.add(field_Id);
                    }

                    clientSpinner.setAdapter(new ArrayAdapter<String>(SupportActivity.this, android.R.layout.simple_spinner_dropdown_item, FieldName));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    private void fieldSupport(String getDropDown) {
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, getDropDown, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    progressDialog.dismiss();
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("locations");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String locations =jsonObject1.getString("locationName");

                        Locationfield_Id = Integer.valueOf(jsonObject1.getString("locationId"));

                        locationFieldName.add(locations);

                        locationfieldArrayId.add(Locationfield_Id);
                    }

                    companyLocation.setAdapter(new ArrayAdapter<String>(SupportActivity.this, android.R.layout.simple_spinner_dropdown_item, locationFieldName));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    private void typeSupport(String getDropDown) {
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, getDropDown, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    progressDialog.dismiss();
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("supportTypes");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String supportType =jsonObject1.getString("supportNameShort");

                        typefield_Id = Integer.valueOf(jsonObject1.getString("supportId"));

                        typeFieldName.add(supportType);

                        typefieldArrayId.add(typefield_Id);
                    }

                    supportNameShort.setAdapter(new ArrayAdapter<String>(SupportActivity.this, android.R.layout.simple_spinner_dropdown_item, typeFieldName));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    private void purposeSupport(String getDropDown) {
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, getDropDown, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    progressDialog.dismiss();
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("termsType");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String purposeType =jsonObject1.getString("termsType");

                        purposefield_Id = Integer.valueOf(jsonObject1.getString("termsId"));

                        purposeFieldName.add(purposeType);

                        purposefieldArrayId.add(typefield_Id);
                    }

                    purposeNameShort.setAdapter(new ArrayAdapter<String>(SupportActivity.this, android.R.layout.simple_spinner_dropdown_item, purposeFieldName));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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


    private void post(){
        String name = contactName.getText().toString();
        String designation = contactDesignation.getText().toString();
        String phone = contactPhone.getText().toString();
        String description = descriptionDetails.getText().toString();
        String withPersonName = withPerson.getText().toString();
        String nextStep = next_Step.getText().toString();
        String inTimeShow = inTime.getText().toString();
        String outTimeshow = outTime.getText().toString();

        if (name.isEmpty()) {
            contactName.setError("Please provide name");
            contactName.requestFocus();
            return;
        }else if (designation.isEmpty()) {
            contactDesignation.setError("Please provide Designation");
            contactDesignation.requestFocus();
            return;
        }else if (phone.isEmpty()) {
            contactPhone.setError("Please provide phone");
            contactPhone.requestFocus();
            return;
        }else if (description.isEmpty()) {
            descriptionDetails.setError("Please provide Description");
            descriptionDetails.requestFocus();
            return;
        }else if (nextStep.isEmpty()) {
            next_Step.setError("Please provide Next step");
            next_Step.requestFocus();
            return;
        }else if (inTimeShow.isEmpty()) {
            inTime.setError("Please provide InTime");
            inTime.requestFocus();
            return;
        }else if (outTimeshow.isEmpty()) {
            outTime.setError("Please provide OutTime");
            outTime.requestFocus();
            return;
        }else {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            Api api = retrofit.create(Api.class);
            JsonObject bodyParameters = new JsonObject();
            bodyParameters.addProperty("EmpId", empId);
            bodyParameters.addProperty("MWLMonth", currentTime);
            bodyParameters.addProperty("LUserId", empId);

            JsonArray MWL_Detailses = new JsonArray();
            JsonObject MWL_DetailsesList = new JsonObject();

            MWL_DetailsesList.addProperty("MWlDate", todayDate);
            MWL_DetailsesList.addProperty("LocationId", LocationfieldId);
            MWL_DetailsesList.addProperty("ClientId", fieldId);
            MWL_DetailsesList.addProperty("ClientName", clientName);
            MWL_DetailsesList.addProperty("ContPerson", name);
            MWL_DetailsesList.addProperty("ContDesig", designation);
            MWL_DetailsesList.addProperty("ContMobile", phone);
            MWL_DetailsesList.addProperty("SupportId", typefieldId);
            MWL_DetailsesList.addProperty("SupportNameShort", shortSupportName);
            MWL_DetailsesList.addProperty("Purpos", purposeName);
            MWL_DetailsesList.addProperty("Description", description);
            MWL_DetailsesList.addProperty("WithP", withPersonName);
            MWL_DetailsesList.addProperty("StTime", inFormattedTime);
            MWL_DetailsesList.addProperty("EndTime", outFormattedTime);
            MWL_DetailsesList.addProperty("Next_step", nextStep);

            MWL_Detailses.add(MWL_DetailsesList);
            bodyParameters.add("MWL_Detailses", MWL_Detailses);
            Call<String> call = api.createMWL(token, bodyParameters);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String status = response.body();
                    if (status.equals("true")) {
                        Toast.makeText(SupportActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SupportActivity.this, UserDashboardActivity.class)
                                .putExtra("empId", empId)
                                .putExtra("empCode", empCode)
                                .putExtra("empName", empName)
                                .putExtra("desigName", desigName)
                                .putExtra("token", token)
                                .putExtra("empImage", empImage));

                        contactName.setText("");
                        contactDesignation.setText("");
                        contactPhone.setText("");
                        descriptionDetails.setText("");
                        withPerson.setText("");
                        next_Step.setText("");
                        inTime.setText("");
                        outTime.setText("");

                    } else {
                        Toast.makeText(SupportActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(SupportActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            getCurrentLoction();
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCurrentLoction() {
        progressDialog.show();
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationServices.getFusedLocationProviderClient(SupportActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        progressDialog.dismiss();
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(SupportActivity.this)
                                .removeLocationUpdates(this);
                        if (locationRequest != null && locationResult.getLocations().size() > 0) {
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            /* textLatLong.setText(String.format("Latitude: %s\nLongitude: %s",latitude,longitude));*/

                            Location location = new Location("providerNA");
                            location.setLatitude(latitude);
                            location.setLongitude(longitude);
                            fetchAddressFromLatLong(location);
                        }else {
                            progressDialog.dismiss();
                        }
                    }
                }, Looper.myLooper());
    }
    private void fetchAddressFromLatLong (Location location){
        String latitudeShow= String.valueOf(location.getLatitude());

        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(ConstantModels.RECEIVER, resultReceiver);
        intent.putExtra(ConstantModels.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }

    private class AddressResultReceiver extends ResultReceiver {

        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            super.onReceiveResult(resultCode, resultData);
            locationAddress = resultData.getString(ConstantModels.RESULT_DATA_KEY);
            if (resultCode == ConstantModels.SUCCESS_RESULT){
                progressDialog.dismiss();

                currentLocation.setText(String.format("%s",locationAddress));

            }else {
                Toast.makeText(SupportActivity.this, resultData.getString(ConstantModels.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        }
    }
}