package com.example.lo1gliwice;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.lo1gliwice.achievements.achievementsActivity;
import com.example.lo1gliwice.news.newsActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

public class aboutSchoolActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //ADS
    private AdView mAdView;

    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    //BUTTONS
    Button achievements;
    Button for_candidates;
    Button organization_of_the_school_year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_school);

        //ADS
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //SIDEBAR MENU
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle( this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        achievements = findViewById(R.id.button_achievements);
        for_candidates = findViewById(R.id.button_for_candidates);
        organization_of_the_school_year = findViewById(R.id.button_organization_of_the_school_year);

        achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(aboutSchoolActivity.this, achievementsActivity.class);
                startActivity(intent);
            }
        });

        for_candidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(aboutSchoolActivity.this, "Zakładka \"Dla kandydatów\" nie jest jeszcze gotowa", Toast.LENGTH_SHORT).show();
            }
        });

        organization_of_the_school_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(aboutSchoolActivity.this, "Zakładka \"Organizacja roku szkolnego\" nie jest jeszcze gotowa", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.menu_mainPage:
                Toast.makeText(aboutSchoolActivity.this, "Strona główna", Toast.LENGTH_SHORT).show();
                moveToMainActivity();
                break;

            case R.id.menu_classSwap:
                Toast.makeText(aboutSchoolActivity.this, "Zamiana klas", Toast.LENGTH_SHORT).show();
                moveToclassSwapActivity();
                break;

            case R.id.menu_news:
                Toast.makeText(aboutSchoolActivity.this, "Aktualnosci", Toast.LENGTH_SHORT).show();
                moveToNewsActivity();
                break;

            case R.id.menu_about_school:
                Toast.makeText(aboutSchoolActivity.this,"O szkole", Toast.LENGTH_SHORT).show();
                moveToAboutSchoolActivity();
                break;

            case R.id.menu_setting:
                Toast.makeText(aboutSchoolActivity.this, "Ustawienia", Toast.LENGTH_SHORT).show();
                moveToSettingsActivity();
                break;

            case R.id.menu_information:
                Toast.makeText(aboutSchoolActivity.this, "Informacje", Toast.LENGTH_SHORT).show();
                moveToInfoActivity();
                break;


        }

        return false;
    }

    //CHANGE ACTIVITY
    private void moveToMainActivity() {
        Intent intent = new Intent(aboutSchoolActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void moveToSettingsActivity(){
        Intent intent = new Intent(aboutSchoolActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void moveToclassSwapActivity(){
        Intent intent = new Intent(aboutSchoolActivity.this, classSwapActivity.class);
        startActivity(intent);
    }

    private void moveToInfoActivity(){
        Intent intent = new Intent(aboutSchoolActivity.this, infoActivity.class);
        startActivity(intent);
    }

    private void moveToNewsActivity(){
        Intent intent = new Intent(aboutSchoolActivity.this, newsActivity.class);
        startActivity(intent);
    }

    private void moveToAboutSchoolActivity(){
        Intent intent = new Intent(aboutSchoolActivity.this, aboutSchoolActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
