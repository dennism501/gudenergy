package com.example.simon.brisol;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.simon.brisol.Helpers.Getters;
import com.example.simon.brisol.RESTFetcher.Post;
import com.example.simon.brisol.RESTFetcher.ServerFiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * Created by SIMON on 8/15/2017.
 */

public class Table_Reports extends AppCompatActivity {

    String[] spaceProbeHeaders = {"Date", "Transactions", "Debit", "Credit", "Balance"};
    String[][] spaceProbes;
    String name;
    public static String EXTRA_NRC = "nrc";
    public static String EXTRA_NAME = "name";
    private JSONObject param = new JSONObject();
    private String checker;
    ArrayList<Getters> spaceprobeList;
    private String reportByNrc = ServerFiles.reportByNc;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_report);

        name = getIntent().getStringExtra(EXTRA_NAME);

        getSupportActionBar().setTitle(name);

        final TableView<String[]> tb = (TableView<String[]>) findViewById(R.id.tableView);
        tb.setColumnCount(5);
        tb.setHeaderBackgroundColor(R.color.purpleStart);

        //populateData();

        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this, spaceProbeHeaders));
        tb.setDataAdapter(new SimpleTableDataAdapter(this, spaceProbes));
    }

    /*private void populateData() {

        Getters getters = new Getters();

        ArrayList<Getters>
        getters.set_date("15 Aug 2017");
        getters.set_transactions("Bought Fuel");
        getters.set_debit("250");
        getters.set_credit(" ");
        getters.set_balance(" ");
        spaceprobeList.add(getters);

        getters = new Getters();
        getters.set_date("17 Aug 2017");
        getters.set_transactions("Paid");
        getters.set_debit(" ");
        getters.set_credit("280");
        getters.set_balance(" ");
        spaceprobeList.add(getters);

        getters = new Getters();
        getters.set_date("17 Aug 2017");
        getters.set_transactions("Bought Fuel");
        getters.set_debit("250");
        getters.set_credit(" ");
        getters.set_balance(" ");
        spaceprobeList.add(getters);

        getters = new Getters();
        getters.set_date("19 Aug 2017");
        getters.set_transactions("Paid");
        getters.set_debit(" ");
        getters.set_credit("270");
        getters.set_balance("10");
        spaceprobeList.add(getters);

        spaceProbes = new String[spaceprobeList.size()][5];

        for (int i = 0; i < spaceprobeList.size(); i++) {

            Getters g = spaceprobeList.get(i);

            spaceProbes[i][0] = g.get_date();
            spaceProbes[i][1] = g.get_transactions();
            spaceProbes[i][2] = g.get_debit();
            spaceProbes[i][3] = g.get_credit();
            spaceProbes[i][4] = g.get_balance();
        }
    }
    */

    public void parseResult(String result){

        spaceprobeList = new ArrayList<>();



        try {
            JSONArray array = new JSONArray(result);
            Getters getters = new Getters();
            for(int i = 0; i<array.length(); i++) {
                JSONObject post = array.getJSONObject(i);


            }

            spaceProbes = new String[spaceprobeList.size()][5];

            for (int i = 0; i < spaceprobeList.size(); i++) {

                Getters g = spaceprobeList.get(i);

                spaceProbes[i][0] = g.get_date();
                spaceProbes[i][1] = g.get_transactions();
                spaceProbes[i][2] = g.get_debit();
                spaceProbes[i][3] = g.get_credit();
                spaceProbes[i][4] = g.get_balance();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public class AsyncReportByNrc extends AsyncTask<String, Void, Integer> {

        Integer result = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... params) {

            Post post = new Post();
            checker = post.getData(reportByNrc,param);

            if(checker.contains("Date")){


                parseResult(checker);
                result =1;

            }
            if(checker.contains("")){

                Toast.makeText(Table_Reports.this,"Reports not generated",Toast.LENGTH_LONG).show();
                result=2;
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }
}