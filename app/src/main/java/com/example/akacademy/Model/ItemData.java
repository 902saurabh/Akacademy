package com.example.akacademy.Model;

public class ItemData {
    private String image,name,id,type;

    public ItemData(){

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ItemData(String image, String name, String courseid, String type) {
        this.image = image;
        this.name = name;
        this.id = courseid;

    }

    public String getId() {
        return id;
    }

    public void setCourseid(String courseid) {
        this.id = courseid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
