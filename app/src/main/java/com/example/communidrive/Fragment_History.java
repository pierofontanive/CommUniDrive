package com.example.communidrive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fragment_History extends Fragment implements AdapterView.OnItemSelectedListener {

    private Context main_context = getContext();
    private String title;
    ArrayList<Note> downloadList;

    @Override public void onAttach(@NonNull Context context) { super.onAttach(context); main_context = context; }
    @Override public void onDetach() { super.onDetach(); main_context = null; }
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        // Get download list
        Activity activity = getActivity();
        if (activity != null) {
            String activityName = activity.getClass().getSimpleName() + "";
            if (activityName.equals("MainActivity")) downloadList = ((MainActivity) requireActivity()).getDownloadList();
            else if (activityName.equals("MainActivityAnon")) downloadList = ((MainActivityAnon) requireActivity()).getDownloadList();
        }

        emptyList(downloadList);

        // Check for files to add to downloadlist
        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/CommUniDrive/");
        File[] files = path.listFiles();
        if(files != null && files.length > 0) {
            for (File file : files) {
                downloadList.add(new Note(file.getName(), R.drawable.book, null, null, null, null, null, null, null, null, null, file.getPath(), R.drawable.ic_launcher_background, false, ""));
            }
        }


        // Imposto il recyclerview
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.download_recycleview);
        RecycleViewAdapterPDF recycleViewAdapter = new RecycleViewAdapterPDF(main_context, downloadList);
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(main_context, 1));

        return rootView;
    }

    public void emptyList(List<Note> downloadList) {
        downloadList.clear();
    }

    @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { }
    @Override public void onNothingSelected(AdapterView<?> adapterView) { }
}
