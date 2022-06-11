package com.example.drawintapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.drawintapp.R;
import com.example.drawintapp.activity.commonComponent.CommonButton;
import com.example.drawintapp.config.ConstantBase;
import com.example.drawintapp.controller.TerminalController;
import com.example.drawintapp.controller.TerminalMngController;
import com.example.drawintapp.controller.UserController;
import com.example.drawintapp.domain.bo.TerminalActionBO;
import com.example.drawintapp.domain.bo.TerminalBO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressLint("Registered")
public class TerminalHomeActivity extends AppCompatActivity {
    private final UserController userController;
    private final TerminalMngController terminalMngController;
    private final TerminalController terminalController;

    TableLayout tableTerminalOverview;

    Button buttonRegisterTerminal;

    {
        userController = new UserController();
        terminalMngController = new TerminalMngController();
        terminalController = new TerminalController();
    }

    private void initViews() {
        tableTerminalOverview = findViewById(R.id.terminalOverview);
        buttonRegisterTerminal = findViewById(R.id.buttonRegisterTerminal);

        CommonButton.registerButtonHome(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terminal_home_activity);

        initViews();

        try {
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
            setResult(500);
            finish();
        }

        buttonRegisterTerminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TerminalHomeActivity.this, TerminalRegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳到的界面
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activity
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            refreshTable();
        }
    }

    private void refreshTable() {
        List<TerminalBO> terminalList = terminalMngController.list();
        tableTerminalOverview.removeAllViews();
        tableTerminalOverview.setStretchAllColumns(true);

        TableRow rowTitle = new TableRow(tableTerminalOverview.getContext());
        TextView titleColumnId = newTableTextView();
        titleColumnId.setText("ID");
        TextView titleColumnName = newTableTextView();
        titleColumnName.setText("name");
        TextView titleColumnActions = newTableTextView();
        titleColumnActions.setText("actions");
        rowTitle.addView(titleColumnId);
        rowTitle.addView(titleColumnName);
        rowTitle.addView(titleColumnActions);
        tableTerminalOverview.addView(rowTitle);

        for (TerminalBO terminal : terminalList) {
            TableRow row = new TableRow(tableTerminalOverview.getContext());

            TextView columnId = newTableTextView();
            columnId.setText(String.valueOf(terminal.getId()));
            TextView columnName = newTableTextView();
            columnName.setText(String.valueOf(terminal.getName()));
            TextView columnActions = newTableTextView();
            List<String> actionList = terminal.getActionList().stream()
                    .map(rec -> String.format("%s(%s)", rec.getName(), rec.getCode())).collect(Collectors.toList());
            columnActions.setText(String.join(", ", actionList));

            List<Button> actionButtons = new ArrayList<>(5);

            for (TerminalActionBO actionBO : terminal.getActionList()) {
                Button buttonAction = new Button(tableTerminalOverview.getContext());
                buttonAction.setText(actionBO.getName());
                buttonAction.setBackgroundColor(ConstantBase.colorButtonOk);
                buttonAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            terminalController.callAction(terminal.getId(), actionBO.getCode());
                            buttonAction.setBackgroundColor(ConstantBase.colorButtonOk);
                        } catch (Exception e) {
                            e.printStackTrace();
                            buttonAction.setBackgroundColor(ConstantBase.colorButtonError);
                        }
                    }
                });
                actionButtons.add(buttonAction);
            }

            LinearLayout layoutButtons = new TableLayout(tableTerminalOverview.getContext());
            actionButtons.forEach(layoutButtons::addView);

            row.addView(columnId);
            row.addView(columnName);
//            row.addView(columnActions);
            row.addView(layoutButtons);
            tableTerminalOverview.addView(row);
        }
    }

    private TextView newTableTextView() {
        TextView textView = new TextView(tableTerminalOverview.getContext());
        textView.setTextSize(15);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        return textView;
    }
}
