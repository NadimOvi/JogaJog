package com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.SupportAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.Models.MwlDetailsModel;
import com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.Models.MwlGetByDate;
import com.nadim.jogajogapplication.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.ViewHolder> {
    private Context context;
    private List<MwlDetailsModel> list;
    public SecondAdapter(Context context, List<MwlDetailsModel> list) {
        this.context =context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.support_second_adapter_viewholder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MwlDetailsModel mwlDetailsModel = list.get(position);

        String date = mwlDetailsModel.getmWlDate();

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");


        Date dateFormet = null;
        try {
            dateFormet = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(dateFormet);


        String companyName =mwlDetailsModel.getClientName();
        String contactPerson = mwlDetailsModel.getContPerson();
        String designation = mwlDetailsModel.getContDesig();
        String mobileNo = mwlDetailsModel.getContMobile();
        String purpose = mwlDetailsModel.getPurpos();
        String type = mwlDetailsModel.getSupportNameShort();
        String description = mwlDetailsModel.getDescription();
        String nextStep = mwlDetailsModel.getNext_step();
        String location = mwlDetailsModel.getCat_Location().getLocationName();
        String with = mwlDetailsModel.getWithP();
        String startTime = mwlDetailsModel.getStTime();
        String endTime = mwlDetailsModel.getEndTime();

        holder.date.setText(outputDateStr);
        holder.location.setText(location);
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
        holder.endTime.setText(endTime);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,companyName,contactPerson,designation,mobileNo,type,purpose,description,nextStep,location,with,startTime,endTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            companyName = itemView.findViewById(R.id.companyName);
            contactPerson = itemView.findViewById(R.id.contactPerson);
            designation = itemView.findViewById(R.id.designation);
            mobileNo = itemView.findViewById(R.id.mobileNo);
            purpose = itemView.findViewById(R.id.purpose);
            type = itemView.findViewById(R.id.type);
            description = itemView.findViewById(R.id.description);
            nextStep = itemView.findViewById(R.id.nextStep);
            location = itemView.findViewById(R.id.location);
            with = itemView.findViewById(R.id.with);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
        }
    }

}
