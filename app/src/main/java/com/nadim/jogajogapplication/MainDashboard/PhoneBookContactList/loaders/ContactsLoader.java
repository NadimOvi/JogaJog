package com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.loaders;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.contacts.ContactsProvider;
import com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.contacts.SystemContact;

import java.util.Map;


public class ContactsLoader extends AsyncTaskLoader<Map<Long, SystemContact>> {

    private boolean e164format;

    public ContactsLoader(Context context, boolean e164format) {
        super(context);
        this.e164format = e164format;
    }

    @Override
    public Map<Long, SystemContact> loadInBackground() {
        return ContactsProvider.getInstance().getSystemContactMap(getContext(), e164format);
    }
}
