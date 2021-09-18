package com.nadim.jogajogapplication.MainDashboard.OldContactAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nadim.jogajogapplication.MainDashboard.Models.OldContactShow;
import com.nadim.jogajogapplication.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class OldDataAdapter extends RecyclerView.Adapter<OldDataAdapter.ViewHolder> {

    private List<OldContactShow> list;
    private Context context;

    public OldDataAdapter(List<OldContactShow> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.old_contact_view_holder, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder oldDataViewHolder, int position) {
        final OldContactShow oldContactShow = list.get(position);

        byte[] viewPhoto;

        String personName = oldContactShow.getName();
        String dateUpdated = oldContactShow.getDateUpdated();
        String dateAdded = oldContactShow.getDateAdded();
        String companyName = oldContactShow.getCustomer().getCustName();

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MMM-dd");

      /*  viewPhoto = Base64.decode(comapnyPhoto,Base64.DEFAULT);
        Bitmap decoded= BitmapFactory.decodeByteArray(viewPhoto,0,viewPhoto.length);*/

        try {
          /*  viewPhoto = Base64.decode(comapnyPhoto, Base64.DEFAULT);*/
         /*   Bitmap decoded = BitmapFactory.decodeByteArray(viewPhoto, 0, viewPhoto.length);*/
            if (dateUpdated == null) {
                /*    oldDataViewHolder.companyPersonName.setText(personName);*/
              /*  oldDataViewHolder.profilePicture.setImageBitmap(decoded);*/
                oldDataViewHolder.showName.setText(companyName);
                /*  oldDataViewHolder.lastContactTime.setText(dateAdded);*/

                Date date = null;
                try {
                    date = inputFormat.parse(dateAdded);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String outputDateStr = outputFormat.format(date);
                oldDataViewHolder.lastContactTime.setText(outputDateStr);
            } else {
                /*     oldDataViewHolder.companyPersonName.setText(personName);*/
                /*oldDataViewHolder.profilePicture.setImageBitmap(decoded);*/
                oldDataViewHolder.showName.setText(companyName);
                oldDataViewHolder.lastContactTime.setText(dateUpdated);

                Date date = null;
                try {
                    date = inputFormat.parse(dateUpdated);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String outputDateStr = outputFormat.format(date);
                oldDataViewHolder.lastContactTime.setText(outputDateStr);
            }

        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profilePicture;
        TextView showName,lastContactTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePicture= itemView.findViewById(R.id.profilePicture);
            showName= itemView.findViewById(R.id.showName);
            lastContactTime= itemView.findViewById(R.id.lastContactTime);
        }
    }
}
