package com.example.communidrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    private DrawerLayout drawer;
    private Spinner spinner;

    public ArrayList<Note> noteList;
    public ArrayList<Note> downloadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer
        drawer = findViewById(R.id.drawer_layout);

        // NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Resources and Spinner
        String[] languages = getResources().getStringArray(R.array.languages);
        int[] flags = {R.drawable.ita, R.drawable.eng};

        spinner = findViewById(R.id.language_spinner);
        spinner.setOnItemSelectedListener(this);
        LanguageAdapter languageAdapter = new LanguageAdapter(getApplicationContext(), flags, languages);
        spinner.setAdapter(languageAdapter);

        // ActionBar toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Sets default random note samples everytime I start the app
        Date date = new Date(); String stringDate = DateFormat.getDateInstance().format(date);

        AssetManager assetManager = getAssets();
        noteList = new ArrayList<>();
        downloadList = new ArrayList<>();

        // General, can be random and needs no checks
        String[] uni = getResources().getStringArray(R.array.universities_array); String random_uni;
        String[] author = getResources().getStringArray(R.array.authors_array); String random_author;
        String[] dep = getResources().getStringArray(R.array.departments_array); String random_dep;
        String[] prof = getResources().getStringArray(R.array.prof_array); String random_prof;
        String[] type = getResources().getStringArray(R.array.types_array); String random_type;
        String[] description = getResources().getStringArray(R.array.description_array); String random_desc;
        String[] aa = getResources().getStringArray(R.array.aa_array); String random_aa;

        // Specific, needs to be checked before inserting it
        String[] course = getResources().getStringArray(R.array.courses_array);

        // Check for files to add to notelist
        try {
            String[] files = assetManager.list("data");
            for (String file : files) {

                random_uni = uni[new Random().nextInt(uni.length)];
                random_author = author[new Random().nextInt(author.length)];
                random_dep = dep[new Random().nextInt(dep.length)];
                random_prof = prof[new Random().nextInt(prof.length)];
                random_type = type[new Random().nextInt(type.length)];
                random_desc = description[new Random().nextInt(description.length)];
                random_aa = aa[new Random().nextInt(aa.length)];

                String file_wo_ext = file.substring(0, file.lastIndexOf("."));
                noteList.add(new Note(file_wo_ext, R.drawable.ic_launcher_background, random_desc, random_author, "" + stringDate, random_uni, random_dep, "LdPSMeT", random_aa, random_type, random_prof, file));
            }
        } catch (IOException e1) { e1.printStackTrace(); }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit(); break;
            case R.id.nav_upload: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Upload()).commit(); break;
            case R.id.nav_history: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_History()).commit(); break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    // onBackPressed behaviour
    @Override public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) { drawer.closeDrawer(GravityCompat.START); }
        else { super.onBackPressed(); }
    }

    // Implemented methods by AdapterView.OnItemSelectedListener
    @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { adapterView.getItemAtPosition(i); }
    @Override public void onNothingSelected(AdapterView<?> adapterView) {}

    // Methods to share notelist and downloadlist
    public ArrayList<Note> getNoteList() {
        return noteList;
    }
    public ArrayList<Note> getDownloadList() { return downloadList; }
}