
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
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    boolean aBoolean = false;
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
        List<String> classList = new ArrayList<>();
        classList.add("IA");
        classList.add("IBC");
        classList.add("IE");
        classList.add("ID");
        classList.add("IIAp");
        classList.add("IIBp");
        classList.add("IICp");
        classList.add("IIDp");
        classList.add("IIAg");
        classList.add("IIBg");
        classList.add("IICg");
        classList.add("IIDg");
        classList.add("IIEg");
        classList.add("IIIA");
        classList.add("IIIB");
        classList.add("IIIC");
        classList.add("IIID");
        classList.add("IIIE");

        Spinner spinner = findViewById(R.id.classSpinner);
        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classList);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classSpinner, android.R.layout.simple_spinner_item);
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
                    if (chosenClass.equals("Klasa nie została wybrana")){

                    } else {
                        new doit().execute();
                    }
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
    public class doit extends AsyncTask<Void, Void, String> {

        String result = "";
        String date;


        @Override
        protected String doInBackground(Void... params) {
            aBoolean = true;
            if (chosenClass.isEmpty()) {
                chosenClass = "Wybierz";
            }

            Document doc;
            try {
                doc = Jsoup.connect("http://www.lo1.gliwice.pl/zastepstwa-2/").userAgent("Mozilla/5.0").get();
                Elements elements = doc.select("div#post-3833").select("p");
                List<String> list = new ArrayList<>();
                List<String> list2 = new ArrayList<>();
                //for (Element element : elements) {
                //    list.add(element.text());
                //}
                String[] arr = {"6l – IE j. polski p. Jania", "6l – IIE j. polski p. Jania", "6l – IIACp j. polski p. Jania"};
                for (int i = 0; i < arr.length; i ++){
                    list.add(arr[i]);
                }
                System.out.println(list);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).contains(chosenClass)) {

                        if (chosenClass.equals("IA") || chosenClass.equals("IBC")
                            || chosenClass.equals("ID") || chosenClass.equals("IE")){

                            if (!list.get(i).contains("II") && !list.get(i).contains("III")){
                                list2.add(list.get(i));
                            }
                            if (list.get(i).contains("IABC") || list.get(i).contains("IDE")){
                                list2.add(list.get(i));
                            }
                        }

                        if (chosenClass.equals("IIAp") || chosenClass.equals("IIBp") || chosenClass.equals("IICp")
                            || chosenClass.equals("IIDp") || chosenClass.equals("IIEp")){

                                list2.add(list.get(i));

                            if (chosenClass.equals("IIAp") || chosenClass.equals("IICp")){System.out.println("iamworking");
                                for (int j = 0; j < list.size(); j++){
                                    if (list.get(j).contains("IIACp")){
                                        list2.add(list.get(j));
                                    }
                                }
                            }
                        }

                        if (chosenClass.equals("IIAg") || chosenClass.equals("IIBg") || chosenClass.equals("IICg")
                                || chosenClass.equals("IIDg") || chosenClass.equals("IIEg")){

                            if (!list.get(i).contains("II") && !list.get(i).contains("III")){
                                list2.add(list.get(i));
                            }
                            if (chosenClass.equals("IIAg") || chosenClass.equals("IIBg")){
                                for (int j = 0; j < list.size(); j++){
                                    if (list.get(j).contains("IIA0Cg")){
                                        list2.add(list.get(j));
                                    }
                                }
                            }
                        }




                    }
                }
                System.out.println(list2);
                if (list2.size() == 0){
                    list2.add("Brak zastępstw");

                }
                for (int i = 0; i < list2.size(); i++){
                    result += list2.get(i);
                }

                date = doc.select("u").text();


            } catch (IOException e) {
                e.printStackTrace();
            }
        return result;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            result_TV.setText(result);
            date_TV.setText(date);
        }
    }

    //READ SETTINGS
    public void readSettings() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        chosenClass = sharedPreferences.getString(PREF_YOUR_CLASS, "");
    }


}
