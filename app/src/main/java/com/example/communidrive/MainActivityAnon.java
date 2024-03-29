package com.example.communidrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivityAnon extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener{

    private DrawerLayout drawer;
    private Spinner spinner;

    private Button accedi, registrati;

    public ArrayList<Note> noteList;
    public ArrayList<Note> downloadList;

    String[] uni, author, dep, prof, type, aa, courses, description;
    int lang_flag = 0;

    private Boolean isUserAction = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_anon);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer
        drawer = findViewById(R.id.drawer_layout_anon);

        accedi = findViewById(R.id.accedi_button);
        registrati = findViewById(R.id.registrati_button);

        // NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        View navHeaderAnonView = headerView.findViewById(R.id.nav_header_anon_layout);
        //R.layout.nav_header_anon

        navHeaderAnonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityAnon.this, Login.class);
                startActivity(intent);
            }
        });

        // Resources and Spinner
        String[] old_languages = getResources().getStringArray(R.array.languages); String[] languages = new String[old_languages.length - 1]; System.arraycopy(old_languages, 1, languages, 0, languages.length);
        int[] flags = {R.drawable.ita, R.drawable.eng};

        spinner = findViewById(R.id.language_spinner);
        spinner.setOnItemSelectedListener(this);
        LanguageAdapter languageAdapter = new LanguageAdapter(getApplicationContext(), flags, languages);
        spinner.setAdapter(languageAdapter);

        // Check which language is selected
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    isUserAction = true; // User DID touched the spinner!
                }

                return false;
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (isUserAction) {
                        if (spinner.getSelectedItemPosition()==0) changeAppLanguage("it"); else changeAppLanguage("en");
                    }
                }
            }
            @Override public void onNothingSelected(AdapterView<?> adapterView) {}
        });


        // ActionBar toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityAnon.this, Login.class);
                startActivity(intent);
            }
        });
        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityAnon.this, Register.class);
                startActivity(intent);
            }
        });

        // Sets default random note samples everytime I start the app
        Date date = new Date(); String stringDate = DateFormat.getDateInstance().format(date);

        AssetManager assetManager = getAssets();

        if (NoteListHolder.check==true) noteList = NoteListHolder.noteArrayList;
        else noteList = new ArrayList<>();

        downloadList = new ArrayList<>();

        // General, can be random and needs no checks (and we will remove the first item, cause its the "N/D" one)
        String[] old_uni = getResources().getStringArray(R.array.universities_array);           uni = new String[old_uni.length - 1];                  System.arraycopy(old_uni, 1, uni, 0, uni.length);
        String[] old_author = getResources().getStringArray(R.array.authors_array);             author = new String[old_author.length - 1];            System.arraycopy(old_author, 1, author, 0, author.length);
        String[] old_dep = getResources().getStringArray(R.array.departments_array);            dep = new String[old_dep.length - 1];                  System.arraycopy(old_dep, 1, dep, 0, dep.length);
        String[] old_prof = getResources().getStringArray(R.array.prof_array);                  prof = new String[old_prof.length - 1];                System.arraycopy(old_prof, 1, prof, 0, prof.length);
        String[] old_type = getResources().getStringArray(R.array.types_array);                 type = new String[old_type.length - 1];                System.arraycopy(old_type, 1, type, 0, type.length);
        String[] old_description = getResources().getStringArray(R.array.description_array);    description = new String[old_description.length - 1];  System.arraycopy(old_description, 1, description, 0, description.length);
        String[] old_aa = getResources().getStringArray(R.array.aa_array);                      aa = new String[old_aa.length - 1];                    System.arraycopy(old_aa, 1, aa, 0, aa.length);
        String[] old_courses = getResources().getStringArray(R.array.courses_array);            courses = new String[old_courses.length - 1];          System.arraycopy(old_courses, 1, courses, 0, courses.length);
        String random_uni, random_author, random_dep, random_prof, random_type, random_desc, random_aa, random_lang, random_course, random_description;

        // Specific, needs to be checked before inserting it


        // Check for files to add to notelist
        if (NoteListHolder.check==false) {
            try {
                String[] files = assetManager.list("data");
                for (String file : files) {

                    random_uni = uni[new Random().nextInt(uni.length)];
                    System.out.println(random_uni);
                    random_author = author[new Random().nextInt(author.length)];
                    random_dep = dep[new Random().nextInt(dep.length)];
                    random_prof = prof[new Random().nextInt(prof.length)];
                    random_type = type[new Random().nextInt(type.length)];
                    random_aa = aa[new Random().nextInt(aa.length)];
                    random_lang = languages[new Random().nextInt(languages.length)];
                    random_course = courses[new Random().nextInt(courses.length)];
                    random_description = description[new Random().nextInt(description.length)];

                    if (random_lang.equals("ITA")) lang_flag = R.drawable.ita;
                    else if (random_lang.equals("ENG")) lang_flag = R.drawable.eng;

                    String file_wo_ext = file.substring(0, file.lastIndexOf("."));
                    noteList.add(new Note(file_wo_ext, R.drawable.book, random_description, random_author, "" + stringDate, random_uni, random_dep, random_course, random_aa, random_type, random_prof, file, lang_flag, false, ""));
                }
            } catch (IOException e1) { e1.printStackTrace(); }
            NoteListHolder.check=true;
        }


        // Setup the array holder
        NoteListHolder.noteArrayList = noteList;
        Intent intent = new Intent(MainActivityAnon.this, MainActivity.class);
        //startActivity(intent);

        //
        System.out.println("NoteListHolder.noteArrayList: "+ NoteListHolder.noteArrayList.size());
        System.out.println("noteList: "+ noteList.size());
        //

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit(); break;
            case R.id.nav_support: Toast.makeText(this, "Pagina non ancora implementata!", Toast.LENGTH_LONG).show(); break;
            case R.id.nav_history:
                File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/CommUniDrive/");
                File[] files = path.listFiles();
                if(files == null || files.length == 0){
                    Toast.makeText(this, "Non hai scaricato alcun documento", Toast.LENGTH_LONG).show();
                    break;
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_History()).commit(); break;
                }
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

    // Methods to share the arrays of strings for each field
    public String[] getUniStrings() {
        return uni;
    }

    // Function to change the app language
    public void changeAppLanguage(String languageCode) {
        // Set the desired language code
        Locale newLocale = new Locale(languageCode);
        // Get the current resources and configuration
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        // Set the new locale
        configuration.setLocale(newLocale);
        // Update the resources and configuration
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        // Restart the activity to apply the language change
        recreate();
    }
}