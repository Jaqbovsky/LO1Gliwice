
//----------------------------------------//
// created by: Jakub Olszewski            //
// idea for application: Jakub Olszewski //
//          All rights reserved           //
//----------------------------------------//

package com.example.lo1gliwice.news;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.lo1gliwice.MainActivity;
import com.example.lo1gliwice.R;
import com.example.lo1gliwice.SettingsActivity;
import com.example.lo1gliwice.aboutSchool.aboutSchoolActivity;
import com.example.lo1gliwice.achievements.achievementsActivity;
import com.example.lo1gliwice.achievements.achievementsActivity_article;
import com.example.lo1gliwice.archiveActivity;
import com.example.lo1gliwice.classSwapActivity;
import com.example.lo1gliwice.infoActivity;
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
import java.util.LinkedHashSet;
import java.util.Set;

public class newsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //ADS
    private AdView mAdView;

    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> link = new ArrayList<String>();
    LinearLayout linearLayout;

    int pageN = 1;

    private WebView webView;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

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
        //MobileAds.initialize(this,"ca-app-pub-6373386798183476~7251446395");
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        linearLayout = findViewById(R.id.ll);

        new doIt().execute();

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
        Intent intent = new Intent(newsActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(newsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void moveToclassSwapActivity() {
        Intent intent = new Intent(newsActivity.this, classSwapActivity.class);
        startActivity(intent);
    }

    private void moveToInfoActivity() {
        Intent intent = new Intent(newsActivity.this, infoActivity.class);
        startActivity(intent);
    }

    private void moveToNewsActivity() {
        Intent intent = new Intent(newsActivity.this, newsActivity.class);
        startActivity(intent);
    }

    private void moveToAboutSchoolActivity() {
        Intent intent = new Intent(newsActivity.this, aboutSchoolActivity.class);
        startActivity(intent);
    }

    private void moveToArchiveActivity() {
        Intent intent = new Intent(newsActivity.this, archiveActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    //GETING DATA FROM WEBSITE
    public class doIt extends AsyncTask<Void, Void, Void> {
        ProgressBar progressBar = findViewById(R.id.progressBar2);
        int i = 0;
        @Override
        protected Void doInBackground(Void... params) {
            Document doc = null;
            String url;

                url = "http://www.lo1.gliwice.pl/category/dla-uczniow/aktualnosci-dla-uczniow/page/" + pageN + "/";
                try {
                    doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
                    Elements elementsT = doc.body().select("a[title]");

                    for (Element t : elementsT) {
                        title.add(t.text());
                        String l = t.attr("href");
                        if (!l.isEmpty()) {
                            link.add(l);
                        } else {
                            link.add("error");
                        }
                        progressBar.setProgress(i = i + 10);
                    }
                    Set<String> set = new LinkedHashSet<>(title);
                    title.clear();
                    title.addAll(set);
                    title.remove("lo1.gliwice.pl");
                    title.remove("Wykonanie");

                    Set<String> set2 = new LinkedHashSet<>(link);
                    link.clear();
                    link.addAll(set2);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (int k = 0; k < title.size(); k++) {
                String text = title.get(k);
                String link1 = link.get(k);
                print(text, link1);
                System.out.println(title);
            }
            Button button = new Button(newsActivity.this);
            LinearLayout.LayoutParams paramsT;
            paramsT = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsT.setMargins(205, 10, 205, 10);
            button.setText("Pokaż więcej");
            button.setTextColor(button.getContext().getResources().getColor(R.color.textColorTest));
            button.setBackground(button.getContext().getResources().getDrawable(R.drawable.rounded_button));
            button.setLayoutParams(paramsT);
            linearLayout.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pageN++;
                    new doIt().execute();
                    title.clear();
                    link.clear();
                    i = 0;
                    linearLayout.removeView(button);
                }
            });
        }
    }

    public void print(String titleA, String linkA){
        LinearLayout.LayoutParams paramsT;
        paramsT = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsT.setMargins(25, 20, 25, 20);
        CardView cardView = new CardView(newsActivity.this);
        TextView textView = new TextView(newsActivity.this);
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
                Intent intent = new Intent(newsActivity.this, newsActivity_article.class);
                intent.putExtra("TITLE", titleA);
                intent.putExtra("LINK", linkA);
                intent.putExtra("TITLE_LIST", title);
                intent.putExtra("LINK_LIST", link);
                startActivity(intent);
            }
        });
        if (!titleA.isEmpty()){
            linearLayout.addView(cardView);
        }
    }

}