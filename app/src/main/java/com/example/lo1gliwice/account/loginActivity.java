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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //ADS
    private AdView mAdView;
    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    public EditText email_ET, password_ET;
    Button login_btn;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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
        login_btn = findViewById(R.id.button_logout);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mfirebaseUser = firebaseAuth.getCurrentUser();
                if (mfirebaseUser != null ){
                        Toast.makeText(loginActivity.this, "Zalogowano pomyślnie", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(loginActivity.this, homeActivity.class);
                        startActivity(intent);
                }else{
                    Toast.makeText(loginActivity.this, "Logowanie nie powiodło się", Toast.LENGTH_SHORT).show();

                }
            }
        };

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_ET.getText().toString();
                String passwd = password_ET.getText().toString();

                if (email.isEmpty()) {
                    email_ET.setError("Email jest wymagany");
                    email_ET.requestFocus();
                } else if (passwd.isEmpty()) {
                    password_ET.setError("Hasło jest wymagane");
                    password_ET.requestFocus();
                } else if (email.isEmpty() && passwd.isEmpty()) {
                    Toast.makeText(loginActivity.this, "Wypełnij pola!", Toast.LENGTH_SHORT).show();
                    password_ET.requestFocus();
                    email_ET.requestFocus();
                } else if (!(email.isEmpty() && passwd.isEmpty())) {
                    firebaseAuth.signInWithEmailAndPassword(email, passwd).addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(loginActivity.this, "Wystapił błąd. Spróbuj ponownie.", Toast.LENGTH_SHORT).show();
                            }else {
                                Intent intent = new Intent(loginActivity.this, homeActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    Toast.makeText(loginActivity.this, "Wystąpił bląd!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_mainPage:
                Toast.makeText(loginActivity.this, "Strona główna", Toast.LENGTH_SHORT).show();
                moveToMainActivity();
                break;

            case R.id.menu_classSwap:
                Toast.makeText(loginActivity.this, "Zamiana klas", Toast.LENGTH_SHORT).show();
                moveToclassSwapActivity();
                break;

            case R.id.menu_news:
                Toast.makeText(loginActivity.this, "Aktualnosci", Toast.LENGTH_SHORT).show();
                moveToNewsActivity();
                break;

            case R.id.menu_about_school:
                Toast.makeText(loginActivity.this, "O szkole", Toast.LENGTH_SHORT).show();
                moveToAboutSchoolActivity();
                break;

            case R.id.menu_setting:
                Toast.makeText(loginActivity.this, "Ustawienia", Toast.LENGTH_SHORT).show();
                moveToSettingsActivity();
                break;

            case R.id.menu_information:
                Toast.makeText(loginActivity.this, "Informacje", Toast.LENGTH_SHORT).show();
                moveToInfoActivity();
                break;


        }

        return false;
    }

    //CHANGE ACTIVITY
    private void moveToMainActivity() {
        Intent intent = new Intent(loginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void moveToSettingsActivity() {
        Intent intent = new Intent(loginActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void moveToclassSwapActivity() {
        Intent intent = new Intent(loginActivity.this, classSwapActivity.class);
        startActivity(intent);
    }

    private void moveToInfoActivity() {
        Intent intent = new Intent(loginActivity.this, infoActivity.class);
        startActivity(intent);
    }

    private void moveToNewsActivity() {
        Intent intent = new Intent(loginActivity.this, newsActivity.class);
        startActivity(intent);
    }

    private void moveToAboutSchoolActivity() {
        Intent intent = new Intent(loginActivity.this, aboutSchoolActivity.class);
        startActivity(intent);
    }

    public void register(View view){
        Intent intent = new Intent(loginActivity.this, registerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    protected void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}
