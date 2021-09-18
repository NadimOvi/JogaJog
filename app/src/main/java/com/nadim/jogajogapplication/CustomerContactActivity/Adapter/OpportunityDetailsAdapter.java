package com.nadim.jogajogapplication.CustomerContactActivity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.nadim.jogajogapplication.CustomerContactActivity.Model.OpportunityDetails;
import com.nadim.jogajogapplication.CustomerContactActivity.Model.Product;
import com.nadim.jogajogapplication.R;

import java.util.List;


public class OpportunityDetailsAdapter extends RecyclerView.Adapter<OpportunityDetailsAdapter.ViewHolder> {

    private Context context;
    private List<OpportunityDetails> list;

    public OpportunityDetailsAdapter(Context context, List<OpportunityDetails> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public OpportunityDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.opportunity_details_view_holder,viewGroup,false);
        return new OpportunityDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OpportunityDetailsAdapter.ViewHolder holder, int position) {

        final OpportunityDetails opportunityDetails = list.get(position);
        String stageName = opportunityDetails.getStage().getStageName();
        String remarks = opportunityDetails.getRemarks();

        //////////Product Show Adapter
        List<Product> product = opportunityDetails.getProducts();

        ProductAdapter productAdapter = new ProductAdapter(context,product);
        holder.productNameRecyclerView.setHasFixedSize(true);
        holder.productNameRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
        holder.productNameRecyclerView.setAdapter(productAdapter);

        holder.stageNameTxt.setText(stageName);
        holder.descriptionTxt.setText(remarks);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionTxt,stageNameTxt;
        RecyclerView productNameRecyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTxt = itemView.findViewById(R.id.descriptionTxt);
            productNameRecyclerView = itemView.findViewById(R.id.productNameRecyclerView);
            stageNameTxt = itemView.findViewById(R.id.stageNameTxt);
        }
    }
}
