package com.example.lo1gliwice.achievements;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.lo1gliwice.MainActivity;
import com.example.lo1gliwice.R;
import com.example.lo1gliwice.SettingsActivity;
import com.example.lo1gliwice.aboutSchool.aboutSchoolActivity;
import com.example.lo1gliwice.archiveActivity;
import com.example.lo1gliwice.classSwapActivity;
import com.example.lo1gliwice.infoActivity;
import com.example.lo1gliwice.news.newsActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

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

    LinearLayout linearLayout ;
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> link = new ArrayList<String>();
    ArrayList<String> title2 = new ArrayList<String>();
    ArrayList<String> link2= new ArrayList<String>();
    ArrayList<String> contain = new ArrayList<String>();
    ProgressBar progressBar;

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
        linearLayout  = findViewById(R.id.LL);
        progressBar = findViewById(R.id.progressBar);

            boolean run;
            run = getIntent().getBooleanExtra("RUN", true);

            if (run){
                new doIt().execute();
            } else{
                progressBar.setVisibility(View.GONE);
                title = getIntent().getStringArrayListExtra("TITLE_LIST");
                link = getIntent().getStringArrayListExtra("LINK_LIST");
                for (int k = 0; k < title.size(); k++) {
                    String text = title.get(k);
                    String link1 = link.get(k);
                    print(text, link1);
                }
                new doIt2().execute();
            }

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

            case R.id.menu_archive:
                moveToArchiveActivity();
                break;

        }

        return false;
    }


    public class doIt extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Document doc = null;
            String url;

            for (int i = 1; i <= 5; i++) {

                url = "http://www.lo1.gliwice.pl/category/dla-uczniow/aktualnosci-dla-uczniow/page/" + i + "/";
                try {
                    doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
                    Elements elementsT = doc.body().select("a[title]");

                    contain.add("wynik");
                    contain.add("w finale");
                    contain.add("etapie");
                    contain.add("sukces");
                    contain.add("olimpiad");
                    contain.add("mistrz");
                    contain.add("laureat");
                    contain.add("finalist");

                    int j;
                    for (Element t : elementsT) {
                        for ( j = 0; j < contain.size(); j++) {
                            if (t.text().toLowerCase().contains(contain.get(j))) {
                                title.add(t.text());
                                link.add(t.attr("href"));
                            }
                        }
                    }
                    Set<String> set = new LinkedHashSet<>(title);
                    title.clear();
                    title.addAll(set);

                    Set<String> set2 = new LinkedHashSet<>(link);
                    link.clear();
                    link.addAll(set2);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            for (int k = 0; k < title.size(); k++){
                String text = title.get(k);
                String link1 = link.get(k);
                print(text, link1);
            }
            new doIt2().execute();
        }
    }
    public class doIt2 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            int maxValue = progressBar.getMax();
            progressBar.setMax(100);
            Document doc = null;
            String url;

            for (int i = 6; i <= 10; i++) {

                url = "http://www.lo1.gliwice.pl/category/dla-uczniow/aktualnosci-dla-uczniow/page/" + i + "/";
                try {
                    doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
                    Elements elementsT = doc.body().select("a[title]");

                    contain.add("wynik");
                    contain.add("w finale");
                    contain.add("etapie");
                    contain.add("sukces");
                    contain.add("olimpiad");
                    contain.add("mistrz");
                    contain.add("laureat");
                    contain.add("finalist");

                    int j;
                    for (Element t : elementsT) {
                        for ( j = 0; j < contain.size(); j++) {
                            if (t.text().toLowerCase().contains(contain.get(j))) {
                                title2.add(t.text());
                                link2.add(t.attr("href"));
                            }
                        }
                    }
                    Set<String> set3 = new LinkedHashSet<>(title2);
                    title2.clear();
                    title2.addAll(set3);

                    Set<String> set4 = new LinkedHashSet<>(link2);
                    link2.clear();
                    link2.addAll(set4);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            for (int k = 0; k < title2.size(); k++){
                String text = title2.get(k);
                String link1 = link2.get(k);
                print(text, link1);
            }
        }
    }

    public void print(String titleA, String linkA){
        LinearLayout.LayoutParams paramsT;
        paramsT = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsT.setMargins(25, 20, 25, 20);
        CardView cardView = new CardView(achievementsActivity.this);
        TextView textView = new TextView(achievementsActivity.this);
        textView.setLayoutParams(paramsT);
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setTextSize(16);
        textView.setText(titleA);
        cardView.addView(textView);
        LinearLayout.LayoutParams params;
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(25, 10, 25, 15);
        cardView.setLayoutParams(params);
        cardView.setRadius(15);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(achievementsActivity.this, achievementsActivity_article.class);
                intent.putExtra("TITLE", titleA);
                intent.putExtra("LINK", linkA);
                intent.putExtra("TITLE_LIST", title);
                intent.putExtra("LINK_LIST", link);
                startActivity(intent);
            }
        });
        linearLayout.addView(cardView);
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

    private void moveToArchiveActivity() {
        Intent intent = new Intent(achievementsActivity.this, archiveActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}