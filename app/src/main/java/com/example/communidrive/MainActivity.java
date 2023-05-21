package com.example.communidrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    private DrawerLayout drawer;
    private Spinner spinner;

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

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit(); break;
            case R.id.nav_upload: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Upload()).commit(); break;
            case R.id.nav_history: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_3()).commit(); break;
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
}