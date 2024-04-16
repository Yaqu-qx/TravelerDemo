package com.example.travelor.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

public class Attractions implements Serializable {

    private String id;
    private String name;
    private String location;
    private String introduce;
    private String hotels;
    private String images;
    private String video;
    private String price;
    private String rank;
    private String category;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public void setCategory(String category) { this.category = category; }

    public String getCategory() { return category; }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setHotels(String hotels) {
        this.hotels = hotels;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getHotels() {
        return hotels;
    }

    public String getImages() {
        return images;
    }

    public String getVideo() {
        return video;
    }

    public String getPrice() {
        return price;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return "Attractions{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", introduce='" + introduce + '\'' +
                ", hotels='" + hotels + '\'' +
                ", images='" + images + '\'' +
                ", video='" + video + '\'' +
                ", price='" + price + '\'' +
                ", rank='" + rank + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
