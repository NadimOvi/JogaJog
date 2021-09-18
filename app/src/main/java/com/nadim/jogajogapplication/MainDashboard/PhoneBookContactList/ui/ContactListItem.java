package com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.contacts.SystemContact;
import com.nadim.jogajogapplication.R;


public class ContactListItem extends RelativeLayout {

    private TextView name;
    private TextView number;


    public ContactListItem(Context context) {
        super(context);
    }

    public ContactListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        this.name = findViewById(R.id.contact_name);
        this.number = findViewById(R.id.contact_number);

    }

    public void set(SystemContact systemContact) {
        this.name.setText(systemContact.getName());
        this.number.setText(systemContact.getNumber());
        /*Toast.makeText(getContext(),systemContact.getName(), Toast.LENGTH_SHORT).show();*/
    }
}
