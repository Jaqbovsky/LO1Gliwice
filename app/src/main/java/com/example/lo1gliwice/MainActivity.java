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
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


//JSOUP
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
    private FirebaseAnalytics mFirebaseAnalytics;


    //ADS
    private static final String TAG = "MainActivity";

    private AdView mAdView;
    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    //STRINGS
    String chosenClass;
    //INT'S

    //BOOLEANS

    //TEXTVIEW'S
    TextView chosenClass_TV;
    TextView date_TV;

    //BUTTONS
    Button btn_refresh;
    Button btn_yourClass;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ADS
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
            //ADS - banner
            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //BUTTONS
        btn_refresh = findViewById(R.id.button_refresh);
        btn_yourClass = findViewById(R.id.button_yourClass);

        //TEXTVIEW'S
        chosenClass_TV = findViewById(R.id.textView_chosenClass);
        date_TV = findViewById(R.id.textView_date);

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
                chosenClass = "IAp";
            }
        });

        //BUTTON YOURCLASS
        btn_yourClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        chosenClass = parent.getItemAtPosition(position).toString();
        chosenClass_TV.setText(chosenClass);
        new doit().execute();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.menu_mainPage:
                Toast.makeText(MainActivity.this, "Strona główna", Toast.LENGTH_SHORT).show();
            break;

            case R.id.menu_clasroomChange:
                Toast.makeText(MainActivity.this, "Zamiana klas", Toast.LENGTH_SHORT).show();
            break;

            case R.id.menu_setting:
                Toast.makeText(MainActivity.this, "Ustawienia", Toast.LENGTH_SHORT).show();
                moveToSettingsActivity();
            break;

            case R.id.menu_information:
                Toast.makeText(MainActivity.this, "Informacje", Toast.LENGTH_SHORT).show();
            break;
        }

        return false;
    }

    //CHANGE ACTIVITY
    private void moveToSettingsActivity(){
      Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
      startActivity(intent);
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    //GETING DATA FROM WEBSITE
    public class doit extends AsyncTask<Void, Void, Void> {

        String result;

        @Override
        protected Void doInBackground(Void... params) {

            Document doc = null;
            try {
                doc = Jsoup.connect("http://www.lo1.gliwice.pl/").get();
                Elements temp = doc.select("div#post-3833");//Selecting div}
                    // result = doc.select("p:contains(IIID)").text();

                switch (chosenClass){
                    case "IAp": result = doc.select("p:contains(IAp)").text(); break;
                    case "IBp": result = doc.select("p:contains(IBp)").text(); break;
                    case "ICp": result = doc.select("p:contains(ICp)").text(); break;
                    case "IDp": result = doc.select("p:contains(IDp)").text(); break;
                    case "IEp": result = doc.select("p:contains()").text(); break;
                }



            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
                chosenClass_TV.setText(result);
                date_TV.setText(result);
        }
    }
}
