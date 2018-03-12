package com.company;

import java.util.ArrayList;
import java.util.List;

public class Convention {

    private String Session;
    private String Convention;
    private String designation;
    private String start;
    private String end;
    private String quantity;
    private String unit;
    private String pu;
    private String ht;
    private List<Trainee> traineeList=new ArrayList<Trainee>();

    public Convention() {
    }

    public Convention(String session, String convention, String start, String end, String quantity, String unit, String pu, String ht,String designation) {
        Session = session;
        Convention = convention;
        this.start = start;
        this.end = end;
        this.quantity = quantity;
        this.unit = unit;
        this.pu = pu;
        this.ht = ht;
        this.designation=designation;
    }

    public String getConvention() {
        return Convention;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setConvention(String convention) {
        Convention = convention;
    }

    public String getSession() {
        return Session;
    }

    public void setSession(String session) {
        Session = session;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPu() {
        return pu;
    }

    public void setPu(String pu) {
        this.pu = pu;
    }

    public String getHt() {
        return ht;
    }

    public void setHt(String ht) {
        this.ht = ht;
    }

    public List<Trainee> getTraineeList() {
        return traineeList;
    }

    public void setTraineeList(List<Trainee> traineeList) {
        this.traineeList = traineeList;
    }
}
