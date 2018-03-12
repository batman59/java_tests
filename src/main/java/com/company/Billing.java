package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josef on 12.03.2018.
 */
public class Billing {
    private List<Convention> conventionList=new ArrayList<Convention>();
    private String client;
    private String date;
    private String reference;


    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Billing() {
    }

    public List<Convention> getConventionList() {
        return conventionList;
    }

    public void setConventionList(List<Convention> conventionList) {
        this.conventionList = conventionList;
    }
}
