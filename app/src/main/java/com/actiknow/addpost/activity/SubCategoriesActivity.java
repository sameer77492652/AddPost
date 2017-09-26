package com.actiknow.addpost.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;

import com.actiknow.addpost.R;
import com.actiknow.addpost.adapter.SubCategoriesAdapter;
import com.actiknow.addpost.model.Banner;
import com.actiknow.addpost.model.ParentSubCategories;
import com.actiknow.addpost.model.SubCategories;
import com.actiknow.addpost.utils.Constants;
import com.actiknow.addpost.utils.SimpleDividerItemDecoration;
import com.actiknow.addpost.utils.TypefaceSpan;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import java.util.ArrayList;

/**
 * Created by actiknow on 9/19/17.
 */

public class SubCategoriesActivity extends AppCompatActivity{
    ArrayList<SubCategories>subCategorylist = new ArrayList<>();

    SubCategoriesAdapter subCategoriesAdapter;
    RecyclerView rvSubCategoryList;
    private SliderLayout slider;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categories);
        initView();
        initData();
    }



    private void initView() {
        rvSubCategoryList = (RecyclerView) findViewById(R.id.rvSubCategoryList);
        slider = (SliderLayout) findViewById(R.id.slider);

    }

    private void initData() {

        subCategorylist.add(new SubCategories(1, "https://i.pinimg.com/originals/57/2e/4f/572e4f51d0a4d67669784df53026b5a7.png", "Men"));
        subCategorylist.add(new SubCategories(2, "https://i.pinimg.com/originals/57/2e/4f/572e4f51d0a4d67669784df53026b5a7.png", "Women"));
        subCategorylist.add(new SubCategories(3, "https://i.pinimg.com/originals/57/2e/4f/572e4f51d0a4d67669784df53026b5a7.png", "Kids"));
        subCategoriesAdapter = new SubCategoriesAdapter (this, subCategorylist);
        rvSubCategoryList.setAdapter (subCategoriesAdapter);
        rvSubCategoryList.setHasFixedSize (true);
        rvSubCategoryList.setLayoutManager (new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvSubCategoryList.addItemDecoration (new SimpleDividerItemDecoration(this));
        rvSubCategoryList.setItemAnimator (new DefaultItemAnimator());
    }


}
