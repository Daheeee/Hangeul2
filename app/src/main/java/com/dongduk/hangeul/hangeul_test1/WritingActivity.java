package com.dongduk.hangeul.hangeul_test1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WritingActivity extends BaseActivity {

    private NetworkService networkService;

    LinearLayout dialogLayout;
    AlertDialog dialog;

    TextView tvWordWriting, tvMeaningWriting;
    EditText etWriting;
    Long wid;
    String uid;

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvTitle.setText("");

        ViewStub stub = (ViewStub)findViewById(R.id.stub);
        stub.setLayoutResource(R.layout.activity_writing);
        stub.inflate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ApplicationController application = ApplicationController.getInstance();
        //application.buildNetworkService("ab2a6169.ngrok.io");
        application.buildNetworkService("54.237.215.221", 8000);
        networkService = ApplicationController.getInstance().getNetworkService();

        tvWordWriting = (TextView) findViewById(R.id.tvWordWriting);
        tvMeaningWriting = (TextView) findViewById(R.id.tvMeaningWriting);
        etWriting = (EditText)findViewById(R.id.et_writing);

        SharedPreferences pr = getSharedPreferences("pr", MODE_PRIVATE);
        tvWordWriting.setText(pr.getString("word", ""));
        tvMeaningWriting.setText(pr.getString(tvWordWriting.getText().toString(), ""));

        wid = Long.parseLong(pr.getString("wid", ""));

        Intent intent = getIntent();
        //uid = intent.getStringExtra("user");

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            uid = user.getUid();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.barbtn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.barBtn:
                dialog = new AlertDialog.Builder(this)
                        .setMessage("글을 저장합니다")

                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                post_writing();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();

                return true;

            case android.R.id.home:
                dialogLayout = (LinearLayout) View.inflate(this, R.layout.dialog_end_writing, null);
                dialog = new AlertDialog.Builder(this)
                        .setView(dialogLayout)
                        .show();

                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(params);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v){


        switch (v.getId()) {
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.btn_end_writing:
                Intent mIntent;
                mIntent = new Intent(WritingActivity.this, MainActivity.class);
                startActivity(mIntent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        dialogLayout = (LinearLayout) View.inflate(this, R.layout.dialog_end_writing, null);
        dialog = new AlertDialog.Builder(this)
                .setView(dialogLayout)
                .show();

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);

    }

    public void post_writing(){
        //POST
        Writing writing = new Writing();


        writing.setUid(uid);
        writing.setWid(wid);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        String currentDateTimeString = df.format(new Date());

        writing.setDate(currentDateTimeString);
        writing.setWriting(etWriting.getText().toString());

        Log.d("datetime", uid.toString());

        Call<Writing> postCall = networkService.post_writing(writing);
        postCall.enqueue(new Callback<Writing>() {
            @Override
            public void onResponse(Call<Writing> call, Response<Writing> response) {
                if( response.isSuccessful()) {
                    Intent mIntent;
                    mIntent = new Intent(WritingActivity.this, MainActivity.class);
                    startActivity(mIntent);
                    finish();

                } else {
                    int StatusCode = response.code();
                    Log.i(ApplicationController.TAG, "Status Code : " + StatusCode);
                }

            }

            @Override
            public void onFailure(Call<Writing> call, Throwable t) {
                Log.i(ApplicationController.TAG, "Fail Message : " + t.getMessage());
            }
        });
    }
}
