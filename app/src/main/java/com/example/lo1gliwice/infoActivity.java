
//----------------------------------------//
// created by: Jakub Olszewski            //
// idea for applications: Jakub Olszewski //
//          All rights reserved           //
//----------------------------------------//

package com.example.lo1gliwice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.navigation.NavigationView;

public class infoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "infoActivity";

    //ADS
    private AdView mAdView;

    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    //Textview
    TextView github_TV;
    String gitHub = "Repozytorium GitHub dostępne TUTAJ";
    TextView watchAd_TV;
    String watchAd = "Wesprzyj mnie i obejrzyj reklamę klikając TUTAJ";

    //Ad
    private RewardedAd rewardedAd;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //SIDEBAR MENU
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //ADS
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        loadAd();
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

       //TEXTVIEW
        github_TV = findViewById(R.id.textView_github);

            //ANNOTATION GIT REPOSITORY
            SpannableString spannableString_git =  new SpannableString(gitHub);
            ClickableSpan clickableSpan_git = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    openGIT("https://github.com/Jaqbovsky/LO1Gliwice");
                }
            };

            spannableString_git.setSpan(clickableSpan_git,29,34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            github_TV.setText(spannableString_git);
            github_TV.setMovementMethod(LinkMovementMethod.getInstance());

        watchAd_TV = findViewById(R.id.textView_watchAd);

            //ANNOTATION WATCH AD
            SpannableString spannableString_watchAd = new SpannableString(watchAd);
            ClickableSpan clickableSpan_watchAd = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {

                    showAd();
                }
            };

            spannableString_watchAd.setSpan(clickableSpan_watchAd, 42,47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            watchAd_TV.setText(spannableString_watchAd);
            watchAd_TV.setMovementMethod(LinkMovementMethod.getInstance());


    }

    //
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_mainPage:
                Toast.makeText(infoActivity.this, "Strona główna", Toast.LENGTH_SHORT).show();
                moveToMainActivity();
                break;

            case R.id.menu_classSwap:
                Toast.makeText(infoActivity.this, "Zamiana klas", Toast.LENGTH_SHORT).show();
                moveToclassSwapActivity();
                break;

            case R.id.menu_setting:
                Toast.makeText(infoActivity.this, "Ustawienia", Toast.LENGTH_SHORT).show();
                moveToSettingsActivity();
                break;

            case R.id.menu_information:
                Toast.makeText(infoActivity.this, "Informacje", Toast.LENGTH_SHORT).show();
                moveToInfoActivity();
                break;

            case R.id.menu_news:
            Toast.makeText(infoActivity.this, "Aktualnosci", Toast.LENGTH_SHORT).show();
            moveToNewsActivity();
            break;
        }

        return false;
    }

    //CHANGE ACTIVITY
    private void moveToSettingsActivity() {
        Intent intent = new Intent(infoActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(infoActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void moveToclassSwapActivity(){
        Intent intent = new Intent(infoActivity.this, classSwapActivity.class);
        startActivity(intent);
    }

    private void moveToInfoActivity(){
        Intent intent = new Intent(infoActivity.this, infoActivity.class);
        startActivity(intent);
    }
    private void moveToNewsActivity(){
        Intent intent = new Intent(infoActivity.this, newsActivity.class);
        startActivity(intent);
    }

    //OPENNING WEBBROSER
    public void openGIT(String url){
        Uri uri = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launchWeb);
    }

    public void loadAd(){
        this.rewardedAd = new RewardedAd(this, "ca-app-pub-6373386798183476/7988579463");
        //test//this.rewardedAd = new RewardedAd(this, "ca-app-pub-3940256099942544/5224354917");
        RewardedAdLoadCallback loadCallback = new RewardedAdLoadCallback(){
            @Override
            public void onRewardedAdFailedToLoad(int i) {
                super.onRewardedAdFailedToLoad(i);
                Log.i(TAG, "onRewardedAdFailedToLoad");
            }

            @Override
            public void onRewardedAdLoaded() {
                super.onRewardedAdLoaded();
                Log.i(TAG, "onRewardedAdLoaded");
            }
        };
        this.rewardedAd.loadAd(new AdRequest.Builder().build(), loadCallback);
    }
    public void showAd(){
        if (this.rewardedAd.isLoaded()){
            RewardedAdCallback rewardedAdCallback = new RewardedAdCallback() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    Log.i(TAG, "OnUserEarnedReward");
                }

                @Override
                public void onRewardedAdOpened() {
                    super.onRewardedAdOpened();
                    Log.i(TAG, "onRewardedAdOpened");
                }

                @Override
                public void onRewardedAdClosed() {
                    super.onRewardedAdClosed();
                    Log.i(TAG,"onRewardedAdClosed");
                }
            };
            this.rewardedAd.show(this, rewardedAdCallback);
        }else{
            Log.i(TAG,"Ad not loaded");
        }
    }
}
