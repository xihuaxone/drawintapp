package com.example.drawintapp.activity.commonComponent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import com.example.drawintapp.MainActivity;
import com.example.drawintapp.R;

public class CommonButton {
    public static Button registerButtonHome(Activity activity) {
        Button button = activity.findViewById(R.id.buttonHome);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳到的界面
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activity
                activity.startActivityForResult(intent, 1000);
            }
        });

        return button;
    }

}
