package com.example.travelor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelor.bean.Users;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.example.travelor.datebase.UsersDbOpenHelper;

public class SignInActivity extends AppCompatActivity {
    private EditText etAccount;
    private EditText etPassword;
    private EditText etConfirmPwd;
    private EditText etUserName;
    private EditText etVerifyCode;
    private Button btnSignIn;
    private Button btnVerify;
    private TextView tvAgree;
    private TextView tvLogin;
    private CheckBox ckAgree;
    private UsersDbOpenHelper mUsersDbOpenHelper;

    private String verification_code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);

        etAccount = findViewById(R.id.sign_account);
        etPassword = findViewById(R.id.new_password);
        etConfirmPwd = findViewById(R.id.confirm_password);
        etVerifyCode = findViewById(R.id.verification_code);
        etUserName = findViewById(R.id.username);
        btnSignIn = findViewById(R.id.buttonSignIn);
        btnVerify = findViewById(R.id.sent_VerifyCode);
        tvAgree = findViewById(R.id.agree_pact);
        tvLogin = findViewById(R.id.tvlogin);
        ckAgree = findViewById(R.id.agree_check);
        mUsersDbOpenHelper = new UsersDbOpenHelper(this);

        //发送验证码
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int randomNumber = 1000 + random.nextInt(9000);
                verification_code =  String.valueOf(randomNumber);

                Toast.makeText(SignInActivity.this, verification_code, Toast.LENGTH_SHORT).show();
            }
        });

        // 注册按钮
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mAccount = etAccount.getText().toString();
                String mPassword = etPassword.getText().toString();
                String mConfirmPwd = etConfirmPwd.getText().toString();
                String mVerifyCode = etVerifyCode.getText().toString();
                String UserName = etUserName.getText().toString();

                Boolean is_OK = isValidAccount(mAccount, mPassword, mConfirmPwd, mVerifyCode, UserName);
                if(is_OK) {
                    saveDate(mAccount, mPassword, UserName);
                    Toast.makeText(SignInActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                builder.setTitle("安全协议");
                builder.setMessage("xxxxxxxxx\nxxxxxxxxxxxxx\nxxxxxxxxxxxxxxxxxxxxx");
                builder.setCancelable(false);
                builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ckAgree.setChecked(true);
                    }
                });
                builder.show();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // 获取当前时间
    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY年MM月dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    // 将新用户保存到数据库
    private void saveDate(String mAccount, String mPassword, String userName) {
        Users newUser = new Users();

        newUser.setAccount(mAccount);
        newUser.setUserName(userName);
        newUser.setPassword(mPassword);
        newUser.setCreatedTime(getCurrentTimeFormat());

        mUsersDbOpenHelper.insertData(newUser);
    }

    // 判断新用户设置是否合法
    private Boolean isValidAccount(String mAccount, String mPassword, String mConfirmPwd, String mVerifyCode, String userName) {
        String regex = "^1[3456789]\\d{9}$";
        if(!mAccount.matches(regex)) {
            Toast.makeText(SignInActivity.this, "账号不合法！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!mUsersDbOpenHelper.queryFromDbByAccount(mAccount).equals("")) {
            Toast.makeText(SignInActivity.this, "此账号已经存在，请登录！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(mPassword.length() < 6) {
            Toast.makeText(SignInActivity.this, "密码太短！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!mConfirmPwd.equals(mPassword)) {
            Toast.makeText(SignInActivity.this, "密码确认有误！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(verification_code.equals("") || !mVerifyCode.equals(verification_code)) {
            Toast.makeText(SignInActivity.this, "验证码错误！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!ckAgree.isChecked()) {
            Toast.makeText(SignInActivity.this, "请同意协议！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}