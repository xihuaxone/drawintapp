package com.example.drawintapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.drawintapp.R;
import com.example.drawintapp.activity.commonComponent.CommonButton;
import com.example.drawintapp.config.ConstantBase;
import com.example.drawintapp.controller.TerminalMngController;
import com.example.drawintapp.domain.bo.TerminalRegisterBO;

@SuppressLint("Registered")
public class TerminalRegisterActivity extends AppCompatActivity {
    private final TerminalMngController terminalMngController;

    Button buttonRegisterTerminal;
    EditText terminalNameInput;
    EditText terminalTopicInput;
    EditText terminalTypeInput;

    {
        terminalMngController = new TerminalMngController();
    }

    private void initViews() {
        buttonRegisterTerminal = findViewById(R.id.buttonRegisterTerminal);
        terminalNameInput = findViewById(R.id.terminalNameInput);
        terminalTopicInput = findViewById(R.id.terminalTopicInput);
        terminalTypeInput = findViewById(R.id.terminalTypeInput);

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
                    terminalMngController.register(terminalRegisterBO);
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
        terminalRegisterBO.setType(String.valueOf(terminalTypeInput.getText()));
        return terminalRegisterBO;
    }
}
