package com.example.lo1gliwice;

import androidx.annotation.InspectableProperty;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lo1gliwice.aboutSchool.aboutSchoolActivity;
import com.example.lo1gliwice.news.newsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class planActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,  AdapterView.OnItemSelectedListener {

    CardView pon, wt, sr, czw, pt;
    LinearLayout ll;
    GridLayout gridLayout, gridLayout2;
    FirebaseFirestore db;
    ProgressBar progressBar;
    Spinner spinner;

    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Map<String, Object>[] a;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

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


        //SPINNER
        List<String> classList = new ArrayList<>();
        classList.add("1A");
        classList.add("1B");
        classList.add("1C");
        classList.add("1E");
        classList.add("1D");
        classList.add("2Ap");
        classList.add("2Bp");
        classList.add("2Cp");
        classList.add("2Dp");
        classList.add("2Ep");
        classList.add("2Ag");
        classList.add("2Bg");
        classList.add("2Cg");
        classList.add("3A");
        classList.add("3B");
        classList.add("3C");


        spinner = findViewById(R.id.classSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classList);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        Object test = spinner.getSelectedItem();


        db = FirebaseFirestore.getInstance();

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        gridLayout = findViewById(R.id.grid);
        gridLayout2 = findViewById(R.id.grid2);

        pon = findViewById(R.id.pon);
        wt = findViewById(R.id.wt);
        sr = findViewById(R.id.sr);
        czw = findViewById(R.id.czw);
        pt = findViewById(R.id.pt);

        pon.setEnabled(false);
        wt.setEnabled(false);
        sr.setEnabled(false);
        czw.setEnabled(false);
        pt.setEnabled(false);
        spinner.setEnabled(false);

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        pon.setEnabled(false);
        wt.setEnabled(false);
        sr.setEnabled(false);
        czw.setEnabled(false);
        pt.setEnabled(false);
        spinner.setEnabled(false);
        spinner.setClickable(false);
        progressBar.setVisibility(View.VISIBLE);
        gridLayout.removeAllViews();
        a = new Map[]{new HashMap<>()};
        DocumentReference docRef = db.collection("Plan").document(parent.getItemAtPosition(position).toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        a[0] = document.getData();
                        pon.setEnabled(true);
                        wt.setEnabled(true);
                        sr.setEnabled(true);
                        czw.setEnabled(true);
                        pt.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                            Calendar calendar = Calendar.getInstance();
                            int day = calendar.get(Calendar.DAY_OF_WEEK);

                            switch (day) {
                                case Calendar.SUNDAY:
                                    Monday();
                                    break;
                                case Calendar.MONDAY:
                                    Monday();
                                    break;
                                case Calendar.TUESDAY:
                                    Tuesday();
                                    break;
                                case Calendar.WEDNESDAY:
                                    Wednesday();
                                    break;
                                case Calendar.THURSDAY:
                                    Thursday();
                                    break;
                                case Calendar.FRIDAY:
                                    Friday();
                                    break;
                                case Calendar.SATURDAY:
                                    Monday();
                                    break;
                            }


                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }

            }
        });



        pon.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Monday();
            }
        });

        wt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Tuesday();
            }
        });

        sr.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Wednesday();
            }
        });

        czw.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Thursday();
            }
        });

        pt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Friday();
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Monday(){
        Object o = a[0].get("Monday");
        List<String> list = new ArrayList<>();
        list = (List<String>) o;
        showPlan(list);
        
        pon.setScaleX((float) 1.2);
        pon.setScaleY((float) 1.2);
        wt.setScaleX((float) 1);
        wt.setScaleY((float) 1);
        sr.setScaleX((float) 1);
        sr.setScaleY((float) 1);
        czw.setScaleX((float) 1);
        czw.setScaleY((float) 1);
        pt.setScaleX((float) 1);
        pt.setScaleY((float) 1);

        pon.setCardBackgroundColor(Color.parseColor("#fffcf0"));
        wt.setCardBackgroundColor(Color.parseColor("#ffffff"));
        sr.setCardBackgroundColor(Color.parseColor("#ffffff"));
        czw.setCardBackgroundColor(Color.parseColor("#ffffff"));
        pt.setCardBackgroundColor(Color.parseColor("#ffffff"));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Tuesday(){
        Object o = a[0].get("Tuesday");
        List<String> list = new ArrayList<>();
        list = (List<String>) o;
        showPlan(list);

        pon.setScaleX((float) 1);
        pon.setScaleY((float) 1);
        wt.setScaleX((float) 1.2);
        wt.setScaleY((float) 1.2);
        sr.setScaleX((float) 1);
        sr.setScaleY((float) 1);
        czw.setScaleX((float) 1);
        czw.setScaleY((float) 1);
        pt.setScaleX((float) 1);
        pt.setScaleY((float) 1);

        pon.setCardBackgroundColor(Color.parseColor("#ffffff"));
        wt.setCardBackgroundColor(Color.parseColor("#fffcf0"));
        sr.setCardBackgroundColor(Color.parseColor("#ffffff"));
        czw.setCardBackgroundColor(Color.parseColor("#ffffff"));
        pt.setCardBackgroundColor(Color.parseColor("#ffffff"));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Wednesday(){
        Object o = a[0].get("Wednesday");
        List<String> list = new ArrayList<>();
        list = (List<String>) o;
        showPlan(list);

        pon.setScaleX((float) 1);
        pon.setScaleY((float) 1);
        wt.setScaleX((float) 1);
        wt.setScaleY((float) 1);
        sr.setScaleX((float) 1.2);
        sr.setScaleY((float) 1.2);
        czw.setScaleX((float) 1);
        czw.setScaleY((float) 1);
        pt.setScaleX((float) 1);
        pt.setScaleY((float) 1);



        pon.setCardBackgroundColor(Color.parseColor("#ffffff"));
        wt.setCardBackgroundColor(Color.parseColor("#ffffff"));
        sr.setCardBackgroundColor(Color.parseColor("#fffcf0"));
        czw.setCardBackgroundColor(Color.parseColor("#ffffff"));
        pt.setCardBackgroundColor(Color.parseColor("#ffffff"));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Thursday(){
        Object o = a[0].get("Thursday");
        List<String> list = new ArrayList<>();
        list = (List<String>) o;
        showPlan(list);

        pon.setScaleX((float) 1);
        pon.setScaleY((float) 1);
        wt.setScaleX((float) 1);
        wt.setScaleY((float) 1);
        sr.setScaleX((float) 1);
        sr.setScaleY((float) 1);
        czw.setScaleX((float) 1.2);
        czw.setScaleY((float) 1.2);
        pt.setScaleX((float) 1);
        pt.setScaleY((float) 1);

        pon.setCardBackgroundColor(Color.parseColor("#ffffff"));
        wt.setCardBackgroundColor(Color.parseColor("#ffffff"));
        sr.setCardBackgroundColor(Color.parseColor("#ffffff"));
        czw.setCardBackgroundColor(Color.parseColor("#fffcf0"));
        pt.setCardBackgroundColor(Color.parseColor("#ffffff"));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Friday(){
        Object o = a[0].get("Friday");
        List<String> list = new ArrayList<>();
        list = (List<String>) o;
        showPlan(list);

        pon.setScaleX((float) 1);
        pon.setScaleY((float) 1);
        wt.setScaleX((float) 1);
        wt.setScaleY((float) 1);
        sr.setScaleX((float) 1);
        sr.setScaleY((float) 1);
        czw.setScaleX((float) 1);
        czw.setScaleY((float) 1);
        pt.setScaleX((float) 1.2);
        pt.setScaleY((float) 1.2);

        pon.setCardBackgroundColor(Color.parseColor("#ffffff"));
        wt.setCardBackgroundColor(Color.parseColor("#ffffff"));
        sr.setCardBackgroundColor(Color.parseColor("#ffffff"));
        czw.setCardBackgroundColor(Color.parseColor("#ffffff"));
        pt.setCardBackgroundColor(Color.parseColor("#fffcf0"));

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    public void showPlan(List<String> list) {

        String[] arr = {
                "7:10 - 7:55",
                "8:00 - 8:45",
                "8:50 - 9:35",
                "9:45 - 10:30",
                "10:40 - 11:25",
                "11:35 - 12:20",
                "12:50 - 13:35",
                "13:40 - 14:25",
                "14:30 - 15:15",
                "15:20 - 16:05",
                "16:10 - 16:55",
                "17:00 - 17:45"
        };

        int hour = LocalDateTime.now().getHour();
        int minutes = LocalDateTime.now().getMinute();

        gridLayout.removeAllViews();
        System.out.println(list);
        LinearLayout.LayoutParams paramsC;

        for (int i = 0; i < list.size(); i++) {

            String[] timeString = arr[i].split(" - ");
            String[] time1arr = timeString[0].split(":");
            String[] time2arr = timeString[1].split(":");

            int time1 = Integer.parseInt(time1arr[0])*60 + Integer.parseInt(time1arr[1])-10;
            int time2 = Integer.parseInt(time2arr[0])*60 + Integer.parseInt(time2arr[1]);

            int myTime = hour*60 + minutes;

            System.out.println(time1 + "\n" + time2);

            paramsC = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsC.setMargins(20, 20, 20, 20);

            CardView cardView = new CardView(planActivity.this);
            cardView.setRadius(15);
            cardView.setCardElevation(15);
            cardView.setLayoutParams(paramsC);

            LinearLayout.LayoutParams params;
            params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            LinearLayout linearLayout = new LinearLayout(planActivity.this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(params);
            linearLayout.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams paramsT;
            paramsT = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            paramsT.setMargins(50, 50, 50, 50);

            LinearLayout.LayoutParams paramsT2;
            paramsT2 = new LinearLayout.LayoutParams(
                    350,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            paramsT2.setMargins(15, 50, 15, 50);

            TextView textView = new TextView(planActivity.this);
            TextView textView2 = new TextView(planActivity.this);

            List<String> numList = new ArrayList<>();
            String a = list.get(i);
            if (!(a.contains("114C") && a.contains("114F"))) {
                String str = list.get(i).replace("1/2", "").replace("2/2", "");
                str = str.replaceAll("[a-zA-Z]", "").replace("-", "").replace("#", "");
                String[] nums = str.split(" ");
                for (String b : nums) {
                    if (b.length() == 3) {
                        numList.add(b);
                    }
                }
                System.out.println(numList);
                if (numList.size() > 1) {
                    for (int j = 0; j < numList.size() - 1; j++) {
                        a = a.replace(numList.get(j), numList.get(j) + "\n");
                    }
                }
            } else {
                a = a.replace("114C","114C\n");
            }
            textView.setText(a);
            textView.setTextColor(Color.parseColor("#000000"));
            textView.setTextSize(16);
            textView.setLayoutParams(paramsT);
            textView.setGravity(Gravity.CENTER);

            textView2.setText(arr[i]);
            textView2.setTextColor(Color.parseColor("#000000"));
            textView2.setTextSize(16);
            textView2.setLayoutParams(paramsT2);
            textView2.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams paramsV;
            paramsV = new LinearLayout.LayoutParams(4,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
        paramsV.setMargins(0,5,0,5);


            if (time2 >= myTime && myTime >= time1){
                cardView.setCardBackgroundColor(Color.parseColor("#fffcf0"));
                System.out.println("crap");
            }


            View view = new View(planActivity.this);
            view.setBackgroundColor(Color.parseColor("#000000"));
            view.setLayoutParams(paramsV);
            view.setElevation(15);

            linearLayout.addView(textView2);
            linearLayout.addView(view);
            linearLayout.addView(textView);
            cardView.addView(linearLayout);
            if (!list.get(i).equals("brak"))
                gridLayout.addView(cardView);
        }

        spinner.setEnabled(true);
        spinner.setClickable(true);
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

            case R.id.menu_plan:
                moveToPlan();
        }

        return false;
    }

    //CHANGE ACTIVITY
    private void moveToMainActivity() {
        Intent intent = new Intent(planActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void moveToSettingsActivity() {
        Intent intent = new Intent(planActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void moveToclassSwapActivity() {
        Intent intent = new Intent(planActivity.this, classSwapActivity.class);
        startActivity(intent);
    }

    private void moveToInfoActivity() {
        Intent intent = new Intent(planActivity.this, infoActivity.class);
        startActivity(intent);
    }

    private void moveToNewsActivity() {
        Intent intent = new Intent(planActivity.this, newsActivity.class);
        startActivity(intent);
    }

    private void moveToAboutSchoolActivity() {
        Intent intent = new Intent(planActivity.this, aboutSchoolActivity.class);
        startActivity(intent);
    }

    private void moveToArchiveActivity() {
        Intent intent = new Intent(planActivity.this, archiveActivity.class);
        startActivity(intent);
    }

    private void moveToPlan() {
        Intent intent = new Intent(planActivity.this, planActivity.class);
        startActivity(intent);
    }

}