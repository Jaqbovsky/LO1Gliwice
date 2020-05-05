package com.example.lo1gliwice.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.firebase.client.Firebase;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //ADS
    private AdView mAdView;
    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Firebase firebase;

    public EditText email_ET, password_ET, name_ET;
    Button login_btn;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


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

        //AUTH
        firebaseAuth = FirebaseAuth.getInstance();
        email_ET = findViewById(R.id.editText_email);
        password_ET = findViewById(R.id.editText_passwd);
        name_ET = findViewById(R.id.editText_name);
        login_btn = findViewById(R.id.button_logout);

        Firebase.setAndroidContext(this);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_ET.getText().toString();
                String passwd = password_ET.getText().toString();
                String name = name_ET.getText().toString();
                if (email.isEmpty()) {
                    email_ET.setError("Email jest wymagany");
                    email_ET.requestFocus();
                } else if (passwd.isEmpty()) {
                    password_ET.setError("Hasło jest wymagane");
                    password_ET.requestFocus();
                } else if (email.isEmpty() && passwd.isEmpty()) {
                    Toast.makeText(registerActivity.this, "Wypełnij pola!", Toast.LENGTH_SHORT).show();
                    password_ET.requestFocus();
                    email_ET.requestFocus();
                } else if (!(email.isEmpty() && passwd.isEmpty())) {
                    firebaseAuth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(registerActivity.this, "Wystąpił problem podczas rejestracji!", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(registerActivity.this, homeActivity.class);
                                startActivity(intent);

                                firebase = new Firebase("https://lo1-gliwice-remake.firebaseio.com/users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

                                Firebase child_name = firebase.child("Name");
                                child_name.setValue(name);

                                Firebase child_email = firebase.child("Email");
                                child_email.setValue(email);

                                Firebase child_permission = firebase.child("Permission");
                                child_permission.setValue("user");
                            }
                        }
                    });
                } else {
                    Toast.makeText(registerActivity.this, "Wystąpił bląd!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_mainPage:
                Toast.makeText(registerActivity.this, "Strona główna", Toast.LENGTH_SHORT).show();
                moveToMainActivity();
                break;

            case R.id.menu_classSwap:
                Toast.makeText(registerActivity.this, "Zamiana klas", Toast.LENGTH_SHORT).show();
                moveToclassSwapActivity();
                break;

            case R.id.menu_news:
                Toast.makeText(registerActivity.this, "Aktualnosci", Toast.LENGTH_SHORT).show();
                moveToNewsActivity();
                break;

            case R.id.menu_about_school:
                Toast.makeText(registerActivity.this, "O szkole", Toast.LENGTH_SHORT).show();
                moveToAboutSchoolActivity();
                break;

            case R.id.menu_setting:
                Toast.makeText(registerActivity.this, "Ustawienia", Toast.LENGTH_SHORT).show();
                moveToSettingsActivity();
                break;

            case R.id.menu_information:
                Toast.makeText(registerActivity.this, "Informacje", Toast.LENGTH_SHORT).show();
                moveToInfoActivity();
                break;


        }

        return false;
    }

    //CHANGE ACTIVITY
    private void moveToMainActivity() {
        Intent intent = new Intent(registerActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void moveToSettingsActivity() {
        Intent intent = new Intent(registerActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void moveToclassSwapActivity() {
        Intent intent = new Intent(registerActivity.this, classSwapActivity.class);
        startActivity(intent);
    }

    private void moveToInfoActivity() {
        Intent intent = new Intent(registerActivity.this, infoActivity.class);
        startActivity(intent);
    }

    private void moveToNewsActivity() {
        Intent intent = new Intent(registerActivity.this, newsActivity.class);
        startActivity(intent);
    }

    private void moveToAboutSchoolActivity() {
        Intent intent = new Intent(registerActivity.this, aboutSchoolActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent(registerActivity.this, loginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
