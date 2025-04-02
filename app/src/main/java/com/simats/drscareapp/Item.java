package com.simats.drscareapp;

public class Item {

    private String name;
    private int image;
    private String age;
    private String gender;
    private String id;

    public Item(String name, int image, String age, String gender, String id) {
        this.name = name;
        this.image = image;
        this.age = age;
        this.gender = gender;
        this.id = id;
    }

    // Getter and setter methods for all fields

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
