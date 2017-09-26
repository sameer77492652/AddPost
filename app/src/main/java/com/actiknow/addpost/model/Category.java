package com.actiknow.addpost.model;

/**
 * Created by actiknow on 9/19/17.
 */

public class Category {
    int id;
    String category_image, category_name;

    public Category(int id, String category_image, String category_name) {

        this.id = id;
        this.category_image = category_image;
        this.category_name = category_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }


}
