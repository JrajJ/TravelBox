package com.sparklinktech.stech.travelbox;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sparklinktech.stech.travelbox.ConnectToServer.ConnectToServer;
import com.sparklinktech.stech.travelbox.NetworkCheck.CheckNetwork;
import com.sparklinktech.stech.travelbox.sharedpref.SessionManager;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registration_ extends AppCompatActivity
{

    EditText name,address,email,contact;
    //DatePickerDialog dob;
    SessionManager manager;
    private int mYear, mMonth, mDay, mHour, mMinute;
    RadioGroup rg;
    RadioButton male, female;
    String genderg;
    Button dob;
    Boolean dob_flag= false;
    SharedPreferences prefs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        manager = new SessionManager();

        name       = (EditText)findViewById(R.id.username);
        address    = (EditText)findViewById(R.id.address);
        email      = (EditText)findViewById(R.id.email);
        contact    = (EditText)findViewById(R.id.contactNo);
        dob       = (Button) findViewById(R.id.dob);
        rg         = (RadioGroup) findViewById(R.id.rg1);
        male       = (RadioButton) findViewById(R.id.rb_male);
        female     = (RadioButton) findViewById(R.id.rb_female);
//==================================================================================================
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //some code
                genderg = "male";
                hideKeyboard(v);
                Log.e("GENDERG = ", "" + genderg);
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //some code
                genderg = "female";
                hideKeyboard(v);
                Log.e("GENDERG = ", "" + genderg);
            }
        });

        prefs = getApplicationContext().getSharedPreferences("status",	Context.MODE_PRIVATE);
        if(prefs.contains("userflag"))
        {
            Intent i = new Intent(Registration_.this, Main_Activity.class);
            startActivity(i);

            Registration_.this.finish();
        }





    }
    public void hideKeyboard(View view)
    {
        // Check if no view has focus:
        view = this.getCurrentFocus();
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }

    public void signUp(View view)
    {

        if (CheckNetwork.isInternetAvailable(getApplicationContext()))

        {

            if (name.getText().toString().trim().equals(""))
            {
                name.setError(getString(R.string.name));
                name.requestFocus();
            } else {
                if (address.getText().toString().trim().equals(""))
                {
                    address.setError(getString(R.string.address));
                    address.requestFocus();
                } else {
                    if (email.getText().toString().trim().equals(""))
                    {
                        email.setError(getString(R.string.email_id));
                        email.requestFocus();
                    } else {
                        if (male.isChecked() || female.isChecked()) {
                            if (dob_flag) {
                                if (contact.getText().toString().trim().equals("")) {
                                    contact.setError(getString(R.string.contact_no));
                                    contact.requestFocus();
                                } else if (contact.length() == 10) {
                                    final ProgressDialog progressDialog = new ProgressDialog(this);
                                    progressDialog.setMessage("Signing Up...");
                                    progressDialog.show();

                                    final String name_ = name.getText().toString().trim();
                                    String address_ = address.getText().toString().trim();
                                    final String email_ = email.getText().toString().trim();
                                    final String contactno_ = contact.getText().toString().trim();
                                    String dob_ = dob.getText().toString().trim();

                                    Log.e("Name      >>>>  ", "    " + name_);
                                    Log.e("Address   >>>>  ", "    " + address_);
                                    Log.e("Email     >>>>  ", "    " + email_);
                                    Log.e("Gender   >>>>  ", "    " + genderg);
                                    Log.e("DOB       >>>>  ", "    " + dob_);
                                    Log.e("Contact   >>>>  ", "    " + contactno_);

                                    Gson gson = new GsonBuilder()
                                            .setLenient()
                                            .create();

                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(ConnectToServer.URL)
                                            .addConverterFactory(GsonConverterFactory.create(gson))
                                            .build();

                                    CategoryInterface service = retrofit.create(CategoryInterface.class);


                                    Call<Login> call = service.getLoginDetails(name_, address_, email_, genderg, dob_, contactno_);

                                    call.enqueue(new Callback<Login>() {
                                        @Override
                                        public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
                                            Login jsonResponse = response.body();
                                            Log.e("RES   >>>>   ", "" + response.message());
                                            progressDialog.dismiss();
                                            assert jsonResponse != null;
                                            if (jsonResponse.getLogin()) {
                                                Toast.makeText(Registration_.this, "Registered Successfully!!", Toast.LENGTH_SHORT).show();

                                                Intent i = new Intent(Registration_.this, Registration_.class);
                                                startActivity(i);
                                                finish();
                                                manager.setPreferences(getApplicationContext(),
                                                        "userflag", "true");
                                                manager.setPreferences(getApplicationContext(),
                                                        "username", name_);
                                                manager.setPreferences(getApplicationContext(),
                                                        "useremail", email_);
                                                manager.setPreferences(getApplicationContext(),
                                                        "usercontact", contactno_);
                                            } else {
                                                Log.e("RES   >>>>   ", "" + response.message());

                                                Toast.makeText(Registration_.this, "Invalid Uid/Password!!!", Toast.LENGTH_SHORT).show();
                                                name.setText("");
                                                address.setText("");
                                                email.setText("");
                                                //dob.setText("");
                                                contact.setText("");


                                            }
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                                            progressDialog.dismiss();
                                            Log.e("Error  ***", t.getMessage());
                                            Toast.makeText(Registration_.this, "RESPONSE ERROR!!! TRY AGAIN.", Toast.LENGTH_SHORT).show();
                                            name.setText("");
                                            address.setText("");
                                            email.setText("");
                                            // dob.setText("");
                                            contact.setText("");
                                        }
                                    });
                                } else {
                                    contact.setError(getString(R.string.tenDigit));
                                    contact.requestFocus();
                                    contact.setText("");

                                }
                            } else {
                                Toast.makeText(Registration_.this, "Select Date of Birth", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else {
                            Toast.makeText(Registration_.this, "Select Gender", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        }


        else
        {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    public void dtp(View view)
    {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        //Toast.makeText(Registration_.this, "dayOfMonth + \"-\" + (monthOfYear + 1) + \"-\" + year", Toast.LENGTH_SHORT).show();

                        dob.setText("DOB : "+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        dob_flag = true;

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}