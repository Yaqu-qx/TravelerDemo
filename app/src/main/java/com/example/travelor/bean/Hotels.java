package com.example.travelor.bean;

import java.io.Serializable;

public class Hotels implements Serializable {
    private String hotelName;
    private String hotelLocation;
    private String hotelImage;
    private String hotelRank;
    private String hotelScore;
    private String hotelPrice;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelLocation() {
        return hotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        this.hotelLocation = hotelLocation;
    }

    public String getHotelImage() {
        return hotelImage;
    }

    public void setHotelImage(String hotelImage) {
        this.hotelImage = hotelImage;
    }

    public String getHotelRank() {
        return hotelRank;
    }

    public void setHotelRank(String hotelRank) {
        this.hotelRank = hotelRank;
    }

    public String getHotelScore() {
        return hotelScore;
    }

    public void setHotelScore(String hotelScore) {
        this.hotelScore = hotelScore;
    }

    public String getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(String hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    @Override
    public String toString() {
        return "Hotels{" +
                "hotelName='" + hotelName + '\'' +
                ", hotelLocation='" + hotelLocation + '\'' +
                ", hotelImage='" + hotelImage + '\'' +
                ", hotelRank='" + hotelRank + '\'' +
                ", hotelScore='" + hotelScore + '\'' +
                ", hotelPrice='" + hotelPrice + '\'' +
                '}';
    }
}
