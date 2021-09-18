package com.nadim.jogajogapplication.MainDashboard.CallLogData;

import android.content.Context;
import android.provider.CallLog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.nadim.jogajogapplication.R;
import com.nadim.jogajogapplication.databinding.ListRowBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CallLogAdapter  extends RecyclerView.Adapter<CallLogAdapter.CallLogViewHolder>{

    ArrayList<CallLogInfo> callLogInfoArrayList;
    Context context;
    OnCallLogItemClickListener onItemClickListener;

    interface OnCallLogItemClickListener {
        void onItemClicked(CallLogInfo callLogInfo);
    }

    public CallLogAdapter(Context context){
        callLogInfoArrayList = new ArrayList<>();
        this.context = context;
    }

    public void setOnItemClickListener(OnCallLogItemClickListener listener){
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public CallLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ListRowBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_row,parent,false);
        return new CallLogViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CallLogViewHolder holder, int position) {
        holder.bind(callLogInfoArrayList.get(position));
    }

    public void addCallLog(CallLogInfo callLogInfo){
        callLogInfoArrayList.add(callLogInfo);
    }

    public void addAllCallLog(ArrayList<CallLogInfo> list){
        callLogInfoArrayList.clear();
        callLogInfoArrayList.addAll(list);
    }

    @Override
    public int getItemCount() {
        return callLogInfoArrayList.size();
    }

    class CallLogViewHolder extends RecyclerView.ViewHolder{
        ListRowBinding itemBinding;
        public CallLogViewHolder(ListRowBinding binding) {
            super(binding.getRoot());
            itemBinding = binding;
        }

        public void bind(final CallLogInfo callLog){
            switch(Integer.parseInt(callLog.getCallType()))
            {
                case CallLog.Calls.OUTGOING_TYPE:
                    itemBinding.imageView.setImageResource(R.drawable.ic_outgoing);
                    DrawableCompat.setTint(itemBinding.imageView.getDrawable(), ContextCompat.getColor(context, R.color.green));
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    itemBinding.imageView.setImageResource(R.drawable.ic_missed);
                    DrawableCompat.setTint(itemBinding.imageView.getDrawable(), ContextCompat.getColor(context, R.color.colorPrimary));
                    itemBinding.textViewCallDuration.setText(Utils.formatSeconds(callLog.getDuration()));
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    itemBinding.imageView.setImageResource(R.drawable.ic_missed);
                    DrawableCompat.setTint(itemBinding.imageView.getDrawable(), ContextCompat.getColor(context, R.color.colorPrimary));
                    break;
            }

            if(TextUtils.isEmpty(callLog.getName()))
                itemBinding.textViewName.setText(callLog.getNumber());
            else
                itemBinding.textViewName.setText(callLog.getName());
            itemBinding.textViewCallDuration.setText(Utils.formatSeconds(callLog.getDuration()));
            Date dateObj = new Date(callLog.getDate());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy   hh:mm a");
            itemBinding.textViewCallNumber.setText(callLog.getNumber());
            itemBinding.textViewCallDate.setText(formatter.format(dateObj));

            itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null)
                        onItemClickListener.onItemClicked(callLog);
                }
            });
        }
    }
}