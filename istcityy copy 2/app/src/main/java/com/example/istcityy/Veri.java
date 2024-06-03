package com.example.istcityy;

public class Veri {
    private String images;
    private String comment;
    private String name;
    private String location;

    // Boş yapıcı metot (Firebase verilerini alırken gerekli)
    public Veri() {
    }

    // Yapıcı metot
    public Veri(String images, String comment, String name, String location) {
        this.images = images;
        this.comment = comment;
        this.name = name;
        this.location = location;
    }

    // Getter ve setter metotları
    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
