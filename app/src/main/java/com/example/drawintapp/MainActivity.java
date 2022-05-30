package com.example.drawintapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.WindowDecorActionBar;
import com.example.drawintapp.activity.LoginActivity;
import com.example.drawintapp.activity.TerminalHomeActivity;
import com.example.drawintapp.controller.DoorController;
import com.example.drawintapp.controller.LightController;
import com.example.drawintapp.controller.UserController;
import com.example.drawintapp.dal.UserIdentifyDao;
import com.example.drawintapp.domain.UserLoginBO;
import com.example.drawintapp.service.UserService;
import com.example.drawintapp.utils.Counter;
import lombok.Data;

import java.security.interfaces.RSAPublicKey;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class MainActivity extends AppCompatActivity {
    private final UserController userController;
    private final LightController lightController;
    private final DoorController doorController;

    private final int colorButtonError = Color.rgb(220, 20, 60);
    private final int colorButtonOk = Color.rgb(220, 220, 220);
    private final int colorTextDisable = Color.rgb(190, 190, 190);
    private final int colorTextDefault = Color.rgb(	0, 0, 0);

    private Button buttonLoginOut;
    private Button buttonLight;
    private Button buttonDoorOpenAuto;
    private Button buttonTerminalOverview;
    private TextView currentUser;

    {
        userController = new UserController();
        lightController = new LightController();
        doorController = new DoorController();
    }

    private void initViews() {
        buttonLoginOut = this.findViewById(R.id.buttonLoginOut);
        buttonLight = this.findViewById(R.id.buttonLight);
        currentUser = this.findViewById(R.id.currentUser);
        buttonDoorOpenAuto = this.findViewById(R.id.buttonDoorOpenAuto);
        buttonTerminalOverview = this.findViewById(R.id.terminalOverview);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        initViews();

        buttonLoginOut.setBackgroundColor(colorButtonOk);
        buttonLight.setBackgroundColor(colorButtonOk);
        buttonDoorOpenAuto.setBackgroundColor(colorButtonOk);

        refreshCurrentUser();

        if (userController.isLogin()) {
            buttonLoginOut.setText(R.string.logout);
        } else {

            buttonLoginOut.setText(R.string.login);
        }
        buttonDoorOpenAuto.setText(R.string.doorOpenAuto);

        buttonLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    refreshCurrentUser();
                    if (currentUser.getText().toString().contains(getString(R.string.notLogin))) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳到的界面
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activit
                        startActivityForResult(intent, 1);
                        buttonLoginOut.setText(R.string.logout);

                    } else {
                        userController.logout();
                        buttonLoginOut.setText(R.string.login);
                    }
                    buttonLoginOut.setBackgroundColor(colorButtonOk);
                } catch (Exception e) {
                    e.printStackTrace();
                    buttonLoginOut.setBackgroundColor(colorButtonError);
                }
                refreshCurrentUser();
            }
        });

        buttonLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    lightController.switchLight();
                    buttonLight.setBackgroundColor(colorButtonOk);
                } catch (Exception e) {
                    e.printStackTrace();
                    buttonLight.setBackgroundColor(colorButtonError);
                }
            }
        });

        buttonDoorOpenAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (TagsMap.openDoorAutoFinish) {
                        CompletableFuture.supplyAsync(new Supplier<Object>() {
                            @Override
                            public Object get() {

                                try {
                                    doorController.openAuto();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    buttonDoorOpenAuto.setBackgroundColor(colorButtonError);
                                }
                                buttonDoorOpenAuto.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        buttonDoorOpenAuto.setText(R.string.doorOpenAuto);
                                        buttonDoorOpenAuto.setTextColor(colorTextDefault);
                                        TagsMap.openDoorAutoFinish = true;
                                    }
                                });
                                return null;
                            }
                        });

                        TagsMap.openDoorAutoFinish = false;
                        buttonDoorOpenAuto.setText(R.string.doorOpening);
                        buttonDoorOpenAuto.setTextColor(colorTextDisable);
                    }
                    buttonDoorOpenAuto.setBackgroundColor(colorButtonOk);
                } catch (Exception e) {
                    e.printStackTrace();
                    buttonDoorOpenAuto.setBackgroundColor(colorButtonError);
                }
            }
        });

        buttonTerminalOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TerminalHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳到的界面
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activit
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            refreshCurrentUser();
        }
    }

    private void refreshCurrentUser() {
        String currentUser = userController.getCurrentUser();
        if (currentUser == null || currentUser.trim().equals("")) {
            currentUser = getString(R.string.notLogin);
        }
        this.currentUser.setText(getString(R.string.currentUser) + currentUser);
    }

    private static class TagsMap {
        private static boolean openDoorAutoFinish = true;
    }
}
