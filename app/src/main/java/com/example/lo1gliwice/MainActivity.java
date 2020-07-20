
//-----------------------------//
// created by: Jakub Olszewski //
//-----------------------------//


package com.example.lo1gliwice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lo1gliwice.aboutSchool.aboutSchoolActivity;
import com.example.lo1gliwice.news.newsActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


//JSOUP
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.jsoup.*;
import org.jsoup.nodes.*;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
    private FirebaseAnalytics mFirebaseAnalytics;


    //ADS
    private AdView mAdView;
    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    //STRINGS
    String chosenClass;
    public static final String PREF_YOUR_CLASS = "yourClass";

    //INT'S
    int resultLength;
    //BOOLEANS

    //TEXTVIEW'S
    TextView chosenClass_TV;
    TextView date_TV;
    TextView result_TV;

    //BUTTONS
    Button btn_refresh;
    Button btn_yourClass;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ADS
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //BUTTONS
        btn_refresh = findViewById(R.id.button_goBack);
        btn_yourClass = findViewById(R.id.button_yourClass);

        //TEXTVIEW'S
        chosenClass_TV = findViewById(R.id.textView_chosenClass);
        date_TV = findViewById(R.id.textView_date);
        result_TV = findViewById(R.id.textView_result);

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

        //SPINNER

        Spinner spinner = findViewById(R.id.classSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        Object test = spinner.getSelectedItem();

        //BUTTON REFRESH
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!chosenClass.isEmpty()) {
                    chosenClass_TV.setText("Wybrałeś klasę: " + chosenClass);
                    new doit().execute();
                }


            }
        });

        //BUTTON YOURCLASS
        btn_yourClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readSettings();

                if (chosenClass.isEmpty()) {
                    chosenClass_TV.setText("Klasa nie została wybrana");
                    Toast.makeText(MainActivity.this, "Przejź do ustawień aby wybrać swoją klasę.", Toast.LENGTH_SHORT).show();
                } else {
                    chosenClass_TV.setText("Wybrałeś klasę: " + chosenClass);
                    new doit().execute();
                }


            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        chosenClass = parent.getItemAtPosition(position).toString();
        chosenClass_TV.setText("Wybrałeś klasę: " + chosenClass);
        new doit().execute();
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
        }

        return false;
    }

    //CHANGE ACTIVITY
    private void moveToMainActivity() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void moveToSettingsActivity() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void moveToclassSwapActivity() {
        Intent intent = new Intent(MainActivity.this, classSwapActivity.class);
        startActivity(intent);
    }

    private void moveToInfoActivity() {
        Intent intent = new Intent(MainActivity.this, infoActivity.class);
        startActivity(intent);
    }

    private void moveToNewsActivity() {
        Intent intent = new Intent(MainActivity.this, newsActivity.class);
        startActivity(intent);
    }

    private void moveToAboutSchoolActivity() {
        Intent intent = new Intent(MainActivity.this, aboutSchoolActivity.class);
        startActivity(intent);
    }

    private void moveToArchiveActivity() {
        Intent intent = new Intent(MainActivity.this, archiveActivity.class);
        startActivity(intent);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    //GETING DATA FROM WEBSITE
    public class doit extends AsyncTask<Void, Void, Void> {

        String result;
        String date;

        @Override
        protected Void doInBackground(Void... params) {

            if (chosenClass.isEmpty()) {
                chosenClass = "Wybierz";
            }

            Document doc = null;
            try {
                doc = Jsoup.connect("http://www.lo1.gliwice.pl/zastepstwa-2/").userAgent("Mozilla/5.0").get();

                switch (chosenClass) {
                    case "IA":
                            result = doc.select("p:contains(IA)").text();
                        break;
                    case "IBC":
                        result = doc.select("p:contains(IBC)").text();
                        break;
                    case "ID":
                        result = doc.select("p:contains(ID)").text();
                        break;
                    case "IE":
                        result = doc.select("p:contains(IE)").text();
                        break;
                    case "IIAp":
                        result = doc.select("p:contains(IIAp)").text();
                        break;
                    case "IIBp":
                        result = doc.select("p:contains(IIBp)").text();
                        break;
                    case "IICp":
                        result = doc.select("p:contains(IICp)").text();
                        break;
                    case "IIDp":
                        result = doc.select("p:contains(IIDp)").text();
                        break;
                    case "IIEp":
                        result = doc.select("p:contains(IIEp)").text();
                        break;
                    case "IIAg":
                        result = doc.select("p:contains(IIAg)").text();
                        break;
                    case "IIBg":
                        result = doc.select("p:contains(IIBg)").text();
                        break;
                    case "IICg":
                        result = doc.select("p:contains(IICg)").text();
                        break;
                    case "IIDg":
                        result = doc.select("p:contains(IIDg)").text();
                        break;
                    case "IIEg":
                        result = doc.select("p:contains(IIEg)").text();
                        break;
                    case "IIIa":
                        result = doc.select("p:contains(IIIa)").text();
                        break;
                    case "IIIb":
                        result = doc.select("p:contains(IIIb)").text();
                        break;
                    case "IIIc":
                        result = doc.select("p:contains(IIIc)").text();
                        break;
                    case "":
                        break;
                }

                date = doc.select("u").text();

                if (result.length() == 0) {
                    result = "Brak zastępstw";
                }

                result = result.replace("1l", "\n1l").replace("2l", "\n2l").replace("3l", "\n3l")
                        .replace("4l", "\n4l").replace("5l", "\n5l").replace("6l", "\n6l")
                        .replace("7l", "\n7l").replace("8l", "\n8l").replace("9l", "\n9l");

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            date_TV.setText(date);
            result_TV.setText(result);
        }
    }

    //READ SETTINGS
    public void readSettings() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        chosenClass = sharedPreferences.getString(PREF_YOUR_CLASS, "");
    }

}
