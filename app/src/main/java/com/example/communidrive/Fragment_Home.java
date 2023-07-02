package com.example.communidrive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Fragment_Home extends Fragment implements AdapterView.OnItemSelectedListener {

    private Button filterButton, applyfilterButton, resetfilterButton, uploadButton;
    private Context main_context = getContext();
    private Spinner uni_spinner, dep_spinner, courses_spinner, academic_year_spinner, types_spinner, lang_spinner, prof_spinner;
    private AutoCompleteTextView uni_comptextview, dep_comptextview, courses_comptextview, academic_year_comptextview, types_comptextview, lang_comptextview, prof_comptextview;
    private int uni_value, dep_value, courses_value, ay_value, type_value, lang_value, prof_value;

    ArrayList<Note> noteList = NoteListHolder.noteArrayList;
    ArrayList<Note> filteredList;

    @Override public void onAttach(@NonNull Context context) { super.onAttach(context); main_context = context; }
    @Override public void onDetach() { super.onDetach(); main_context = null; }
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        filterButton = (Button) rootView.findViewById(R.id.filter_button);
        uploadButton = (Button) rootView.findViewById(R.id.upload_button);

        //Gestione del SearchView
        SearchView searchView = rootView.findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);

        // Imposto la lista filtrata
        filteredList = getFilteredList(noteList);

        // Imposto il recyclerview
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.note_recycleview);
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(main_context, filteredList);
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(main_context, 1));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Azione da eseguire quando l'utente preme il pulsante di ricerca
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Azione da eseguire quando il testo di ricerca cambia
                filteredList = filter(noteList, newText);
                RecycleViewAdapter recycleViewAdapter_ = new RecycleViewAdapter(main_context, filteredList);
                recyclerView.setAdapter(recycleViewAdapter_);
                return false;
            }
        });


        // Gestione del filterbutton
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(main_context, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(main_context).inflate(R.layout.item_bottom_sheet, getView().findViewById(R.id.item_bottom_sheet));
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.show();

                // uni_comptextview instance
                uni_spinner = (Spinner) bottomSheetView.findViewById(R.id.uni_spinner); ArrayAdapter<CharSequence> uni_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.universities_array, android.R.layout.simple_dropdown_item_1line); uni_spinner.setAdapter(uni_spinner_adapter);

                // Dep_spinner instance
                dep_spinner = (Spinner) bottomSheetView.findViewById(R.id.dep_spinner); ArrayAdapter<CharSequence> dep_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.departments_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item); dep_spinner.setAdapter(dep_spinner_adapter);

                // Courses_spinner instance
                courses_spinner = (Spinner) bottomSheetView.findViewById(R.id.courses_spinner); ArrayAdapter<CharSequence> courses_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.courses_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item); courses_spinner.setAdapter(courses_spinner_adapter);

                // AcademicYear_spinner instance
                academic_year_spinner = (Spinner) bottomSheetView.findViewById(R.id.aa_spinner); ArrayAdapter<CharSequence> academic_year_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.aa_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item); academic_year_spinner.setAdapter(academic_year_spinner_adapter);

                // Note types instance
                types_spinner = (Spinner) bottomSheetView.findViewById(R.id.type_spinner); ArrayAdapter<CharSequence> types_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.types_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item); types_spinner.setAdapter(types_spinner_adapter);

                // Language spinner instance
                lang_spinner = (Spinner) bottomSheetView.findViewById(R.id.language_spinner); ArrayAdapter<CharSequence> lang_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.languages, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item); lang_spinner.setAdapter(lang_spinner_adapter);

                // Professor spinner instance
                prof_spinner = (Spinner) bottomSheetView.findViewById(R.id.prof_spinner); ArrayAdapter<CharSequence> prof_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.prof_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item); prof_spinner.setAdapter(prof_spinner_adapter);

                // Assegno i valori salvati ai vari spinner, così rimangono memorizzati
                uni_spinner.setSelection(uni_value);
                dep_spinner.setSelection(dep_value);
                courses_spinner.setSelection(courses_value);
                academic_year_spinner.setSelection(ay_value);
                types_spinner.setSelection(type_value);
                lang_spinner.setSelection(lang_value);
                prof_spinner.setSelection(prof_value);

                // Applica filtri e cerca Button
                applyfilterButton = (Button) bottomSheetView.findViewById(R.id.applica_filtri_e_cerca);
                applyfilterButton.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        uni_value = uni_spinner.getSelectedItemPosition();
                        dep_value = dep_spinner.getSelectedItemPosition();
                        courses_value = courses_spinner.getSelectedItemPosition();
                        ay_value = academic_year_spinner.getSelectedItemPosition();
                        type_value = types_spinner.getSelectedItemPosition();
                        lang_value = lang_spinner.getSelectedItemPosition();
                        prof_value = prof_spinner.getSelectedItemPosition();
                        filteredList = getFilteredList(noteList);

                        RecycleViewAdapter recycleViewAdapter_ = new RecycleViewAdapter(main_context, filteredList);
                        recyclerView.setAdapter(recycleViewAdapter_);

                        bottomSheetDialog.dismiss();
                    }
                });

                // Resetta filtri Button
                resetfilterButton = (Button) bottomSheetView.findViewById(R.id.filter_reset);
                resetfilterButton.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        uni_spinner.setSelection(0);
                        dep_spinner.setSelection(0);
                        courses_spinner.setSelection(0);
                        academic_year_spinner.setSelection(0);
                        types_spinner.setSelection(0);
                        lang_spinner.setSelection(0);
                        prof_spinner.setSelection(0);
                    }
                });

            }
        });

        // Gestione del uploadbutton
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Switch to fragment upload
                Activity activity = getActivity();
                FragmentManager fragmentManager = getFragmentManager();
                if (activity != null && fragmentManager != null) {
                    String activityName = activity.getClass().getSimpleName() + "";
                    if (activityName.equals("MainActivityAnon")) {
                        Toast.makeText(main_context, "Non puoi caricare appunti da utente anonimo!\nAccedi per effettuare l'upload!", Toast.LENGTH_LONG).show();
                    } else fragmentManager.beginTransaction().replace(R.id.fragment_container, new Fragment_Upload()).commit();
                }
            }
        });

        return rootView;
    }

    // Implemented methods by AdapterView.OnItemSelectedListener
    @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { adapterView.getItemAtPosition(i); }
    @Override public void onNothingSelected(AdapterView<?> adapterView) { }

    // List filter function
    private ArrayList<Note> getFilteredList(ArrayList<Note> arrayList) {

        // Output list
        ArrayList<Note> outputList = new ArrayList<>();

        // Check's check
        boolean fieldsAreModified = false;

        // Check which fields have modified parameters
        boolean uni_check = uni_value > 1;
        boolean dep_check = dep_value > 1;
        boolean courses_check = courses_value > 1;
        boolean ay_check = ay_value > 1;
        boolean type_check = type_value > 1;
        boolean lang_check = lang_value > 1;
        boolean prof_check = prof_value > 1;

        // Check if field is modified
        if (uni_check || dep_check || courses_check || ay_check || type_check || lang_check || prof_check) fieldsAreModified=true;

        // Main function loop
        if (fieldsAreModified) {
            for(Note item : arrayList) {

                // Check if item is valid
                boolean final_check = false;

                // If the field is changed, check if Item's string is equals to the filter's one.
                if (uni_check) { if (item.getUni().equals(uni_spinner.getItemAtPosition(uni_value))) { final_check = true; }}
                if (dep_check) { if (item.getDep().equals(dep_spinner.getItemAtPosition(dep_value))) { final_check = true; }}
                if (courses_check) { if (item.getCourse().equals(courses_spinner.getItemAtPosition(courses_value))) { final_check = true; }}
                if (ay_check) { if (item.getAa().equals(academic_year_spinner.getItemAtPosition(ay_value))) { final_check = true; }}
                if (type_check) { if (item.getNoteType().equals(types_spinner.getItemAtPosition(type_value))) { final_check = true; }}
                // if (lang_check) { if (item.get().equals(uni_spinner.getItemAtPosition(uni_value))) { final_check = true; }}
                if (prof_check) { if (item.getProf().equals(prof_spinner.getItemAtPosition(prof_value))) { final_check = true; }}

                if (final_check) outputList.add(item);
            }
        } else for(Note item : arrayList) outputList.add(item);

        return outputList;
    }

    // Searchview filter function
    private ArrayList<Note> filter(ArrayList<Note> arrayList, String text) {

        ArrayList<Note> outputList = new ArrayList<>();
        for(Note item : arrayList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) outputList.add(item);
        }
        return outputList;
    }
}
