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
import com.example.drawintapp.domain.po.UserRegisterPO;

@SuppressLint("Registered")
public class RegisterActivity extends AppCompatActivity {
    private final UserController userController;

    private Button buttonRegister;
    private Button buttonBack2Login;

    private TextView textUserName;
    private TextView textPassword;
    private TextView textGender;

    private EditText inputUserName;
    private EditText inputPassword;
    private EditText inputGender;

    {
        userController = new UserController();
    }

    private void initViews() {
        textUserName = this.findViewById(R.id.username);
        textPassword = this.findViewById(R.id.password);
        textPassword = this.findViewById(R.id.gender);

        inputUserName = this.findViewById(R.id.usernameInput);
        inputPassword = this.findViewById(R.id.passwordInput);
        inputGender = this.findViewById(R.id.genderInput);

        buttonRegister = this.findViewById(R.id.buttonRegister);
        buttonBack2Login = this.findViewById(R.id.buttonBack2Login);

        CommonButton.registerButtonHome(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        initViews();

        buttonRegister.setBackgroundColor(ConstantBase.colorButtonOk);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegisterPO userRegisterPO = getUserRegisterPO();
                try {
                    userController.register(userRegisterPO);
                    inputPassword.setText("");

                    buttonRegister.setBackgroundColor(ConstantBase.colorButtonOk);
                    setResult(200);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    buttonRegister.setBackgroundColor(ConstantBase.colorButtonError);
                }
            }
        });

        buttonBack2Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳到的界面
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activity
                startActivityForResult(intent, 1);
            }
        });
    }

    private UserRegisterPO getUserRegisterPO() {
        UserRegisterPO userRegisterPO = new UserRegisterPO();
        userRegisterPO.setName(String.valueOf(inputUserName.getText()));
        userRegisterPO.setPassword(String.valueOf(inputPassword.getText()));
        userRegisterPO.setAccount(String.valueOf(inputUserName.getText()));
        userRegisterPO.setGender(String.valueOf(inputGender.getText()));
        userRegisterPO.setAccountType("DRAW_INT");
        return userRegisterPO;
    }
}
