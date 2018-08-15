package com.dongduk.hangeul.hangeul_test1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tsengvn.typekit.TypekitContextWrapper;

public class RegisterActivity extends BaseActivity {

    String TAG = "RegisterActivity";
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRePassword;

    private TextInputLayout mEmailInputLayout;
    private TextInputLayout mPasswordInputLayout;
    private TextInputLayout mRePasswordInputLayout;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private ActionBar actionBar;
    private Toolbar toolbar;

    private Button btnDelete;
    private MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvTitle.setText("회원가입");

        ViewStub stub = (ViewStub)findViewById(R.id.stub);
        stub.setLayoutResource(R.layout.activity_register);
        stub.inflate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnDelete = (Button)findViewById(R.id.btnDelete);
        mEmailInputLayout = (TextInputLayout) findViewById(R.id.input_email);
        mPasswordInputLayout = (TextInputLayout) findViewById(R.id.input_password);
        mRePasswordInputLayout = (TextInputLayout) findViewById(R.id.input_re_password);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        }

        progressDialog = new ProgressDialog(this);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextRePassword  = (EditText) findViewById(R.id.editTextPassword2);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.barbtn, menu);
        item = menu.getItem(0);
        item.setTitle("완료");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();

        registerUser();

        return super.onOptionsItemSelected(item);
    }


    private void registerUser() {

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String password2 = editTextRePassword.getText().toString();

        Log.d(TAG, "preshowError");
        if(!(showError(email, password, password2))){
            Log.d(TAG, "shoError");
            return;
        }
        Log.d(TAG, "register");

        progressDialog.setMessage("회원가입을 진행중입니다...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                if (task.isSuccessful()) {

                                    //user is successfully registered and logged in
                                    //we will start the profile activity here

                                    finish();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                } else {
//                                    Toast.makeText(RegisterActivity.this, "회원가입이 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                    mEmailInputLayout.setError(null);
                                    mPasswordInputLayout.setError(null);
                                    mRePasswordInputLayout.setError(null);
                                    mEmailInputLayout.setError("회원가입이 실패하였습니다. 다시 시도해주세요.");
                                }
                            }
                        }
                );

        progressDialog.hide();
    }


    public boolean showError(String e, String p, String p2){
        mEmailInputLayout.setError(null);
        mPasswordInputLayout.setError(null);
        mRePasswordInputLayout.setError(null);

        if (TextUtils.isEmpty(e)) {
//            Toast.makeText(LoginActivity.this, "email을 입력해주세요.", Toast.LENGTH_SHORT).show();
            mEmailInputLayout.setError("email을 입력해주세요.");
            return false;
        }
        else if (TextUtils.isEmpty(p)) {
//            Toast.makeText(LoginActivity.this, "password를 입력해주세요.", Toast.LENGTH_SHORT).show();
            mPasswordInputLayout.setError("password를 입력해주세요.");
            return false;
        }
        else if(TextUtils.isEmpty(p2)) {
            mRePasswordInputLayout.setError("password를 재입력해주세요.");
            return false;
        }
        else if(!p.equals(p2)){
            mRePasswordInputLayout.setError("password가 일치하지 않습니다.");
            return false;
        }

        return true;
    }

    // 폰트적용
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


}
