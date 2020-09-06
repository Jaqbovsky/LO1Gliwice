
//-----------------------------//
// created by: Jakub Olszewski //
//-----------------------------//

package com.example.lo1gliwice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.lo1gliwice.aboutSchool.aboutSchoolActivity;
import com.example.lo1gliwice.news.newsActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.ads.InterstitialAd;


public class infoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "infoActivity";

    //ADS
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    //Textview
    TextView github_TV;
    String gitHub = "Repozytorium GitHub dostępne \nTUTAJ";
    TextView watchAd_TV;
    String watchAd = "Wesprzyj mnie\nklikając TUTAJ";
    TextView feedback_TV;
    String feedback= "Znalazłeś błąd lub chcesz zasugerować zmainę, \nkliknij TUTAJ.";

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
        //loadAd();
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.fullScrean_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        List<String> updateList = new ArrayList<>();
        updateList.add("Naprawiono błędy");
        updateList.add("Poprawiono wygląd");
        updateList.add("Zoptymalizowano\nosiągnięcia uczniów");
        updateList.add("Zmieniono działanie\naktualności");
        updateList.add("Dodano zakładke\n\"Organizacja roku szkolnego\"");

        int l = updateList.size();
        GridLayout gridLayout = findViewById(R.id.grid);
        gridLayout.setRowCount(1);
        gridLayout.setColumnCount(l);

        for (int i = 0; i < updateList.size(); i++ ){

            LinearLayout.LayoutParams paramsC;
            paramsC = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsC.setMargins(20,20,20,20);

            CardView cardView = new CardView(infoActivity.this);
            cardView.setRadius(15);
            cardView.setCardElevation(15);
            cardView.setLayoutParams(paramsC);

            LinearLayout.LayoutParams params;
            params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            LinearLayout linearLayout = new LinearLayout(infoActivity.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(params);
            linearLayout.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams paramsT;
            paramsT = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsT.setMargins(50, 50, 50, 50);
            TextView textView = new TextView(infoActivity.this);
            textView.setText(updateList.get(i));
            textView.setTextColor(Color.parseColor("#000000"));
            textView.setTextSize(16);
            textView.setLayoutParams(paramsT);
            textView.setGravity(Gravity.CENTER);

            linearLayout.addView(textView);
            cardView.addView(linearLayout);
            gridLayout.addView(cardView);
        }

    }

    //
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_mainPage:
                moveToMainActivity();
                break;

            case R.id.menu_classSwap:
                moveToclassSwapActivity();
                break;

            case R.id.menu_setting:
                moveToSettingsActivity();
                break;

            case R.id.menu_information:
                moveToInfoActivity();
                break;

            case R.id.menu_news:
            moveToNewsActivity();
            break;

            case R.id.menu_about_school:
                moveToAboutSchoolActivity();
                break;

            case R.id.menu_archive:
                moveToArchiveActivity();
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

    private void moveToFeedbackActivity(){
    Intent intent = new Intent(infoActivity.this, feedbackActivity.class);
    startActivity(intent);
    }

    private void moveToAboutSchoolActivity(){
        Intent intent = new Intent(infoActivity.this, aboutSchoolActivity.class);
        startActivity(intent);
    }

    private void moveToArchiveActivity() {
        Intent intent = new Intent(infoActivity.this, archiveActivity.class);
        startActivity(intent);
    }


    //OPENNING WEBBROSER
    public void openGIT(String url){
        Uri uri = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launchWeb);
    }

    public void moveToFeedback(View view) {
        Intent intent = new Intent(infoActivity.this, feedbackActivity.class);
        startActivity(intent);
    }

    public void openEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "j.olszewski05@gmail.com" });
        startActivity(Intent.createChooser(intent, ""));
    }

    public void supportMe(View view) {
        Uri uri = Uri.parse("https://paypal.me/jolszewski05");
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launchWeb);
    }

    public void watchAd(View view) throws InterruptedException {
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        Thread.sleep(1000);
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }
}
