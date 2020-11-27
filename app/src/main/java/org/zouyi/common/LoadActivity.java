package org.zouyi.common;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.zouyi.common.utils.ImmerseStatusBarV6;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmerseStatusBarV6.setImmerseStatusBar(this);
        setContentView(R.layout.activity_load);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoadActivity.this,MainActivity.class));
                LoadActivity.this.finish();
            }
        },3000);
    }
}
