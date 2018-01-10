package com.example.mahmoudrawy.repo.Views;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mahmoudrawy.repo.R;

/**
 * Created by mahmoud rawy™ 01221240053 on 09/01/2018.
 */
/*
this is a splash screen to show a splash screen to the user
 */
public class RepoSplashActivity extends AppCompatActivity {
    private static int MSECONDS = 3000;
    private static int SECONDS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repo_splash_activity);
        new Handler().postDelayed(new Runnable() {
            public void run() {

                startActivity(new Intent(RepoSplashActivity.this, RepoActivity.class));
                overridePendingTransition(R.anim.activity_slide_up, R.anim.activity_slide_stay);
                finish();
                overridePendingTransition(R.anim.activity_slide_up, R.anim.activity_slide_down);


            }
        }, SECONDS * MSECONDS);
    }
}
