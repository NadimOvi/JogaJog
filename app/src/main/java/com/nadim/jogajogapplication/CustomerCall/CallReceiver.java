package com.nadim.jogajogapplication.CustomerCall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

public class CallReceiver extends BroadcastReceiver {
    private String phoneNumber;
    private Integer empId,contactId;


    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            showToast(context,"Call started...");
          /*  Intent i=getIntent();
            empId= i.getIntExtra("empId",0);
            contactId= i.getIntExtra("contactId",0);
            personName= i.getStringExtra("personName");
            companyName= i.getStringExtra("companyName");*/
            empId= intent.getIntExtra("empId",0);
            contactId= intent.getIntExtra("contactId",0);
            phoneNumber= intent.getStringExtra("phoneNumber");

            showToast(context, String.valueOf(empId+contactId)+phoneNumber);
          /*  Toast.makeText(context, "", Toast.LENGTH_SHORT).show();*/
        }
        else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)){

        }
        else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
            showToast(context,"Incoming call...");
        }
        else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.CALL_STATE_IDLE)){
            showToast(context,"Changes...");
        }
    }

    void showToast(Context context, String message){
        Toast toast= Toast.makeText(context,message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public interface Message {
    }
}
