package com.nadim.jogajogapplication.CustomerCall;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nadim.jogajogapplication.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CallHistoryAdapter extends RecyclerView.Adapter<CallHistoryAdapter.ViewHolder> {
    private List<CallHistoryModel> list;
    private Context context;
    private String token;

    private String remarks, nextDiscussion, reason, reminder;


    public CallHistoryAdapter(List<CallHistoryModel> list, Context context, String token) {
        this.list = list;
        this.context=context;
        this.token = token;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.call_history_view_adapter,viewGroup,false);
        return new CallHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final CallHistoryModel callHistoryModel = list.get(position);

        final Integer callHistoryId = callHistoryModel.getCallHistoryId();
        final Integer empId = callHistoryModel.getEmpId();
        final Integer contactId = callHistoryModel.getContactId();

        remarks = callHistoryModel.todayDiscussion;
        nextDiscussion = callHistoryModel.nextDiscussion;
        reason = callHistoryModel.meetingReason;
        reminder = callHistoryModel.reminderDate;
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");


        if (reminder ==null){
            holder.summary.setText(remarks);
            holder.nextStep.setText(nextDiscussion);
            holder.reason.setText(reason);
        }else {
            holder.summary.setText(remarks);
            holder.nextStep.setText(nextDiscussion);
            holder.reason.setText(reason);

            Date date = null;
            try {
                date = inputFormat.parse(reminder);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDateStr = outputFormat.format(date);
            holder.anyReminder.setText(outputDateStr);

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();*/

                    Intent intent = new Intent(context,CallHistoryEditActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("callHistoryId",callHistoryId);
                    intent.putExtra("empId",empId);
                    intent.putExtra("contactId",contactId);
                    intent.putExtra("token",token);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView summary,nextStep,reason,anyReminder,edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            summary = itemView.findViewById(R.id.summary);
            nextStep = itemView.findViewById(R.id.nextStep);
            reason = itemView.findViewById(R.id.reason);
            anyReminder = itemView.findViewById(R.id.anyReminder);
            edit = itemView.findViewById(R.id.edit);
        }

    }
}

