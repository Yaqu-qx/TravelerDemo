package com.example.travelor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelor.util.SpfUtil;

import com.example.travelor.datebase.UsersDbOpenHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText etAccount, etPassword;
    private Button btnLogin;
    private TextView etSignIn;
    private CheckBox cbRememberMe;

    private UsersDbOpenHelper mUsersDbOpenHelper;

    public static final String KEY_ACCOUNT = "account";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_REMEMBER_CHECK = "remember_check";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        etAccount = findViewById(R.id.account);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.buttonLogin);
        etSignIn = findViewById(R.id.sign_new_account);
        cbRememberMe = findViewById(R.id.checkbox);

        // 自动填充记录
        String rmAccount = SpfUtil.getString(this, KEY_ACCOUNT);
        String rmPassword = SpfUtil.getString(this, KEY_PASSWORD);
        Boolean rmCheck = SpfUtil.getBoolean(this, KEY_REMEMBER_CHECK);
        etAccount.setText(rmAccount);
        etPassword.setText(rmPassword);
        cbRememberMe.setChecked(rmCheck);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();

                // 进行登录验证逻辑
                if (isValidCredentials(account, password)) {
                    rememberMe(account, password);
                    
                    // 登录成功，跳转到主界面或其他目标界面
                    Intent intent = new Intent(LoginActivity.this, PageJumpActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 登录失败，显示错误消息
                    Toast.makeText(LoginActivity.this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        etSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void rememberMe(String account, String password) {
        boolean isChecked = cbRememberMe.isChecked();
        if(isChecked) {
            SpfUtil.saveString(this, KEY_ACCOUNT, account);
            SpfUtil.saveString(this, KEY_PASSWORD, password);
            SpfUtil.saveBoolean(this, KEY_REMEMBER_CHECK, true);
        }
        else {
            SpfUtil.saveString(this, KEY_ACCOUNT, "");
            SpfUtil.saveString(this, KEY_PASSWORD, "");
            SpfUtil.saveBoolean(this, KEY_REMEMBER_CHECK, false);
        }
    }

    private boolean isValidCredentials(String account, String password) {
        mUsersDbOpenHelper = new UsersDbOpenHelper(this);
        String realPassword = mUsersDbOpenHelper.queryFromDbByAccount(account);
        if(realPassword.equals("") || !realPassword.equals(password))
            return false;
        return true;
    }
}
