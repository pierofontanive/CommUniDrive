package com.example.communidrive;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Fragment_Upload extends Fragment implements AdapterView.OnItemSelectedListener {

    private Button filePickerButton, file_submitButton;
    private Context main_context = getContext();
    private TextInputEditText name_textinputedittext;
    private AutoCompleteTextView uni_comptextview, dep_comptextview, courses_comptextview, academic_year_comptextview, types_comptextview, lang_comptextview, prof_comptextview;
    private TextView filePath_TextView, username_TextView;
    private static final int PICK_PDF_FILE = 2;
    ArrayList<Note> noteList;

    @Override public void onAttach(@NonNull Context context) { super.onAttach(context); main_context = context; }
    @Override public void onDetach() { super.onDetach(); main_context = null; }
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_upload, container, false);
        filePickerButton = (Button) rootView.findViewById(R.id.filePicker_Button);
        filePath_TextView = (TextView) rootView.findViewById(R.id.filePicker_path_textview);
        file_submitButton = (Button) rootView.findViewById(R.id.submit_button);
        name_textinputedittext = (TextInputEditText) rootView.findViewById(R.id.note_name);

        username_TextView = ((MainActivity) requireActivity()).getUserName();

        // Sets default random note samples everytime I start the app
        Date date = new Date(); String stringDate = DateFormat.getDateInstance().format(date);

        // Opens built-int file picker activity
        filePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                startActivityForResult(intent, PICK_PDF_FILE);
            }
        });

        file_submitButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                noteList = ((MainActivity) requireActivity()).getNoteList();

                // Recupero informazioni dai completetextview
                String title = name_textinputedittext.getText().toString();
                String uni = uni_comptextview.getText().toString();
                String dep = dep_comptextview.getText().toString();
                String course = courses_comptextview.getText().toString();
                String aa = academic_year_comptextview.getText().toString();
                String type = types_comptextview.getText().toString();
                String lang = lang_comptextview.getText().toString();
                String prof = prof_comptextview.getText().toString();

                String user = username_TextView.getText().toString();

                noteList.add(new Note(title, R.drawable.ic_launcher_background, "desc", user, stringDate, uni, dep, course, aa, type, prof, filePath_TextView.getText().toString()));

                // Aggiorno l'holder
                NoteListHolder.noteArrayList=noteList;

                // Switch back to fragment home
                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager != null) { fragmentManager.beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit(); }

            }
        });

        // uni_comptextview instance
        uni_comptextview = (AutoCompleteTextView) rootView.findViewById(R.id.uni_autocompletetextview);
        ArrayAdapter<CharSequence> uni_comptextview_adapter = ArrayAdapter.createFromResource(main_context, R.array.universities_array, R.layout.dropdown_list_item);
        uni_comptextview.setAdapter(uni_comptextview_adapter);

        // dep_comptextview instance
        dep_comptextview = (AutoCompleteTextView) rootView.findViewById(R.id.dep_autocompletetextview);
        ArrayAdapter<CharSequence> dep_comptextview_adapter = ArrayAdapter.createFromResource(main_context, R.array.departments_array, R.layout.dropdown_list_item);
        dep_comptextview.setAdapter(dep_comptextview_adapter);

        // courses_comptextview instance
        courses_comptextview = (AutoCompleteTextView) rootView.findViewById(R.id.courses_autocompletetextview);
        ArrayAdapter<CharSequence> courses_comptextview_adapter = ArrayAdapter.createFromResource(main_context, R.array.courses_array, R.layout.dropdown_list_item);
        courses_comptextview.setAdapter(courses_comptextview_adapter);

        // academic_year_comptextview instance
        academic_year_comptextview = (AutoCompleteTextView) rootView.findViewById(R.id.aa_autocompletetextview);
        ArrayAdapter<CharSequence> academic_year_comptextview_adapter = ArrayAdapter.createFromResource(main_context, R.array.aa_array, R.layout.dropdown_list_item);
        academic_year_comptextview.setAdapter(academic_year_comptextview_adapter);

        // types_comptextview instance
        types_comptextview = (AutoCompleteTextView) rootView.findViewById(R.id.type_autocompletetextview);
        ArrayAdapter<CharSequence> types_comptextview_adapter = ArrayAdapter.createFromResource(main_context, R.array.types_array, R.layout.dropdown_list_item);
        types_comptextview.setAdapter(types_comptextview_adapter);

        // Language spinner instance
        lang_comptextview = (AutoCompleteTextView) rootView.findViewById(R.id.lang_autocompletetextview);
        ArrayAdapter<CharSequence> lang_comptextview_adapter = ArrayAdapter.createFromResource(main_context, R.array.languages, R.layout.dropdown_list_item);
        lang_comptextview.setAdapter(lang_comptextview_adapter);

        // Professor spinner instance
        prof_comptextview = (AutoCompleteTextView) rootView.findViewById(R.id.prof_autocompletetextview);
        ArrayAdapter<CharSequence> prof_comptextview_adapter = ArrayAdapter.createFromResource(main_context, R.array.prof_array, R.layout.dropdown_list_item);
        prof_comptextview.setAdapter(prof_comptextview_adapter);

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
                Uri uri = data.getData();
                String src = uri.getPath();
                filePath_TextView.setText(src);
            }
        }
    }
}
