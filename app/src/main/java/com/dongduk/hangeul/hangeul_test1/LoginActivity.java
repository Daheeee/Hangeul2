package com.dongduk.hangeul.hangeul_test1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tsengvn.typekit.TypekitContextWrapper;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    String TAG = "LoginActivity";
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignin;
    private TextView textSignup;

    private TextInputLayout mEmailInputLayout;
    private TextInputLayout mPasswordInputLayout;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




//        ActionBar actionBar = getActionBar();
//        actionBar.hide();

        mEmailInputLayout = (TextInputLayout) findViewById(R.id.input_email);
        mPasswordInputLayout = (TextInputLayout) findViewById(R.id.input_password);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            Intent it2 = new Intent(LoginActivity.this, MainActivity.class);
            //넘겨줄 데이터의 key와 value
            user = firebaseAuth.getCurrentUser();
            it2.putExtra("user", user.getUid());
            startActivity(it2);
            //startActivity(new Intent(getApplicationContext(), MainActivity.class));

        }



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    // Splash(로딩화면) 띄우기
                    //startActivity(new Intent(LoginActivity.this, Splash.class));

                    //Intent it = new Intent(LoginActivity.this, MainActivity.class);
                    //넘겨줄 데이터의 key와 value
                    //it.putExtra("user", user.getUid());
                    //startActivity(it);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        // Splash(로딩화면) 띄우기
        //startActivity(new Intent(this, Splash.class));

        progressDialog = new ProgressDialog(this);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignin = (Button) findViewById(R.id.buttonSignIn);
        textSignup = (TextView) findViewById(R.id.textSignUp);

        buttonSignin.setOnClickListener(this);
        textSignup.setOnClickListener(this);

    }

    // 폰트적용
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    private void userLogin(){

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if(!(showError(email, password))){

            return;
        }

        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }
                        else{
//                            Toast.makeText(LoginActivity.this, "회원 정보가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            mEmailInputLayout.setError("회원 정보가 일치하지 않습니다.");
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        if (view == buttonSignin) {
            userLogin();
        }
        if (view == textSignup) {

            startActivity(new Intent(this, RegisterActivity.class));
        }

    }

    public boolean showError(String e, String p){
        mEmailInputLayout.setError(null);
        mPasswordInputLayout.setError(null);

        if (TextUtils.isEmpty(e)) {
//            Toast.makeText(LoginActivity.this, "email을 입력해주세요.", Toast.LENGTH_SHORT).show();
            mEmailInputLayout.setError("email을 입력해주세요.");
            return false;
        }
        if (TextUtils.isEmpty(p)) {
//            Toast.makeText(LoginActivity.this, "password를 입력해주세요.", Toast.LENGTH_SHORT).show();
            mPasswordInputLayout.setError("password를 입력해주세요.");
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

}

