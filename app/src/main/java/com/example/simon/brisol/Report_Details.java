package com.example.simon.brisol;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.simon.brisol.POJO.ReportDetailsPOJO;
import com.example.simon.brisol.RESTFetcher.Post;
import com.example.simon.brisol.RESTFetcher.ServerFiles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SIMON on 8/6/2017.
 */

public class Report_Details extends AppCompatActivity {

    private RecyclerView recyclerview;
    private  ProgressBar progressBar;
    private String checker;
    private String reportUrl = ServerFiles.reportDetails;
    private String input;
    private List<ReportDetailsPOJO> reportlist;
    private ReportDetailsPOJO reportDetailsPOJO = new ReportDetailsPOJO();
    private JSONObject param = new JSONObject();
    public static String EXTRA_19 = "report_param";
    private RVAdapter adapter;


    // this the changed ClickListner you need
    private RVAdapter.onItemClickListener onItemClickListener =  new RVAdapter.onItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {

            Intent intent = new Intent(Report_Details.this,Table_Reports.class);
            intent.putExtra(Table_Reports.EXTRA_NRC,reportDetailsPOJO.getNrc());
            intent.putExtra(Table_Reports.EXTRA_NAME,reportDetailsPOJO.getName());
            startActivity(intent);
            System.gc();

        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_details);



        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_previous_item));
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        Intent intent = getIntent();
        String i = intent.getStringExtra(EXTRA_19);

        if(i.equals("19hrs")){

            try {
                param.put("19hrs",1);
                Log.e("report_param",param.toString());
                Log.e("report_param",EXTRA_19);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(i.equals("saturday")){

            try {
                param.put("19hrs",2);
                Log.e("report_param",param.toString());
                Log.e("report_param",EXTRA_19);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(i.equals("defaulted")){

            try {
                param.put("19hrs",3);
                Log.e("report_param",param.toString());
                Log.e("report_param",EXTRA_19);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(i.equals("dormant")){

            try {
                param.put("19hrs",4);
                Log.e("report_param",param.toString());
                Log.e("report_param",EXTRA_19);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        new AsyncReport().execute(reportUrl);

        /*String card_names[] = {"Eric Chinamasa", "Dennis Mubamba", "Pendo Manjele", "Alex Makukula", "Sambwa Chipungu", "Henry Gjibolyo", "Ali Mwanza"};
        String card_types[] = {"342878/66/1", "852369/68/1", "695847/69/1", "784542/12/1", "121241/61/1", "478956/67/1", "127485/66/1"};
*/

    }

    public void parseResult(String result){

        reportlist = new ArrayList<>();

        try{

            JSONArray array = new JSONArray(result);
            for(int i = 0; i<array.length(); i++){

                JSONObject post = array.getJSONObject(i);
                reportDetailsPOJO.setName(post.optString("fname")+" "+post.optString("lname"));
                reportDetailsPOJO.setNrc(post.optString("nrc"));

                reportlist.add(reportDetailsPOJO);
            }


        }catch (JSONException ex){

            ex.getLocalizedMessage();
        }

    }

    public void SetAdaptor(){

        recyclerview.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Report_Details.this);
        recyclerview.setLayoutManager(layoutManager);
        adapter = new RVAdapter(reportlist, Report_Details.this);
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

    }

    public class AsyncReport extends AsyncTask <String, Void, Integer>{

        private Integer result = 0;
        Post post = new Post();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            checker = post.getData(reportUrl,param);

            if(checker.contains("fname")){

                parseResult(checker);
                result = 1;

            }
            if(checker.contains("report:null")){

                result =2;
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressBar.setVisibility(View.GONE);

            if(result ==1){

                SetAdaptor();

            }

            else{
                Toast.makeText(Report_Details.this,"No Generated report yet",Toast.LENGTH_LONG).show();
            }
        }
    }


}
