package com.example.simon.brisol;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simon.brisol.Helpers.Getters;
import com.example.simon.brisol.POJO.ReportDetailsPOJO;

import java.util.List;

/**
 * Created by SIMON on 8/6/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.GettersViewHolder> {

    private List<ReportDetailsPOJO> list;
    private Context context;
    private onItemClickListener itemClickListener;

    public RVAdapter(List<ReportDetailsPOJO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setOnItemClickListener(final onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }

    public interface onItemClickListener {

        void onItemClick(View v, int position);
    }

    public class GettersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CardView card_view;
        private TextView card_name;
        private TextView card_nrc;


        public GettersViewHolder(View itemView) {

            super(itemView);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            card_name = (TextView) itemView.findViewById(R.id.card_name);
            card_nrc = (TextView) itemView.findViewById(R.id.card_nrc);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {

                itemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }

    @Override
    public RVAdapter.GettersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_views, parent, false);
        GettersViewHolder gettersViewHolder = new GettersViewHolder(view);
        return gettersViewHolder;
    }

    @Override
    public void onBindViewHolder(RVAdapter.GettersViewHolder holder, int i) {
        holder.card_name.setText(list.get(i).getName());
        holder.card_nrc.setText(list.get(i).getNrc());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
