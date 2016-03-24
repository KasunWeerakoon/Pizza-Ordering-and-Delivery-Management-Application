package com.genius.pizzahutmanager;

import android.app.Activity;
import android.content.Context;

/**
 * Created by RaminduS on 5/23/2015.
 */
public class NewList {

    private String desname;
    private Double distance;
    private int pizza;
    private int app;
    private int pasta;
    private int des;
    private int bev;
    private String orderId;
    private String nic;
    private Double latitude;
    private Double longitude;


    public NewList(String desname,Double distance,int pizza,int app,int pasta,int des,int bev,String orderId,String nic,Double latitude,Double longitude){
        this.desname=desname;
        this.distance=distance;
        this.pizza=pizza;
        this.app=app;
        this.pasta=pasta;
        this.des=des;
        this.bev=bev;
        this.orderId=orderId;
        this.nic=nic;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getDesname(){
        return desname;
    }
    public Double getDistance(){
        return distance;
    }
    public int getPizza(){
        return pizza;
    }
    public int getApp(){
        return app;
    }
    public int getPasta(){
        return pasta;
    }
    public int getDes(){
        return des;
    }
    public int getBev(){
        return bev;
    }

    public Double getLatitude() {return latitude;}
    public String getOrderId() {return orderId;}
    public String getNic() {return nic;}
    public Double getLongitude() {return longitude;}

}
