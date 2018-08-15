package com.dongduk.hangeul.hangeul_test1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
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
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyWordActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private int prevCenterPos;
    private Button btnDelete;
    private MenuItem item;
    private MyWordAdapter myWordAdapter;
    private MyWordAdapter deleteAdapter;
    private RecyclerView recyclerView;
    private List<MyWord> wordList;
    int centerPos;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("");
        TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvTitle.setText("나의 우리말");

        ViewStub stub = (ViewStub)findViewById(R.id.stub);
        stub.setLayoutResource(R.layout.activity_my_word);
        stub.inflate();

        btnDelete = (Button)findViewById(R.id.btnDelete);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        backPressCloseHandler = new BackPressCloseHandler(this);

        wordList = new ArrayList<>();
        wordList.add(new MyWord("       ", "", "", ""));
        wordList.add(new MyWord("       ", "", "", ""));
        wordList.add(new MyWord("09.20", "살\n갑\n다\n", "집\n에\n나\n\n세\n간\n\n따\n위\n가", "겉\n으\n로\n\n보\n기\n\n보\n다\n속\n이\n\n너\n르\n다."));
        wordList.add(new MyWord("09.18", "가\n시\n버\n시\n", "부\n부\n를\n\n속\n되\n게", "이\n르\n는\n\n말."));
        wordList.add(new MyWord("09.14", "미\n리\n내\n", "가\n르\n거\n나\n\n쪼\n개\n지\n\n않\n고.", "자\n연\n그\n대\n로\n\n언\n제\n나\n\n변\n함\n없\n이\n"));
        wordList.add(new MyWord("08.20", "꽃\n샘\n", " ", "봄\n철\n\n꽃\n이\n\n필\n\n무\n렵\n의\n\n추\n위."));
        wordList.add(new MyWord("08.13", "모\n가\n\n", "인\n부\n나\n\n광\n대\n등\n의\n\n우\n두\n머\n리.", "낮\n은\n\n패\n의\n\n우\n두\n머\n리"));
        wordList.add(new MyWord("09.09", "살\n갑\n다\n", "가\n르\n거\n나\n\n쪼\n개\n지\n\n않\n고.", "자\n연\n그\n대\n로\n\n언\n제\n나\n\n변\n함\n없\n이\n"));
        wordList.add(new MyWord("09.09", "꽃\n샘\n", "가\n르\n거\n나\n\n쪼\n개\n지\n\n않\n고.", "자\n연\n그\n대\n로\n\n언\n제\n나\n\n변\n함\n없\n이\n"));
        wordList.add(new MyWord("09.09", "미\n리\n내\n", "가\n르\n거\n나\n\n쪼\n개\n지\n\n않\n고.", "자\n연\n그\n대\n로\n\n언\n제\n나\n\n변\n함\n없\n이\n"));
        wordList.add(new MyWord("09.09", "미\n리\n내\n", "가\n르\n거\n나\n\n쪼\n개\n지\n\n않\n고.", "자\n연\n그\n대\n로\n\n언\n제\n나\n\n변\n함\n없\n이\n"));
        wordList.add(new MyWord("09.09", "미\n리\n내\n", "가\n르\n거\n나\n\n쪼\n개\n지\n\n않\n고.", "자\n연\n그\n대\n로\n\n언\n제\n나\n\n변\n함\n없\n이\n"));
        wordList.add(new MyWord("09.09", "미\n리\n내\n", "가\n르\n거\n나\n\n쪼\n개\n지\n\n않\n고.", "자\n연\n그\n대\n로\n\n언\n제\n나\n\n변\n함\n없\n이\n"));
        wordList.add(new MyWord("09.09", "미\n리\n내\n", "가\n르\n거\n나\n\n쪼\n개\n지\n\n않\n고.", "자\n연\n그\n대\n로\n\n언\n제\n나\n\n변\n함\n없\n이\n"));
        wordList.add(new MyWord("09.09", "미\n리\n내\n", "가\n르\n거\n나\n\n쪼\n개\n지\n\n않\n고.", "자\n연\n그\n대\n로\n\n언\n제\n나\n\n변\n함\n없\n이\n"));
        wordList.add(new MyWord("09.09", "미\n리\n내\n", "가\n르\n거\n나\n\n쪼\n개\n지\n\n않\n고.", "자\n연\n그\n대\n로\n\n언\n제\n나\n\n변\n함\n없\n이\n"));
        wordList.add(new MyWord("09.09", "미\n리\n내\n", "가\n르\n거\n나\n\n쪼\n개\n지\n\n않\n고.", "자\n연\n그\n대\n로\n\n언\n제\n나\n\n변\n함\n없\n이\n"));
        wordList.add(new MyWord("09.09", "미\n리\n내\n", "가\n르\n거\n나\n\n쪼\n개\n지\n\n않\n고.", "자\n연\n그\n대\n로\n\n언\n제\n나\n\n변\n함\n없\n이\n"));
        wordList.add(new MyWord("09.09", "미\n리\n내\n", "가\n르\n거\n나\n\n쪼\n개\n지\n\n않\n고.", "자\n연\n그\n대\n로\n\n언\n제\n나\n\n변\n함\n없\n이\n"));
        wordList.add(new MyWord("       ", "", "", ""));
        wordList.add(new MyWord("       ", "", "", ""));

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        // set up the RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(recyclerView);

        myWordAdapter = new MyWordAdapter(wordList, false);
        deleteAdapter = new MyWordAdapter(wordList, true);

        recyclerView.setAdapter(myWordAdapter);



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int center = recyclerView.getWidth() / 2;
                View centerView = recyclerView.findChildViewUnder(center, recyclerView.getTop());
                centerPos = recyclerView.getChildAdapterPosition(centerView);

                if (prevCenterPos != centerPos) {
                    // dehighlight the previously highlighted view
                    View prevView = recyclerView.getLayoutManager().findViewByPosition(prevCenterPos);

                    if (prevView != null) {
                        Log.d("preview", Integer.toString(prevCenterPos));
                        TextView textView = (TextView) prevView.findViewById(R.id.tvMyWord);
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
                        textView.setTextColor(Color.LTGRAY);

                        TextView desc1 = (TextView)prevView.findViewById(R.id.tvDesc1);
                        TextView desc2 = (TextView)prevView.findViewById(R.id.tvDesc2);

                        RadioButton radioButton = (RadioButton)prevView.findViewById(R.id.radio);


                        desc1.setVisibility(View.GONE);
                        desc2.setVisibility(View.GONE);

                    }

                    // highlight view in the middle
                    if (centerView != null) {
                        Log.d("centerView", Integer.toString(centerPos));
                        TextView textView = (TextView) centerView.findViewById(R.id.tvMyWord);

                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                        textView.setTextColor(Color.BLACK);

                        TextView desc1 = (TextView)centerView.findViewById(R.id.tvDesc1);
                        TextView desc2 = (TextView)centerView.findViewById(R.id.tvDesc2);

                        desc1.setVisibility(View.VISIBLE);
                        desc2.setVisibility(View.VISIBLE);
                    }

                    prevCenterPos = centerPos;
                }
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAdapter.onItemRemove(centerPos);
                myWordAdapter.onItemRemove(centerPos);

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            backPressCloseHandler.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.barbtn, menu);
        item = menu.getItem(0);
        item.setTitle("수정");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //수정버튼 클릭시
        if (id == R.id.barBtn) {

            if(item.getTitle().equals("수정")){
                item.setTitle("취소");
                btnDelete.setVisibility(View.VISIBLE);
                myWordAdapter.setRadioButton(true);
                recyclerView.setAdapter(deleteAdapter);

            }
            else if(item.getTitle().equals("취소")){
                item.setTitle("수정");
                btnDelete.setVisibility(View.GONE);
                myWordAdapter.setRadioButton(false);
                recyclerView.setAdapter(myWordAdapter);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        Intent mIntent;

        int id = item.getItemId();

        if (id == R.id.todayWord) {
            mIntent = new Intent(this, MainActivity.class);
            startActivity(mIntent);
            finish();


        } else if (id == R.id.myWord) {
            //현재페이지
        } else if (id == R.id.myRecord) {
            mIntent = new Intent(this, MyRecordActivity.class);
            startActivity(mIntent);
            finish();
        } else if (id == R.id.setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

//    AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
//        @Override
//        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//            final int pos = position;
//
//            delete_builder.setTitle("삭제 확인");
//            delete_builder.setMessage("삭제하시겠습니까?");
//            delete_builder.setPositiveButton("확인버튼",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            wordList.remove(pos);
//
//                            myWordAdapter.notifyDataSetChanged();
//                            deleteAdapter.notifyDataSetChanged();
//                        }
//                    }
//            );
//            delete_builder.setNegativeButton("취소버튼", null);
//
//            Dialog dlg = delete_builder.create();
//
//            dlg.setCanceledOnTouchOutside(true);
//            dlg.show();
//
//            return true;
//        }
//    };
}
