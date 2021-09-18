package com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.permissions;

import android.Manifest;
import android.content.Context;

import androidx.core.content.PermissionChecker;


public class PermissionHandler {

    public static final int CONTACTS_PERMISSION_CODE = 1;

    public static final String[] CONTACTS_PERMISSIONS = {Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_CONTACTS};

    public static boolean hasPermissionsGranted(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (!isGranted(context, permission)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isGranted(Context context, String permission) {
        return PermissionChecker.checkSelfPermission(context, permission)
                == PermissionChecker.PERMISSION_GRANTED;
    }

}
