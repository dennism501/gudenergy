package com.example.simon.brisol;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.simon.brisol.POJO.Stations;
import com.example.simon.brisol.RESTFetcher.Get;
import com.example.simon.brisol.RESTFetcher.Post;
import com.example.simon.brisol.RESTFetcher.ServerFiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SIMON on 8/5/2017.
 */

public class Register extends Fragment {

    final String nrcRegex = "\\d{6}\\/\\d{2}\\/\\d{1}";


    private EditText etDriverName;
    private AutoCompleteTextView acTextview;
    private EditText etDriverNrc;
    private EditText etVehicleLicenseNumber;
    private EditText etDriverPhoneNumer;
    private EditText etVehicleOwnerName;
    private EditText etVehicleOwnerPhoneNumber;
    private EditText etDriverLicenseNumber;
    private Button btRegister;
    private List<Stations> StationList;
    private JSONObject param = new JSONObject();
    private Post post = new Post();
    private Get get = new Get();
    private String checker;
    private Stations station = new Stations();
    private ProgressBar progressBar;
    private String stationsUrl = "";
    private String registerUrl = ServerFiles.registerClient;
    // = {"kalingalinga", "Kamwala", "Libala", "Lilayi", "Villa Elizabetha", "Villa Wanga", "Matero", "Makishi", "Chelston", "Avondale", ""};
    String[] stationarray;
    String[] stationresult;
    AutoCompleteTextView actv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register, container, false);


        etDriverNrc = (EditText) rootView.findViewById(R.id.edit_text_driver_nrc);
        etDriverName = (EditText) rootView.findViewById(R.id.edit_text_driver_name);
        etDriverLicenseNumber = (EditText) rootView.findViewById(R.id.edit_text_license_number);
        etVehicleLicenseNumber = (EditText) rootView.findViewById(R.id.edit_text_vehicle_reg_num);
        etDriverPhoneNumer = (EditText) rootView.findViewById(R.id.edit_text_driver_phone);
        etVehicleOwnerName = (EditText) rootView.findViewById(R.id.edit_text_owner_name);
        etVehicleOwnerPhoneNumber = (EditText) rootView.findViewById(R.id.edit_text_owner_num);
        btRegister = (Button) rootView.findViewById(R.id.button_sign_up);
        actv = (AutoCompleteTextView) rootView.findViewById(R.id.ac_station_name);
        stationsUrl = ServerFiles.getStations;

        //new AsyncStation().execute(stationsUrl);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stationName = actv.getText().toString();
                String driverName = etDriverName.getText().toString();
                String driverNrc = etDriverNrc.getText().toString();
                String driverPhoneNumber = etDriverPhoneNumer.getText().toString();
                String vehicleLicenseNumber = etVehicleLicenseNumber.getText().toString();
                String vehicleOwnerNumber = etVehicleOwnerPhoneNumber.getText().toString();
                String vehicleOwnerName = etVehicleOwnerName.getText().toString();
                String licenseNumber = etDriverLicenseNumber.getText().toString();
                String DriverFirstname = "";
                String DriverLastname = "";
                String OwnerFirstname = "";
                String OwnerLastname = "";

                String[] DriverName = driverName.split(" ");
                String[] OwnerName = vehicleOwnerName.split(" ");

                DriverFirstname = DriverName[0];
                DriverLastname = DriverName[1];
                OwnerFirstname = OwnerName[0];
                OwnerLastname = OwnerName[1];
                try {
                    param.put("driverFirstName", DriverFirstname);
                    param.put("driverLasrName", DriverLastname);
                    param.put("driverNrc", driverNrc);
                    param.put("driverLicenseNumber", licenseNumber);
                    param.put("driverPhoneNumber", driverPhoneNumber);
                    param.put("vehicleLicenseNumber", vehicleLicenseNumber);
                    param.put("vehicleOwnerNumber", vehicleOwnerNumber);
                    param.put("vehicleOwnerFirstName", OwnerFirstname);
                    param.put("vehicleOwnerLastName", OwnerLastname);

                    Log.e("test", param.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new AsyncRegister().execute(registerUrl);
            }
        });


        init();
        return rootView;
    }

    private void init() {
        etDriverNrc.setFilters(
                new InputFilter[]{
                        new PartialRegexInputFilter(nrcRegex)
                }
        );
        TextWatcher nrctextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString();
                if (value.matches(nrcRegex))
                    etDriverNrc.setTextColor(Color.BLACK);
                else
                    etDriverNrc.setTextColor(Color.RED);
            }
        };
    }

    public class AsyncRegister extends AsyncTask<String, Void, Integer> {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
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

            boolean success = false;
            boolean failed = false;

            checker = post.getData(registerUrl, param);

            success = checker.contains("Registered");
            failed = checker.contains("failed");

            try {

                if (success) {

                    result = 1;

                    //add output to show the user that the client has been registered
                }
                if (failed) {

                    result = 0;
                    //show that the client has not been added

                }


            } catch (Exception e) {

                e.getLocalizedMessage();
            }


            return result;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressDialog.hide();
        }
    }

    private String[] parseResult(String result) {


        try {
            StationList = new ArrayList<>();
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject post = array.getJSONObject(i);
                stationarray = new String[array.length()];
                station.setStationName(post.optString("name"));

                stationarray[i] = station.getStationName();

                    Log.e("Stations",String.valueOf(stationarray[i]));


            }

        } catch (JSONException ex) {

            ex.getLocalizedMessage();
        }

        return stationarray;

    }

    public class AsyncStation extends AsyncTask<String, Void, String[]> {

        Integer result = 0;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String[] doInBackground(String... params) {

            String checker = get.getData(stationsUrl);
            if (checker.contains("name")) {

               stationresult = new String[parseResult(checker).length];
                stationresult = parseResult(checker);
                result = 1;
            }
            if (checker.contains("result not found")) {

                result = 2;
            }


            return stationresult;
        }


        @Override
        protected void onPostExecute(String[] integer) {
            super.onPostExecute(integer);


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, stationarray);
                actv.setThreshold(1);
                actv.setAdapter(adapter);
                actv.setTextColor(Color.BLACK);

                Log.e("Loaded",String.valueOf(stationarray[1]));

            }


    }
}
