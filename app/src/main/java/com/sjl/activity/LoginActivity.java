package com.sjl.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.sjl.view.dialog.loading.CustomLoadingDialog;
import com.sjl.uidemo.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button;
    private TextInputLayout mUsernameWraper;
    private TextInputLayout mPasswordWraper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        mUsernameWraper = (TextInputLayout) findViewById(R.id.usernameWraper);
        mPasswordWraper = (TextInputLayout) findViewById(R.id.passwordWraper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.user_login);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        button=(Button)findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() { // TextInputLayout可以取得包裹的EditText
        String username = mUsernameWraper.getEditText().getText().toString().trim();
        String password = mPasswordWraper.getEditText().getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            mUsernameWraper.setError("用户名不能为空");
            return;
        }else{
            // 移除错误提示信息
            mUsernameWraper.setErrorEnabled(false);
        }
        if(TextUtils.isEmpty(password)){
            mPasswordWraper.setError("密码不能为空");
            return;
        }else{
            // 移除错误提示信息
            mPasswordWraper.setErrorEnabled(false);
        }

        button.setEnabled(false);

//        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("登录中...");
//        progressDialog.show();


        final CustomLoadingDialog progressDialog = new CustomLoadingDialog(LoginActivity.this,R.style.CustomDialog);
        progressDialog.show();

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        button.setEnabled(true);
                        progressDialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 400);
    }
    @Override
    public void onClick(View v) {

    }

    /**
     * toolbar菜单点击监听
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 隐藏键盘
     */
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
