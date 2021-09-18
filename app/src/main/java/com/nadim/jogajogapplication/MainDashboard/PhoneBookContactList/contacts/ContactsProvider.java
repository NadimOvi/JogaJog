package com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.contacts;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.provider.ContactsContract;


import androidx.annotation.Nullable;

import com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.util.PhoneUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ContactsProvider {

    private static final String TAG = ContactsProvider.class.getSimpleName();

    private static ContactsProvider instance = null;

    public static ContactsProvider getInstance() {
        synchronized (ContactsProvider.class) {
            if (instance == null) {
                instance = new ContactsProvider();
            }
            return instance;
        }
    }

    public @Nullable
    Cursor getSystemContacts(Context context) {
        Cursor cursor;
        try {
            cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            return cursor;
        } catch (SQLException e) {
            return null;
        }
    }

    public @Nullable
    List<SystemContact> getSystemContactsList(Context context, boolean e164format) {
        Cursor cursor = getSystemContacts(context);

        if (cursor != null) {
            List<SystemContact> contacts = newReaderFor(cursor).getSystemContacts();
            if (e164format) {
                for (SystemContact contact : contacts) {
                    contact.setNumber(PhoneUtil.formatE164Number(context, contact.getNumber()));
                }
            }
            return contacts;
        }

        return null;
    }

    public @Nullable
    Map<Long, SystemContact> getSystemContactMap(Context context, boolean e164format) {
        List<SystemContact> systemContacts = getSystemContactsList(context, e164format);

        if (systemContacts != null && !systemContacts.isEmpty()) {
            Map<Long, SystemContact> contacts = new HashMap<>();

            for (SystemContact contact : systemContacts) {
                Long contactId = contact.getContactId();
                if (!contacts.containsKey(contactId)) {
                    contacts.put(contactId, contact);
                }
            }
            return contacts;
        }
        return null;
    }

    public Reader newReaderFor(Cursor cursor) {
        return new Reader(cursor);
    }

    public class Reader {

        private Cursor cursor;

        public Reader(Cursor cursor) {
            this.cursor = cursor;
        }

        private boolean hasNext() {
            return cursor != null && cursor.moveToNext();
        }

        public List<SystemContact> getSystemContacts() {
            List<SystemContact> systemContactList = new LinkedList<>();

            while (hasNext()) {

                SystemContact systemContact = new SystemContact();
                systemContact.setContactId(cursor.getLong(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)));
                systemContact.setRawContactId(cursor.getLong(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID)));
                systemContact.setNumber(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                systemContact.setName(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));

                systemContactList.add(systemContact);
            }

            cursor.close();

            return systemContactList;
        }

    }

}
