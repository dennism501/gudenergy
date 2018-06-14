package com.example.simon.brisol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by SIMON on 8/5/2017.
 */

public class Reports extends Fragment {

    //Button nineteen, saturday;
    LinearLayout nineteen, saturday,defaulted,dormant;

    String input;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reports, container, false);

        nineteen = (LinearLayout) rootView.findViewById(R.id.nineteen);
        saturday = (LinearLayout) rootView.findViewById(R.id.saturday);
        defaulted = (LinearLayout) rootView.findViewById(R.id.defaulted);
        dormant = (LinearLayout) rootView.findViewById(R.id.dormant);


        nineteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Report_Details.class);
                intent.putExtra(Report_Details.EXTRA_19, 1);
                startActivity(intent);
                System.gc();
            }
        });

        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Report_Details.class);
                intent.putExtra(Report_Details.EXTRA_19, 2);
                startActivity(intent);
                System.gc();
            }
        });

        defaulted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Report_Details.class);
                intent.putExtra(Report_Details.EXTRA_19, 3);
                startActivity(intent);
                System.gc();

            }
        });

        dormant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Report_Details.class);
                intent.putExtra(Report_Details.EXTRA_19, 4);
                startActivity(intent);
                System.gc();

            }
        });

        return rootView;
    }
}
