package com.actiknow.addpost.model;

public class ParentSubCategories {
    int id;
    String name, contact, address, amount, image, time;


    public ParentSubCategories(int id, String name, String contact, String address, String amount, String image, String time) {

        this.id = id;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.amount = amount;
        this.image = image;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
