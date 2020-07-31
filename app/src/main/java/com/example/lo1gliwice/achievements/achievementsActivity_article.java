
//----------------------------------------//
// created by: Jakub Olszewski            //
//----------------------------------------//

package com.example.lo1gliwice.achievements;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class achievementsActivity_article extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Button goBack;
    TextView title_TV;
    TextView article_TV;
    String article;
    WebView webView;
    FloatingActionButton fab_openInWeb;
    int a;
    boolean run = false;
    //SIDEBAR MENU

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> link = new ArrayList<String>();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements_article);

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


        title = getIntent().getStringArrayListExtra("TITLE_LIST");
        link = getIntent().getStringArrayListExtra("LINK_LIST");

        title_TV = findViewById(R.id.textView_title);
        title_TV.setText(getIntent().getStringExtra("TITLE"));
        fab_openInWeb = findViewById(R.id.floatingActionButton_openInWeb);
        goBack = findViewById(R.id.button_goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(achievementsActivity_article.this, achievementsActivity.class);
                intent.putExtra("TITLE_LIST", title);
                intent.putExtra("LINK_LIST", link);
                intent.putExtra("RUN", run);
                startActivity(intent);
            }
        });

        article_TV = findViewById(R.id.textView_article);
        webView = findViewById(R.id.webView_article);
        webView.setVisibility(View.INVISIBLE);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        new doit().execute();



    }

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
        Intent intent = new Intent(achievementsActivity_article.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(achievementsActivity_article.this, MainActivity.class);
        startActivity(intent);
    }

    private void moveToclassSwapActivity(){
        Intent intent = new Intent(achievementsActivity_article.this, classSwapActivity.class);
        startActivity(intent);
    }

    private void moveToInfoActivity(){
        Intent intent = new Intent(achievementsActivity_article.this, infoActivity.class);
        startActivity(intent);
    }
    private void moveToNewsActivity(){
        Intent intent = new Intent(achievementsActivity_article.this, newsActivity.class);
        startActivity(intent);
    }

    private void moveToAboutSchoolActivity(){
        Intent intent = new Intent(achievementsActivity_article.this, aboutSchoolActivity.class);
        startActivity(intent);
    }

    private void moveToAchievementsActivity(){
        Intent intent = new Intent(achievementsActivity_article.this, achievementsActivity.class);
        startActivity(intent);
    }

    private void moveToArchiveActivity() {
        Intent intent = new Intent(achievementsActivity_article.this, archiveActivity.class);
        startActivity(intent);
    }

    public void openInWeb(View view) {
        CardView cardView = findViewById(R.id.card_view);
        if (a == 0) {
            cardView.setVisibility(View.GONE);
            String url = getIntent().getStringExtra("LINK");
            webView.setVisibility(View.VISIBLE);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(url);
            a = 1;
        }else{
            cardView.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            a = 0;
        }
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
}
