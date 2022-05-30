package com.example.drawintapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.drawintapp.R;
import com.example.drawintapp.activity.commonComponent.CommonButton;
import com.example.drawintapp.config.ConstantBase;
import com.example.drawintapp.controller.TerminalController;
import com.example.drawintapp.controller.UserController;
import com.example.drawintapp.domain.bo.TerminalBO;
import com.example.drawintapp.domain.bo.TerminalRegisterBO;

import java.util.List;
import java.util.stream.Collectors;

@SuppressLint("Registered")
public class TerminalRegisterActivity extends AppCompatActivity {
    private final TerminalController terminalController;

    Button buttonRegisterTerminal;
    EditText terminalNameInput;
    EditText terminalTopicInput;

    {
        terminalController = new TerminalController();
    }

    private void initViews() {
        buttonRegisterTerminal = findViewById(R.id.buttonRegisterTerminal);
        terminalNameInput = findViewById(R.id.terminalNameInput);
        terminalTopicInput = findViewById(R.id.terminalTopicInput);

        CommonButton.registerButtonHome(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terminal_register_activity);

        initViews();

        buttonRegisterTerminal.setBackgroundColor(ConstantBase.colorButtonOk);

        buttonRegisterTerminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TerminalRegisterBO terminalRegisterBO = getTerminalRegisterBO();
                try {
                    terminalController.register(terminalRegisterBO);
                    buttonRegisterTerminal.setBackgroundColor(ConstantBase.colorButtonOk);
                    setResult(200);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    buttonRegisterTerminal.setBackgroundColor(ConstantBase.colorButtonError);
                }
            }
        });
    }

    private TerminalRegisterBO getTerminalRegisterBO() {
        TerminalRegisterBO terminalRegisterBO = new TerminalRegisterBO();
        terminalRegisterBO.setName(String.valueOf(terminalNameInput.getText()));
        terminalRegisterBO.setTopic(String.valueOf(terminalTopicInput.getText()));
        return terminalRegisterBO;
    }
}
