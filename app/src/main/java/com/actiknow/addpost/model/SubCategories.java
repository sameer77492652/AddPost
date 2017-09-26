package com.actiknow.addpost.model;

import java.util.ArrayList;

/**
 * Created by actiknow on 9/19/17.
 */

public class SubCategories {
    int id;
    String sub_category_image, sub_category_name;

    public SubCategories(int id, String sub_category_image, String sub_category_name) {
        this.id = id;
        this.sub_category_image = sub_category_image;
        this.sub_category_name = sub_category_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSub_category_image() {
        return sub_category_image;
    }

    public void setSub_category_image(String sub_category_image) {
        this.sub_category_image = sub_category_image;
    }

    public String getSub_category_name() {
        return sub_category_name;
    }

    public void setSub_category_name(String sub_category_name) {
        this.sub_category_name = sub_category_name;
    }
}
