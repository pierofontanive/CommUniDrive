package com.example.communidrive;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Note {
    private String title;
    private int image;
    private String description;
    private String user;
    private String date;
    private String uni;
    private String dep;
    private String course;
    private String aa;
    private String noteType;
    private String prof;
    private String file_path;
    private int lang;
    private boolean email_check;
    private String email;

    // Constructor
    public Note(String title, int image, String description, String user, String date, String uni, String dep, String course, String aa, String noteType, String prof, String file_path, int lang, boolean email_check, String email) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.user = user;
        this.date = date;
        this.uni = uni;
        this.dep = dep;
        this.course = course;
        this.aa = aa;
        this.noteType = noteType;
        this.prof = prof;
        this.file_path = file_path;
        this.lang = lang;
        this.email_check = email_check;
        this.email = email;
    }

    // Getter
    public String getTitle() { return title; }
    public int getImage() { return image; }
    public String getDescription() { return description; }
    public String getUser() { return user; }
    public String getDate() { return date; }
    public String getUni() { return uni; }
    public String getDep() { return dep; }
    public String getCourse() { return course; }
    public String getAa() { return aa; }
    public String getNoteType() { return noteType; }
    public String getProf() { return prof; }
    public String getFile_path() { return file_path; }
    public int getLang() { return lang; }
    public boolean getMailCheck() { return email_check; }
    public String getEmail() { return email; }

    // Setter
    public void setTitle(String title) { this.title = title; }
    public void setImage(int image) { this.image = image; }
    public void setDescription(String description) { this.description = description; }
    public void setUser(String user) { this.user = user; }
    public void setDate(String date) { this.date = date; }
    public void setUni(String uni) { this.uni = uni; }
    public void setDep(String dep) { this.dep = dep; }
    public void setCourse(String course) { this.course = course; }
    public void setAa(String aa) { this.aa = aa; }
    public void setNoteType(String noteType) { this.noteType = noteType; }
    public void setProf(String prof) { this.prof = prof; }
    public void setFile_path(String file_path) { this.file_path = file_path; }
    public void setLang(int lang) { this.lang = lang; }
    public void setEmail_check(boolean email_check) { this.email_check = email_check; }
    public void setEmail(String email) { this.email = email; }
}
