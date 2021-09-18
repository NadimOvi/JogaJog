package com.nadim.jogajogapplication.CustomerContactActivity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nadim.jogajogapplication.CustomerContactActivity.Model.CustomerHistoryOpportunityModel;
import com.nadim.jogajogapplication.CustomerContactActivity.Model.OpportunityDetails;
import com.nadim.jogajogapplication.MainDashboard.Models.SaveContactShow;
import com.nadim.jogajogapplication.MainDashboard.SaveContactAdapter.SaveDataAdapter;
import com.nadim.jogajogapplication.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomerHistoryOpportunityAdapter extends RecyclerView.Adapter<CustomerHistoryOpportunityAdapter.ViewHolder>  {
    private List<CustomerHistoryOpportunityModel> list;
    private Context context;
    String token;
    public CustomerHistoryOpportunityAdapter(List<CustomerHistoryOpportunityModel> list, Context context, String token) {
        this.context = context;
        this.list = list;
        this.token = token;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.customer_activity_view_holder,viewGroup,false);
        return new CustomerHistoryOpportunityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CustomerHistoryOpportunityModel customerHistoryOpportunityModel = list.get(position);

        Integer oppMasterId = customerHistoryOpportunityModel.getOppMasterId();
        Integer contactId = customerHistoryOpportunityModel.getContactId();
        boolean opportunityExist = customerHistoryOpportunityModel.isExisting();
        String opportunityCreateName = customerHistoryOpportunityModel.getRemarks();
        String dateAdded = customerHistoryOpportunityModel.getDateAdded();
        String dateUpdated = customerHistoryOpportunityModel.getDateUpdated();
        Integer addedByUserId = customerHistoryOpportunityModel.getAddedByUserId();
        holder.businessNameTxt.setText(opportunityCreateName);

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MMM-dd");
        Date date = null;
        try {
            date = inputFormat.parse(dateAdded);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);

        holder.dateAddedShow.setText(outputDateStr);

        ////////// Opportunity Details adapter create
        List<OpportunityDetails> opportunityDetails = customerHistoryOpportunityModel.getOpportunityDetailses();
        OpportunityDetailsAdapter opportunityDetailsAdapter = new OpportunityDetailsAdapter(context,opportunityDetails);

        holder.opportunityDetailsRecyclerView.setHasFixedSize(true);
        holder.opportunityDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        holder.opportunityDetailsRecyclerView.setAdapter(opportunityDetailsAdapter);
        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView businessNameTxt,dateAddedShow;
        RecyclerView opportunityDetailsRecyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            businessNameTxt = itemView.findViewById(R.id.businessNameTxt);
            dateAddedShow = itemView.findViewById(R.id.dateAddedShow);
            opportunityDetailsRecyclerView = itemView.findViewById(R.id.opportunityDetailsRecyclerView);
        }
    }
}
