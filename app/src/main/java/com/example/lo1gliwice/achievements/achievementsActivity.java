package com.example.lo1gliwice.achievements;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.lo1gliwice.MainActivity;
import com.example.lo1gliwice.R;
import com.example.lo1gliwice.SettingsActivity;
import com.example.lo1gliwice.aboutSchoolActivity;
import com.example.lo1gliwice.classSwapActivity;
import com.example.lo1gliwice.infoActivity;
import com.example.lo1gliwice.news.newsActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class achievementsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //ADS
    private AdView mAdView;

    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    //TEXTVIEW
    TextView result_TV;
    TextView achievements1_TV;
    TextView achievements2_TV;
    TextView achievements3_TV;
    TextView achievements4_TV;
    TextView achievements5_TV;
    TextView achievements6_TV;
    TextView achievements7_TV;
    TextView achievements8_TV;
    TextView achievements9_TV;
    TextView achievements10_TV;
    TextView achievements11_TV;
    TextView achievements12_TV;
    TextView achievements13_TV;
    TextView achievements14_TV;
    TextView achievements15_TV;
    TextView achievements16_TV;
    TextView achievements17_TV;
    TextView achievements18_TV;
    TextView achievements19_TV;
    TextView achievements20_TV;
    TextView achievements21_TV;
    TextView achievements22_TV;
    TextView achievements23_TV;
    TextView achievements24_TV;
    TextView achievements25_TV;
    TextView achievements26_TV;
    TextView achievements27_TV;
    TextView achievements28_TV;
    TextView achievements29_TV;
    TextView achievements30_TV;
    TextView achievements31_TV;
    TextView achievements32_TV;
    TextView achievements33_TV;
    TextView achievements34_TV;
    TextView achievements35_TV;
    TextView achievements36_TV;
    TextView achievements37_TV;
    TextView achievements38_TV;
    TextView achievements39_TV;
    TextView achievements40_TV;

    String doc1_wyniki, doc1_w_finale, doc1_w_etapie, doc1_sukces, doc1_olimpiad, doc1_mistrz, doc1_laurat, doc1_finalist;
    String doc1_wyniki_link, doc1_w_finale_link, doc1_w_etapie_link, doc1_sukces_link, doc1_olimpiad_link, doc1_mistrz_link, doc1_laurat_link, doc1_finalist_link;

    String doc2_wyniki, doc2_w_finale, doc2_w_etapie, doc2_sukces, doc2_olimpiad, doc2_mistrz, doc2_laurat, doc2_finalist;
    String doc2_wyniki_link, doc2_w_finale_link, doc2_w_etapie_link, doc2_sukces_link, doc2_olimpiad_link, doc2_mistrz_link, doc2_laurat_link, doc2_finalist_link;

    String doc3_wyniki, doc3_w_finale, doc3_w_etapie, doc3_sukces, doc3_olimpiad, doc3_mistrz, doc3_laurat, doc3_finalist;
    String doc3_wyniki_link, doc3_w_finale_link, doc3_w_etapie_link, doc3_sukces_link, doc3_olimpiad_link, doc3_mistrz_link, doc3_laurat_link, doc3_finalist_link;

    String doc4_wyniki, doc4_w_finale, doc4_w_etapie, doc4_sukces, doc4_olimpiad, doc4_mistrz, doc4_laurat, doc4_finalist;
    String doc4_wyniki_link, doc4_w_finale_link, doc4_w_etapie_link, doc4_sukces_link, doc4_olimpiad_link, doc4_mistrz_link, doc4_laurat_link, doc4_finalist_link;

    String doc5_wyniki, doc5_w_finale, doc5_w_etapie, doc5_sukces, doc5_olimpiad, doc5_mistrz, doc5_laurat, doc5_finalist;
    String doc5_wyniki_link, doc5_w_finale_link, doc5_w_etapie_link, doc5_sukces_link, doc5_olimpiad_link, doc5_mistrz_link, doc5_laurat_link, doc5_finalist_link;

    String url1 = "http://www.lo1.gliwice.pl/category/dla-uczniow/aktualnosci-dla-uczniow/";
    String url2 = "http://www.lo1.gliwice.pl/category/dla-uczniow/aktualnosci-dla-uczniow/page/2/";
    String url3 = "http://www.lo1.gliwice.pl/category/dla-uczniow/aktualnosci-dla-uczniow/page/3/";
    String url4 = "http://www.lo1.gliwice.pl/category/dla-uczniow/aktualnosci-dla-uczniow/page/4/";
    String url5 = "http://www.lo1.gliwice.pl/category/dla-uczniow/aktualnosci-dla-uczniow/page/5/";


    final StringBuilder stringBuilder = new StringBuilder();
    final StringBuilder stringBuilder_link = new StringBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        //ADS
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
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
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        result_TV = findViewById(R.id.textView_result);
        achievements1_TV = findViewById(R.id.textView_achievements1);
        achievements2_TV = findViewById(R.id.textView_achievements2);
        achievements3_TV = findViewById(R.id.textView_achievements3);
        achievements4_TV = findViewById(R.id.textView_achievements4);
        achievements5_TV = findViewById(R.id.textView_achievements5);
        achievements6_TV = findViewById(R.id.textView_achievements6);
        achievements7_TV = findViewById(R.id.textView_achievements7);
        achievements8_TV = findViewById(R.id.textView_achievements8);
        achievements9_TV = findViewById(R.id.textView_achievements9);
        achievements10_TV = findViewById(R.id.textView_achievements10);
        achievements11_TV = findViewById(R.id.textView_achievements11);
        achievements12_TV = findViewById(R.id.textView_achievements12);
        achievements13_TV = findViewById(R.id.textView_achievements13);
        achievements14_TV = findViewById(R.id.textView_achievements14);
        achievements15_TV = findViewById(R.id.textView_achievements15);
        achievements16_TV = findViewById(R.id.textView_achievements16);
        achievements17_TV = findViewById(R.id.textView_achievements17);
        achievements18_TV = findViewById(R.id.textView_achievements18);
        achievements19_TV = findViewById(R.id.textView_achievements19);
        achievements20_TV = findViewById(R.id.textView_achievements20);
        achievements21_TV = findViewById(R.id.textView_achievements21);
        achievements22_TV = findViewById(R.id.textView_achievements22);
        achievements23_TV = findViewById(R.id.textView_achievements23);
        achievements24_TV = findViewById(R.id.textView_achievements24);
        achievements25_TV = findViewById(R.id.textView_achievements25);
        achievements26_TV = findViewById(R.id.textView_achievements26);
        achievements27_TV = findViewById(R.id.textView_achievements27);
        achievements28_TV = findViewById(R.id.textView_achievements28);
        achievements29_TV = findViewById(R.id.textView_achievements29);
        achievements30_TV = findViewById(R.id.textView_achievements30);
        achievements31_TV = findViewById(R.id.textView_achievements31);
        achievements32_TV = findViewById(R.id.textView_achievements32);
        achievements33_TV = findViewById(R.id.textView_achievements33);
        achievements34_TV = findViewById(R.id.textView_achievements34);
        achievements35_TV = findViewById(R.id.textView_achievements35);
        achievements36_TV = findViewById(R.id.textView_achievements36);
        achievements37_TV = findViewById(R.id.textView_achievements37);
        achievements38_TV = findViewById(R.id.textView_achievements38);
        achievements39_TV = findViewById(R.id.textView_achievements39);
        achievements40_TV = findViewById(R.id.textView_achievements40);

        new do1().execute();
        new do2().execute();
        new do3().execute();
        new do4().execute();
        new do5().execute();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_mainPage:
                Toast.makeText(achievementsActivity.this, "Strona główna", Toast.LENGTH_SHORT).show();
                moveToMainActivity();
                break;

            case R.id.menu_classSwap:
                Toast.makeText(achievementsActivity.this, "Zamiana klas", Toast.LENGTH_SHORT).show();
                moveToclassSwapActivity();
                break;

            case R.id.menu_news:
                Toast.makeText(achievementsActivity.this, "Aktualnosci", Toast.LENGTH_SHORT).show();
                moveToNewsActivity();
                break;

            case R.id.menu_about_school:
                Toast.makeText(achievementsActivity.this, "O szkole", Toast.LENGTH_SHORT).show();
                moveToAboutSchoolActivity();
                break;

            case R.id.menu_setting:
                Toast.makeText(achievementsActivity.this, "Ustawienia", Toast.LENGTH_SHORT).show();
                moveToSettingsActivity();
                break;

            case R.id.menu_information:
                Toast.makeText(achievementsActivity.this, "Informacje", Toast.LENGTH_SHORT).show();
                moveToInfoActivity();
                break;


        }

        return false;
    }


    public class do1 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            Document doc1 = null;

            try {
                //PIERWSZA STRONA
                doc1 = Jsoup.connect(url1).userAgent("Mozilla/5.0").get();

                doc1_wyniki = doc1.select("a[title]:contains(wynik)").text();
                doc1_w_finale = doc1.select("a[title]:contains(w finale)").text();
                doc1_w_etapie = doc1.select("a[title]:contains(etapie)").text();
                doc1_sukces = doc1.select("a[title]:contains(sukces)").text();
                doc1_olimpiad = doc1.select("a[title]:contains(olimpiad)").text();
                doc1_mistrz = doc1.select("a[title]:contains(mistrz)").text();
                doc1_laurat = doc1.select("a[title]:contains(laureat)").text();
                doc1_finalist = doc1.select("a[title]:contains(finalist)").text();

                doc1_wyniki_link = doc1.select("a[title]:contains(wynik)").attr("href");
                doc1_w_finale_link = doc1.select("a[title]:contains(w finale)").attr("href");
                doc1_w_etapie_link = doc1.select("a[title]:contains(etapie)").attr("href");
                doc1_sukces_link = doc1.select("a[title]:contains(sukces)").attr("href");
                doc1_olimpiad_link = doc1.select("a[title]:contains(olimpiad)").attr("href");
                doc1_mistrz_link = doc1.select("a[title]:contains(mistrz)").attr("href");
                doc1_laurat_link = doc1.select("a[title]:contains(laureat)").attr("href");
                doc1_finalist_link = doc1.select("a[title]:contains(finalist)").attr("href");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            write1();
        }
    }

    public class do2 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            Document doc2 = null;
            try {
                doc2 = Jsoup.connect(url2).userAgent("Mozilla/5.0").get();

                doc2_wyniki = doc2.select("a[title]:contains(wynik)").text();
                doc2_w_finale = doc2.select("a[title]:contains(w finale)").text();
                doc2_w_etapie = doc2.select("a[title]:contains(etapie)").text();
                doc2_sukces = doc2.select("a[title]:contains(sukces)").text();
                doc2_olimpiad = doc2.select("a[title]:contains(olimpiad)").text();
                doc2_mistrz = doc2.select("a[title]:contains(mistrz)").text();
                doc2_laurat = doc2.select("a[title]:contains(laureat)").text();
                doc2_finalist = doc2.select("a[title]:contains(finalist)").text();

                doc2_wyniki_link = doc2.select("a[title]:contains(wynik)").attr("href");
                doc2_w_finale_link = doc2.select("a[title]:contains(w finale)").attr("href");
                doc2_w_etapie_link = doc2.select("a[title]:contains(etapie)").attr("href");
                doc2_sukces_link = doc2.select("a[title]:contains(sukces)").attr("href");
                doc2_olimpiad_link = doc2.select("a[title]:contains(olimpiad)").attr("href");
                doc2_mistrz_link = doc2.select("a[title]:contains(mistrz)").attr("href");
                doc2_laurat_link = doc2.select("a[title]:contains(laureat)").attr("href");
                doc2_finalist_link = doc2.select("a[title]:contains(finalist)").attr("href");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            write2();
        }
    }

    public class do3 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            Document doc3 = null;
            try {
                doc3 = Jsoup.connect(url3).userAgent("Mozilla/5.0").get();

                doc3_wyniki = doc3.select("a[title]:contains(wynik)").text();
                doc3_w_finale = doc3.select("a[title]:contains(w finale)").text();
                doc3_w_etapie = doc3.select("a[title]:contains(etapie)").text();
                doc3_sukces = doc3.select("a[title]:contains(sukces)").text();
                doc3_olimpiad = doc3.select("a[title]:contains(olimpiad)").text();
                doc3_mistrz = doc3.select("a[title]:contains(mistrz)").text();
                doc3_laurat = doc3.select("a[title]:contains(laureat)").text();
                doc3_finalist = doc3.select("a[title]:contains(finalist)").text();

                doc3_wyniki_link = doc3.select("a[title]:contains(wynik)").attr("href");
                doc3_w_finale_link = doc3.select("a[title]:contains(w finale)").attr("href");
                doc3_w_etapie_link = doc3.select("a[title]:contains(etapie)").attr("href");
                doc3_sukces_link = doc3.select("a[title]:contains(sukces)").attr("href");
                doc3_olimpiad_link = doc3.select("a[title]:contains(olimpiad)").attr("href");
                doc3_mistrz_link = doc3.select("a[title]:contains(mistrz)").attr("href");
                doc3_laurat_link = doc3.select("a[title]:contains(laureat)").attr("href");
                doc3_finalist_link = doc3.select("a[title]:contains(finalist)").attr("href");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            write3();
        }
    }

    public class do4 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            Document doc4 = null;
            try {
                //CZAWRTA STRONA
                doc4 = Jsoup.connect(url4).userAgent("Mozilla/5.0").get();

                doc4_wyniki = doc4.select("a[title]:contains(wynik)").text();
                doc4_w_finale = doc4.select("a[title]:contains(w finale)").text();
                doc4_w_etapie = doc4.select("a[title]:contains(etapie)").text();
                doc4_sukces = doc4.select("a[title]:contains(sukces)").text();
                doc4_olimpiad = doc4.select("a[title]:contains(olimpiad)").text();
                doc4_mistrz = doc4.select("a[title]:contains(mistrz)").text();
                doc4_laurat = doc4.select("a[title]:contains(laureat)").text();
                doc4_finalist = doc4.select("a[title]:contains(finalist)").text();

                doc4_wyniki_link = doc4.select("a[title]:contains(wynik)").attr("href");
                doc4_w_finale_link = doc4.select("a[title]:contains(w finale)").attr("href");
                doc4_w_etapie_link = doc4.select("a[title]:contains(etapie)").attr("href");
                doc4_sukces_link = doc4.select("a[title]:contains(sukces)").attr("href");
                doc4_olimpiad_link = doc4.select("a[title]:contains(olimpiad)").attr("href");
                doc4_mistrz_link = doc4.select("a[title]:contains(mistrz)").attr("href");
                doc4_laurat_link = doc4.select("a[title]:contains(laureat)").attr("href");
                doc4_finalist_link = doc4.select("a[title]:contains(finalist)").attr("href");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            write4();
        }
    }

    public class do5 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            Document doc5 = null;
            try {
                doc5 = Jsoup.connect(url5).userAgent("Mozilla/5.0").get();

                doc5_wyniki = doc5.select("a[title]:contains(wynik)").text();
                doc5_w_finale = doc5.select("a[title]:contains(w finale)").text();
                doc5_w_etapie = doc5.select("a[title]:contains(etapie)").text();
                doc5_sukces = doc5.select("a[title]:contains(sukces)").text();
                doc5_olimpiad = doc5.select("a[title]:contains(olimpiad)").text();
                doc5_mistrz = doc5.select("a[title]:contains(mistrz)").text();
                doc5_laurat = doc5.select("a[title]:contains(laureat)").text();
                doc5_finalist = doc5.select("a[title]:contains(finalist)").text();

                doc5_wyniki_link = doc5.select("a[title]:contains(wynik)").attr("href");
                doc5_w_finale_link = doc5.select("a[title]:contains(w finale)").attr("href");
                doc5_w_etapie_link = doc5.select("a[title]:contains(etapie)").attr("href");
                doc5_sukces_link = doc5.select("a[title]:contains(sukces)").attr("href");
                doc5_olimpiad_link = doc5.select("a[title]:contains(olimpiad)").attr("href");
                doc5_mistrz_link = doc5.select("a[title]:contains(mistrz)").attr("href");
                doc5_laurat_link = doc5.select("a[title]:contains(laureat)").attr("href");
                doc5_finalist_link = doc5.select("a[title]:contains(finalist)").attr("href");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            write5();
        }
    }


    public void write1() {
        achievements1_TV.setText(doc1_wyniki);
        achievements2_TV.setText(doc1_w_finale);
        achievements3_TV.setText(doc1_w_etapie);
        achievements4_TV.setText(doc1_sukces);
        achievements5_TV.setText(doc1_olimpiad);
        achievements6_TV.setText(doc1_mistrz);
        achievements7_TV.setText(doc1_laurat);
        achievements8_TV.setText(doc1_finalist);

        if (doc1_wyniki.isEmpty()) {
            achievements1_TV.setVisibility(View.GONE);
        }
        if (doc1_w_finale.isEmpty()) {
            achievements2_TV.setVisibility(View.GONE);
        }
        if (doc1_w_etapie.isEmpty()) {
            achievements3_TV.setVisibility(View.GONE);
        }
        if (doc1_sukces.isEmpty()) {
            achievements4_TV.setVisibility(View.GONE);
        }
        if (doc1_olimpiad.isEmpty()) {
            achievements5_TV.setVisibility(View.GONE);
        }
        if (doc1_mistrz.isEmpty()) {
            achievements6_TV.setVisibility(View.GONE);
        }
        if (doc1_laurat.isEmpty()) {
            achievements7_TV.setVisibility(View.GONE);
        }
        if (doc1_finalist.isEmpty()) {
            achievements8_TV.setVisibility(View.GONE);
        }
        if (doc1_w_finale.equals(doc1_olimpiad)) {
            achievements5_TV.setVisibility(View.GONE);
        }
        if (doc1_w_etapie.equals(doc1_olimpiad)) {
            achievements5_TV.setVisibility(View.GONE);
        }
    }

    public void write2() {
        achievements9_TV.setText(doc2_wyniki);
        achievements10_TV.setText(doc2_w_finale);
        achievements11_TV.setText(doc2_w_etapie);
        achievements12_TV.setText(doc2_sukces);
        achievements13_TV.setText(doc2_olimpiad);
        achievements14_TV.setText(doc2_mistrz);
        achievements15_TV.setText(doc2_laurat);
        achievements16_TV.setText(doc2_finalist);

        //DRUGA STRONA
        if (doc2_wyniki.isEmpty()) {
            achievements9_TV.setVisibility(View.GONE);
        }
        if (doc2_w_finale.isEmpty()) {
            achievements10_TV.setVisibility(View.GONE);
        }
        if (doc2_w_etapie.isEmpty()) {
            achievements11_TV.setVisibility(View.GONE);
        }
        if (doc2_sukces.isEmpty()) {
            achievements12_TV.setVisibility(View.GONE);
        }
        if (doc2_olimpiad.isEmpty()) {
            achievements13_TV.setVisibility(View.GONE);
        }
        if (doc2_mistrz.isEmpty()) {
            achievements14_TV.setVisibility(View.GONE);
        }
        if (doc2_laurat.isEmpty()) {
            achievements15_TV.setVisibility(View.GONE);
        }
        if (doc2_finalist.isEmpty()) {
            achievements16_TV.setVisibility(View.GONE);
        }

        if (doc2_w_finale.equals(doc2_olimpiad)) {
            achievements13_TV.setVisibility(View.GONE);
        }
        if (doc2_w_etapie.equals(doc2_olimpiad)) {
            achievements13_TV.setVisibility(View.GONE);
        }
    }

    public void write3() {
        achievements17_TV.setText(doc3_wyniki);
        achievements18_TV.setText(doc3_w_finale);
        achievements19_TV.setText(doc3_w_etapie);
        achievements20_TV.setText(doc3_sukces);
        achievements21_TV.setText(doc3_olimpiad);
        achievements22_TV.setText(doc3_mistrz);
        achievements23_TV.setText(doc3_laurat);
        achievements24_TV.setText(doc3_finalist);

        //TRZECIA STRONA
        if (doc3_wyniki.isEmpty()) {
            achievements17_TV.setVisibility(View.GONE);
        }
        if (doc3_w_finale.isEmpty()) {
            achievements18_TV.setVisibility(View.GONE);
        }
        if (doc3_w_etapie.isEmpty()) {
            achievements19_TV.setVisibility(View.GONE);
        }
        if (doc3_sukces.isEmpty()) {
            achievements20_TV.setVisibility(View.GONE);
        }
        if (doc3_olimpiad.isEmpty()) {
            achievements21_TV.setVisibility(View.GONE);
        }
        if (doc3_mistrz.isEmpty()) {
            achievements22_TV.setVisibility(View.GONE);
        }
        if (doc3_laurat.isEmpty()) {
            achievements23_TV.setVisibility(View.GONE);
        }
        if (doc3_finalist.isEmpty()) {
            achievements24_TV.setVisibility(View.GONE);
        }
        if (doc3_w_finale.equals(doc3_olimpiad)) {
            achievements21_TV.setVisibility(View.GONE);
        }
        if (doc2_w_etapie.equals(doc3_olimpiad)) {
            achievements21_TV.setVisibility(View.GONE);
        }
    }

    public void write4() {
        achievements25_TV.setText(doc4_wyniki);
        achievements26_TV.setText(doc4_w_finale);
        achievements27_TV.setText(doc4_w_etapie);
        achievements28_TV.setText(doc4_sukces);
        achievements29_TV.setText(doc4_olimpiad);
        achievements30_TV.setText(doc4_mistrz);
        achievements31_TV.setText(doc4_laurat);
        achievements32_TV.setText(doc4_finalist);

        //CZWARTA STRONA
        if (doc4_wyniki.isEmpty()) {
            achievements25_TV.setVisibility(View.GONE);
        }
        if (doc4_w_finale.isEmpty()) {
            achievements26_TV.setVisibility(View.GONE);
        }
        if (doc4_w_etapie.isEmpty()) {
            achievements27_TV.setVisibility(View.GONE);
        }
        if (doc4_sukces.isEmpty()) {
            achievements28_TV.setVisibility(View.GONE);
        }
        if (doc4_olimpiad.isEmpty()) {
            achievements29_TV.setVisibility(View.GONE);
        }
        if (doc4_mistrz.isEmpty()) {
            achievements30_TV.setVisibility(View.GONE);
        }
        if (doc4_laurat.isEmpty()) {
            achievements31_TV.setVisibility(View.GONE);
        }
        if (doc4_finalist.isEmpty()) {
            achievements32_TV.setVisibility(View.GONE);
        }
        if (doc4_w_finale.equals(doc4_olimpiad)) {
            achievements29_TV.setVisibility(View.GONE);
        }
        if (doc4_w_etapie.equals(doc4_olimpiad)) {
            achievements29_TV.setVisibility(View.GONE);
        }
    }

    public void write5() {
        achievements33_TV.setText(doc5_wyniki);
        achievements34_TV.setText(doc5_w_finale);
        achievements35_TV.setText(doc5_w_etapie);
        achievements36_TV.setText(doc5_sukces);
        achievements37_TV.setText(doc5_olimpiad);
        achievements38_TV.setText(doc5_mistrz);
        achievements39_TV.setText(doc5_laurat);
        achievements40_TV.setText(doc5_finalist);

        //PIĄTA STRONA
        if (doc5_wyniki.isEmpty()) {
            achievements33_TV.setVisibility(View.GONE);
        }
        if (doc5_w_finale.isEmpty()) {
            achievements34_TV.setVisibility(View.GONE);
        }
        if (doc5_w_etapie.isEmpty()) {
            achievements35_TV.setVisibility(View.GONE);
        }
        if (doc5_sukces.isEmpty()) {
            achievements36_TV.setVisibility(View.GONE);
        }
        if (doc5_olimpiad.isEmpty()) {
            achievements37_TV.setVisibility(View.GONE);
        }
        if (doc5_mistrz.isEmpty()) {
            achievements38_TV.setVisibility(View.GONE);
        }
        if (doc5_laurat.isEmpty()) {
            achievements39_TV.setVisibility(View.GONE);
        }
        if (doc5_finalist.isEmpty()) {
            achievements40_TV.setVisibility(View.GONE);
        }
        if (doc5_w_finale.equals(doc5_olimpiad)) {
            achievements37_TV.setVisibility(View.GONE);
        }
        if (doc5_w_etapie.equals(doc5_olimpiad)) {
            achievements37_TV.setVisibility(View.GONE);
        }
    }

    public void goToAchievement1(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc1_wyniki);
        intent.putExtra("LINK", doc1_wyniki_link);
        startActivity(intent);
    }

    public void goToAchievement2(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc1_w_finale);
        intent.putExtra("LINK", doc1_w_finale_link);
        startActivity(intent);
    }

    public void goToAchievement3(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc1_w_etapie);
        intent.putExtra("LINK", doc1_w_etapie_link);
        startActivity(intent);
    }

    public void goToAchievement4(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc1_sukces);
        intent.putExtra("LINK", doc1_sukces_link);
        startActivity(intent);
    }

    public void goToAchievement5(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc1_olimpiad);
        intent.putExtra("LINK", doc1_olimpiad_link);
        startActivity(intent);
    }

    public void goToAchievement6(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc1_mistrz);
        intent.putExtra("LINK", doc1_mistrz_link);
        startActivity(intent);
    }

    public void goToAchievement7(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc1_laurat);
        intent.putExtra("LINK", doc1_laurat_link);
        startActivity(intent);
    }

    public void goToAchievement8(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc1_finalist);
        intent.putExtra("LINK", doc1_finalist_link);
        startActivity(intent);
    }

    //DRUGA STRONA
    public void goToAchievement9(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc2_wyniki);
        intent.putExtra("LINK", doc2_wyniki_link);
        startActivity(intent);
    }

    public void goToAchievement10(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc2_w_finale);
        intent.putExtra("LINK", doc2_w_finale_link);
        startActivity(intent);
    }

    public void goToAchievement11(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc2_w_etapie);
        intent.putExtra("LINK", doc2_w_etapie_link);
        startActivity(intent);
    }

    public void goToAchievement12(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc2_sukces);
        intent.putExtra("LINK", doc1_sukces_link);
        startActivity(intent);
    }

    public void goToAchievement13(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc2_olimpiad);
        intent.putExtra("LINK", doc2_olimpiad_link);
        startActivity(intent);
    }

    public void goToAchievement14(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc2_mistrz);
        intent.putExtra("LINK", doc2_mistrz_link);
        startActivity(intent);
    }

    public void goToAchievement15(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc2_laurat);
        intent.putExtra("LINK", doc2_laurat_link);
        startActivity(intent);
    }

    public void goToAchievement16(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc2_finalist);
        intent.putExtra("LINK", doc2_finalist_link);
        startActivity(intent);
    }

    //TRZECIA STRONA
    public void goToAchievement17(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc3_wyniki);
        intent.putExtra("LINK", doc3_wyniki_link);
        startActivity(intent);
    }

    public void goToAchievement18(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc3_w_finale);
        intent.putExtra("LINK", doc3_w_finale_link);
        startActivity(intent);
    }

    public void goToAchievement19(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc3_w_etapie);
        intent.putExtra("LINK", doc3_w_etapie_link);
        startActivity(intent);
    }

    public void goToAchievement20(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc3_sukces);
        intent.putExtra("LINK", doc3_sukces_link);
        startActivity(intent);
    }

    public void goToAchievement21(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc3_olimpiad);
        intent.putExtra("LINK", doc3_olimpiad_link);
        startActivity(intent);
    }

    public void goToAchievement22(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc3_mistrz);
        intent.putExtra("LINK", doc3_mistrz_link);
        startActivity(intent);
    }

    public void goToAchievement23(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc3_laurat);
        intent.putExtra("LINK", doc3_laurat_link);
        startActivity(intent);
    }

    public void goToAchievement24(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc3_finalist);
        intent.putExtra("LINK", doc3_finalist_link);
        startActivity(intent);
    }

    //CZWARTA STRONA
    public void goToAchievement25(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc4_wyniki);
        intent.putExtra("LINK", doc4_wyniki_link);
        startActivity(intent);
    }

    public void goToAchievement26(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc4_w_finale);
        intent.putExtra("LINK", doc4_w_finale_link);
        startActivity(intent);
    }

    public void goToAchievement27(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc4_w_etapie);
        intent.putExtra("LINK", doc4_w_etapie_link);
        startActivity(intent);
    }

    public void goToAchievement28(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc4_sukces);
        intent.putExtra("LINK", doc4_sukces_link);
        startActivity(intent);
    }

    public void goToAchievement29(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc1_finalist);
        intent.putExtra("LINK", doc1_finalist_link);
        startActivity(intent);
    }

    public void goToAchievement30(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc4_mistrz);
        intent.putExtra("LINK", doc4_mistrz_link);
        startActivity(intent);
    }

    public void goToAchievement31(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc4_laurat);
        intent.putExtra("LINK", doc4_laurat_link);
        startActivity(intent);
    }

    public void goToAchievement32(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc4_finalist);
        intent.putExtra("LINK", doc4_finalist_link);
        startActivity(intent);
    }

    //PIĄTA STRONA
    public void goToAchievement33(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc5_wyniki);
        intent.putExtra("LINK", doc5_wyniki_link);
        startActivity(intent);
    }

    public void goToAchievement34(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc5_w_finale);
        intent.putExtra("LINK", doc5_w_finale_link);
        startActivity(intent);
    }

    public void goToAchievement35(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc5_w_etapie);
        intent.putExtra("LINK", doc5_w_etapie_link);
        startActivity(intent);
    }

    public void goToAchievement36(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc5_sukces);
        intent.putExtra("LINK", doc5_sukces_link);
        startActivity(intent);
    }

    public void goToAchievement37(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc5_olimpiad);
        intent.putExtra("LINK", doc5_olimpiad_link);
        startActivity(intent);
    }

    public void goToAchievement38(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc5_mistrz);
        intent.putExtra("LINK", doc5_mistrz_link);
        startActivity(intent);
    }

    public void goToAchievement39(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc5_laurat);
        intent.putExtra("LINK", doc5_laurat_link);
        startActivity(intent);
    }

    public void goToAchievement40(View view) {
        Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
        intent.putExtra("TITLE", doc5_finalist);
        intent.putExtra("LINK", doc5_finalist_link);
        startActivity(intent);
    }


    //CHANGE ACTIVITY
    private void moveToMainActivity() {
        Intent intent = new Intent(achievementsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void moveToSettingsActivity() {
        Intent intent = new Intent(achievementsActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void moveToclassSwapActivity() {
        Intent intent = new Intent(achievementsActivity.this, classSwapActivity.class);
        startActivity(intent);
    }

    private void moveToInfoActivity() {
        Intent intent = new Intent(achievementsActivity.this, infoActivity.class);
        startActivity(intent);
    }

    private void moveToNewsActivity() {
        Intent intent = new Intent(achievementsActivity.this, newsActivity.class);
        startActivity(intent);
    }

    private void moveToAboutSchoolActivity() {
        Intent intent = new Intent(achievementsActivity.this, aboutSchoolActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}