package com.example.simon.brisol;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simon.brisol.RESTFetcher.Post;
import com.example.simon.brisol.RESTFetcher.ServerFiles;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SIMON on 8/11/2017.
 */

public class Register_Emp extends AppCompatActivity {

    String item = "";
    TextView select;
    private JSONObject param = new JSONObject();
    private EditText etFirstName, etLastName, etNrc, etphoneNumber, etpin, etUserName;
    private Button btnregister;
    private String paymentUrl;
    private View view;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_emp);

        etFirstName = (EditText) findViewById(R.id.edit_text_first_name);
        etLastName = (EditText) findViewById(R.id.edit_text_second_name);
        etNrc = (EditText) findViewById(R.id.edit_text_user_nrc);
        etphoneNumber = (EditText) findViewById(R.id.edit_phone_number);
        etpin = (EditText) findViewById(R.id.edit_user_pin);
        etUserName = (EditText) findViewById(R.id.edit_text_user_name);
        btnregister = (Button) findViewById(R.id.button_sign_up_user);


        select = (TextView) findViewById(R.id.textview_select_user);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = showRadioButtonDialogType();
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                paymentUrl = ServerFiles.registerEmployee;


                if (etUserName.getText().toString().trim().isEmpty() && etNrc.getText().toString().trim().isEmpty() && etphoneNumber.getText().toString().trim().isEmpty()) {


                    etFirstName.setError("Field is required");
                    etLastName.setError("Field is required");
                    etNrc.setError("Field is required");
                    etphoneNumber.setError("Field is required");
                    etpin.setError("Field is required");
                    etUserName.setError("Field is required");
                    select.setError("Field is required");


                } else {

                    try {
                        param.put("employeeType", item);
                        param.put("fname", etFirstName.getText().toString());
                        param.put("lname", etLastName.getText().toString());
                        param.put("nrc", etNrc.getText().toString());
                        param.put("phoneNumber", etphoneNumber.getText().toString());
                        param.put("pin", etpin.getText().toString());
                        param.put("userName", etUserName.getText().toString());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (CheckNetwork() && !etNrc.getText().toString().isEmpty() && !etpin.toString().isEmpty()) {
                        new RegEmpTask().execute(paymentUrl);
                        Log.e("employee", param.toString());
                    } else {

                        Snackbar.make(v.getRootView(), "Enter details", Snackbar.LENGTH_LONG).show();

                    }


                }


            }

        });
    }

    public boolean CheckNetwork() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            return true;
        } else {

            return false;

        }

    }

    public String showRadioButtonDialogType() {

        final String[] items = {"Attendant", "Director", "Accountant"};
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);

        alt_bld.setTitle("Select User Type");
        alt_bld.setSingleChoiceItems(items, -1, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int it) {
                item = items[it];
                select.setText(item);
                dialog.cancel();
            }
        });
        final AlertDialog alert = alt_bld.create();
        alert.show();

        return item;
    }


    public class RegEmpTask extends AsyncTask<String, Void, Integer> {
        Integer result = 0;
        ProgressDialog progressDialog = new ProgressDialog(Register_Emp.this);
        Post post = new Post();
        View view = new View(getApplicationContext());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Registering...");
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {


            String checker = post.getData(paymentUrl, param);

            Log.e("Balance", checker);

            if (checker.contains("fname")) {
                result = 1;

            }

            if (checker.contains("Unknown")) {

                result = 2;
            }


            return result;
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressDialog.hide();

            if (integer == 1) {

                Snackbar.make(view.getRootView(), "The User has been registered", Snackbar.LENGTH_LONG).show();

                etFirstName.setText("");
                etLastName.setText("");
                etNrc.setText("");
                etphoneNumber.setText("");
                etpin.setText("");
                etUserName.setText("");
                btnregister.setText("");

            }

            if (result == 2) {


                Snackbar.make(view.getRootView(), "Check employee details", Snackbar.LENGTH_LONG).show();


            }

        }
    }
}
