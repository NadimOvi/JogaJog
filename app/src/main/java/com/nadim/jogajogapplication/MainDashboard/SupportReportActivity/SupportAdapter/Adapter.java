package com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.SupportAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.Models.MwlDetailsModel;
import com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.Models.MwlGetByDate;
import com.nadim.jogajogapplication.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<MwlGetByDate> list;
    private Context context;

    public Adapter(List<MwlGetByDate> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.support_adapter_viewholder, parent, false);
        return new Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        final MwlGetByDate mwlGetByDate = list.get(position);

        List<MwlDetailsModel> mwlDetailsModels = mwlGetByDate.getMwL_Detailses();
        SecondAdapter secondAdapter = new SecondAdapter(context,mwlDetailsModels);
        holder.secondRecyclerView.setHasFixedSize(true);
        holder.secondRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
        holder.secondRecyclerView.setAdapter(secondAdapter);



        /*String date = mwlGetByDate.getMwL_Detailses().get(position).getmWlDate();

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");


        Date dateFormet = null;
        try {
            dateFormet = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(dateFormet);


        String companyName = mwlGetByDate.getMwL_Detailses().get(position).getClientName();
        String contactPerson = mwlGetByDate.getMwL_Detailses().get(position).getContPerson();
        String designation = mwlGetByDate.getMwL_Detailses().get(position).getContDesig();
        String mobileNo = mwlGetByDate.getMwL_Detailses().get(position).getContMobile();
        String purpose = mwlGetByDate.getMwL_Detailses().get(position).getPurpos();
        String type = mwlGetByDate.getMwL_Detailses().get(position).getSupportNameShort();
        String description = mwlGetByDate.getMwL_Detailses().get(position).getDescription();
        String nextStep = mwlGetByDate.getMwL_Detailses().get(position).getNext_step();
        *//*String location = mwlGetByDate.getMwL_Detailses().get(position).;*//*
        String with = mwlGetByDate.getMwL_Detailses().get(position).getWithP();
        String startTime = mwlGetByDate.getMwL_Detailses().get(position).getStTime();
        String endTime = mwlGetByDate.getMwL_Detailses().get(position).getEndTime();

        holder.date.setText(outputDateStr);
        holder.companyName.setText(companyName);
        holder.contactPerson.setText(contactPerson);
        holder.designation.setText(designation);
        holder.mobileNo.setText(mobileNo);
        holder.purpose.setText(purpose);
        holder.description.setText(description);
        holder.nextStep.setText(nextStep);
        holder.with.setText(with);
        holder.type.setText(type);
        holder.startTime.setText(startTime);
        holder.endTime.setText(endTime);*/
        /*holder.location.setText(location);*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView secondRecyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            secondRecyclerView = itemView.findViewById(R.id.secondRecyclerView);

        }
    }
}
