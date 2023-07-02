package com.example.communidrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Note_Activity extends AppCompatActivity {

    private TextView title_tv, user_tv, date_tv, uni_tv, dep_tv, course_tv, aa_tv, type_tv, desc_tv, email_tv;
    private Button download_button;
    private ImageView image_iv, lang_flag_iv;

    public ArrayList<Note> downloadList;

    private static int REQUEST_CODE = 1;
    private static final int STORAGE_PERMISSION_CODE = 101;

    String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        title_tv = (TextView) findViewById(R.id.note_title);
        user_tv = (TextView) findViewById(R.id.note_uploaded_by);
        image_iv = (ImageView) findViewById(R.id.note_image);
        date_tv = (TextView) findViewById(R.id.note_uploaded_date);
        uni_tv = (TextView) findViewById(R.id.note_uni);
        dep_tv = (TextView) findViewById(R.id.note_dep);
        course_tv = (TextView) findViewById(R.id.note_course);
        aa_tv = (TextView) findViewById(R.id.note_aa);
        type_tv = (TextView) findViewById(R.id.note_mat_type);
        desc_tv = (TextView) findViewById(R.id.note_description);
        lang_flag_iv = (ImageView) findViewById(R.id.lang_flag);
        email_tv = (TextView) findViewById(R.id.note_mat_email);

        // Ricevo i dati
        Intent intent = getIntent();
        Integer image = intent.getExtras().getInt("Image");
        String title = intent.getExtras().getString("Title");
        String user = intent.getExtras().getString("User");
        String date = intent.getExtras().getString("Date");
        String uni = intent.getExtras().getString("Uni");
        String dep = intent.getExtras().getString("Dep");
        String desc = intent.getExtras().getString("Desc");
        String course = intent.getExtras().getString("Course");
        String aa = intent.getExtras().getString("Aa");
        String type = intent.getExtras().getString("Note_Type");
        String file_path = intent.getExtras().getString("FilePath");
        Integer flag = intent.getExtras().getInt("Lang");
        boolean mail = intent.getExtras().getBoolean("EmailCheck");
        String email = intent.getExtras().getString("Email");

        // Setto i dati
        image_iv.setImageResource(image);
        title_tv.setText(title);
        user_tv.setText(user);
        date_tv.setText(date);
        uni_tv.setText(uni);
        dep_tv.setText(dep);
        desc_tv.setText(desc);
        course_tv.setText(course);
        aa_tv.setText(aa);
        type_tv.setText(type);
        lang_flag_iv.setImageResource(flag);

        String current_locale = getResources().getConfiguration().locale.toString();

        if (current_locale.equals("it_IT") || current_locale.equals("it")) {
            if (mail) email_tv.setText("Puoi contattare l'autore a questo indirizzo: " + email);
            else email_tv.setText("L'utente non ha reso disponibile la sua mail.");
        } else if (current_locale.equals("en")) {
            if (mail) email_tv.setText("The user has not made their email available.");
            else email_tv.setText("The user has not made their email available.");
        }

        // "Scarico" il file
        download_button = (Button) findViewById(R.id.download_button);
        download_button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

                    // Check if the file is from "assets/data" or not
                    if (file_path.contains("sample")) {
                        copyFileFromAssetsToDownloads(file_path);
                    } else {
                        copyFileFromAnywhereToDownloads(file_path);
                    }
                // if not using emulator
                } else { copyFileFromAssetsToDownloads(file_path); }
            }
        });
    }
    
    // Makes Note info public
    public String getNoteActivityTitle() {
        return String.valueOf(title_tv.getText());
    }

    // Methods for checking if download permission
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] { permission }, requestCode);
        }
        else {
            //Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }
    // Check if external storage is available
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
    // Check if file already exists
    boolean hasExternalStoragePublicDownload(String fileName) {
        // Create a path where we will place our picture in the user's
        // public pictures directory and check if the file exists.  If
        // external storage is not currently mounted this will think the
        // picture doesn't exist.
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path+"/CommUniDrive/", fileName);
        return file.exists();
    }

    // Method to move "asset/data" file
    public void copyFileFromAssetsToDownloads(String fileName) {
        AssetManager assetManager = getAssets();
        boolean fileExist = false;
        try {

            // Cerco il file
            String[] files = assetManager.list("data");
            for (String file : files) { if (file.equals(fileName)) { fileExist = true; break; } }

            // Se file trovato, copialo nella cartella download
            if (fileExist && isExternalStorageWritable() && !hasExternalStoragePublicDownload(fileName)) {

                File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/CommUniDrive/");
                File file = new File(path, fileName);
                try {
                    // Make sure the Download/CommUniDrive/ directory exists.
                    path.mkdirs();

                    // Open input/output streams
                    InputStream in = assetManager.open("data/"+fileName);
                    OutputStream os = new FileOutputStream(file);

                    // Write data
                    byte[] data = new byte[in.available()];
                    in.read(data);
                    os.write(data);
                    in.close();
                    os.close();

                    Toast.makeText(this, "Troverai "+fileName+" all'interno della cartella dei Download!", Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (fileExist && hasExternalStoragePublicDownload(fileName)) {
                Toast.makeText(this, fileName+" si trova già nella cartella dei Download!", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to move "anywhere" file
    public void copyFileFromAnywhereToDownloads(String fileName) {

        boolean fileExist = false;

        // Cerco il file
        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/CommUniDrive/");
        String file_name = fileName.substring(fileName.lastIndexOf(":") + 1);
        File file = new File(path, file_name);

        // Doc folder exist?
        File doc_path = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)));
        File doc_dir = new File(doc_path, file_name);

        File[] files = doc_path.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().equals(file_name)) {
                fileExist = true;
                break;
            }
        }

        if (fileExist && isExternalStorageWritable() && !hasExternalStoragePublicDownload(file_name)) {
            try {
                // Make sure the Download/CommUniDrive/ directory exists.
                path.mkdirs();

                FileInputStream in = new FileInputStream(doc_dir);
                OutputStream os = new FileOutputStream(file);

                // Write data
                byte[] data = new byte[in.available()];
                in.read(data);
                os.write(data);
                in.close();
                os.close();

                Toast.makeText(this, "Troverai " + file_name + " all'interno della cartella dei Download!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (fileExist && hasExternalStoragePublicDownload(file_name)) { Toast.makeText(this, file_name + " si trova già nella cartella dei Download!", Toast.LENGTH_LONG).show(); }
    }
}