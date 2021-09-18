package com.nadim.jogajogapplication.MainDashboard.NewContact;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nadim.jogajogapplication.Api;
import com.nadim.jogajogapplication.Login.LoginActivity;
import com.nadim.jogajogapplication.MainDashboard.NewContact.Model.Profile;
import com.nadim.jogajogapplication.MainDashboard.UserDashboardActivity;
import com.nadim.jogajogapplication.R;
import com.theartofdev.edmodo.cropper.CropImage;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateContactActivity extends AppCompatActivity {

    private ImageView imageButton;
    private Bitmap bitmap;

    private String cameraPermission[];

    private  static final int CAMERA_REQUEST_CODE = 200;
    public static final String PROFILE_DATA_KEY = "profile_data_key";
    //Uri
    Uri resultUri;

    //String All info
    private List<String> profile_data = new ArrayList<String>();
    byte[] byteArray;

    /////////////////////////////////////////////////////////
    private EditText nameInput;
    private EditText jobTitleInput;
    private EditText telephoneInput;
    private EditText emailInput;
    private EditText saveCompanyName;

    private EditText companyInput;

    private Button nameCandidatesButton;
    private Button jobTitleCandidatesButton;
    private Button telephoneCandidatesButton;
    private Button emailCandidatesButton;
    private Button company_candidates_button;

    Map<String, Integer> phoneNumberCandidates = new HashMap<String, Integer>();
    Map<String, Integer> emailCandidates = new HashMap<String, Integer>();
    List<String> genericCandidates = new ArrayList<String>();
    List<String> nameCandidates = new ArrayList<String>();
    List<String> companyCandidates = new ArrayList<String>();
    private Profile profile;
    private String image;

    //set Spinner
    String URL="https://www.pqstec.com/GTRJOGAJOG/API/Customer";
    ArrayList<String> FieldName;
    ArrayList<Integer> fieldArrayId;
    Integer field_Id;
    Integer fieldId;
    Spinner spinner;

    private String empCode,empName,desigName,empImage,token;
    private Integer empId;
    private String name, designation, phoneNumber,cardImage, email;
    private Integer addedByUserId;
    private String authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJFbXBJZCI6IjEzNjMiLCJuYmYiOjE2MTc5MDU0NzIsImV4cCI6MTYxODUxMDI3MiwiaWF0IjoxNjE3OTA1NDcyfQ.ykLaV2gD_rN2CINOkXXMRRovxWUyi23ef6Y-6poIMRU";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        /*spinner.setEnabled(false);*/

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext())
                .build();
        imageButton = findViewById(R.id.imageButton);

        cameraPermission= new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        initialize();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(CreateContactActivity.this, "sdasdasd", Toast.LENGTH_SHORT).show();*/
                showImageImportDialog();
            }
        });


        //set Spinner
        fieldList(URL);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                /*String id=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();*/
                fieldId = fieldArrayId.get(i);
               /* Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();*/
                /*Toast.makeText(DashboardActivity.this, fieldId, Toast.LENGTH_SHORT).show();*/
                /*listShow();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

    }

    private void fieldList(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("customer");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        String country=jsonObject1.getString("custName");

                        field_Id = Integer.valueOf(jsonObject1.getString("custId"));

                        FieldName.add(country);
                        fieldArrayId.add(field_Id);
                    }

                    spinner.setAdapter(new ArrayAdapter<String>(CreateContactActivity.this, android.R.layout.simple_spinner_dropdown_item, FieldName));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> params = new HashMap<String, String>();
                /*HashMap<String, String> headers = new HashMap<String, String>();*/
                params.put("Authorization", token);
                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void initialize() {

        Intent i=getIntent();
        empId= i.getIntExtra("empId",0);
        empCode= i.getStringExtra("empCode");
        empName= i.getStringExtra("empName");
        desigName= i.getStringExtra("desigName");
        empImage= i.getStringExtra("empImage");
        token= i.getStringExtra("token");

        companyInput = findViewById(R.id.companyInput);
        nameInput = (EditText) findViewById(R.id.input_name);
        jobTitleInput = (EditText) findViewById(R.id.input_job_title);
        spinner = (Spinner) findViewById(R.id.input_company);
        telephoneInput = (EditText) findViewById(R.id.input_telephone);
        emailInput = (EditText) findViewById(R.id.input_email);
       /* saveCompanyName = (EditText) findViewById(R.id.saveCompanyName);*/


        nameCandidatesButton = (Button) findViewById(R.id.name_candidates_button);
        jobTitleCandidatesButton = (Button) findViewById(R.id.job_title_candidates_button);

        telephoneCandidatesButton = (Button) findViewById(R.id.telephone_candidates_button);
        emailCandidatesButton = (Button) findViewById(R.id.email_candidates_button);
        company_candidates_button = findViewById(R.id.company_candidates_button);

        Button saveButton = (Button) findViewById(R.id.save_button);
        Button rescanButton = (Button) findViewById(R.id.rescan_button);
 /*       Button exitButton = (Button) findViewById(R.id.exit_button);*/

        FieldName =new ArrayList<>();
        fieldArrayId=new ArrayList<>();


        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                validateAndCreateProfile();

            }
        });

        rescanButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                confirmRescan();
            }
        });
        /* exitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                confirmExit();
            }
        });*/

        company_candidates_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                popUpCandidates(companyCandidates, companyInput);
            }
        });
        nameCandidatesButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                popUpCandidates(nameCandidates, nameInput);
            }
        });
        jobTitleCandidatesButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                popUpCandidates(genericCandidates, jobTitleInput);
            }
        });

        telephoneCandidatesButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                popUpCandidates(phoneNumberCandidates.keySet(), telephoneInput);
            }
        });
        emailCandidatesButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                popUpCandidates(emailCandidates.keySet(), emailInput);
            }
        });

    }
    private void confirmRescan(){
        dialogConfirm(R.string.profile_creator_confirm_rescan,
                R.string.profile_creator_button_rescan,
                CreateContactActivity.class);
    }

    private void confirmExit(){
        dialogConfirm(R.string.profile_creator_confirm_exit,
                R.string.profile_creator_button_exit,
                CreateContactActivity.class);
    }




    private void validateAndCreateProfile(){

        Boolean mark = false;
        Profile profile = new Profile(
                nameInput.getText().toString(),
                jobTitleInput.getText().toString(),
                telephoneInput.getText().toString(),

                emailInput.getText().toString()
        );
        /*if (profile.isValid()){*/



                ///////////////////
                Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
                intent.setType(ContactsContract.RawContacts.CONTENT_ITEM_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME,nameInput.getText().toString());
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL,emailInput.getText().toString());
                intent.putExtra(ContactsContract.Intents.Insert.PHONE,telephoneInput.getText().toString());
                intent.putExtra(ContactsContract.Intents.ATTACH_IMAGE,image);

                if (intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                    mark= true;
                    

                }else{
                    Toast.makeText(CreateContactActivity.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
                }
                
                if (mark==true){
                    postRequest();
                }
                else
                {
                    /*Toast.makeText(this, "asdasdsadassd", Toast.LENGTH_SHORT).show();*/
                }
                //////////////////////

                /*        showSaveSuccessDialog();*/
            /*} else {
                Utils.displayErrorDialog(this);
            }*/

        /*} else {
            alertInvalidProfile();
        }*/

    }

    

    private void alertInvalidProfile() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.profile_creator_save_invalid_title);
        builder.setMessage(R.string.profile_creator_save_invalid_message);
        builder.setPositiveButton(R.string.dialog_ok,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.create().show();
    }

    private void dialogConfirm(int dialogMessage,
                               int confirmMessage,
                               final Class newActivityClass){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_confirmation);
        builder.setMessage(dialogMessage);
        builder.setNegativeButton(R.string.dialog_cancel,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.setPositiveButton(confirmMessage,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(CreateContactActivity.this,
                                newActivityClass);
                        startActivity(intent);
                        finish();
                    }
                });
        builder.show();
    }

    //Save Data to database
    private void postRequest(){
        name = nameInput.getText().toString();
        designation = jobTitleInput.getText().toString();
        phoneNumber = telephoneInput.getText().toString();
        email = emailInput.getText().toString();

        byte[] data = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        data = baos.toByteArray();
        image = Base64.encodeToString(data, Base64.DEFAULT);

        if (name.isEmpty()) {
            nameInput.setError("Please provide name");
            nameInput.requestFocus();
            return;
        }else if (designation.isEmpty()){
            jobTitleInput.setError("Please provide designation");
            jobTitleInput.requestFocus();
            return;
        }else if(phoneNumber.isEmpty()){
            telephoneInput.setError("Please provide phoneNumber");
            telephoneInput.requestFocus();
            return;
        }else if(email.isEmpty()){
            emailInput.setError("Please provide email");
            emailInput.requestFocus();
            return;
        }
        else {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pqstec.com/GTRJOGAJOG/API/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        Api api = retrofit.create(Api.class);
        JsonObject jsonObjectFinal = new JsonObject();

        JSONObject jsonObjectName = new JSONObject();
        try {
            jsonObjectName.put("name", name);
            jsonObjectName.put("custId", fieldId);
            jsonObjectName.put("designation", designation);
            jsonObjectName.put("email", email);
            jsonObjectName.put("phoneNumber", phoneNumber);
            jsonObjectName.put("image", image);
            jsonObjectName.put("addedByUserId", empId);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonParser jsonParser = new JsonParser();
        jsonObjectFinal = (JsonObject) jsonParser.parse(jsonObjectName.toString());

        Call<String> call = api.saveNewContact(token,jsonObjectFinal);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateContactActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    /*Intent intent = new Intent(CreateContactActivity.this, UserDashboardActivity.class);
                    intent.putExtra("empId",empId);
                    intent.putExtra("empCode",empCode);
                    intent.putExtra("empName",empName);
                    intent.putExtra("desigName",desigName);
                    intent.putExtra("empImage",empImage);
                    intent.putExtra("token",token);
                    startActivity(intent);*/

                    nameInput.setText("");
                    jobTitleInput.setText("");
                    telephoneInput.setText("");
                    emailInput.setText("");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CreateContactActivity.this, "False", Toast.LENGTH_SHORT).show();
            }
        });
        }
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }
    private void showImageImportDialog() {
        if (!checkCameraPermission()){

            requestCameraPermission();

        }else{
            pickCamera();
        }
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);
    }
    private void pickCamera() {

        /*imageNo=0;*/
        getImageClick();
    }

    public void getImageClick() {
        CropImage.startPickImageActivity(this);

        /*Intent cameraIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri2);
        startActivityForResult(cameraIntent,0);*/
    }
    //this for Crope Image
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
          /*  try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            //start Crope image
            startCropImageActivity(imageUri);
        }

        // handle result of CropImageActivity
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            resultUri = result.getUri();

            //here you have resultUri for save image or preview as image1
            imageButton.setImageURI(resultUri);

            BitmapDrawable bitmapDrawable = (BitmapDrawable)imageButton.getDrawable();
            bitmap = bitmapDrawable.getBitmap();

            TextRecognizer recognizer= new TextRecognizer.Builder(getApplicationContext()).build();

            if (!recognizer.isOperational()){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }else{
                Frame frame = new Frame.Builder().setBitmap(bitmap).build();

                SparseArray<TextBlock> items = recognizer.detect(frame);
                StringBuilder sb= new StringBuilder();
                for (int i=0;i<items.size();i++){
                    TextBlock myItem = items.valueAt(i);
                    sb.append(myItem.getValue());
                    sb.append("\n");
                }
                /* showText.setText(sb.toString());*/

                String capturedString = sb.toString();

                profile_data.add(capturedString);
               /* Toast.makeText(this, profile_data.toString(), Toast.LENGTH_SHORT).show();*/

                generateProfile();

            }
        }
    }
   /* private boolean saveProfile(Profile profile) {
        return profileDao.insert(profile);
    }*/
    private void popUpCandidates(Collection<String> candidates, final EditText input){
        if (!candidates.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final CharSequence[] items = candidates.toArray(new CharSequence[candidates.size()]);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInteface, int i) {
                    input.setText(items[i]);
                }
            });
            builder.show();
        }
    }

    private void generateProfile() {
        for (String snapshot : profile_data){
            for (String text : snapshot.split("\n")){
                int selected = 0;
                selected = selectPhoneNumber(text, phoneNumberCandidates)
                        + selectEmail(text, emailCandidates);
                if (selected == 0) {
                    selectRest(text, genericCandidates);
                }
            }
        }
        boolean generateProfile = false;
        String phoneNumber = getBestCandidate(phoneNumberCandidates);
        if (StringUtils.isNotBlank(phoneNumber)){
            generateProfile = true;
            telephoneInput.setText(phoneNumber);
        }
        String email = getBestCandidate(emailCandidates);

        if (StringUtils.isNotBlank(email)){
            generateProfile = true;
            emailInput.setText(email);
            String namePart = email.substring(0, email.indexOf("@"));
            String companyPart = email.substring(email.indexOf("@")+1, email.length());
            companyPart = companyPart.substring(0, companyPart.indexOf("."));

            StringBuilder nameBuilder = new StringBuilder();
            int j = 0;
            for (String str : namePart.split("\\.")){
                j++;
                nameBuilder.append(str.substring(0, 1).toUpperCase());
                if (str.length() > 1){
                    nameBuilder.append(str.substring(1));
                }
                nameBuilder.append(" ");
            }
            if (j > 0) {
                nameCandidates.add(nameBuilder.toString().trim());
            }

            if (companyPart.length() > 1
                    && !companyPart.equals("googlemail")
                    && !companyPart.equals("gmail")
                    && !companyPart.equals("hotmail")
                    && !companyPart.equals("live")){
                companyCandidates.add(companyPart.substring(0, 1).toUpperCase()+companyPart.substring(1));
                companyCandidates.add(companyPart.toUpperCase());
                companyCandidates.add(companyPart);
            }

        }

        nameCandidates.addAll(genericCandidates);
        companyCandidates.addAll(genericCandidates);

        if (!nameCandidates.isEmpty()){
            nameInput.setText(nameCandidates.get(0));
            generateProfile = true;
        }
        int i = 0;
        if (!companyCandidates.isEmpty()){
            if (companyCandidates.get(0).equals(nameCandidates.get(0)) && companyCandidates.size() != 1){
                i++;
            }
            companyInput.setText(companyCandidates.get(i));
        }

        if (!genericCandidates.isEmpty()){
            jobTitleInput.setText(genericCandidates.get(0));
        }

        genericCandidates.addAll(phoneNumberCandidates.keySet());
        genericCandidates.addAll(emailCandidates.keySet());
    }

    private void selectRest(String text, List<String> genericCandidates) {
        List<String> toFilter = new ArrayList<String>();
        boolean filter = false;
        for (String candidate : genericCandidates){
            if (candidate.contains(text)){
                filter = true;
                break;
            }
            if (text.contains(candidate)){
                toFilter.add(candidate);
            }
        }
        if (!filter){
            genericCandidates.add(text);
        }
        genericCandidates.removeAll(toFilter);
    }

    private int selectPhoneNumber(String text, Map<String, Integer> phoneNumberCandidates) {
        //At least 6 numbers, allow other characters
        String trimmed = text.toLowerCase().replaceAll("tel:","").replaceAll("mob:","").trim();
        if (phoneNumberCandidates.containsKey(trimmed)) {
            phoneNumberCandidates.put(trimmed, phoneNumberCandidates.get(trimmed) + 1);
        } else {
            int numCount = 0;

            for (char c : trimmed.toCharArray()) {
                if (Character.isDigit(c)) {
                    numCount++;
                }
                if (numCount == 6) {
                    phoneNumberCandidates.put(trimmed, 1);
                    return 1;
                }
            }
        }
        return 0;
    }

    private int selectEmail(String text, Map<String, Integer> emailCandidates) {
        int atPos = text.indexOf("@");
        int dotPos = text.lastIndexOf(".");
        //Very basic check to see if a text COULD BE an email address
        if (atPos != -1 && dotPos > atPos){
            String trimmed = text.trim();
            if (emailCandidates.containsKey(trimmed)){
                emailCandidates.put(trimmed, emailCandidates.get(trimmed)+1);
            } else {
                emailCandidates.put(trimmed, 1);
            }
            return 1;
        }
        return 0;
    }

    private String getBestCandidate(Map<String, Integer> candidates){
        int maxValue = 0;
        String bestCandidate ="";
        for (Map.Entry<String, Integer> candidate : candidates.entrySet()){
            if (candidate.getValue() > maxValue){
                maxValue = candidate.getValue();
                bestCandidate = candidate.getKey();
            }
        }
        //candidates.remove(bestCandidate);
        return bestCandidate;
    }
}