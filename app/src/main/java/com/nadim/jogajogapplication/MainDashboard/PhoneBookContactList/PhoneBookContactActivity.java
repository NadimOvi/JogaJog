package com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.contacts.SystemContact;
import com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.loaders.ContactsLoader;
import com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.permissions.PermissionHandler;
import com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.ui.adapters.ContactsAdapter;
import com.nadim.jogajogapplication.R;

import java.util.LinkedList;
import java.util.Map;

public class PhoneBookContactActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Map<Long, SystemContact>>{


        private static final String TAG = PhoneBookContactActivity.class.getSimpleName();

        private RecyclerView contactsRecyclerView;
        private TextView emptyLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book_contact);
        initializeView();

        if (!PermissionHandler.hasPermissionsGranted(this, PermissionHandler.CONTACTS_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PermissionHandler.CONTACTS_PERMISSIONS,
                    PermissionHandler.CONTACTS_PERMISSION_CODE);
        } else {
            getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getAdapter().setFilter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getAdapter().setFilter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionHandler.CONTACTS_PERMISSION_CODE) {
            if (!PermissionHandler.hasPermissionsGranted(this, permissions)) {
                ActivityCompat.requestPermissions(this, PermissionHandler.CONTACTS_PERMISSIONS,
                        PermissionHandler.CONTACTS_PERMISSION_CODE);
            } else {
                getSupportLoaderManager().initLoader(0, null, this).forceLoad();
            }
        }
    }

    @Override
    public Loader<Map<Long, SystemContact>> onCreateLoader(int id, Bundle args) {
        return new ContactsLoader(this, true);
    }

    @Override
    public void onLoadFinished(Loader<Map<Long, SystemContact>> loader, Map<Long, SystemContact> data) {
        if (data != null && !data.isEmpty()) {
            contactsRecyclerView.setAdapter(new ContactsAdapter(this, new LinkedList<>(data.values())));
            show(true);
        } else {
            show(false);
        }
    }

    @Override
    public void onLoaderReset(Loader<Map<Long, SystemContact>> loader) {
        // do nothing
    }

    private ContactsAdapter getAdapter() {
        return (ContactsAdapter) contactsRecyclerView.getAdapter();

    }

    private void initializeView() {
        contactsRecyclerView = findViewById(R.id.contact_list);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        contactsRecyclerView.setHasFixedSize(true);

        emptyLabel = findViewById(R.id.empty);
    }

    private void show(boolean isVisible) {
        contactsRecyclerView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        emptyLabel.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }
}
