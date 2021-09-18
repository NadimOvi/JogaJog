package com.nadim.jogajogapplication.CustomerContactActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nadim.jogajogapplication.Api;
import com.nadim.jogajogapplication.CustomerCall.CallHistoryActivity;
import com.nadim.jogajogapplication.CustomerCall.Others.CreateEvent;
import com.nadim.jogajogapplication.CustomerContactActivity.Adapter.CustomerHistoryOpportunityAdapter;
import com.nadim.jogajogapplication.CustomerContactActivity.Model.CustomerHistoryOpportunityModel;
import com.nadim.jogajogapplication.MainDashboard.Models.NotificationModel;
import com.nadim.jogajogapplication.MainDashboard.Models.SaveContactShow;
import com.nadim.jogajogapplication.MainDashboard.SaveContactAdapter.SaveDataAdapter;
import com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.SupportHistoryActivity;
import com.nadim.jogajogapplication.MainDashboard.UserDashboardActivity;
import com.nadim.jogajogapplication.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerActivity extends AppCompatActivity {

    private String empCode,empName,desigName,empImage,token,custName,srtName;
    private Integer empId,contactId,updateByUserId,custId;
    private String name, designation, phoneNumber, email,dateAdded,dateUpdated;

    private TextView companyNamesTxt,contactPersonTxt,personPhoneNumberTxt;
    private ImageButton telephoneBtn;
    private Button newOpportunityBtn,makeReminderBtn,supportReport;
    private RecyclerView recyclerCustomerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        getInitialize();
        getOpportunityRecyclerView();
    }

    private void getInitialize(){
        Intent i=getIntent();
        //// User Info
        empCode= i.getStringExtra("empCode");
        empName= i.getStringExtra("empName");
        desigName= i.getStringExtra("desigName");
        empImage= i.getStringExtra("empImage");
        token= i.getStringExtra("token");

        /// Customer/Contact info
        contactId= i.getIntExtra("contactId",0);
        name= i.getStringExtra("name");
        designation= i.getStringExtra("designation");
        email= i.getStringExtra("email");
        phoneNumber= i.getStringExtra("phoneNumber");
        dateAdded= i.getStringExtra("dateAdded");
        empId= i.getIntExtra("addedByUserId",0);
        updateByUserId= i.getIntExtra("updateByUserId",0);
        dateUpdated= i.getStringExtra("dateUpdated");
        contactId= i.getIntExtra("contactId",0);
        custName= i.getStringExtra("custName");
        srtName= i.getStringExtra("srtName");

        /// Initialize
        companyNamesTxt = findViewById(R.id.companyNamesTxt);
        contactPersonTxt = findViewById(R.id.contactPersonTxt);
        personPhoneNumberTxt = findViewById(R.id.personPhoneNumberTxt);

        telephoneBtn = findViewById(R.id.telephoneBtn);
        newOpportunityBtn = findViewById(R.id.newOpportunityBtn);
        makeReminderBtn = findViewById(R.id.makeReminderBtn);
        supportReport = findViewById(R.id.supportReport);

        /// Show Customer/Contact data
        companyNamesTxt.setText(custName);
        contactPersonTxt.setText(name);
        personPhoneNumberTxt.setText(phoneNumber);

        //RecyclerView in Customer/Contact List
        recyclerCustomerView = findViewById(R.id.recyclerCustomerView);
        recyclerCustomerView.setHasFixedSize(true);
        recyclerCustomerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        // Call to the Customer/Contact
        telephoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CustomerActivity.this, CallHistoryActivity.class);
                intent.putExtra("contactId",contactId);
                intent.putExtra("empId",empId);
                intent.putExtra("companyName",custName);
                intent.putExtra("personName",name);
                intent.putExtra("phoneNumber",phoneNumber);
                intent.putExtra("token",token);
                startActivity(intent);
            }
        });

        newOpportunityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, CreateOpportunityActivity.class);
                intent.putExtra("contactId",contactId);
                intent.putExtra("empId",empId);
                intent.putExtra("companyName",custName);
                intent.putExtra("personName",name);
                intent.putExtra("phoneNumber",phoneNumber);
                intent.putExtra("token",token);
                startActivity(intent);
            }
        });

        makeReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, CreateEvent.class);
                intent.putExtra("empId",empId);
                intent.putExtra("contactId",contactId);
                intent.putExtra("personName",name);
                intent.putExtra("companyName",custName);
                intent.putExtra("token",token);
                intent.putExtra("phoneNumber",phoneNumber);
                startActivity(intent);
            }
        });

        supportReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, SupportHistoryActivity.class);
                intent.putExtra("empId",empId);
                intent.putExtra("contactId",contactId);
                intent.putExtra("personName",name);
                intent.putExtra("companyName",custName);
                intent.putExtra("token",token);
                intent.putExtra("phoneNumber",phoneNumber);
                intent.putExtra("designation",designation);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

    }

    private void getOpportunityRecyclerView() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<List<CustomerHistoryOpportunityModel>> call = api.getOpportunity(token,contactId);
        call.enqueue(new Callback<List<CustomerHistoryOpportunityModel>>() {
            @Override
            public void onResponse(Call<List<CustomerHistoryOpportunityModel>> call, Response<List<CustomerHistoryOpportunityModel>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(CustomerActivity.this, "True", Toast.LENGTH_SHORT).show();
                    List<CustomerHistoryOpportunityModel> customerHistoryOpportunityModel = response.body();

                    /*saveShowIt(response.body(),token);*/
                    if (customerHistoryOpportunityModel!=null){
                        for (CustomerHistoryOpportunityModel customerHistoryOpportunityModels : customerHistoryOpportunityModel) {

                            saveShowIt(response.body(),token);
                        }
                    }else{
                        Toast.makeText(CustomerActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    }

                    
                }else{
                    Toast.makeText(CustomerActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CustomerHistoryOpportunityModel>> call, Throwable t) {
                Toast.makeText(CustomerActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void saveShowIt(List<CustomerHistoryOpportunityModel> list, String token) {
       /* setOnClickListener(list);*/
        CustomerHistoryOpportunityAdapter customerHistoryOpportunityAdapter = new CustomerHistoryOpportunityAdapter(list,getApplicationContext(),/*listener,*/token);

        recyclerCustomerView.setAdapter(customerHistoryOpportunityAdapter);
    }

}