package com.example.lo1gliwice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lo1gliwice.aboutSchool.aboutSchoolActivity;
import com.example.lo1gliwice.news.newsActivity;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class archiveActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    //ADS
    private AdView mAdView;
    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    List<String> month = new ArrayList<>();
    List<String> monthEng = new ArrayList<>();
    List<Integer> days = new ArrayList<>();
    String myMonth, pM, eM;
    int myDay;
    boolean a = false;
    TextView date_TV, Rdata_TV;
    FirebaseFirestore db;
    private static String TAG = "archiveActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        db = FirebaseFirestore.getInstance();

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

        date_TV = findViewById(R.id.textView_date);
        Rdata_TV = findViewById(R.id.textView_retrivered_data);

        month.add("Wrzesień");
        month.add("Październik");
        month.add("Listopad");
        month.add("Grudzień");
        month.add("Styczeń");
        month.add("Luty");
        month.add("Marzec");
        month.add("Kwiecień");
        month.add("Maj");
        month.add("Czerwiec");

        monthEng.add("September");
        monthEng.add("October");
        monthEng.add("November");
        monthEng.add("December");
        monthEng.add("January");
        monthEng.add("February");
        monthEng.add("March");
        monthEng.add("April");
        monthEng.add("May");
        monthEng.add("June");

        //SPINNER
        Spinner spinnerM = findViewById(R.id.month_spinner);
        ArrayAdapter<String> adapterM =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, month);
        adapterM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerM.setAdapter(adapterM);
        spinnerM.setOnItemSelectedListener(this);

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        myMonth = parent.getItemAtPosition(position).toString();

        Spinner spinner = (Spinner) parent;
        Spinner spinner1 = (Spinner) parent;

        if (spinner.getId() == R.id.month_spinner){
            myMonth = parent.getItemAtPosition(position).toString();
            Toast.makeText(archiveActivity.this, myMonth , Toast.LENGTH_SHORT).show();
            a = true;

            if (myMonth.equals(month.get(0))){
                days30();
                pM = month.get(0);
                eM = monthEng.get(0);
            }
            if (myMonth.equals(month.get(1))){
                days31();
                pM = month.get(1);
                eM = monthEng.get(1);
            }
            if (myMonth.equals(month.get(2))){
                days30();
                pM = month.get(2);
                eM = monthEng.get(2);
            }
            if (myMonth.equals(month.get(3))){
                days31();
                pM = month.get(3);
                eM = monthEng.get(3);
            }
            if (myMonth.equals(month.get(4))){
                days31();
                pM = month.get(4);
                eM = monthEng.get(4);
            }
            if (myMonth.equals(month.get(5))){
                days28();
                pM = month.get(5);
                eM = monthEng.get(5);
            }
            if (myMonth.equals(month.get(6))){
                days31();
                pM = month.get(6);
                eM = monthEng.get(6);
            }
            if (myMonth.equals(month.get(7))){
                days30();
                pM = month.get(7);
                eM = monthEng.get(7);
            }
            if (myMonth.equals(month.get(8))){
                days31();
                pM = month.get(8);
                eM = monthEng.get(8);
            }
            if (myMonth.equals(month.get(9))){
                days30();
                pM = month.get(9);
                eM = monthEng.get(9);
            }

        }

        if (spinner1.getId() == R.id.day_spinner){
            myDay = Integer.parseInt(parent.getItemAtPosition(position).toString());
            date_TV.setText(myDay + " " + pM );
            String day = String.valueOf(myDay);
            retriverData(day);
        }

        if (a){
            Spinner spinnerD = findViewById(R.id.day_spinner);
            ArrayAdapter<Integer> adapter =  new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerD.setAdapter(adapter);
            spinnerD.setOnItemSelectedListener(this);

            a = false;

        }

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void days30(){
        days.clear();
        for(int i = 1; i <= 30; i++){
            days.add(i);
        }
    }

    public void days31(){
        days.clear();
        for(int i = 1; i <= 31; i++){
            days.add(i);
        }
    }

    public void days28(){
        days.clear();
        for(int i = 1; i <= 28; i++){
            days.add(i);
        }
    }

    public void days29(){
        days.clear();
        for(int i = 1; i <= 29; i++){
            days.add(i);
        }
    }

    public void retriverData(String day){

        DocumentReference docRef = db.collection(eM).document(day);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Rdata_TV.setText(document.getString("replacement").toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_mainPage:
                Toast.makeText(archiveActivity.this, "Strona główna", Toast.LENGTH_SHORT).show();
                moveToMainActivity();
                break;

            case R.id.menu_classSwap:
                Toast.makeText(archiveActivity.this, "Zamiana klas", Toast.LENGTH_SHORT).show();
                moveToclassSwapActivity();
                break;

            case R.id.menu_news:
                Toast.makeText(archiveActivity.this, "Aktualnosci", Toast.LENGTH_SHORT).show();
                moveToNewsActivity();
                break;

            case R.id.menu_about_school:
                Toast.makeText(archiveActivity.this, "O szkole", Toast.LENGTH_SHORT).show();
                moveToAboutSchoolActivity();
                break;

            case R.id.menu_setting:
                Toast.makeText(archiveActivity.this, "Ustawienia", Toast.LENGTH_SHORT).show();
                moveToSettingsActivity();
                break;

            case R.id.menu_information:
                Toast.makeText(archiveActivity.this, "Informacje", Toast.LENGTH_SHORT).show();
                moveToInfoActivity();
                break;


        }

        return false;
    }

    //CHANGE ACTIVITY
    private void moveToMainActivity() {
        Intent intent = new Intent(archiveActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void moveToSettingsActivity() {
        Intent intent = new Intent(archiveActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void moveToclassSwapActivity() {
        Intent intent = new Intent(archiveActivity.this, classSwapActivity.class);
        startActivity(intent);
    }

    private void moveToInfoActivity() {
        Intent intent = new Intent(archiveActivity.this, infoActivity.class);
        startActivity(intent);
    }

    private void moveToNewsActivity() {
        Intent intent = new Intent(archiveActivity.this, newsActivity.class);
        startActivity(intent);
    }

    private void moveToAboutSchoolActivity() {
        Intent intent = new Intent(archiveActivity.this, aboutSchoolActivity.class);
        startActivity(intent);
    }

}
