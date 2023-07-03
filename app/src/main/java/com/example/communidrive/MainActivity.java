package com.example.communidrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    private DrawerLayout drawer;
    private Spinner spinner;

    private Button logout;
    public ArrayList<Note> noteList = NoteListHolder.noteArrayList;
    public ArrayList<Note> downloadList;

    private Boolean isUserAction = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String nome = getIntent().getStringExtra("NOME");
        String cognome = getIntent().getStringExtra("COGNOME");
        String username = getIntent().getStringExtra("USERNAME");
        String email = getIntent().getStringExtra("EMAIL");





        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Drawer
        drawer = findViewById(R.id.drawer_layout);

        // NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView nomeUtente = headerView.findViewById(R.id.nomeUtente);
        TextView email_tv = headerView.findViewById(R.id.email);

        System.out.println(email);

        nomeUtente.setText(username);
        email_tv.setText(email);
        View navHeaderAnonView = headerView.findViewById(R.id.nav_header_layout);

        navHeaderAnonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Profile.class);

                intent.putExtra("NOME", nome);
                intent.putExtra("COGNOME", cognome);
                intent.putExtra("USERNAME", username);
                intent.putExtra("EMAIL", email);
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

        // Sets default random note samples everytime I start the app
        Date date = new Date(); String stringDate = DateFormat.getDateInstance().format(date);

        AssetManager assetManager = getAssets();
        downloadList = new ArrayList<>();

        noteList = NoteListHolder.noteArrayList;

        // General, can be random and needs no checks
        /*
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
                System.out.println(random_uni);
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
        */

        //
        System.out.println("NoteListHolder.noteArrayList: "+ NoteListHolder.noteArrayList.size());
        System.out.println("noteList: "+ noteList.size());
        //

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        logout = findViewById(R.id.log_out_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });


    }

    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit(); break;
            case R.id.nav_upload: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Upload()).commit(); break;
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

    //
    public TextView getUserName() {
        return (TextView) findViewById(R.id.nomeUtente);
    }
    public TextView getEmail() { return (TextView) findViewById(R.id.email); }

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