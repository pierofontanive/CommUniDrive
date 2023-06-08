package com.example.communidrive;

import android.content.Context;
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
import java.util.List;

public class Fragment_History extends Fragment implements AdapterView.OnItemSelectedListener {

    private Context main_context = getContext();
    private String title;
    List<Note> downloadList;

    @Override public void onAttach(@NonNull Context context) { super.onAttach(context); main_context = context; }
    @Override public void onDetach() { super.onDetach(); main_context = null; }
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        downloadList = ((MainActivity) requireActivity()).getDownloadList();
        emptyList(downloadList);

        // Check for files to add to downloadlist
        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/CommUniDrive/");
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            downloadList.add(new Note(files[i].getName(),R.drawable.ic_launcher_background,null,null,null,null,null,null,null,null,null,files[i].getPath()));
        }


        // Imposto il recyclerview
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.download_recycleview);
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(main_context, downloadList);
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(main_context, 3));

        return rootView;
    }

    public void emptyList(List<Note> downloadList) {
        downloadList.clear();
    }

    @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {  }
    @Override public void onNothingSelected(AdapterView<?> adapterView) { }
}
