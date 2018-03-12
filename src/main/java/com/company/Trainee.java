package com.company;

/**
 * Created by Josef on 08.03.2018.
 */
public class Trainee {

    private String fname;
    private String lname;


    public Trainee(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
