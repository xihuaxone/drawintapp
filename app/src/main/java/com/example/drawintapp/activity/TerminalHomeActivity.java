package com.example.drawintapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.drawintapp.R;
import com.example.drawintapp.activity.commonComponent.CommonButton;
import com.example.drawintapp.controller.TerminalController;
import com.example.drawintapp.controller.UserController;
import com.example.drawintapp.domain.bo.TerminalBO;

import java.util.List;
import java.util.stream.Collectors;

@SuppressLint("Registered")
public class TerminalHomeActivity extends AppCompatActivity {
    private final UserController userController;
    private final TerminalController terminalController;

    TableLayout tableTerminalOverview;

    {
        userController = new UserController();
        terminalController = new TerminalController();
    }

    private void initViews() {
        tableTerminalOverview = findViewById(R.id.terminalOverview);

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
    }

    private void refreshTable() {
        List<TerminalBO> terminalList = terminalController.list();
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

            row.addView(columnId);
            row.addView(columnName);
            row.addView(columnActions);
            tableTerminalOverview.addView(row);
        }
    }

    private TextView newTableTextView() {
        TextView textView = new TextView(tableTerminalOverview.getContext());
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        return textView;
    }
}
