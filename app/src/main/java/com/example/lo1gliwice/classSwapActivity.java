
//-----------------------------//
// created by: Jakub Olszewski //
//-----------------------------//

package com.example.lo1gliwice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lo1gliwice.aboutSchool.aboutSchoolActivity;
import com.example.lo1gliwice.achievements.achievementsActivity_article;
import com.example.lo1gliwice.news.newsActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class classSwapActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //ADS
    private AdView mAdView;

    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    TextView result_TV;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_swap);

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

        //MobileAds.initialize(this,"ca-app-pub-6373386798183476~7251446395");
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        result_TV = findViewById(R.id.textView_result);

        Button classSwap = findViewById(R.id.button_classSwap);
        classSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new doit().execute();

            }
        });
    }

    public class doit extends AsyncTask<Void, Void, Void> {

        String result;

        @Override
        protected Void doInBackground(Void... params) {


            Document doc = null;
            try {
                doc = Jsoup.connect("http://www.lo1.gliwice.pl/zastepstwa-2/").userAgent("Mozilla/5.0").get();

                //result = doc.select("table").text();

                for (Element row : doc.select("table tbody tr")) {

                    result = result + row.text()+ "\n\n\n";
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            result_TV.setText(result);
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

            case R.id.menu_news:
                moveToNewsActivity();
                break;

            case R.id.menu_about_school:
                moveToAboutSchoolActivity();
                break;

            case R.id.menu_setting:
                moveToSettingsActivity();
                break;

            case R.id.menu_information:
                moveToInfoActivity();
                break;

            case R.id.menu_archive:
                moveToArchiveActivity();
                break;

            case R.id.menu_plan:
                moveToPlan();
                break;
        }

        return false;
    }

    //CHANGE ACTIVITY
    private void moveToSettingsActivity() {
        Intent intent = new Intent(classSwapActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(classSwapActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void moveToclassSwapActivity(){
        Intent intent = new Intent(classSwapActivity.this, classSwapActivity.class);
        startActivity(intent);
    }

    private void moveToInfoActivity(){
        Intent intent = new Intent(classSwapActivity.this, infoActivity.class);
        startActivity(intent);
    }
    private void moveToNewsActivity(){
        Intent intent = new Intent(classSwapActivity.this, newsActivity.class);
        startActivity(intent);
    }

    private void moveToAboutSchoolActivity(){
        Intent intent = new Intent(classSwapActivity.this, aboutSchoolActivity.class);
        startActivity(intent);
    }

    private void moveToArchiveActivity() {
        Intent intent = new Intent(classSwapActivity.this, archiveActivity.class);
        startActivity(intent);
    }

    private void moveToPlan() {
        Intent intent = new Intent(classSwapActivity.this, planActivity.class);
        startActivity(intent);
    }

}
