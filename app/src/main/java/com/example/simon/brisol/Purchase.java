package com.example.simon.brisol;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simon.brisol.POJO.BalancePayment;
import com.example.simon.brisol.RESTFetcher.Post;
import com.example.simon.brisol.RESTFetcher.ServerFiles;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SIMON on 8/5/2017.
 */

public class Purchase extends Fragment {

    private final String nrcRegex = "\\d{6}\\/\\d{2}\\/\\d{1}";
    private Button payment, select, btnbalancePayment, btpayment;
    private EditText nrc_payment, etBlanaceAmount, etPayment;
    private String item = "";
    private JSONObject param = new JSONObject();
    private String paymentUrl;
    private List<BalancePayment> creditorName;
    private TextView txtnameBalance, txtCreditName;
    BalancePayment balancePayment = new BalancePayment();
    String name;
    int hold = 0;
    String nrc;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.purchase, container, false);

        payment = (Button) rootView.findViewById(R.id.button_payment);
        nrc_payment = (EditText) rootView.findViewById(R.id.edit_text_make_payment);


        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                paymentUrl = ServerFiles.credit_payment;
                nrc = nrc_payment.getText().toString();
                try {
                    param.put("nrc", nrc);
                    Log.e("nrc", nrc);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (!nrc.isEmpty() && CheckNetwork()) {

                    new AsyncPayment().execute(paymentUrl);
                } else {

                    Snackbar.make(rootView.getRootView(), "Enter Nrc Number", Snackbar.LENGTH_LONG).show();

                }


            }
        });

        init();

        return rootView;
    }

    public boolean CheckNetwork() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            return true;
        } else {

            return false;


        }

    }

    private void init() {
        nrc_payment.setFilters(
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
                    nrc_payment.setTextColor(Color.BLACK);
                else
                    nrc_payment.setTextColor(Color.RED);
            }
        };
    }

    public String showRadioButtonDialogFuel() {

        final String[] items = {"Petrol", "Diesel"};
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(getActivity());

        alt_bld.setTitle("Select Fuel");
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

    public void parseResult(String result) {

        try {
            JSONObject details = new JSONObject(result);
            balancePayment = new BalancePayment();
            balancePayment.setFname(details.optString("fname"));
            balancePayment.setLname(details.optString("lname"));
            balancePayment.setAmountOwning(details.optString("Balance"));
            balancePayment.setBalanceAmount(Integer.parseInt(details.optString("Balance")));
            hold = balancePayment.getBalanceAmount();
            creditorName = new ArrayList<>();

            name = balancePayment.getFname() + " " + balancePayment.getLname() + " has a Balance of K"
                    + balancePayment.getAmountOwning() + "\nEnter paid Amount below to clear Balance";

        } catch (JSONException e) {

            e.printStackTrace();
        }

    }

    public class AsyncBalance extends AsyncTask<String, Void, Integer>{


        Post post = new Post();
        ProgressDialog progressDialog = new ProgressDialog(getContext());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }


        @Override
        protected Integer doInBackground(String... params) {

            String checker = post.getData(paymentUrl, param);

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressDialog.hide();

            Toast.makeText(getContext(),"Balance has been entered",Toast.LENGTH_LONG).show();
        }

    }

    public class AsyncPayment extends AsyncTask<String, Void, Integer> {

        Integer result = 0;
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        Post post = new Post();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {


            String checker = post.getData(paymentUrl, param);

            Log.e("Balance", checker);
            parseResult(checker);


            if (hold > 0) {

                result = 1;

            }
            if (hold == 0) {
                result = 2;
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressDialog.hide();

            if (result == 1) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
                View parentView = getActivity().getLayoutInflater().inflate(R.layout.fragment_balance, null);
                bottomSheetDialog.setContentView(parentView);
                txtnameBalance = (TextView) parentView.findViewById(R.id.txt_balance_name);
                etBlanaceAmount = (EditText) parentView.findViewById(R.id.edit_text_add_payment);
                txtnameBalance.setText(name);
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) parentView.getParent());
                bottomSheetBehavior.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics()));
                bottomSheetDialog.show();

                btnbalancePayment = (Button) parentView.findViewById(R.id.button_payment);
                btnbalancePayment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String balanceA = etBlanaceAmount.getText().toString();
                        int balanceAmount = Integer.parseInt(balanceA);

                        paymentUrl = ServerFiles.credit_balance;

                        try {

                                param.put("balanceAmount", balanceAmount);


                               // Snackbar.make(v.getRootView(), "Shafulisha", Snackbar.LENGTH_LONG).show();

                            param.put("nrc", nrc);
                            Log.e("Credit params", param.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (!etBlanaceAmount.getText().toString().isEmpty() && CheckNetwork()) {
                            new AsyncBalance().execute(paymentUrl);
                            Snackbar.make(v.getRootView(),etBlanaceAmount.getText().toString()+ " Has been entered",Snackbar.LENGTH_LONG).show();
                            bottomSheetDialog.hide();
                        } else {

                            Snackbar.make(v.getRootView(), "Enter Nrc Number", Snackbar.LENGTH_LONG).show();
                        }
                    }


                });


            }
            if (integer == 2) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
                final View parentView = getActivity().getLayoutInflater().inflate(R.layout.dialog_payment, null);
                bottomSheetDialog.setContentView(parentView);
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) parentView.getParent());
                etPayment = (EditText) parentView.findViewById(R.id.edit_text_payment_amount);
                txtCreditName = (TextView) parentView.findViewById(R.id.textview_credit_name);
                txtCreditName.setText(balancePayment.getFname() + " " + balancePayment.getLname());
                bottomSheetBehavior.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics()));
                bottomSheetDialog.show();

                select = (Button) parentView.findViewById(R.id.button_select_fuel);
                btpayment = (Button) parentView.findViewById(R.id.intial_payment);

                select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        item = showRadioButtonDialogFuel();

                    }
                });

                btpayment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        paymentUrl = ServerFiles.recordPayment;
                        int amount = Integer.parseInt(etPayment.getText().toString());


                        try {
                            if(!item.isEmpty() && item!=null) {
                                param.put("amount", amount);
                                param.put("type", item);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (CheckNetwork() && !etPayment.getText().toString().isEmpty() ) {
                            new AsyncPayment().execute(paymentUrl);
                            bottomSheetDialog.hide();


                        } else {

                            Snackbar.make(v.getRootView(), "Check internet connection", Snackbar.LENGTH_LONG).show();

                        }

                    }
                });
            }


        }
    }
}