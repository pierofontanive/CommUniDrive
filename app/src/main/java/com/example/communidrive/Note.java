package com.example.communidrive;

import java.util.Date;

public class Note {
    private String title;
    private int image;
    private String description;
    private String user;
    private Date date;
    private String uni;
    private String dep;
    private String course;
    private String noteType;
    private String prof;

    // Constructor
    public Note(String title, int image, String description, String user, Date date, String uni, String dep, String course, String noteType, String prof) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.user = user;
        this.date = date;
        this.uni = uni;
        this.dep = dep;
        this.course = course;
        this.noteType = noteType;
        this.prof = prof;
    }

    // Getter
    public String getTitle() { return title; }
    public int getImage() { return image; }
    public String getDescription() { return description; }
    public String getUser() { return user; }
    public Date getDate() { return date; }
    public String getUni() { return uni; }
    public String getDep() { return dep; }
    public String getCourse() { return course; }
    public String getNoteType() { return noteType; }
    public String getProf() { return prof; }

    // Setter
    public void setTitle(String title) { this.title = title; }
    public void setImage(int image) { this.image = image; }
    public void setDescription(String description) { this.description = description; }
    public void setUser(String user) { this.user = user; }
    public void setDate(Date date) { this.date = date; }
    public void setUni(String uni) { this.uni = uni; }
    public void setDep(String dep) { this.dep = dep; }
    public void setCourse(String course) { this.course = course; }
    public void setNoteType(String noteType) { this.noteType = noteType; }
    public void setProf(String prof) { this.prof = prof; }
}
