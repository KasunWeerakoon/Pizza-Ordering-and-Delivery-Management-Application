package com.genius.pizzahutmanager;

/**
 * Created by RaminduS on 5/25/2015.
 */
public class CompletedItem {
    public String location;
    public String name;
    public String riderName;
    public Integer phone;
    public Double rating;

    public CompletedItem(String name,String location,Integer phone, String riderName,Double rating) {
        this.name=name;
        this.location=location;
        this.phone=phone;
        this.riderName=riderName;
        this.rating=rating;
    }
}
