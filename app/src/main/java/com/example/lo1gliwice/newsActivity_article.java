
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class newsActivity_article extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Button goBack;
    TextView title_TV;
    TextView article_TV;
    String article;
    WebView webView;
    //SIDEBAR MENU

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_article);

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

        title_TV = findViewById(R.id.textView_title);
        title_TV.setText(getIntent().getStringExtra("TITLE"));

        goBack = findViewById(R.id.button_goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNewsActivity();
            }
        });

        article_TV = findViewById(R.id.textView_article);
        webView = findViewById(R.id.webView_article);
        webView.setVisibility(View.INVISIBLE);
        new doit().execute();


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_mainPage:
                Toast.makeText(newsActivity_article.this, "Strona główna", Toast.LENGTH_SHORT).show();
                moveToMainActivity();
                break;

            case R.id.menu_classSwap:
                Toast.makeText(newsActivity_article.this, "Zamiana klas", Toast.LENGTH_SHORT).show();
                moveToclassSwapActivity();
                break;

            case R.id.menu_setting:
                Toast.makeText(newsActivity_article.this, "Ustawienia", Toast.LENGTH_SHORT).show();
                moveToSettingsActivity();
                break;

            case R.id.menu_information:
                Toast.makeText(newsActivity_article.this, "Informacje", Toast.LENGTH_SHORT).show();
                moveToInfoActivity();
                break;

            case R.id.menu_news:
                Toast.makeText(newsActivity_article.this, "Aktualnosci", Toast.LENGTH_SHORT).show();
                moveToNewsActivity();
                break;
        }

        return false;
    }

    //CHANGE ACTIVITY
    private void moveToSettingsActivity() {
        Intent intent = new Intent(newsActivity_article.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(newsActivity_article.this, MainActivity.class);
        startActivity(intent);
    }

    private void moveToclassSwapActivity(){
        Intent intent = new Intent(newsActivity_article.this, classSwapActivity.class);
        startActivity(intent);
    }

    private void moveToInfoActivity(){
        Intent intent = new Intent(newsActivity_article.this, infoActivity.class);
        startActivity(intent);
    }
    private void moveToNewsActivity(){
        Intent intent = new Intent(newsActivity_article.this, newsActivity.class);
        startActivity(intent);
    }



    public class doit extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            Document doc = null;

            try {
                doc = Jsoup.connect(getIntent().getStringExtra("LINK")).get();

                article = doc.select("article").text();


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            article_TV.setText(article);
            article_TV.setMovementMethod(new ScrollingMovementMethod());

            int length;
            length = article_TV.length();
            if (length>0){
            }else {
                article = "Przepraszamy za utrudnienia, aplikacja nie wsperia tego formatu, jeżeli chcesz otworzyć artykuł kliknij TUTAJ";
                SpannableString spannableString = new SpannableString(article);
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        String url = getIntent().getStringExtra("LINK");
                        webView.setVisibility(View.VISIBLE);
                        webView.setWebViewClient(new WebViewClient());
                        webView.loadUrl(url);
                    }
                };
                spannableString.setSpan(clickableSpan, 104,109, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                article_TV.setText(spannableString);
                article_TV.setMovementMethod(LinkMovementMethod.getInstance());

            }

        }
    }
    //OPENNING WEBBROSER
    public void openArticle(String url){

    }
}
