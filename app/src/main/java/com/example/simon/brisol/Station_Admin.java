package com.example.simon.brisol;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import com.example.simon.brisol.RESTFetcher.Post;
import com.example.simon.brisol.RESTFetcher.ServerFiles;

import org.json.JSONObject;


/**
 * Created by SIMON on 8/19/2017.
 */

public class Station_Admin extends AppCompatActivity {

    private Button add, edit;
    private Post post = new Post();
    private String checker = "";
    private JSONObject param = new JSONObject();
    private String stationUrl = ServerFiles.recordStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.station_admin);

        add = (Button) findViewById(R.id.button_station_add);
        edit = (Button) findViewById(R.id.button_station_edit);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(view);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(view);
            }
        });
    }

    public void changeFragment(View view) {

        //Fragment fragment;
        View parentView;
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Station_Admin.this);

        if (view == findViewById(R.id.button_station_add)) {

            parentView = getLayoutInflater().inflate(R.layout.station_add, null);
            bottomSheetDialog.setContentView(parentView);
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) parentView.getParent());
            bottomSheetBehavior.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics()));
            bottomSheetDialog.show();
        }

        if (view == findViewById(R.id.button_station_edit)) {

            parentView = getLayoutInflater().inflate(R.layout.station_edit, null);
            bottomSheetDialog.setContentView(parentView);
            BottomSheetBehavior bottomSheetBehaviors = BottomSheetBehavior.from((View) parentView.getParent());
            bottomSheetBehaviors.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics()));
            bottomSheetDialog.show();
        }
    }


    public class AynscTask extends AsyncTask<String, Void, Integer> {

        ProgressDialog progressDialog = new ProgressDialog(Station_Admin.this);
        Integer result = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Registering...");
            progressDialog.show();
        }


        @Override
        protected Integer doInBackground(String... params) {

            checker = post.getData(stationUrl, param);
            if (checker.contains("it has been inserted")) {

                result = 1;
            }
            if (checker.contains("Station has not been inserted")) {

                result = 0;

            }
            return result;
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressDialog.hide();

            if(integer ==1){




            }

            if(result == 0){


            }
        }

    }
}
