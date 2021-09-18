package com.nadim.jogajogapplication.CustomerContactActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateOpportunityActivity extends AppCompatActivity {

    private Integer contactId,empId;
    private String custName,name,phoneNumber,token;

    private TextView companyNameTxt,accountName;
    private Spinner stageSpinner,categorySpinner,productSpinner;
    private EditText remarksEditText,opportunityName;
    private Button btnDone;

    private String stageURL = "https://www.pqstec.com/GTRJOGAJOG/API/Stage";
    private String categoryURL = "https://www.pqstec.com/GTRJOGAJOG/API/Category";
    private String productURL = "https://www.pqstec.com/GTRJOGAJOG/API/GTProduct";

    ArrayList<String> stageArrayName;
    ArrayList<Integer> stageFieldArrayId;
    Integer stageField_Id;
    Integer stageFieldId;

    ArrayList<String> categoryArrayName;
    ArrayList<Integer> categoryFieldArrayId;
    Integer categoryField_Id;
    Integer categoryFieldId;

    ArrayList<String> productsArrayName;
    ArrayList<Integer> productFieldArrayId;
    Integer productField_Id;
    Integer productFieldId;

    String empCode,empName,desigName,empImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_opportunity);

        initialize();


    }

    private void initialize() {

        companyNameTxt = findViewById(R.id.companyNameOpportunity);
        accountName = findViewById(R.id.accountName);
        stageSpinner = findViewById(R.id.stageSpinner);
        categorySpinner = findViewById(R.id.categorySpinner);
        productSpinner = findViewById(R.id.productSpinner);
        remarksEditText = findViewById(R.id.remarksEditText);
        opportunityName = findViewById(R.id.opportunityName);
        btnDone = findViewById(R.id.btnDone);

        Intent i=getIntent();
        contactId= i.getIntExtra("contactId",0);
        custName= i.getStringExtra("companyName");
        name= i.getStringExtra("personName");
        phoneNumber= i.getStringExtra("phoneNumber");


        ////From UserDashboard
        empId= i.getIntExtra("empId",0);
        empCode= i.getStringExtra("empCode");
        empName= i.getStringExtra("empName");
        desigName= i.getStringExtra("desigName");
        empImage= i.getStringExtra("empImage");
        token= i.getStringExtra("token");


        /////show text view
        companyNameTxt.setText(custName);
        accountName.setText(name);

        stageArrayName =new ArrayList<>();
        stageFieldArrayId=new ArrayList<>();

        categoryArrayName =new ArrayList<>();
        categoryFieldArrayId=new ArrayList<>();

        productsArrayName =new ArrayList<>();
        productFieldArrayId=new ArrayList<>();

        ///Stage show
        fieldStageList(stageURL);
        stageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name= stageSpinner.getItemAtPosition(stageSpinner.getSelectedItemPosition()).toString();
                /*String id= spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();*/
                stageFieldId = stageFieldArrayId.get(i);
                /*Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();*/
                /*Toast.makeText(DashboardActivity.this, fieldId, Toast.LENGTH_SHORT).show();*/
                /*listShow();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        ////Category Show
        fieldCategoryList(categoryURL);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                String name = categorySpinner.getItemAtPosition(categorySpinner.getSelectedItemPosition()).toString();

                categoryFieldId = categoryFieldArrayId.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////Products show
        fieldProductList(productURL);
        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                String name = productSpinner.getItemAtPosition(productSpinner.getSelectedItemPosition()).toString();

                productFieldId = productFieldArrayId.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Post Button
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRequest();
            }
        });
    }

    private void fieldStageList(String stageURL) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, stageURL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("stages");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        String stage=jsonObject1.getString("stageName");
                        stageField_Id = Integer.valueOf(jsonObject1.getString("stageId"));

                        stageArrayName.add(stage);
                        stageFieldArrayId.add(stageField_Id);
                    }

                    stageSpinner.setAdapter(new ArrayAdapter<String>(CreateOpportunityActivity.this, android.R.layout.simple_spinner_dropdown_item, stageArrayName));
                }catch (JSONException e){
                    e.printStackTrace();}
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

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

    private void fieldCategoryList(String categoryURL) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, categoryURL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("categorys");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        String category =jsonObject1.getString("categoryName");
                        categoryField_Id= Integer.valueOf(jsonObject1.getString("categoryId"));

                        categoryArrayName.add(category);
                        categoryFieldArrayId.add(categoryField_Id);
                    }

                    categorySpinner.setAdapter(new ArrayAdapter<String>(CreateOpportunityActivity.this, android.R.layout.simple_spinner_dropdown_item, categoryArrayName));
                }catch (JSONException e){
                    e.printStackTrace();}
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> params = new HashMap<String, String>();
                HashMap<String, String> headers = new HashMap<String, String>();
                params.put("Authorization", token);
                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
    private void fieldProductList(String productURL) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, productURL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("gtProducts");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        String product =jsonObject1.getString("productName");
                        productField_Id= Integer.valueOf(jsonObject1.getString("gtProductId"));

                        productsArrayName.add(product);
                        productFieldArrayId.add(productField_Id);
                    }

                    productSpinner.setAdapter(new ArrayAdapter<String>(CreateOpportunityActivity.this, android.R.layout.simple_spinner_dropdown_item, productsArrayName));
                }catch (JSONException e){
                    e.printStackTrace();}
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> params = new HashMap<String, String>();
                HashMap<String, String> headers = new HashMap<String, String>();
                params.put("Authorization", token);
                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
    private void postRequest() {
        String remarks = remarksEditText.getText().toString();
        String businessName = opportunityName.getText().toString();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        Api api = retrofit.create(Api.class);

        JsonObject bodyParameters = new JsonObject();
        bodyParameters.addProperty("ContactId",contactId);
        bodyParameters.addProperty("AddedByUserId",empId);
        JsonArray OpportunityDetailses = new JsonArray();
        JsonObject OpportunityDetails= new JsonObject();
        OpportunityDetails.addProperty("StageId",stageFieldId);
        OpportunityDetails.addProperty("Remarks",remarks);
        OpportunityDetails.addProperty("FileInfo","null");

        JsonArray Products = new JsonArray();
        JsonObject gtProduct= new JsonObject();
        gtProduct.addProperty("GTProductId",productFieldId);
        Products.add(gtProduct);
        OpportunityDetails.add("Products",Products);
        OpportunityDetailses.add(OpportunityDetails);

        bodyParameters.add("OpportunityDetailses",OpportunityDetailses);
        bodyParameters.addProperty("remarks",businessName);

        Call<String> call = api.getCreateOpportunity(token,bodyParameters);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Toast.makeText(CreateOpportunityActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateOpportunityActivity.this, UserDashboardActivity.class)
                            .putExtra("empId", empId)
                            .putExtra("empCode", empCode)
                            .putExtra("custName", empName)
                            .putExtra("desigName", desigName)
                            .putExtra("empImage", empImage)
                            .putExtra("token", token));

                }else{
                    Toast.makeText(CreateOpportunityActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CreateOpportunityActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}