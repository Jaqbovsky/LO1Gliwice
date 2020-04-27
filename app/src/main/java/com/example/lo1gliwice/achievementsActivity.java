package com.example.lo1gliwice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class achievementsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //ADS
    private AdView mAdView;

    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    //TEXTVIEW
    TextView result_TV;
    TextView achievements1_TV;
    TextView achievements2_TV;
    TextView achievements3_TV;
    TextView achievements4_TV;
    TextView achievements5_TV;
    TextView achievements6_TV;
    TextView achievements7_TV;
    TextView achievements8_TV;
    TextView achievements9_TV;
    TextView achievements10_TV;

    String doc1_wyniki, doc1_w_finale, doc1_w_etapie, doc1_sukces, doc1_olimpiad, doc1_mistrz, doc1_laurat, doc1_finalist;
    String doc1_wyniki_link, doc1_w_finale_link, doc1_w_etapie_link, doc1_sukces_link, doc1_olimpiad_link, doc1_mistrz_link, doc1_laurat_link, doc1_finalist_link;
    String result;
    String str;
    String doc2_str;
    String S_link;

    String url1 = "http://www.lo1.gliwice.pl/category/dla-uczniow/aktualnosci-dla-uczniow/";
    String url2 = "http://www.lo1.gliwice.pl/category/dla-uczniow/aktualnosci-dla-uczniow/page/2/";
    String url3 = "http://www.lo1.gliwice.pl/category/dla-uczniow/aktualnosci-dla-uczniow/page/3/";

    String doc1_title1;
    String doc1_title2;
    String doc1_title3;
    String doc1_title4;
    String doc1_title5;
    String doc1_title6;
    String doc1_title7;
    String doc1_title8;
    String doc1_title9;
    String doc1_title10;

    String doc1_link1;
    String doc1_link2;
    String doc1_link3;
    String doc1_link4;
    String doc1_link5;
    String doc1_link6;
    String doc1_link7;
    String doc1_link8;
    String doc1_link9;
    String doc1_link10;

    final StringBuilder stringBuilder = new StringBuilder();
    final StringBuilder stringBuilder_link = new StringBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
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

        result_TV = findViewById(R.id.textView_result);
        achievements1_TV = findViewById(R.id.textView_achievements1);
        achievements2_TV = findViewById(R.id.textView_achievements2);
        achievements3_TV = findViewById(R.id.textView_achievements3);
        achievements4_TV = findViewById(R.id.textView_achievements4);
        achievements5_TV = findViewById(R.id.textView_achievements5);
        achievements6_TV = findViewById(R.id.textView_achievements6);
        achievements7_TV = findViewById(R.id.textView_achievements7);
        achievements8_TV = findViewById(R.id.textView_achievements8);
        achievements9_TV = findViewById(R.id.textView_achievements9);
        achievements10_TV = findViewById(R.id.textView_achievements10);


        new doit().execute();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_mainPage:
                Toast.makeText(achievementsActivity.this, "Strona główna", Toast.LENGTH_SHORT).show();
                moveToMainActivity();
                break;

            case R.id.menu_classSwap:
                Toast.makeText(achievementsActivity.this, "Zamiana klas", Toast.LENGTH_SHORT).show();
                moveToclassSwapActivity();
                break;

            case R.id.menu_news:
                Toast.makeText(achievementsActivity.this, "Aktualnosci", Toast.LENGTH_SHORT).show();
                moveToNewsActivity();
                break;

            case R.id.menu_about_school:
                Toast.makeText(achievementsActivity.this, "O szkole", Toast.LENGTH_SHORT).show();
                moveToAboutSchoolActivity();
                break;

            case R.id.menu_setting:
                Toast.makeText(achievementsActivity.this, "Ustawienia", Toast.LENGTH_SHORT).show();
                moveToSettingsActivity();
                break;

            case R.id.menu_information:
                Toast.makeText(achievementsActivity.this, "Informacje", Toast.LENGTH_SHORT).show();
                moveToInfoActivity();
                break;


        }

        return false;
    }

    //CHANGE ACTIVITY
    private void moveToMainActivity() {
        Intent intent = new Intent(achievementsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void moveToSettingsActivity() {
        Intent intent = new Intent(achievementsActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void moveToclassSwapActivity() {
        Intent intent = new Intent(achievementsActivity.this, classSwapActivity.class);
        startActivity(intent);
    }

    private void moveToInfoActivity() {
        Intent intent = new Intent(achievementsActivity.this, infoActivity.class);
        startActivity(intent);
    }

    private void moveToNewsActivity() {
        Intent intent = new Intent(achievementsActivity.this, newsActivity.class);
        startActivity(intent);
    }

    private void moveToAboutSchoolActivity() {
        Intent intent = new Intent(achievementsActivity.this, aboutSchoolActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //GETING DATA FROM WEBSITE
    public class doit extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            Document doc1 = null;
            Document doc2 = null;

            try {
                doc1 = Jsoup.connect(url1).userAgent("Mozilla/5.0").get();

                Elements doc1_titles = doc1.select("a[title]");
                Elements doc1_links = doc1.select("a[title]");


                int doc1_a = 1;
                int doc1_b = 1;

                for (Element title : doc1_titles) {
                    if (doc1_a <= 10) {
                        stringBuilder.append("\n").append(doc1_a).append(".. ").append(title.text()).append("\n");
                        doc1_a = doc1_a + 1;
                    }
                }

                for (Element link : doc1_links) {
                    if (doc1_b <= 10) {
                        stringBuilder_link.append("\n").append("(").append(doc1_b).append(") ").append(link.attr("href")).append("\n");
                        doc1_b = doc1_b + 1;
                    }
                }


                str = stringBuilder.toString();
                S_link = stringBuilder_link.toString();


                doc1_title1 = str.substring(0, str.indexOf("2.."));
                doc1_title2 = str.substring(0, str.indexOf("3..")).substring(str.indexOf("2.."));
                doc1_title3 = str.substring(0, str.indexOf("4..")).substring(str.indexOf("3.."));
                doc1_title4 = str.substring(0, str.indexOf("5..")).substring(str.indexOf("4.."));
                doc1_title5 = str.substring(0, str.indexOf("6..")).substring(str.indexOf("5.."));
                doc1_title6 = str.substring(0, str.indexOf("7..")).substring(str.indexOf("6.."));
                doc1_title7 = str.substring(0, str.indexOf("8..")).substring(str.indexOf("7.."));
                doc1_title8 = str.substring(0, str.indexOf("9..")).substring(str.indexOf("8.."));
                doc1_title9 = str.substring(0, str.indexOf("10..")).substring(str.indexOf("9.."));
                doc1_title10 = str.substring(str.indexOf("10.."));

                doc1_link1 = S_link.substring(0, S_link.indexOf("(2)"));
                doc1_link2 = S_link.substring(0, S_link.indexOf("(3)")).substring(S_link.indexOf("(2)"));
                doc1_link3 = S_link.substring(0, S_link.indexOf("(4)")).substring(S_link.indexOf("(3)"));
                doc1_link4 = S_link.substring(0, S_link.indexOf("(5)")).substring(S_link.indexOf("(4)"));
                doc1_link5 = S_link.substring(0, S_link.indexOf("(6)")).substring(S_link.indexOf("(5)"));
                doc1_link6 = S_link.substring(0, S_link.indexOf("(7)")).substring(S_link.indexOf("(6)"));
                doc1_link7 = S_link.substring(0, S_link.indexOf("(8)")).substring(S_link.indexOf("(7)"));
                doc1_link8 = S_link.substring(0, S_link.indexOf("(9)")).substring(S_link.indexOf("(8)"));
                doc1_link9 = S_link.substring(0, S_link.indexOf("(10)")).substring(S_link.indexOf("(9)"));
                doc1_link10 = S_link.substring(S_link.indexOf("(10)"));

                if (doc1_title1.toLowerCase().contains("wyniki")) {
                    doc1_wyniki = doc1_title1;
                    doc1_wyniki_link = doc1_link1;
                } else {
                    if (doc1_title2.toLowerCase().contains("wyniki")) {
                        doc1_wyniki = doc1_title2;
                        doc1_wyniki_link = doc1_link2;
                    } else {
                        if (doc1_title3.toLowerCase().contains("wyniki")) {
                            doc1_wyniki = doc1_title3;
                            doc1_wyniki_link = doc1_link3;
                        } else {
                            if (doc1_title4.toLowerCase().contains("wyniki")) {
                                doc1_wyniki = doc1_title4;
                                doc1_wyniki_link = doc1_link4;
                            } else {
                                if (doc1_title5.toLowerCase().contains("wyniki")) {
                                    doc1_wyniki = doc1_title5;
                                    doc1_wyniki_link = doc1_link5;
                                } else {
                                    if (doc1_title6.toLowerCase().contains("wyniki")) {
                                        doc1_wyniki = doc1_title6;
                                        doc1_wyniki_link = doc1_link6;
                                    } else {
                                        if (doc1_title7.toLowerCase().contains("wyniki")) {
                                            doc1_wyniki = doc1_title7;
                                            doc1_wyniki_link = doc1_link7;
                                        } else {
                                            if (doc1_title8.toLowerCase().contains("wyniki")) {
                                                doc1_wyniki = doc1_title8;
                                                doc1_wyniki_link = doc1_link8;
                                            } else {
                                                if (doc1_title9.toLowerCase().contains("wyniki")) {
                                                    doc1_wyniki = doc1_title9;
                                                    doc1_wyniki_link = doc1_link9;
                                                } else {
                                                    if (doc1_title10.toLowerCase().contains("wyniki")) {
                                                        doc1_wyniki = doc1_title10;
                                                        doc1_wyniki_link = doc1_link10;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (doc1_title1.toLowerCase().contains("sukces")) {
                    doc1_sukces = doc1_title1;
                    doc1_sukces_link = doc1_link1;
                } else {
                    if (doc1_title2.toLowerCase().contains("sukces")) {
                        doc1_sukces = doc1_title2;
                        doc1_sukces_link = doc1_link2;
                    } else {
                        if (doc1_title3.toLowerCase().contains("sukces")) {
                            doc1_sukces = doc1_title3;
                            doc1_sukces_link = doc1_link3;
                        } else {
                            if (doc1_title4.toLowerCase().contains("sukces")) {
                                doc1_sukces = doc1_title4;
                                doc1_sukces_link = doc1_link4;
                            } else {
                                if (doc1_title5.toLowerCase().contains("sukces")) {
                                    doc1_sukces = doc1_title5;
                                    doc1_sukces_link = doc1_link5;
                                } else {
                                    if (doc1_title6.toLowerCase().contains("sukces")) {
                                        doc1_sukces = doc1_title6;
                                        doc1_sukces_link = doc1_link6;
                                    } else {
                                        if (doc1_title7.toLowerCase().contains("sukces")) {
                                            doc1_sukces = doc1_title7;
                                            doc1_sukces_link = doc1_link7;
                                        } else {
                                            if (doc1_title8.toLowerCase().contains("sukces")) {
                                                doc1_sukces = doc1_title8;
                                                doc1_sukces_link = doc1_link8;
                                            } else {
                                                if (doc1_title9.toLowerCase().contains("sukces")) {
                                                    doc1_sukces = doc1_title9;
                                                    doc1_sukces_link = doc1_link9;
                                                } else {
                                                    if (doc1_title10.toLowerCase().contains("sukces")) {
                                                        doc1_sukces = doc1_title10;
                                                        doc1_sukces_link = doc1_link10;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (doc1_title1.toLowerCase().contains("w finale")) {
                    doc1_w_finale = doc1_title1;
                    doc1_w_finale_link = doc1_title1;
                } else {
                    if (doc1_title2.toLowerCase().contains("w finale")) {
                        doc1_w_finale = doc1_title2;
                        doc1_w_finale_link = doc1_link2;
                    } else {
                        if (doc1_title3.toLowerCase().contains("w finale")) {
                            doc1_w_finale = doc1_title3;
                            doc1_w_finale_link = doc1_link3;
                        } else {
                            if (doc1_title4.toLowerCase().contains("w finale")) {
                                doc1_w_finale = doc1_title4;
                                doc1_w_finale_link = doc1_link4;
                            } else {
                                if (doc1_title5.toLowerCase().contains("w finale")) {
                                    doc1_w_finale = doc1_title5;
                                    doc1_w_finale_link = doc1_link5;
                                } else {
                                    if (doc1_title6.toLowerCase().contains("w finale")) {
                                        doc1_w_finale = doc1_title6;
                                        doc1_w_finale_link = doc1_link6;
                                    } else {
                                        if (doc1_title7.toLowerCase().contains("w finale")) {
                                            doc1_w_finale = doc1_title7;
                                        } else {
                                            if (doc1_title8.toLowerCase().contains("w fianle")) {
                                                doc1_w_finale = doc1_title8;
                                            } else {
                                                if (doc1_title9.toLowerCase().contains("w finale")) {
                                                    doc1_w_finale = doc1_title9;
                                                } else {
                                                    if (doc1_title10.toLowerCase().contains("w finale")) {
                                                        doc1_w_finale = doc1_title10;
                                                        doc1_w_finale_link = doc1_link10;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                if (doc1_title1.toLowerCase().contains("laureat")) {
                    if (doc1_title1.toLowerCase().contains("w etapie")) {
                        doc1_w_etapie = doc1_title1;
                        doc1_w_etapie_link = doc1_link1;
                    } else {
                        if (doc1_title2.toLowerCase().contains("w etapie")) {
                            doc1_w_etapie = doc1_title2;
                            doc1_w_etapie_link = doc1_link2;
                        } else {
                            if (doc1_title3.toLowerCase().contains("w etapie")) {
                                doc1_w_etapie = doc1_title3;
                                doc1_w_etapie_link = doc1_link3;
                            } else {
                                if (doc1_title4.toLowerCase().contains("w etapie")) {
                                    doc1_w_etapie = doc1_title4;
                                    doc1_w_etapie_link = doc1_link4;
                                } else {
                                    if (doc1_title5.toLowerCase().contains("w etapie")) {
                                        doc1_w_etapie = doc1_title5;
                                        doc1_w_etapie_link = doc1_link5;
                                    } else {
                                        if (doc1_title6.toLowerCase().contains("w etapie")) {
                                            doc1_w_etapie = doc1_title6;
                                            doc1_w_etapie_link = doc1_link6;
                                        } else {
                                            if (doc1_title7.toLowerCase().contains("w etapie")) {
                                                doc1_w_etapie = doc1_title7;
                                                doc1_w_etapie_link = doc1_link7;
                                            } else {
                                                if (doc1_title8.toLowerCase().contains("w etapie")) {
                                                    doc1_w_etapie = doc1_title8;
                                                    doc1_w_etapie_link = doc1_link8;
                                                } else {
                                                    if (doc1_title9.toLowerCase().contains("w etapie")) {
                                                        doc1_w_etapie = doc1_title9;
                                                        doc1_w_etapie_link = doc1_link9;
                                                    } else {
                                                        if (doc1_title10.toLowerCase().contains("w etapie")) {
                                                            doc1_w_etapie = doc1_title10;
                                                            doc1_w_etapie_link = doc1_link10;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (doc1_title1.toLowerCase().contains("laureat")) {
                        doc1_laurat = doc1_title1;
                        doc1_laurat_link = doc1_link1;
                    } else {
                        if (doc1_title2.toLowerCase().contains("laureat")) {
                            doc1_laurat = doc1_title2;
                            doc1_laurat_link = doc1_link2;
                        } else {
                            if (doc1_title3.toLowerCase().contains("laureat")) {
                                doc1_laurat = doc1_title3;
                                doc1_laurat_link = doc1_link3;
                            } else {
                                if (doc1_title4.toLowerCase().contains("laureat")) {
                                    doc1_laurat = doc1_title4;
                                    doc1_laurat_link = doc1_link4;
                                } else {
                                    if (doc1_title5.toLowerCase().contains("laureat")) {
                                        doc1_laurat = doc1_title5;
                                        doc1_laurat_link = doc1_link5;
                                    } else {
                                        if (doc1_title6.toLowerCase().contains("laureat")) {
                                            doc1_laurat = doc1_title6;
                                            doc1_laurat_link = doc1_link6;
                                        } else {
                                            if (doc1_title7.toLowerCase().contains("laureat")) {
                                                doc1_laurat = doc1_title7;
                                                doc1_laurat_link = doc1_link7;
                                            } else {
                                                if (doc1_title8.toLowerCase().contains("laureat")) {
                                                    doc1_laurat = doc1_title8;
                                                    doc1_laurat_link = doc1_link8;
                                                } else {
                                                    if (doc1_title9.toLowerCase().contains("laureat")) {
                                                        doc1_laurat = doc1_title9;
                                                        doc1_laurat_link = doc1_link9;
                                                    } else {
                                                        if (doc1_title10.toLowerCase().contains("laureat")) {
                                                            doc1_laurat = doc1_title10;
                                                            doc1_laurat_link = doc1_link10;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (doc1_title1.toLowerCase().contains("finalist")) {
                        doc1_olimpiad = doc1_title1;
                        doc1_olimpiad_link = doc1_link1;
                    } else {
                        if (doc1_title2.toLowerCase().contains("finalist")) {
                            doc1_finalist = doc1_title2;
                            doc1_olimpiad_link = doc1_link2;
                        } else {
                            if (doc1_title3.toLowerCase().contains("finalist")) {
                                doc1_finalist = doc1_title3;
                                doc1_olimpiad_link = doc1_link3;
                            } else {
                                if (doc1_title4.toLowerCase().contains("finalist")) {
                                    doc1_finalist = doc1_title4;
                                    doc1_olimpiad_link = doc1_link4;
                                } else {
                                    if (doc1_title5.toLowerCase().contains("finalist")) {
                                        doc1_finalist = doc1_title5;
                                        doc1_olimpiad_link = doc1_link5;
                                    } else {
                                        if (doc1_title6.toLowerCase().contains("finalist")) {
                                            doc1_finalist = doc1_title6;
                                            doc1_olimpiad_link = doc1_link6;
                                        } else {
                                            if (doc1_title7.toLowerCase().contains("finalist")) {
                                                doc1_finalist = doc1_title7;
                                                doc1_olimpiad_link = doc1_link7;
                                            } else {
                                                if (doc1_title8.toLowerCase().contains("finalist")) {
                                                    doc1_finalist = doc1_title8;
                                                    doc1_olimpiad_link = doc1_link8;
                                                } else {
                                                    if (doc1_title9.toLowerCase().contains("finalist")) {
                                                        doc1_finalist = doc1_title9;
                                                        doc1_olimpiad_link = doc1_link9;
                                                    } else {
                                                        if (doc1_title10.toLowerCase().contains("finalist")) {
                                                            doc1_finalist = doc1_title10;
                                                            doc1_olimpiad_link = doc1_link10;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (doc1_title1.toLowerCase().contains("mistrz")) {
                        doc1_mistrz = doc1_title1;
                        doc1_mistrz_link = doc1_link1;
                    } else {
                        if (doc1_title2.toLowerCase().contains("mistrz")) {
                            doc1_mistrz = doc1_title2;
                            doc1_mistrz_link = doc1_link2;
                        } else {
                            if (doc1_title3.toLowerCase().contains("mistrz")) {
                                doc1_mistrz = doc1_title3;
                                doc1_mistrz_link = doc1_link3;
                            } else {
                                if (doc1_title4.toLowerCase().contains("mistrz")) {
                                    doc1_mistrz = doc1_title4;
                                    doc1_mistrz_link = doc1_link4;
                                } else {
                                    if (doc1_title5.toLowerCase().contains("mistrz")) {
                                        doc1_mistrz = doc1_title5;
                                        doc1_mistrz_link = doc1_link5;
                                    } else {
                                        if (doc1_title6.toLowerCase().contains("mistrz")) {
                                            doc1_mistrz = doc1_title6;
                                            doc1_mistrz_link = doc1_link6;
                                        } else {
                                            if (doc1_title7.toLowerCase().contains("mistrz")) {
                                                doc1_mistrz = doc1_title7;
                                                doc1_mistrz_link = doc1_link7;
                                            } else {
                                                if (doc1_title8.toLowerCase().contains("mistrz")) {
                                                    doc1_mistrz = doc1_title8;
                                                    doc1_mistrz_link = doc1_link8;
                                                } else {
                                                    if (doc1_title9.toLowerCase().contains("mistrz")) {
                                                        doc1_mistrz = doc1_title9;
                                                        doc1_mistrz_link = doc1_link9;
                                                    } else {
                                                        if (doc1_title10.toLowerCase().contains("mistrz")) {
                                                            doc1_mistrz = doc1_title10;
                                                            doc1_mistrz_link = doc1_link10;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    //second page

                    doc2 = Jsoup.connect(url2).userAgent("Mozilla/5.0").get();
                    Elements doc2_titles = doc2.select("a[title]");
                    Elements doc2_links = doc2.select("a[title]");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //result_TV.setText(result);
            achievements1_TV.setText(doc1_wyniki);
            achievements2_TV.setText(doc1_sukces);
            achievements3_TV.setText(doc1_w_finale);
            achievements4_TV.setText(doc1_w_etapie);
            achievements5_TV.setText(doc1_laurat);
            achievements6_TV.setText(doc1_finalist);
            achievements7_TV.setText(doc1_mistrz);

            if (achievements2_TV.length() == 0) {
                achievements2_TV.setText(achievements3_TV.toString());
                if (achievements2_TV.length() == 0) {
                    achievements2_TV.setText(achievements4_TV.toString());
                    if (achievements2_TV.length() == 0) {
                        achievements2_TV.setText(achievements5_TV.toString());
                        if (achievements2_TV.length() == 0) {
                            achievements2_TV.setText(achievements6_TV.toString());
                            if (achievements2_TV.length() == 0) {
                                achievements2_TV.setText(achievements7_TV.toString());
                            }
                        }
                    }
                }
            }

        }
    }

    public void goToAchievement1(View view) {
        Toast.makeText(achievementsActivity.this, doc1_wyniki_link, Toast.LENGTH_SHORT).show();
    }

    public void goToAchievement2(View view) {
        Toast.makeText(achievementsActivity.this, doc1_sukces_link, Toast.LENGTH_SHORT).show();
    }

    public void goToAchievement3(View view) {
        Toast.makeText(achievementsActivity.this, doc1_w_finale_link, Toast.LENGTH_SHORT).show();
    }

    public void goToAchievement4(View view) {
        Toast.makeText(achievementsActivity.this, doc1_w_etapie_link, Toast.LENGTH_SHORT).show();
    }

    public void goToAchievement5(View view) {
        Toast.makeText(achievementsActivity.this, doc1_laurat_link, Toast.LENGTH_SHORT).show();
    }

    public void goToAchievement6(View view) {
        Toast.makeText(achievementsActivity.this, doc1_finalist_link, Toast.LENGTH_SHORT).show();
    }

    public void goToAchievement7(View view) {
        Toast.makeText(achievementsActivity.this, doc1_wyniki_link, Toast.LENGTH_SHORT).show();
    }

    public void goToAchievement8(View view) {
        Toast.makeText(achievementsActivity.this, doc1_mistrz_link, Toast.LENGTH_SHORT).show();

    }

    public void goToAchievement9(View view) {

    }

    public void goToAchievement10(View view) {

    }
}