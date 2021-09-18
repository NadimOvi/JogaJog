package com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.util;

import android.content.Context;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;


public class PhoneUtil {

    private static PhoneNumberUtil numberUtil = PhoneNumberUtil.getInstance();

    public static String formatE164Number(Context context, String number) {
        try {
            Phonenumber.PhoneNumber phoneNumber = numberUtil.parse(number, getSimCountryIso(context));

            return numberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            return null;
        }
    }

    private static String getSimCountryIso(Context context) {
        String simCountryIso = SystemUtil.getTelephonyManager(context).getSimCountryIso();
        return simCountryIso != null ? simCountryIso.toUpperCase() : null;
    }
}
