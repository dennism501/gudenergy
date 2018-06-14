package com.example.simon.brisol;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simon.brisol.RESTFetcher.Post;
import com.example.simon.brisol.RESTFetcher.ServerFiles;
import com.example.simon.brisol.SessionManager.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //TextInputLayout variables
    private TextInputLayout textInputLayoutPin;

    //EditText variables
    private EditText editTextPin, etUserName;

    //Button
    private Button buttonLogin;

    private String loginUrl = ServerFiles.loginEmployee;
    private Post post = new Post();
    private SessionManager session;
    private String checker;
    private String pin = "";
    private String username = "eric123";
    private JSONObject param = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = (Button) findViewById(R.id.button_log_in);
        editTextPin = (EditText) findViewById(R.id.edit_text_pin);
        etUserName = (EditText)findViewById(R.id.edit_text_username);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextPin.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Enter 4 Digit Pin!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                if (editTextPin.getText().toString().length() <= 3) {
                    Snackbar.make(view, "Enter 4 Digit Pin!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                if (CheckNetwork()) {

                    String pin = editTextPin.getText().toString();
                    username = etUserName.getText().toString();
                    //add username edittext


                    try {
                        param.put("userName", username);
                        param.put("pin", pin);
                        Log.e("Login",param.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    new Asynclogin().execute(loginUrl);


                } else {

                    Snackbar.make(view, "Please check internet connection", Snackbar.LENGTH_LONG)
                            .show();
                }

            }
        });

    }

    /*checks if the network is working*/
    public boolean CheckNetwork() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            return true;
        } else {

            return false;


        }

    }


    public class Asynclogin extends AsyncTask<String, Void, Integer> {

        boolean success = false;
        boolean failed = false;
        Integer result = 0;

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Logging in...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }


        @Override
        protected Integer doInBackground(String... params) {

            //send login details and returns result
            checker = post.getData(loginUrl, param);
            success = checker.contains("userName") && checker.contains("pin");

            try {

                if (success) {

                    //after successful login set user sessions
                    Log.e("Login success", checker);
                    result = 1;
                    //session.setLogin(true);

                }

                if (failed) {

                    result = 0;
                    Log.e("Login failed", checker);
                }


            } catch (Exception e) {

                e.printStackTrace();
                e.getLocalizedMessage();
            }

            Log.e("Result", checker);

            return result;
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressDialog.hide();

            if (integer == 1) {

                Intent intent = new Intent(MainActivity.this, Main.class);
                startActivity(intent);
                editTextPin.setText("");

            }

            if (integer == 0) {

               /* Snackbar.make(,"",Snackbar.LENGTH_LONG).show();*/

            }

        }
    }


}