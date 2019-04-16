package com.wikifoundry.todo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ProgressBar;
import com.github.ybq.android.spinkit.style.FadingCircle;
public class SplashScreenActivity extends AppCompatActivity {
    static final int SPLASH_TIMEOUT = 2500;
    private static final String NO_TIMER = "no_timer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            handleNavigation();
        } else {
            handleNavigation();
        }
    }

    private void handleNavigation() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMEOUT);

        ProgressBar cubeFolder = findViewById(R.id.spin_kit);
        FadingCircle threeBounce = new FadingCircle();
        cubeFolder.setIndeterminateDrawable(threeBounce);
    }
}
