package com.dongduk.hangeul.hangeul_test1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.ColorStateList;
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
    boolean radiostate=false;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("");
        TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.myWord));

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
        wordList.add(new MyWord("08.15", "살\n갑\n다", "겉\n으\n로\n\n보\n기\n\n보\n다\n속\n이\n\n너\n르\n다\n.\n", "집\n에\n나\n\n세\n간\n\n따\n위\n가\n"));
        wordList.add(new MyWord("08.13", "비\n보\n라", "휘\n몰\n아\n치\n는\n\n비\n.\n", "세\n찬\n\n바\n람\n과\n\n함\n께\n\n"));
        wordList.add(new MyWord("08.08", "밤\n볼", "살\n이\n\n볼\n록\n하\n게\n\n찐\n\n볼\n.\n", "입\n\n안\n에\n\n밤\n을\n\n문\n\n것\n처\n럼\n"));
        wordList.add(new MyWord("08.07", "치\n룽\n구\n니",  "낮\n잡\n아\n\n이\n르\n는\n\n말\n.\n", "어\n리\n석\n어\n서\n\n쓸\n모\n가\n\n없\n는\n\n사\n람\n을\n"));
        wordList.add(new MyWord("08.01", "쥐\n뿔", "것\n을\n\n비\n유\n적\n으\n로\n\n이\n르\n는\n\n말\n.\n", "아\n주\n\n보\n잘\n것\n없\n거\n나\n\n규\n모\n가\n\n작\n은\n\n"));
        wordList.add(new MyWord("07.31", "까\n치\n걸\n음",  "걷\n는\n\n걸\n음\n.\n", "발\n뒤\n꿈\n치\n를\n\n들\n고\n\n살\n살\n\n"));
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
                prevCenterPos = centerPos - 2;

                if (prevCenterPos != centerPos) {
                    // dehighlight the previously highlighted view
                    View prevView = recyclerView.getLayoutManager().findViewByPosition(prevCenterPos);

                    Log.d("preview", Integer.toString(prevCenterPos));
                    Log.d("centerView", Integer.toString(centerPos));

                    if(prevView != null && centerView != null) {
                        for (int i = prevCenterPos; i <= centerPos + 2; i++) {
                            View view = recyclerView.getLayoutManager().findViewByPosition(i);

                            if (i == centerPos) {
                                Log.d("radiostate", Boolean.toString(radiostate));
                                TextView textView = (TextView) centerView.findViewById(R.id.tvMyWord);

                                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                                textView.setTextColor(Color.BLACK);

                                TextView desc1 = (TextView) centerView.findViewById(R.id.tvDesc1);
                                TextView desc2 = (TextView) centerView.findViewById(R.id.tvDesc2);

                                desc1.setVisibility(View.VISIBLE);
                                desc2.setVisibility(View.VISIBLE);

                                if( radiostate == true ) {
                                    RadioButton radiobtn = (RadioButton) centerView.findViewById(R.id.radiobtn);
                                    radiobtn.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#c4792f")));
                                }
                            } else {
                                TextView textView = (TextView) view.findViewById(R.id.tvMyWord);

                                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                                textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
                                textView.setTextColor(Color.parseColor("#808080"));

                                TextView desc1 = (TextView) view.findViewById(R.id.tvDesc1);
                                TextView desc2 = (TextView) view.findViewById(R.id.tvDesc2);

                                desc1.setVisibility(View.GONE);
                                desc2.setVisibility(View.GONE);

                                if( radiostate == true ){
                                    RadioButton radiobtn = (RadioButton) view.findViewById(R.id.radiobtn);
                                    radiobtn.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#e1e0e0")));
                                }
                            }
                        }
                    }
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
        item.setTitle(getString(R.string.delete));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //수정버튼 클릭시
        if (id == R.id.barBtn) {

            if(item.getTitle().equals(getString(R.string.delete))){
                item.setTitle(R.string.cancel);
                btnDelete.setVisibility(View.VISIBLE);
                myWordAdapter.setRadioButton(true);
                recyclerView.setAdapter(deleteAdapter);
                radiostate = true;
            }
            else if(item.getTitle().equals(getString(R.string.cancel))){
                item.setTitle(R.string.delete);
                btnDelete.setVisibility(View.GONE);
                myWordAdapter.setRadioButton(false);
                recyclerView.setAdapter(myWordAdapter);
                radiostate = false;
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
        }
        else if(id == R.id.language) {
            Locale locale = getResources().getConfiguration().locale;
            String language =  locale.getLanguage();

            Locale en = Locale.US;
            Locale ko = Locale.KOREA;

            Configuration config = new Configuration();

            Log.d("MainActivity", "언어 : " + language);

            if(language.equals("en")){
                config.locale = ko;

            }
            else{
                config.locale = en;

            }
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());

            Intent intent = getIntent();
            finish();
            startActivity(intent);


        }
        else if (id == R.id.setting) {

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
