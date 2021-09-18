package com.nadim.jogajogapplication.MainDashboard.CallLogData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.nadim.jogajogapplication.R;
import com.nadim.jogajogapplication.databinding.ActivityCallLogBinding;

import java.util.ArrayList;
import java.util.List;

public class CallLogActivity extends AppCompatActivity {

    ActivityCallLogBinding binding;
    CallLogViewPagerAdapter adapter;

    private String empCode,empName,desigName,empImage,token;
    Integer empId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_call_log);

        Intent i=getIntent();
        empId= i.getIntExtra("empId",0);
        empCode= i.getStringExtra("empCode");
        empName= i.getStringExtra("empName");
        desigName= i.getStringExtra("desigName");
        empImage= i.getStringExtra("empImage");
        token= i.getStringExtra("token");

        initComponents();
    }

    private void initComponents(){
        setSupportActionBar(binding.toolbar);
        if(getRuntimePermission())
            setUpViewPager();
    }

    private void setUpViewPager(){
        binding.tabs.setupWithViewPager(binding.contentView.viewpager);
        adapter = new CallLogViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putString("empId", String.valueOf(empId));
        bundle.putString("empCode", empCode);
        bundle.putString("empName", empName);
        bundle.putString("desigName", desigName);
        bundle.putString("empImage", empImage);
        bundle.putString("token", token);


        AllCallLogFragment fragment1 = new AllCallLogFragment();
        fragment1.setArguments(bundle);
        MissedCallsFragment mFragment = new MissedCallsFragment();
        mFragment.setArguments(bundle);
        IncomingCallsFragment iFragment = new IncomingCallsFragment();
        iFragment.setArguments(bundle);
        OutgoingCallsFragment oFragment = new OutgoingCallsFragment();
        oFragment.setArguments(bundle);

        adapter.addFragment("All Calls",fragment1);
        adapter.addFragment("Outgoing", oFragment);
        adapter.addFragment("Incoming", iFragment);
        adapter.addFragment("Missed", mFragment);
        binding.contentView.viewpager.setAdapter(adapter);
    }

    class CallLogViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public CallLogViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(String title, Fragment fragment){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private boolean getRuntimePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CALL_LOG},123);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 123){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpViewPager();
            }else{
                finish();
            }
        }
    }
}