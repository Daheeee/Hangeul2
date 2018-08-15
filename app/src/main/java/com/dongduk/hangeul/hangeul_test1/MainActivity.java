package com.dongduk.hangeul.hangeul_test1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NetworkService networkService;
    private BackPressCloseHandler backPressCloseHandler;
    private FirebaseAuth auth;
    private FirebaseUser user;
    String email;
    TextView tvDateMain, tvWordMain, tvMeaning01, tvMeaning02, tvMeaning03, tvMeaning04, tvEmail;
    Button btnWrite;

    SimpleDateFormat df;

    String meaning, meaning01, meaning02, meaning03, meaning04;
    String wordMeaning;     // 오늘의 단어 전체 뜻 저장 변수
    int countSpace = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ViewStub stub = (ViewStub)findViewById(R.id.stub);
        stub.setLayoutResource(R.layout.content_main);
        stub.inflate();

        backPressCloseHandler = new BackPressCloseHandler(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            email = user.getEmail();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }

        tvDateMain = (TextView) findViewById(R.id.tvDateMain);
        tvWordMain = (TextView) findViewById(R.id.tvWordMain);
        tvMeaning01 = (TextView) findViewById(R.id.tvMeaning01);
        tvMeaning02 = (TextView) findViewById(R.id.tvMeaning02);
        tvMeaning03 = (TextView) findViewById(R.id.tvMeaning03);
        tvMeaning04 = (TextView) findViewById(R.id.tvMeaning04);
        tvEmail = (TextView)navigationView.getHeaderView(0).findViewById(R.id.tvUserId);
        btnWrite = (Button) findViewById(R.id.btnWrite);

        tvEmail.setText(email);

        df = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        String currentDateTimeString = df.format(new Date());
        tvDateMain.setText(currentDateTimeString);

        ApplicationController application = ApplicationController.getInstance();
        //application.buildNetworkService("ab2a6169.ngrok.io");
        application.buildNetworkService("54.237.215.221", 8000);
        networkService = ApplicationController.getInstance().getNetworkService();

        //Restaurant GET
        Call<Word> getCall = networkService.get_word();
        getCall.enqueue(new Callback<Word>() {
            @Override
            public void onResponse(Call<Word> call, Response<Word> response) {
                if( response.isSuccessful()) {
                    Word word = response.body();

                    wordMeaning = word.getWordDesc();

                    meaning01 = ""; meaning02 = ""; meaning03 = ""; meaning04 = "";
                    countSpace = 0;

                    SharedPreferences pr = getSharedPreferences("pr", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pr.edit();

                    editor.putString("word", word.getWord());
                    editor.putString("wid", Long.toString(word.getWid()));
//        editor.putString(tvWordMain.getText().toString(), tvMeaning01.getText().toString() + tvMeaning02.getText().toString() + tvMeaning03.getText().toString() + tvMeaning04.getText().toString());
                    editor.putString(word.getWord(), word.getWordDesc());
                    editor.commit();

                    for(int i = 0; i < word.getWordDesc().length(); i++) {
                        meaning = "";

                        if(word.getWordDesc().charAt(i) == ' ') {
                            meaning += word.getWordDesc().charAt(i) + "\n\n";
                            countSpace ++;
                        } else {
                            meaning += word.getWordDesc().charAt(i);
                        }

                        if (countSpace <3) meaning01 += meaning;
                        else if (countSpace >=3 && countSpace < 6) meaning02 += meaning;
                        else if (countSpace >=6 && countSpace < 9) meaning03 += meaning;
                        else if (countSpace >=9) meaning04 += meaning;
                    }

                    tvWordMain.setText(word.getWord().toString());
                    tvMeaning01.setText("\n\n" + meaning01);
                    tvMeaning02.setText(meaning02);
                    tvMeaning03.setText(meaning03);
                    tvMeaning04.setText(meaning04);

                } else {
                    int StatusCode = response.code();
                    Log.i(ApplicationController.TAG, "Status Code : " + StatusCode + " Error Message : " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Word> call, Throwable t) {
                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
            }
        });



    }

    public void onClick(View v) {
        Intent mIntent;

        switch(v.getId()) {
            case R.id.btnWrite:
                mIntent = new Intent(this, WritingActivity.class);
                startActivity(mIntent);
                finish();


                break;
            case R.id.btnList:
                mIntent = new Intent(this, ListingActivity.class);
                startActivity(mIntent);
                break;
        }
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
        MenuItem item = menu.getItem(0);
        item.setTitle("담기");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.barBtn) {
            final LinearLayout dialogLayout = (LinearLayout) View.inflate(this, R.layout.dialog_saveword, null);

            TextView title = new TextView(this);
            title.setText(tvWordMain.getText());
            title.setGravity(Gravity.CENTER);
            title.setTextColor(Color.parseColor("#c4792f"));

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(tvWordMain.getText())
                    .setView(dialogLayout)
                    .show();

            //dialog.setCustomTitle(title);

            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.alpha = 50;
            dialog.getWindow().setAttributes(params);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent mIntent;

        int id = item.getItemId();

        if (id == R.id.todayWord) {
            // 현재페이지
        } else if (id == R.id.myWord) {
            mIntent = new Intent(this, MyWordActivity.class);
            startActivity(mIntent);
            finish();
        } else if (id == R.id.myRecord) {
            mIntent = new Intent(this, MyRecordActivity.class);
            startActivity(mIntent);
            finish();
        } else if (id == R.id.setting) {
            // 로그아웃
            auth.getInstance().signOut();
            Intent sign_intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(sign_intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "클릭", Toast.LENGTH_SHORT).show();

        }
    };
}
