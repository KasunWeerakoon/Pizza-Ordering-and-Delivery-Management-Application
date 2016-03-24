package com.genius.pizzahutmanager;

import java.io.Serializable;

public class RiderDetails implements Serializable {

    String riderName;
    String riderNic;
    String riderPhone;
    String riderRating;
    String riderDelivery;
    String riderStatus;
    String riderUname;

    public String getRiderStatus() {
        return riderStatus;
    }
    public void setRiderStatus(String riderStatus) {
        this.riderStatus = riderStatus;
    }
    public String getRiderUname() {
        return riderUname;
    }
    public void setRiderUname(String riderUname) {
        this.riderUname = riderUname;
    }
    public String getRiderName() {
        return riderName;
    }
    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }
    public String getRiderNic() {
        return riderNic;
    }
    public void setRiderNic(String riderNic) {
        this.riderNic = riderNic;
    }
    public String getRiderPhone() {
        return riderPhone;
    }
    public void setRiderPhone(String riderPhone) {
        this.riderPhone = riderPhone;
    }
    public String getRiderRating() {
        return riderRating;
    }
    public void setRiderRating(String riderRating) {
        this.riderRating = riderRating;
    }
    public String getRiderDelivery() {
        return riderDelivery;
    }
    public void setRiderDelivery(String riderDelivery) {
        this.riderDelivery = riderDelivery;
    }

}