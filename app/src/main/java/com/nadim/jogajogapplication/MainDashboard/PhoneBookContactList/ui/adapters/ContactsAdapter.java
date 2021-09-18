package com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.contacts.SystemContact;
import com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.ui.ContactListItem;
import com.nadim.jogajogapplication.R;

import java.util.LinkedList;
import java.util.List;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<SystemContact> contacts;
    private List<SystemContact> copy;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private Button contact_save;
        ViewHolder(View itemView) {
            super(itemView);
            contact_save = itemView.findViewById(R.id.contact_save);
        }

        private ContactListItem getItem() {
            return (ContactListItem) itemView;
        }
    }

    public ContactsAdapter(Context context, List<SystemContact> contacts) {
        this.inflater = LayoutInflater.from(context);
        this.contacts = contacts;
        this.copy = new LinkedList<>(contacts);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ContactListItem item = (ContactListItem) inflater.inflate(R.layout.contact_list_item,
                parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getItem().set(contacts.get(position));

        final SystemContact saveContactShow = contacts.get(position);
        
        final String Name1 = saveContactShow.getName();
        final String Number1 = saveContactShow.getNumber();
        holder.contact_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(inflater.getContext(), Name1+" "+Number1, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return contacts != null ? contacts.size() : 0; 
    }

    public void setFilter(String filter) {
        contacts.clear();
        if (filter.isEmpty()) {
            contacts.addAll(copy);
        } else {
            String lowCaseFilter = filter.toLowerCase();
            for (SystemContact contact : copy) {
                String name = contact.getName().toLowerCase();
                String number = contact.getNumber();
                if (name.contains(lowCaseFilter) || number.contains(lowCaseFilter)) {
                    contacts.add(contact);
                }
            }
        }
        notifyDataSetChanged();
    }
}
