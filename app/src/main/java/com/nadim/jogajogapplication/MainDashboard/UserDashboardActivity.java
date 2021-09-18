package com.nadim.jogajogapplication.MainDashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import androidx.appcompat.widget.SearchView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.nadim.jogajogapplication.Api;
import com.nadim.jogajogapplication.CustomerContactActivity.CustomerActivity;
import com.nadim.jogajogapplication.Login.LoginActivity;
import com.nadim.jogajogapplication.MainDashboard.CallLogData.CallLogActivity;
import com.nadim.jogajogapplication.MainDashboard.Models.NotificationModel;
import com.nadim.jogajogapplication.MainDashboard.NewContact.NewContactActivity;
import com.nadim.jogajogapplication.MainDashboard.NotificationAdapter.NotificationCallAdapter;
import com.nadim.jogajogapplication.MainDashboard.OldContactAdapter.OldDataAdapter;
import com.nadim.jogajogapplication.MainDashboard.Models.OldContactShow;
import com.nadim.jogajogapplication.MainDashboard.Models.SaveContactShow;
import com.nadim.jogajogapplication.MainDashboard.SaveContactAdapter.SaveDataAdapter;
import com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.DemoTest2Activity;
import com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.DemoTestActivity;
import com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.SupportActivity;
import com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.SupportHistoryActivity;
import com.nadim.jogajogapplication.R;
import com.nadim.jogajogapplication.RemainderForPersonal.Adapter.views.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import softpro.naseemali.ShapedNavigationView;
import softpro.naseemali.ShapedViewSettings;

public class UserDashboardActivity extends AppCompatActivity {

    private ShapedNavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    private Integer empId;
    private String empCode, empName, desigName, empImage,token;


    SharedPreferences sp;
    SharedPreferences.Editor sped;
    private RecyclerView recyclerOldContactView;
    private RecyclerView recyclerNotificationView;
    private RecyclerView recyclerSaveContactView;

    //Click RecyclerView Adapter
    private SaveDataAdapter.RecyclerViewClickListener listener;
    private NotificationCallAdapter.RecyclerViewClickListener notificationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        getInitialize();
        setInitialize();
        notificationList();
        oldContactShow();
        saveContactShow();
    }

    private void getInitialize() {
        Intent i=getIntent();
        empId= i.getIntExtra("empId",0);
        empCode= i.getStringExtra("empCode");
        empName= i.getStringExtra("empName");
        desigName= i.getStringExtra("desigName");
        empImage= i.getStringExtra("empImage");
        token= i.getStringExtra("token");

        sp = getApplicationContext().getSharedPreferences("GTR", MODE_PRIVATE);
        sped = sp.edit();

        /*Toast.makeText(this, token, Toast.LENGTH_SHORT).show();*/
    }
    private void setInitialize() {

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nav = findViewById(R.id.navmenu);
        nav.getSettings().setShapeType(ShapedViewSettings.ROUNDED_RECT);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.menu_dashboard :
                        /*Toast.makeText(getApplicationContext(),"Dashboard Panel is Open",Toast.LENGTH_LONG).show();*/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_newContact :
                        /*  Toast.makeText(getApplicationContext(),"Payment Panel is Open",Toast.LENGTH_LONG).show();*/
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(UserDashboardActivity.this, NewContactActivity.class)
                                .putExtra("empId", empId)
                                .putExtra("empCode", empCode)
                                .putExtra("empName", empName)
                                .putExtra("desigName", desigName)
                                .putExtra("token", token)
                                .putExtra("empImage", empImage));
                        break;
                    case R.id.phoneBookContact :

                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(UserDashboardActivity.this, CallLogActivity.class)
                                .putExtra("empId", empId)
                                .putExtra("empCode", empCode)
                                .putExtra("empName", empName)
                                .putExtra("desigName", desigName)
                                .putExtra("empImage", empImage)
                                .putExtra("token", token));
                        break;

                    case R.id.createReminder :
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(UserDashboardActivity.this, MainActivity.class)
                                .putExtra("empId", empId)
                                .putExtra("empCode", empCode)
                                .putExtra("custName", empName)
                                .putExtra("desigName", desigName)
                                .putExtra("empImage", empImage)
                                .putExtra("token", token));
                        break;

                    case R.id.services_report :
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(UserDashboardActivity.this, SupportHistoryActivity.class)
                                .putExtra("empId", empId)
                                .putExtra("empCode", empCode)
                                .putExtra("custName", empName)
                                .putExtra("desigName", desigName)
                                .putExtra("empImage", empImage)
                                .putExtra("token", token));
                        break;


                    case R.id.logOut :
                        /*Toast.makeText(getApplicationContext(),"Log Out",Toast.LENGTH_LONG).show();*/
                        signout();
                       /* drawerLayout.closeDrawer(GravityCompat.START);*/
                        break;
                }

                return true;
            }
        });

        //RecyclerView in Notification List
        recyclerNotificationView = findViewById(R.id.notificationRecyclerView);
        recyclerNotificationView.setHasFixedSize(true);
        /* recyclerOldContactView.setLayoutManager(new LinearLayoutManager(this));*/
        recyclerNotificationView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        //RecyclerView in Old Contact List
        recyclerOldContactView = findViewById(R.id.oldContactRecyclerView);
        recyclerOldContactView.setHasFixedSize(true);
        /* recyclerOldContactView.setLayoutManager(new LinearLayoutManager(this));*/
        recyclerOldContactView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        //RecyclerView in Save Contact List
        recyclerSaveContactView = findViewById(R.id.saveContactRecyclerView);
        recyclerSaveContactView.setHasFixedSize(true);
        recyclerSaveContactView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }
    private void signout() {
        sped.putString("UserName", "");
        sped.putString("Password", "");
        sped.putString("Remember", "");
        sped.commit();

        Toast.makeText(UserDashboardActivity.this,"Logout Successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(UserDashboardActivity.this, LoginActivity.class));
        finish();
    }

    /// Notification List
    private void notificationList() {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d'T'hh:mm");
        String dateToStr = format.format(today);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<NotificationModel>> call = api.getNotificationList(token,empId,dateToStr);
        call.enqueue(new Callback<List<NotificationModel>>() {
            @Override
            public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {
                if (response.isSuccessful()){
                    List<NotificationModel> notificationModels = response.body();

                    for (NotificationModel ourDataSet : notificationModels) {

                        notificationShowIt(response.body());
                    }

                }else{
                    Toast.makeText(UserDashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NotificationModel>> call, Throwable t) {
                Toast.makeText(UserDashboardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void notificationShowIt(List<NotificationModel> list) {
        setNotificationOnClickListener(list);
        NotificationCallAdapter notificationCallAdapter = new NotificationCallAdapter(list,getApplicationContext(),notificationListener,token);

        recyclerNotificationView.setAdapter(notificationCallAdapter);
    }


    ///Old contact Show
    private void oldContactShow() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<OldContactShow>> call= api.getOldContactList(token,empId);
        call.enqueue(new Callback<List<OldContactShow>>() {
            @Override
            public void onResponse(Call<List<OldContactShow>> call, Response<List<OldContactShow>> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(UserDashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<OldContactShow> oldContactShows = response.body();


                for (OldContactShow ourDataSet : oldContactShows) {

                    oldShowIt(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<OldContactShow>> call, Throwable t) {


                Toast.makeText(UserDashboardActivity.this, "System Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void oldShowIt(List<OldContactShow> list) {

        OldDataAdapter oldDataAdapter = new OldDataAdapter(list,getApplicationContext());

        recyclerOldContactView.setAdapter(oldDataAdapter);
    }

    ///Save Contact  List Show
    private void saveContactShow() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        try {
            Api api = retrofit.create(Api.class);
            Call<List<SaveContactShow>> call= api.getSaveContactList(token,empId);
            call.enqueue(new Callback<List<SaveContactShow>>() {
                @Override
                public void onResponse(Call<List<SaveContactShow>> call, Response<List<SaveContactShow>> response) {

                    if (!response.isSuccessful()){
                        Toast.makeText(UserDashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    List<SaveContactShow> saveContactShows = response.body();

                    saveShowIt(response.body(),token);

                }

                @Override
                public void onFailure(Call<List<SaveContactShow>> call, Throwable t) {
                    Toast.makeText(UserDashboardActivity.this, "System Failure", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (NumberFormatException e){
           e.getMessage();
        }

        /*com.google.gson.JsonSyntaxException:java.lang.NumberFormatException: For input string: "C0002"*/

    }


    private void saveShowIt(List<SaveContactShow> list, String token) {
        setOnClickListener(list);
        SaveDataAdapter saveDataAdapter = new SaveDataAdapter(list,getApplicationContext(),listener,token);

        recyclerSaveContactView.setAdapter(saveDataAdapter);
    }



    private void setOnClickListener(final List<SaveContactShow> list) {
        listener = new SaveDataAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                Toast.makeText(UserDashboardActivity.this, "Save Listener", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);

                //////////////User Info details
                intent.putExtra("empCode", empCode);
                intent.putExtra("empName", empName);
                intent.putExtra("desigName", desigName);
                intent.putExtra("token", token);
                intent.putExtra("empImage", empImage);


                ////////// Save Customer/Contact Model Info
                intent.putExtra("contactId",list.get(position).getContactId());
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("designation",list.get(position).getDesignation());
                intent.putExtra("email",list.get(position).getEmail());
                intent.putExtra("phoneNumber",list.get(position).getPhoneNumber());
                intent.putExtra("dateAdded",list.get(position).getDateAdded());
                intent.putExtra("dateUpdated",list.get(position).getDateUpdated());
                intent.putExtra("addedByUserId",list.get(position).getAddedByUserId());
                intent.putExtra("updateByUserId",list.get(position).getUpdateByUserId());
                intent.putExtra("custId",list.get(position).getCustId());
                intent.putExtra("custName",list.get(position).getCustomer().getCustName());
                intent.putExtra("srtName",list.get(position).getCustomer().getSrtName());
                startActivity(intent);
            }
        };
    }

    private void setNotificationOnClickListener(final List<NotificationModel> list){
        notificationListener = new NotificationCallAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

             /*   Toast.makeText(UserDashboardActivity.this, "Notification Listener", Toast.LENGTH_SHORT).show();

                String todayDiscussion = list.get(position).getTodayDiscussion();

                Intent intent = new Intent(getApplicationContext(), NotificationMessage.class);
                intent.putExtra("todayDiscussion",todayDiscussion);
                intent.putExtra("nextDiscussion",list.get(position).getNextDiscussion());
                intent.putExtra("meetingReason",list.get(position).getMeetingReason());
                intent.putExtra("reminderDate",list.get(position).getReminderDate());
                intent.putExtra("empId",list.get(position).getEmpId());

                intent.putExtra("contactId",list.get(position).getContact().getContactId());
                intent.putExtra("phoneNumber",list.get(position).getContact().getPhoneNumber());

                intent.putExtra("name",list.get(position).getContact().getName());
                startActivity(intent);*/
            }
        };
    }

    public void searchViewContact(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        try {
            Api api = retrofit.create(Api.class);
            Call<List<SaveContactShow>> call= api.getSaveContactList(token,empId);
            call.enqueue(new Callback<List<SaveContactShow>>() {
                @Override
                public void onResponse(Call<List<SaveContactShow>> call, Response<List<SaveContactShow>> response) {

                    if (!response.isSuccessful()){
                        Toast.makeText(UserDashboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    List<SaveContactShow> saveContactShows = response.body();

                    saveShowIt(response.body(),token);

                }

                @Override
                public void onFailure(Call<List<SaveContactShow>> call, Throwable t) {
                    Toast.makeText(UserDashboardActivity.this, "System Failure", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (NumberFormatException e){
            e.getMessage();
        }
    }

}