
//-----------------------------//
// created by: Jakub Olszewski //
//-----------------------------//

package com.example.lo1gliwice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.lo1gliwice.aboutSchool.aboutSchoolActivity;
import com.example.lo1gliwice.achievements.achievementsActivity_article;
import com.example.lo1gliwice.news.newsActivity;
import com.firebase.client.Firebase;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

public class feedbackActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText nameData;
    EditText emailData;
    EditText messageData;
    Button send;
    Button details;
    Firebase firebase;
    //ADS
    private AdView mAdView;

    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    CheckBox error_CB, opinion_CB, idea_CB;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

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
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //FEEDBACK
        nameData = findViewById(R.id.editText_nameData);
        emailData = findViewById(R.id.editText_emailData);
        messageData = findViewById(R.id.editText_messageData);

        error_CB = findViewById(R.id.checkBox_error);
        opinion_CB = findViewById(R.id.checkBox_opinion);
        idea_CB = findViewById(R.id.checkBox_idea);

        send = findViewById(R.id.button_send);
        details = findViewById(R.id.button_details);


        Firebase.setAndroidContext(this);

        String UniqueID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        error_CB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opinion_CB.setChecked(false);
                idea_CB.setChecked(false);
                firebase = new Firebase("https://lo-gliwice.firebaseio.com/error/" + UniqueID);
            }
        });

        opinion_CB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error_CB.setChecked(false);
                idea_CB.setChecked(false);
                firebase = new Firebase("https://lo-gliwice.firebaseio.com/opinion/" + UniqueID);
            }
        });

        idea_CB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error_CB.setChecked(false);
                opinion_CB.setChecked(false);
                firebase = new Firebase("https://lo-gliwice.firebaseio.com/idea/" + UniqueID);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details.setEnabled(true);
                final String name = nameData.getText().toString();
                final String email = emailData.getText().toString();
                final String message = messageData.getText().toString();

                Firebase child_name = firebase.child("Name");
                child_name.setValue(name);
                if (name.isEmpty()){
                    nameData.setError("To pole jest wymagane!");
                    send.setEnabled(false);

                }else{
                    nameData.setError(null);
                    send.setEnabled(true);
                }

                Firebase child_email = firebase.child("Email");
                child_email.setValue(email);
                if (email.isEmpty()){
                    emailData.setError("To pole jest wymagane!");
                    send.setEnabled(false);

                }else{
                    emailData.setError(null);
                    send.setEnabled(true);
                }

                Firebase child_message = firebase.child("Message");
                child_message.setValue(message);
                if (message.isEmpty()){
                    messageData.setError("To pole jest wymagane!");
                    send.setEnabled(false);

                }else{
                    messageData.setError(null);
                    send.setEnabled(true);
                }
                Toast.makeText(feedbackActivity.this, "Wiadomość zostałą wysłana", Toast.LENGTH_SHORT).show();

                details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(feedbackActivity.this)
                                .setTitle("Szczegoły wiadomości:")
                                .setMessage("Imię - " + name + "\n\nEmail - " + email + "\n\nWiadomość - " + message)
                                .show();
                    }
                });
            }
        });
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
                break;

            case R.id.menu_plan:
                moveToPlan();
                break;
        }

        return false;
    }

    //CHANGE ACTIVITY
    private void moveToSettingsActivity() {
        Intent intent = new Intent(feedbackActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(feedbackActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void moveToclassSwapActivity(){
        Intent intent = new Intent(feedbackActivity.this, classSwapActivity.class);
        startActivity(intent);
    }

    private void moveToInfoActivity(){
        Intent intent = new Intent(feedbackActivity.this, infoActivity.class);
        startActivity(intent);
    }
    private void moveToNewsActivity(){
        Intent intent = new Intent(feedbackActivity.this, newsActivity.class);
        startActivity(intent);
    }

    private void moveToFeedbackActivity(){
        Intent intent = new Intent(feedbackActivity.this, feedbackActivity.class);
        startActivity(intent);
    }
    private void moveToAboutSchoolActivity(){
        Intent intent = new Intent(feedbackActivity.this, aboutSchoolActivity.class);
        startActivity(intent);
    }

    private void moveToArchiveActivity() {
        Intent intent = new Intent(feedbackActivity.this, archiveActivity.class);
        startActivity(intent);
    }

    private void moveToPlan() {
        Intent intent = new Intent(feedbackActivity.this, planActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
