package com.example.lo1gliwice.account;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class homeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //ADS
    private AdView mAdView;
    //SIDEBAR MENU
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    public EditText email_ET, password_ET;
    Button logout_btn;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    CircleImageView profileImage;
    private static final int request_Code = 5;
    Uri imageUri;
    StorageReference reference;
    StorageReference mStorageReference;
    StorageReference Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mStorageReference = FirebaseStorage.getInstance().getReference("/profilePhotos");

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

        logout_btn = findViewById(R.id.button_logout);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(homeActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(homeActivity.this, "Wylogowano pomyslnie", Toast.LENGTH_SHORT).show();
            }
        });

        profileImage = findViewById(R.id.imageView_profile);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, request_Code);
            }
        });

        //fileDownloader();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == request_Code) {
            Uri imagePath = data.getData();
            CropImage.activity(imagePath).setGuidelines(CropImageView.Guidelines.ON)
                    .start(homeActivity.this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            profileImage.setImageURI(imageUri);
            fileUploader();
        }
    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void fileUploader() {
        reference = mStorageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(homeActivity.this, "Dodawanie zdjęcia przebiegło pomyślne", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(homeActivity.this, "Dodawanie zdjęcia nie przebiegło pomyślne", Toast.LENGTH_SHORT).show();

                    }
                });
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_mainPage:
                Toast.makeText(homeActivity.this, "Strona główna", Toast.LENGTH_SHORT).show();
                moveToMainActivity();
                break;

            case R.id.menu_classSwap:
                Toast.makeText(homeActivity.this, "Zamiana klas", Toast.LENGTH_SHORT).show();
                moveToclassSwapActivity();
                break;

            case R.id.menu_news:
                Toast.makeText(homeActivity.this, "Aktualnosci", Toast.LENGTH_SHORT).show();
                moveToNewsActivity();
                break;

            case R.id.menu_about_school:
                Toast.makeText(homeActivity.this, "O szkole", Toast.LENGTH_SHORT).show();
                moveToAboutSchoolActivity();
                break;

            case R.id.menu_setting:
                Toast.makeText(homeActivity.this, "Ustawienia", Toast.LENGTH_SHORT).show();
                moveToSettingsActivity();
                break;

            case R.id.menu_information:
                Toast.makeText(homeActivity.this, "Informacje", Toast.LENGTH_SHORT).show();
                moveToInfoActivity();
                break;


        }

        return false;
    }

    //CHANGE ACTIVITY
    private void moveToMainActivity() {
        Intent intent = new Intent(homeActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void moveToSettingsActivity() {
        Intent intent = new Intent(homeActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void moveToclassSwapActivity() {
        Intent intent = new Intent(homeActivity.this, classSwapActivity.class);
        startActivity(intent);
    }

    private void moveToInfoActivity() {
        Intent intent = new Intent(homeActivity.this, infoActivity.class);
        startActivity(intent);
    }

    private void moveToNewsActivity() {
        Intent intent = new Intent(homeActivity.this, newsActivity.class);
        startActivity(intent);
    }

    private void moveToAboutSchoolActivity() {
        Intent intent = new Intent(homeActivity.this, aboutSchoolActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public void accountSettings(View view) {
    }

    public void aboutAccount(View view) {
    }

    public void logout(View view) {
    }
}
