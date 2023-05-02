package com.example.communidrive;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_Upload extends Fragment implements AdapterView.OnItemSelectedListener {

    private Button filePickerButton;
    private Context main_context = getContext();
    private Spinner uni_spinner, dep_spinner, courses_spinner, academic_year_spinner, types_spinner, lang_spinner, prof_spinner;
    private TextView filePath_TextView;
    private static final int PICK_PDF_FILE = 2;

    @Override public void onAttach(@NonNull Context context) { super.onAttach(context); main_context = context; }
    @Override public void onDetach() { super.onDetach(); main_context = null; }
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_upload, container, false);
        filePickerButton = (Button) rootView.findViewById(R.id.filePicker_Button);
        filePath_TextView = (TextView) rootView.findViewById(R.id.filePicker_path_textview);


        filePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                startActivityForResult(intent, PICK_PDF_FILE);
            }
        });

        // Uni_spinner instance
        uni_spinner = (Spinner) rootView.findViewById(R.id.uni_spinner);
        ArrayAdapter<CharSequence> uni_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.universities_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        uni_spinner.setAdapter(uni_spinner_adapter);
        uni_spinner.setOnItemSelectedListener(Fragment_Upload.this);

        // Dep_spinner instance
        dep_spinner = (Spinner) rootView.findViewById(R.id.dep_spinner);
        ArrayAdapter<CharSequence> dep_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.departments_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        dep_spinner.setAdapter(dep_spinner_adapter);
        dep_spinner.setOnItemSelectedListener(Fragment_Upload.this);

        // Courses_spinner instance
        courses_spinner = (Spinner) rootView.findViewById(R.id.courses_spinner);
        ArrayAdapter<CharSequence> courses_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.courses_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        courses_spinner.setAdapter(courses_spinner_adapter);
        courses_spinner.setOnItemSelectedListener(Fragment_Upload.this);

        // AcademicYear_spinner instance
        academic_year_spinner = (Spinner) rootView.findViewById(R.id.aa_spinner);
        ArrayAdapter<CharSequence> academic_year_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.aa_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        academic_year_spinner.setAdapter(academic_year_spinner_adapter);
        academic_year_spinner.setOnItemSelectedListener(Fragment_Upload.this);

        // Note types instance
        types_spinner = (Spinner) rootView.findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> types_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.types_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        types_spinner.setAdapter(types_spinner_adapter);
        types_spinner.setOnItemSelectedListener(Fragment_Upload.this);

        // Language spinner instance
        lang_spinner = (Spinner) rootView.findViewById(R.id.lang_spinner);
        ArrayAdapter<CharSequence> lang_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.languages, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        lang_spinner.setAdapter(lang_spinner_adapter);
        lang_spinner.setOnItemSelectedListener(Fragment_Upload.this);

        // Professor spinner instance
        prof_spinner = (Spinner) rootView.findViewById(R.id.prof_spinner);
        ArrayAdapter<CharSequence> prof_spinner_adapter = ArrayAdapter.createFromResource(main_context, R.array.prof_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        prof_spinner.setAdapter(prof_spinner_adapter);
        prof_spinner.setOnItemSelectedListener(Fragment_Upload.this);

        return rootView;
    }

    // Implemented methods by AdapterView.OnItemSelectedListener
    @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { adapterView.getItemAtPosition(i); }
    @Override public void onNothingSelected(AdapterView<?> adapterView) { }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_PDF_FILE) {
            if (resultCode==RESULT_OK) {
                String path = data.getData().getPath();
                filePath_TextView.setText(path);
            }
        }
    }
}
