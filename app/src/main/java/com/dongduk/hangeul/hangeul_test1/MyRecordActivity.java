package com.dongduk.hangeul.hangeul_test1;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.List;

public class MyRecordActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    final int ITEM_SIZE = 5;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvTitle.setText("나의 글자취");

        ViewStub stub = (ViewStub)findViewById(R.id.stub);
        stub.setLayoutResource(R.layout.activity_my_record);
        stub.inflate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backPressCloseHandler = new BackPressCloseHandler(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_record);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);


        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(recyclerView);

        List<MyRecordCard> cards = new ArrayList<>();
        MyRecordCard[] card = new MyRecordCard[ITEM_SIZE];
        card[0] = new MyRecordCard("2017.09.20", "살\n갑\n다\n","날\n\n보\n는\n\n그\n\n아\n이\n의\n\n표\n정\n이\n","살\n갑\n다\n.\n\n마\n음\n\n속\n에\n\n","꽃\n\n한\n송\n이\n가\n\n폈\n다\n.\n","");
        card[1] = new MyRecordCard("2017.09.15", "미\n쁘\n다\n","여\n기\n저\n기\n\n눈\n치\n를\n\n살\n피\n는\n","모\n습\n이\n\n도\n무\n지\n\n미\n쁘\n게\n","보\n이\n지\n\n않\n는\n다\n.\n","");
        card[2] = new MyRecordCard("2017.09.10", "여\n우\n비\n","한\n여\n름\n에\n\n예\n상\n치\n도\n못\n한\n","여\n우\n비\n를\n\n만\n났\n다\n.\n ","내\n\n마\n음\n도\n\n보\n슬\n보\n슬\n","");
        card[3] = new MyRecordCard("2017.09.02", "주\n니\n","오\n늘\n도\n\n어\n제\n와\n\n똑\n같\n은\n","반\n복\n되\n는\n\n하\n루\n에\n","밀\n려\n오\n는\n\n주\n니\n를\n\n떨\n치\n기\n ","힘\n들\n다\n");
        card[4] = new MyRecordCard("2017.08.30", "허\n출\n하\n다\n","과\n제\n를\n\n하\n다\n\n한\n끼\n도\n","먹\n지\n\n못\n했\n다\n.\n\n허\n출\n하\n다\n.\n","","");

        for (int i = 0; i < ITEM_SIZE; i++) {
            cards.add(card[i]);
        }

        MyRecordAdapter mAdapter = new MyRecordAdapter(getApplicationContext(), cards, R.layout.activity_my_record);

        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            backPressCloseHandler.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.barbtn, menu);
//        MenuItem item = menu.getItem(0);
//        item.setTitle("수정");
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.barBtn) {    // 수정버튼
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent mIntent;

        int id = item.getItemId();

        if (id == R.id.todayWord) {
            mIntent = new Intent(this, MainActivity.class);
            startActivity(mIntent);
            finish();
        } else if (id == R.id.myWord) {
            mIntent = new Intent(this, MyWordActivity.class);
            startActivity(mIntent);
            finish();
        } else if (id == R.id.myRecord) {
            // 현재 페이지
        } else if (id == R.id.setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_vmore_record:
                final LinearLayout dialogLayout = (LinearLayout) View.inflate(this, R.layout.dialog_viewmore, null);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        //.setTitle("온새미로")
                        .setView(dialogLayout)
                        .show();

                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(params);
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }
}
