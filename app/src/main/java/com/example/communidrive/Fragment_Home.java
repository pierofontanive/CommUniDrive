package com.example.communidrive;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;

public class Fragment_Home extends Fragment implements AdapterView.OnItemSelectedListener {

    private Button filterButton, applyfilterButton, resetfilterButton;
    private Context main_context = getContext();
    private Spinner uni_spinner, dep_spinner, courses_spinner, academic_year_spinner, types_spinner, lang_spinner, prof_spinner;
    private int uni_value, dep_value, courses_value, ay_value, type_value, lang_value, prof_value;

    @Override public void onAttach(@NonNull Context context) { super.onAttach(context); main_context = context; }
    @Override public void onDetach() { super.onDetach(); main_context = null; }
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        filterButton = (Button) rootView.findViewById(R.id.filter_button);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(main_context, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(main_context).inflate(R.layout.item_bottom_sheet, getView().findViewById(R.id.item_bottom_sheet));
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.show();

                // Uni_spinner instance
                uni_spinner = (Spinner) bottomSheetView.findViewById(R.id.uni_spinner);
                ArrayAdapter<CharSequence> uni_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.universities_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                uni_spinner.setAdapter(uni_spinner_adapter);
                uni_spinner.setOnItemSelectedListener(Fragment_Home.this);

                // Dep_spinner instance
                dep_spinner = (Spinner) bottomSheetView.findViewById(R.id.dep_spinner);
                ArrayAdapter<CharSequence> dep_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.departments_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                dep_spinner.setAdapter(dep_spinner_adapter);
                dep_spinner.setOnItemSelectedListener(Fragment_Home.this);

                // Courses_spinner instance
                courses_spinner = (Spinner) bottomSheetView.findViewById(R.id.courses_spinner);
                ArrayAdapter<CharSequence> courses_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.courses_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                courses_spinner.setAdapter(courses_spinner_adapter);
                courses_spinner.setOnItemSelectedListener(Fragment_Home.this);

                // AcademicYear_spinner instance
                academic_year_spinner = (Spinner) bottomSheetView.findViewById(R.id.aa_spinner);
                ArrayAdapter<CharSequence> academic_year_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.aa_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                academic_year_spinner.setAdapter(academic_year_spinner_adapter);
                academic_year_spinner.setOnItemSelectedListener(Fragment_Home.this);

                // Note types instance
                types_spinner = (Spinner) bottomSheetView.findViewById(R.id.type_spinner);
                ArrayAdapter<CharSequence> types_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.types_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                types_spinner.setAdapter(types_spinner_adapter);
                types_spinner.setOnItemSelectedListener(Fragment_Home.this);

                // Language spinner instance
                lang_spinner = (Spinner) bottomSheetView.findViewById(R.id.language_spinner);
                ArrayAdapter<CharSequence> lang_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.languages, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                lang_spinner.setAdapter(lang_spinner_adapter);
                lang_spinner.setOnItemSelectedListener(Fragment_Home.this);

                // Professor spinner instance
                prof_spinner = (Spinner) bottomSheetView.findViewById(R.id.prof_spinner);
                ArrayAdapter<CharSequence> prof_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.prof_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                prof_spinner.setAdapter(prof_spinner_adapter);
                prof_spinner.setOnItemSelectedListener(Fragment_Home.this);

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

        return rootView;
    }

    // Implemented methods by AdapterView.OnItemSelectedListener
    @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { adapterView.getItemAtPosition(i); }
    @Override public void onNothingSelected(AdapterView<?> adapterView) { }
}
