package com.nadim.jogajogapplication.MainDashboard.CallLogData;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nadim.jogajogapplication.R;
import com.nadim.jogajogapplication.databinding.CallLogFragmentBinding;


public class IncomingCallsFragment extends Fragment {
    CallLogFragmentBinding binding;
    CallLogAdapter adapter;
    CallLogAdapter.OnCallLogItemClickListener onItemClickListener;

    private String empCode,empName,desigName,empImage,token;
    String empId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.call_log_fragment,container,false);

        Bundle bundle = getArguments();
        empId = bundle.getString("empId");
        empCode = bundle.getString("empCode");
        empName = bundle.getString("empName");
        desigName = bundle.getString("desigName");
        empImage = bundle.getString("empImage");
        token = bundle.getString("token");

        initComponents();
        return binding.getRoot();
    }

    public void initComponents(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CallLogAdapter(getContext());
        binding.recyclerView.setAdapter(adapter);
        loadData();
    }

    public void loadData(){
        CallLogUtils callLogUtils = CallLogUtils.getInstance(getContext());
        adapter.addAllCallLog(callLogUtils.getIncommingCalls());
        adapter.notifyDataSetChanged();
        onItemClickListener = new CallLogAdapter.OnCallLogItemClickListener() {
            @Override
            public void onItemClicked(CallLogInfo callLogInfo) {
                Intent intent = new Intent(getContext(),StatisticsActivity.class);
                intent.putExtra("number",callLogInfo.getNumber());
                intent.putExtra("name",callLogInfo.getName());
                intent.putExtra("empId",empId);
                intent.putExtra("empCode",empCode);
                intent.putExtra("empName",empName);
                intent.putExtra("desigName",desigName);
                intent.putExtra("empImage",empImage);
                intent.putExtra("token",token);
                startActivity(intent);
            }
        };
        adapter.setOnItemClickListener(onItemClickListener);
    }
}