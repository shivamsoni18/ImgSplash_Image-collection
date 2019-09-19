package com.example.insta;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class SplashScreen extends AppCompatActivity {

    LinearLayout mainLogoLay;
    ImageView splashscreenImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mainLogoLay = (LinearLayout) findViewById(R.id.mainLogoLay);
        splashscreenImg = (ImageView) findViewById(R.id.splashscreenImg);

        logoAnim();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, 1500);
    }


    private void logoAnim() {

        mainLogoLay.animate()
                .scaleX(1.3f)
                .scaleY(1.3f)
                .setDuration(1500);

        splashscreenImg.animate()
                .rotation(360f)
                .setDuration(1500);

    }
}
