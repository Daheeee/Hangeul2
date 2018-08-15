package com.dongduk.hangeul.hangeul_test1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListingActivity extends BaseActivity {

    final int ITEM_SIZE = 5;

    TextView tvWordList, tvMeaningList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvTitle.setText("");

        ViewStub stub = (ViewStub)findViewById(R.id.stub);
        stub.setLayoutResource(R.layout.activity_listing);
        stub.inflate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvWordList = (TextView) findViewById(R.id.tvWordList);
        tvMeaningList = (TextView) findViewById(R.id.tvMeaningList);

        SharedPreferences pr = getSharedPreferences("pr", MODE_PRIVATE);
        tvWordList.setText(pr.getString("word", ""));
        tvMeaningList.setText(pr.getString(tvWordList.getText().toString(), ""));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_listing);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(recyclerView);

        List<ListingCard> cards = new ArrayList<>();
        ListingCard[] card = new ListingCard[ITEM_SIZE];
        card[0] = new ListingCard("2017.09.17","날\n\n 보\n는\n\n 그\n\n 아\n이\n의\n\n 표\n정\n이\n\n ","살\n갑\n다\n.\n\n마\n음\n\n속\n에\n ","꽃\n\n한\n송\n이\n가\n\n폈\n다\n.\n","","","");
        card[1] = new ListingCard("2017.09.17","찰\n랑\n찰\n랑\n\n밀\n려\n\n들\n어\n오\n는\n\n","물\n결\n이\n\n어\n떻\n게\n\n살\n가\n운\n지\n\n","몰\n랐\n다\n.\n\n","","","");
        card[2] = new ListingCard("2017.09.16","겉\n으\n로\n는\n\n다\n가\n가\n기\n\n","어\n려\n워\n보\n여\n도\n\n선\n생\n님\n은\n\n","그\n\n누\n구\n보\n다\n\n살\n가\n운\n","분\n이\n셨\n다\n.\n\n","","");
        card[3] = new ListingCard("2017.09.14","형\n이\n라\n고\n\n해\n도\n\n살\n가\n운\n","정\n을\n\n느\n끼\n기\n보\n다\n는\n\n","믿\n음\n직\n스\n러\n우\n면\n서\n도\n\n어\n려\n웠\n다\n.\n","","","");
        card[4] = new ListingCard("2017.09.10","살\n갑\n기\n는\n\n평\n양\n\n나\n막\n신\n\n","-\n속\n담\n","","","","");

        for (int i = 0; i < ITEM_SIZE; i++) {
            cards.add(card[i]);
        }

        recyclerView.setAdapter(new ListingAdapter(getApplicationContext(), cards, R.layout.activity_listing));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onClick(View v) {
        final LinearLayout dialogLayout;
        AlertDialog dialog;

        switch (v.getId()) {

            case R.id.btn_vmore_list:
                dialogLayout = (LinearLayout) View.inflate(this, R.layout.dialog_viewmore, null);
                dialog = new AlertDialog.Builder(this)
                        .setView(dialogLayout)
                        .show();

                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(params);
                break;

        }
    }
}
