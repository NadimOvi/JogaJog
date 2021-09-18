package com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.util;

import android.content.Context;
import android.telephony.TelephonyManager;


public class SystemUtil {
    public static TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
    }
}
