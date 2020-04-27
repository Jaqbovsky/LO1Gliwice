
//----------------------------------------//
// created by: Jakub Olszewski            //
// idea for application: Jakub Olszewski //
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

import org.jsoup.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class newsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //ADS
    private AdView mAdView;

    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    //STRINGS
    String url = "http://www.lo1.gliwice.pl/category/dla-uczniow/aktualnosci/";
    String title1;
    String title2;
    String title3;
    String title4;
    String title5;
    String title6;
    String title7;
    String title8;
    String title9;
    String title10;

    String link1;
    String link2;
    String link3;
    String link4;
    String link5;
    String link6;
    String link7;
    String link8;
    String link9;
    String link10;

    String str;
    String S_link;
    String news_title;
    final StringBuilder stringBuilder = new StringBuilder();
    final StringBuilder stringBuilder_link = new StringBuilder();
    //TEXTVIEWS
    TextView title1_TV;
    TextView title2_TV;
    TextView title3_TV;
    TextView title4_TV;
    TextView title5_TV;
    TextView title6_TV;
    TextView title7_TV;
    TextView title8_TV;
    TextView title9_TV;
    TextView title10_TV;
    int a;


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
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //NEWS
//        news_title_TV = findViewById(R.id.textView_news);


        //TEXTVIEW
        title1_TV = findViewById(R.id.textView_title1);
        title2_TV = findViewById(R.id.textView_title2);
        title3_TV = findViewById(R.id.textView_title3);
        title4_TV = findViewById(R.id.textView_title4);
        title5_TV = findViewById(R.id.textView_title5);
        title6_TV = findViewById(R.id.textView_title6);
        title7_TV = findViewById(R.id.textView_title7);
        title8_TV = findViewById(R.id.textView_title8);
        title9_TV = findViewById(R.id.textView_title9);
        title10_TV = findViewById(R.id.textView_title10);

        new doit().execute();
    }

    //
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_mainPage:
                Toast.makeText(newsActivity.this, "Strona główna", Toast.LENGTH_SHORT).show();
                moveToMainActivity();
                break;

            case R.id.menu_classSwap:
                Toast.makeText(newsActivity.this, "Zamiana klas", Toast.LENGTH_SHORT).show();
                moveToclassSwapActivity();
                break;

            case R.id.menu_setting:
                Toast.makeText(newsActivity.this, "Ustawienia", Toast.LENGTH_SHORT).show();
                moveToSettingsActivity();
                break;

            case R.id.menu_information:
                Toast.makeText(newsActivity.this, "Informacje", Toast.LENGTH_SHORT).show();
                moveToInfoActivity();
                break;

            case R.id.menu_news:
                Toast.makeText(newsActivity.this, "Aktualnosci", Toast.LENGTH_SHORT).show();
                moveToNewsActivity();
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

    private void moveToclassSwapActivity(){
        Intent intent = new Intent(newsActivity.this, classSwapActivity.class);
        startActivity(intent);
    }

    private void moveToInfoActivity(){
        Intent intent = new Intent(newsActivity.this, infoActivity.class);
        startActivity(intent);
    }
    private void moveToNewsActivity(){
        Intent intent = new Intent(newsActivity.this, newsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }



    //GETING DATA FROM WEBSITE
    public class doit extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            Document doc = null;
            Document doc2 = null;

            try {
                doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();

               Elements titles = doc.select("a[title]");
               Elements links = doc.select("a[title]");



                int a = 1;
                int b = 1;

                for (Element title : titles) {
                    if (a <= 10) {
                        stringBuilder.append("\n").append(a).append(".. ").append(title.text()).append("\n");
                        a = a + 1;

                    }
                }

                for (Element link : links){
                    if (b <= 10) {
                        stringBuilder_link.append("\n").append("(").append(b).append(") ").append(link.attr("href")).append("\n");
                        b = b+ 1;
                    }
                }


                str = stringBuilder.toString();
                S_link = stringBuilder_link.toString();


                title1 = str.substring(0,str.indexOf("2.."));
                title2 = str.substring(0,str.indexOf("3..")).substring(str.indexOf("2.."));
                title3 = str.substring(0,str.indexOf("4..")).substring(str.indexOf("3.."));
                title4 = str.substring(0,str.indexOf("5..")).substring(str.indexOf("4.."));
                title5 = str.substring(0,str.indexOf("6..")).substring(str.indexOf("5.."));
                title6 = str.substring(0,str.indexOf("7..")).substring(str.indexOf("6.."));
                title7 = str.substring(0,str.indexOf("8..")).substring(str.indexOf("7.."));
                title8 = str.substring(0,str.indexOf("9..")).substring(str.indexOf("8.."));
                title9 = str.substring(0,str.indexOf("10..")).substring(str.indexOf("9.."));
                title10 = str.substring(str.indexOf("10.."));

                link1 = S_link.substring(0,S_link.indexOf("(2)"));
                link2 = S_link.substring(0,S_link.indexOf("(3)")).substring(S_link.indexOf("(2)"));
                link3 = S_link.substring(0,S_link.indexOf("(4)")).substring(S_link.indexOf("(3)"));
                link4 = S_link.substring(0,S_link.indexOf("(5)")).substring(S_link.indexOf("(4)"));
                link5 = S_link.substring(0,S_link.indexOf("(6)")).substring(S_link.indexOf("(5)"));
                link6 = S_link.substring(0,S_link.indexOf("(7)")).substring(S_link.indexOf("(6)"));
                link7 = S_link.substring(0,S_link.indexOf("(8)")).substring(S_link.indexOf("(7)"));
                link8 = S_link.substring(0,S_link.indexOf("(9)")).substring(S_link.indexOf("(8)"));
                link9 = S_link.substring(0,S_link.indexOf("(10)")).substring(S_link.indexOf("(9)"));
                link10 = S_link.substring(S_link.indexOf("(10)"));





            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            title1_TV.setText(title1.replace("..","."));
            title2_TV.setText(title2.replace("..","."));
            title3_TV.setText(title3.replace("..","."));
            title4_TV.setText(title4.replace("..","."));
            title5_TV.setText(title5.replace("..","."));
            title6_TV.setText(title6.replace("..","."));
            title7_TV.setText(title7.replace("..","."));
            title8_TV.setText(title8.replace("..","."));
            title9_TV.setText(title9.replace("..","."));
            title10_TV.setText(title10.replace("..","."));

        }
    }
    public void showTitle1(View view) {
        Intent intent = new Intent(newsActivity.this, newsActivity_article.class);
        title1 = title1.replace("1..","");
        link1 = link1.replace("(1)","");
        intent.putExtra("LINK", link1);
        intent.putExtra("TITLE",title1);
        startActivity(intent);
    }
    public void showTitle2(View view) {
        Intent intent = new Intent(newsActivity.this, newsActivity_article.class);
        title2 = title2.replace("2..","");
        link2 = link2.replace("(2)","");
        intent.putExtra("LINK", link2);
        intent.putExtra("TITLE",title2);
        startActivity(intent);
    }
    public void showTitle3(View view) {
        Intent intent = new Intent(newsActivity.this, newsActivity_article.class);
        title3 = title3.replace("3..","");
        link3 = link3.replace("(3)","");
        intent.putExtra("LINK", link3);
        intent.putExtra("TITLE",title3);
        startActivity(intent);
    }
    public void showTitle4(View view) {
        Intent intent = new Intent(newsActivity.this, newsActivity_article.class);
        title4 = title4.replace("4..","");
        link4 = link4.replace("(4)","");
        intent.putExtra("LINK", link4);
        intent.putExtra("TITLE",title4);
        startActivity(intent);
    }
    public void showTitle5(View view) {
        Intent intent = new Intent(newsActivity.this, newsActivity_article.class);
        title5 = title5.replace("5..","");
        link5 = link5.replace("(5)","");
        intent.putExtra("LINK", link5);
        intent.putExtra("TITLE",title5);
        startActivity(intent);
    }
    public void showTitle6(View view) {
        Intent intent = new Intent(newsActivity.this, newsActivity_article.class);
        title6 = title6.replace("6..","");
        link6 = link6.replace("(6)","");
        intent.putExtra("LINK", link6);
        intent.putExtra("TITLE",title6);
        startActivity(intent);
    }
    public void showTitle7(View view) {
        Intent intent = new Intent(newsActivity.this, newsActivity_article.class);
        title7 = title7.replace("7..","");
        link7 = link7.replace("(7)","");
        intent.putExtra("LINK", link7);
        intent.putExtra("TITLE",title7);
        startActivity(intent);
    }
    public void showTitle8(View view) {
        Intent intent = new Intent(newsActivity.this, newsActivity_article.class);
        title8 = title8.replace("8..","");
        link8 = link8.replace("(8)","");
        intent.putExtra("LINK", link8);
        intent.putExtra("TITLE",title8);
        startActivity(intent);
    }
    public void showTitle9(View view) {
        Intent intent = new Intent(newsActivity.this, newsActivity_article.class);
        title9 = title9.replace("9..","");
        link9 = link9.replace("(9)","");
        intent.putExtra("LINK", link9);
        intent.putExtra("TITLE",title9);
        startActivity(intent);
    }
    public void showTitle10(View view) {
        Intent intent = new Intent(newsActivity.this, newsActivity_article.class);
        title10 = title10.replace("10..","");
        link10 = link10.replace("(10)","");
        intent.putExtra("LINK", link10);
        intent.putExtra("TITLE",title10);
        startActivity(intent);
    }
}