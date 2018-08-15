package com.dongduk.hangeul.hangeul_test1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by yoon1 on 2017-09-17.
 */

public class MyRecordAdapter extends RecyclerView.Adapter<MyRecordAdapter.ViewHolder> {
    Context context;
    List<MyRecordCard> cards;
    int item_layout;



    public MyRecordAdapter(Context context, List<MyRecordCard> cards, int item_layout) {
        this.context = context;
        this.cards = cards;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_myrecord, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MyRecordCard card = cards.get(position);
        holder.tvDateRecord.setText(card.getTvDateRecord());
        holder.tvWordRecord.setText(card.getTvWordRecord());
        holder.tvContentRecord01.setText(card.getTvContentRecord01());
        holder.tvContentRecord02.setText(card.getTvContentRecord02());
        holder.tvContentRecord03.setText(card.getTvContentRecord03());
        holder.tvContentRecord04.setText(card.getTvContentRecord04());
        holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
            int pos = position;
            @Override
            public boolean onLongClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public  void onClick(DialogInterface dialog, int which) {
                        switch ( which ) {
                            case DialogInterface.BUTTON_POSITIVE:
                                cards.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());
                                notifyDataSetChanged();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setMessage("삭제하시겠습니까?")
                        .setPositiveButton("네", dialogClickListener)
                        .setNegativeButton("아니오", dialogClickListener)
                        .show();

                return  true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.cards.size();
    }

    public void onItemRemove(int position) {
        cards.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDateRecord;
        TextView tvWordRecord;
        TextView tvContentRecord01;
        TextView tvContentRecord02;
        TextView tvContentRecord03;
        TextView tvContentRecord04;
        Button btnVmoveRecord;
        Button btnDeleteRecord;
        CardView cardview;


        public ViewHolder(View itemView) {
            super(itemView);

            tvDateRecord = (TextView) itemView.findViewById(R.id.tvDateRecord);
            tvWordRecord = (TextView) itemView.findViewById(R.id.tvWordRecord);
            tvContentRecord01 = (TextView) itemView.findViewById(R.id.tvContentRecord01);
            tvContentRecord02 = (TextView) itemView.findViewById(R.id.tvContentRecord02);
            tvContentRecord03 = (TextView) itemView.findViewById(R.id.tvContentRecord03);
            tvContentRecord04 = (TextView) itemView.findViewById(R.id.tvContentRecord04);
            btnVmoveRecord = (Button) itemView.findViewById(R.id.btn_vmore_record);
            //btnDeleteRecord = (Button) v.findViewById(R.id.btnDeleteRecord);
            cardview = (CardView) itemView.findViewById(R.id.cv_myrecord);
        }
    }

}
