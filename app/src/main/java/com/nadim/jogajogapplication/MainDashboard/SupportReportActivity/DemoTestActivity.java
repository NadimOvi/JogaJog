package com.nadim.jogajogapplication.MainDashboard.SupportReportActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.nadim.jogajogapplication.Api;
import com.nadim.jogajogapplication.MainDashboard.Models.SaveContactShow;
import com.nadim.jogajogapplication.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DemoTestActivity extends AppCompatActivity {


    ListView listView;
    CustomAdapter customAdapter;



    List<ItemNewModel> itemNewModelsList = new ArrayList<ItemNewModel>();
    private String empCode,empName,desigName,empImage,token;
    Integer empId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_test);
        Intent intent=getIntent();
        empId= intent.getIntExtra("empId",0);
        empCode= intent.getStringExtra("empCode");
        empName= intent.getStringExtra("empName");
        desigName= intent.getStringExtra("desigName");
        empImage= intent.getStringExtra("empImage");
        token= intent.getStringExtra("token");
        searchViewContact();


        listView = findViewById(R.id.listview);

        /*for(int i = 0;i<names.length;i++){

            ItemsModel itemsModel = new ItemsModel(names[i],emails[i],images[i]);

            itemsModelList.add(itemsModel);

        }

        customAdapter = new CustomAdapter(itemsModelList,this);

        listView.setAdapter(customAdapter);*/
    }
    public void searchViewContact(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        try {
            Api api = retrofit.create(Api.class);
            Call<List<SaveContactShow>> call= api.getSaveContactList(token,empId);
            call.enqueue(new Callback<List<SaveContactShow>>() {
                @Override
                public void onResponse(Call<List<SaveContactShow>> call, Response<List<SaveContactShow>> response) {

                    if (!response.isSuccessful()){
                        Toast.makeText(DemoTestActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    List<SaveContactShow> saveContactShows = response.body();
                    Toast.makeText(DemoTestActivity.this, "Success", Toast.LENGTH_SHORT).show();
                 /*   ItemsModel itemsModel = new ItemsModel(names[i],emails[i],images[i]);*/

                    if (saveContactShows != null) {
                        for(int i = 0;i<saveContactShows.size();i++){
                             Integer contactId = saveContactShows.get(i).getContactId();
                             String name = saveContactShows.get(i).getName();
                             Integer custId = saveContactShows.get(i).getCustId();
                             String designation = saveContactShows.get(i).getDesignation();
                             String email = saveContactShows.get(i).getEmail();
                             String phoneNumber = saveContactShows.get(i).getPhoneNumber();

                             String dateAdded = saveContactShows.get(i).getDateAdded();
                             String dateUpdated = saveContactShows.get(i).getDateUpdated();
                             Integer addedByUserId = saveContactShows.get(i).getAddedByUserId();
                             Integer updateByUserId = saveContactShows.get(i).getUpdateByUserId();

                             String custCode = saveContactShows.get(i).getCustomer().getCustCode();
                             String custName = saveContactShows.get(i).getCustomer().getCustName();
                             String srtName = saveContactShows.get(i).getCustomer().getSrtName();

                            ItemNewModel saveContactShow = new ItemNewModel(contactId,name,custId,designation,email,phoneNumber,
                                    dateAdded,dateUpdated,addedByUserId,updateByUserId,custCode,custName,srtName);
                            itemNewModelsList.add(saveContactShow);
                            Toast.makeText(DemoTestActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                        customAdapter = new CustomAdapter(itemNewModelsList,this);

                        listView.setAdapter(customAdapter);
                    }

                }

                @Override
                public void onFailure(Call<List<SaveContactShow>> call, Throwable t) {
                    Toast.makeText(DemoTestActivity.this, "System Failure", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (NumberFormatException e){
            e.getMessage();
        }
    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu_search,menu);

        MenuItem menuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.e("Main"," data search"+newText);

                customAdapter.getFilter().filter(newText);

                return true;
            }
        });


        return true;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();


        if(id == R.id.search){

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class CustomAdapter extends BaseAdapter implements Filterable {

        private List<ItemNewModel> itemsModelsl;
        private List<ItemNewModel> itemsModelListFiltered;
        private Callback<List<SaveContactShow>> context;

        public CustomAdapter(List<ItemNewModel> itemsModelsl, Callback<List<SaveContactShow>> context) {
            this.itemsModelsl = itemsModelsl;
            this.itemsModelListFiltered = itemsModelsl;
            this.context = context;
        }

        @Override
        public int getCount() {
            return itemsModelListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return itemsModelListFiltered.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.row_items,null);


            TextView names = view.findViewById(R.id.name);
            TextView emails = view.findViewById(R.id.email);
            ImageView imageView = view.findViewById(R.id.images);

            names.setText(itemsModelListFiltered.get(position).getName());
            emails.setText(itemsModelListFiltered.get(position).getEmail());
           /* imageView.setImageResource(images[position]);*/

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("main activity","item clicked");
                    startActivity(new Intent(DemoTestActivity.this,ItemPreviewActivity.class).putExtra("items", String.valueOf(itemsModelListFiltered.get(position))));
                }
            });

            return view;
        }



        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    FilterResults filterResults = new FilterResults();
                    if(constraint == null || constraint.length() == 0){
                        filterResults.count = itemsModelsl.size();
                        filterResults.values = itemsModelsl;

                    }else{
                        List<ItemNewModel> resultsModel = new ArrayList();
                        String searchStr = constraint.toString().toLowerCase();

                        for(ItemNewModel itemsModel:itemsModelsl){
                            if(itemsModel.getName().contains(searchStr) || itemsModel.getEmail().contains(searchStr)){
                                resultsModel.add(itemsModel);
                                filterResults.count = resultsModel.size();
                                filterResults.values = resultsModel;
                            }
                        }


                    }

                    return filterResults;
                }
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    itemsModelListFiltered = (List<ItemNewModel>) results.values;
                    notifyDataSetChanged();

                }
            };
            return filter;
        }
    }

}