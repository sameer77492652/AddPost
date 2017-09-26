package com.actiknow.addpost.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.actiknow.addpost.R;

import com.actiknow.addpost.adapter.ParentSubCategoriesAdapter;
import com.actiknow.addpost.model.ParentSubCategories;
import com.actiknow.addpost.model.SubCategories;
import com.actiknow.addpost.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;

import static com.actiknow.addpost.R.id.rvSubCategoryList;

/**
 * Created by actiknow on 9/19/17.
 */

public class ParentSubCategoriesActivity extends AppCompatActivity {
    ArrayList<ParentSubCategories> parentSubCategoriesList = new ArrayList<>();
    ParentSubCategoriesAdapter parentSubCategoriesAdapter;
    RecyclerView rvParentSubCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_sub_categories);
        initView();
        initData();
    }

    private void initView() {
        rvParentSubCategoryList = (RecyclerView) findViewById(R.id.rvParentSubCategoryList);
    }

    private void initData() {

        parentSubCategoriesList.add(new ParentSubCategories(1, " DOTNET TRAINING IN MADURAI, Madurai",
                " xxxx0005", "#43, North Masi Street, Near Krishnan Kovil, Simmakal, Madurai-01.", "Not Mentioned",
                "https://i.pinimg.com/originals/57/2e/4f/572e4f51d0a4d67669784df53026b5a7.png", "Ad Posted on: 02 September 2017 18:40:34"));
        parentSubCategoriesList.add(new ParentSubCategories(2, "15087gleam global services india pvt ltd in hyderabad | gleam global services india pvt ltd in neyv",
                " xxxx0005", "Hyderabad", "Not Mentioned", "https://i.pinimg.com/originals/57/2e/4f/572e4f51d0a4d67669784df53026b5a7.png",
                "Ad Posted on: 27 July 2017 16:03:53"));

        parentSubCategoriesAdapter = new ParentSubCategoriesAdapter (this, parentSubCategoriesList);
        rvParentSubCategoryList.setAdapter (parentSubCategoriesAdapter);
        rvParentSubCategoryList.setHasFixedSize (true);
        rvParentSubCategoryList.setLayoutManager (new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvParentSubCategoryList.addItemDecoration (new SimpleDividerItemDecoration(this));
        rvParentSubCategoryList.setItemAnimator (new DefaultItemAnimator());
    }
}
