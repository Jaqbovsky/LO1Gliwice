
//-----------------------------//
// created by: Jakub Olszewski //
//-----------------------------//

package com.example.lo1gliwice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.lo1gliwice.aboutSchool.aboutSchoolActivity;
import com.example.lo1gliwice.news.newsActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

public class SettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String CHANNEL_ID = "CHANNEL_ID";
    //ADS
    private AdView mAdView;
    private CheckBoxPreference Check;
    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //SAVING SETTINGS
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.getBoolean("notification", true);
        sp.getString("yourClass", "Wybierz");
        sp.getString("surname", "Wpisz nazwisko");

        //SIDEBAR MENU
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout.addDrawerListener(toggle);
        //toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //ADS
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //NOTIFICATIONS
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
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private void moveToMainActivity() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private void moveToclassSwapActivity(){
        Intent intent = new Intent(SettingsActivity.this, classSwapActivity.class);
        startActivity(intent);
    }
    private void moveToInfoActivity(){
        Intent intent = new Intent(SettingsActivity.this, infoActivity.class);
        startActivity(intent);
    }
    private void moveToNewsActivity(){
        Intent intent = new Intent(SettingsActivity.this, newsActivity.class);
        startActivity(intent);
    }
    private void moveToAboutSchoolActivity(){
        Intent intent = new Intent(SettingsActivity.this, aboutSchoolActivity.class);
        startActivity(intent);
    }
    private void moveToArchiveActivity() {
        Intent intent = new Intent(SettingsActivity.this, archiveActivity.class);
        startActivity(intent);
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {

        public static final String yourClass = "yourClass";
        public static final String amITeacher = "amITeacher";
        public static final String surname = "surname";
        public static final String categorySurname = "surnameCategory";
        public static final String categoryClass = "classCategory";
        private CheckBoxPreference Check;
        private PreferenceCategory pCS, pCC;
        private EditTextPreference Surname;

        private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            Check = (CheckBoxPreference)findPreference(amITeacher);
            pCS = (PreferenceCategory)findPreference(categorySurname);
            pCC = (PreferenceCategory)findPreference(categoryClass);
            Check.setOnPreferenceChangeListener(new CheckBoxPreference.OnPreferenceChangeListener() {
                public boolean onPreferenceChange(final Preference preference, final Object newValue) {
                    pCS.setEnabled((Boolean) newValue);
                    pCC.setEnabled(!(Boolean) newValue);
                    return true;
                }
            });
            Surname = (EditTextPreference)findPreference(surname);

            preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key.equals(yourClass)) {
                        Preference yourClass = findPreference(key);
                        yourClass.setSummary(sharedPreferences.getString(key, ""));
                    }
                    if (key.equals(surname)){
                        Surname.setText(sharedPreferences.getString("","Wpisz nazw"));
                    }
                }
            };

        }

    }
}