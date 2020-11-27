package org.zouyi.common;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.zouyi.common.utils.ImmerseStatusBar;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmerseStatusBar.setImmerseStatusBar(this,false);
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
