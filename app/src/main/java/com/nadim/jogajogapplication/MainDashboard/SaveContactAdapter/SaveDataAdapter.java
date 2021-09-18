package com.nadim.jogajogapplication.MainDashboard.SaveContactAdapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.nadim.jogajogapplication.CustomerCall.CallHistoryActivity;
import com.nadim.jogajogapplication.Login.Model.UserEmployeeInfo;
import com.nadim.jogajogapplication.MainDashboard.Models.SaveContactShow;
import com.nadim.jogajogapplication.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SaveDataAdapter extends RecyclerView.Adapter<SaveDataAdapter.ViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback{
    private List<SaveContactShow> list;
    private Context context;
    private RecyclerViewClickListener listener;
    String token;


    private String comapnyPhoto,personName,dateUpdated,dateAdded,companyName;
    private Integer contactId, empId;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    String phoneNumber;

    public SaveDataAdapter(List<SaveContactShow> list, Context context, RecyclerViewClickListener listener, String token) {
        this.list = list;
        this.context = context;
        this.listener = listener;
        this.token = token;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.save_contact_view_holder,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder saveDataViewHolder, int position) {

        final SaveContactShow saveContactShow = list.get(position);

        byte[] viewPhoto;

       /*  comapnyPhoto = saveContactShow.getImage();*/
         personName = saveContactShow.getName();
         dateUpdated = saveContactShow.getDateUpdated();
         dateAdded = saveContactShow.getDateAdded();
         companyName = saveContactShow.getCustomer().getCustName();
         contactId = saveContactShow.getContactId();
         empId = saveContactShow.getAddedByUserId();

        /*Toast.makeText(context, token, Toast.LENGTH_SHORT).show();*/





        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MMM-dd");

   /*     viewPhoto = Base64.decode(comapnyPhoto,Base64.DEFAULT);
        Bitmap decoded= BitmapFactory.decodeByteArray(viewPhoto,0,viewPhoto.length);*/

   saveDataViewHolder.telephone.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           personName = saveContactShow.getName();
           companyName = saveContactShow.getCustomer().getCustName();
           phoneNumber = saveContactShow.getPhoneNumber();
           contactId = saveContactShow.getContactId();



           activityCallFunction();

       }
   });


        try {
            viewPhoto = Base64.decode(comapnyPhoto, Base64.DEFAULT);
            Bitmap decoded = BitmapFactory.decodeByteArray(viewPhoto, 0, viewPhoto.length);


        } catch (Exception e) {
            e.getMessage();
        }

        if (dateUpdated == null) {
            saveDataViewHolder.companyPersonName.setText(personName);
            /*saveDataViewHolder.comapnyPhoto.setImageBitmap(decoded);*/
            saveDataViewHolder.companyName.setText(companyName);
            /*     saveDataViewHolder.lastContactDate.setText(dateAdded);*/

            Date date = null;
            try {
                date = inputFormat.parse(dateAdded);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDateStr = outputFormat.format(date);
            saveDataViewHolder.lastContactDate.setText(outputDateStr);
        } else {
            saveDataViewHolder.companyPersonName.setText(personName);
            /*saveDataViewHolder.comapnyPhoto.setImageBitmap(decoded);*/
            saveDataViewHolder.companyName.setText(companyName);
            /* saveDataViewHolder.lastContactDate.setText(dateUpdated);*/

            Date date = null;
            try {
                date = inputFormat.parse(dateUpdated);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDateStr = outputFormat.format(date);
            saveDataViewHolder.lastContactDate.setText(outputDateStr);
        }

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    activityCallFunction();

                }
            }
        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView comapnyPhoto;
        ImageButton telephone;
        TextView companyName,companyPersonName,lastContactDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comapnyPhoto = itemView.findViewById(R.id.companyPhoto);
            companyName = itemView.findViewById(R.id.companyName);
            companyPersonName = itemView.findViewById(R.id.companyPersonName);
            lastContactDate = itemView.findViewById(R.id.lastContactDate);
            telephone = itemView.findViewById(R.id.telephone);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(itemView,getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    private void callPhone()
    {

       /* Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
           *//* context.startActivity(intent);*//*
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }*/

        //Make Call
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            context.startActivity(intent);
        }
    }

    private void activityCallFunction() {

        Intent intent = new Intent(context.getApplicationContext(), CallHistoryActivity.class);
        intent.putExtra("contactId",contactId);
        intent.putExtra("empId",empId);
        intent.putExtra("companyName",companyName);
        intent.putExtra("personName",personName);
        intent.putExtra("phoneNumber",phoneNumber);
        intent.putExtra("token",token);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
