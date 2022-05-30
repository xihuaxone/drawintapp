package com.example.drawintapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.drawintapp.R;
import com.example.drawintapp.activity.commonComponent.CommonButton;
import com.example.drawintapp.config.ConstantBase;
import com.example.drawintapp.controller.UserController;
import com.example.drawintapp.domain.UserLoginBO;

@SuppressLint("Registered")
public class LoginActivity extends AppCompatActivity {
    private final UserController userController;

    private Button buttonLogin;
    private Button buttonRegister;

    private TextView textUserName;
    private TextView textPassword;
    private EditText inputAccount;
    private EditText inputPassword;

    {
        userController = new UserController();
    }

    private void initViews() {
        textUserName = this.findViewById(R.id.username);
        textPassword = this.findViewById(R.id.password);
        inputAccount = this.findViewById(R.id.accountInput);
        inputPassword = this.findViewById(R.id.passwordInput);
        buttonLogin = this.findViewById(R.id.buttonLogin);
        buttonRegister = this.findViewById(R.id.buttonRegister);

        CommonButton.registerButtonHome(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initViews();

        buttonLogin.setBackgroundColor(ConstantBase.colorButtonOk);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLoginBO userLoginBO = getUserLoginBO();
                try {
                    userController.login(userLoginBO);
                    inputPassword.setText("");

                    buttonLogin.setBackgroundColor(ConstantBase.colorButtonOk);
                    setResult(200);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    buttonLogin.setBackgroundColor(ConstantBase.colorButtonError);
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳到的界面
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activity
                startActivityForResult(intent, 1);
            }
        });
    }

    private UserLoginBO getUserLoginBO() {
        UserLoginBO userLoginBO = new UserLoginBO();
        userLoginBO.setAccount(String.valueOf(inputAccount.getText()));
        userLoginBO.setPassword(String.valueOf(inputPassword.getText()));
        userLoginBO.setAccountType("DRAW_INT");
        return userLoginBO;
    }
}
