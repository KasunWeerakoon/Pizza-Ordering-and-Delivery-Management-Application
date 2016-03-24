package com.genius.pizzahutmanager;

/**
 * Created by RaminduS on 5/25/2015.
 */
public class ProcessingItem {
    public String cname;//
    public String location;//
    public String cphone;//
    public String cnic;
    public String orderId;
    public Double latitude;
    public Double longitude;
    public String riderName;//
    public String riderId;

    public ProcessingItem(String cname, String location, String cphone, String cnic, String orderId, Double latitude, Double longitude, String riderName, String riderId) {
        this.cname=cname;
        this.location=location;
        this.cphone=cphone;
        this.cnic=cnic;
        this.orderId=orderId;
        this.latitude=latitude;
        this.longitude=longitude;
        this.riderName=riderName;
        this.riderId=riderId;
    }
}
