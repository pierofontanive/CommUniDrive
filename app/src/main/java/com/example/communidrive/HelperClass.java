package com.example.communidrive;

public class HelperClass {

    String userName;
    String nome;
    String cognome;
    String email;

    String password;

    public HelperClass() {
    }

    public HelperClass(String userName, String nome, String cognome, String email, String password) {
        this.userName = userName;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }






}
