package com.actiknow.addpost.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actiknow.addpost.R;
import com.actiknow.addpost.activity.ParentSubCategoriesActivity;
import com.actiknow.addpost.activity.SubCategoriesActivity;
import com.actiknow.addpost.model.Category;
import com.actiknow.addpost.model.SubCategories;
import com.actiknow.addpost.utils.SetTypeFace;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul jain l on 27/04/2017.
 */


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    OnItemClickListener mItemClickListener;
    private Activity activity;
    ProgressBar progressBar;

    private List<Category> categoryList = new ArrayList<Category>();

    public CategoriesAdapter(Activity activity, List<Category> categoryList) {
        this.activity = activity;
        this.categoryList = categoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.list_item_category, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//        runEnterAnimation (holder.itemView);
        final Category category = categoryList.get (position);
        progressBar = new ProgressBar(activity);
        holder.tvCategoryName.setTypeface (SetTypeFace.getTypeface (activity));

        holder.tvCategoryName.setText (category.getCategory_name () );
        Glide.with (activity)
                .load (category.getCategory_image ())
                .listener (new RequestListener<String, GlideDrawable> () {
                    @Override
                    public boolean onException (Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility (View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady (GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility (View.GONE);
                        return false;
                    }
                })
                .into (holder.ivCategoryLogo);
    }

    @Override
    public int getItemCount() {
        return categoryList.size ();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivCategoryLogo;
        TextView tvCategoryName;


        public ViewHolder(View view) {
            super(view);
            ivCategoryLogo = (ImageView) view.findViewById(R.id.ivCategoryLogo);
            tvCategoryName = (TextView) view.findViewById(R.id.tvCategoryName);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Event event = eventList.get (getLayoutPosition ());
            Intent intent = new Intent(activity, SubCategoriesActivity.class);
           // intent.putExtra (AppConfigTags.EVENT_ID, event.getId ());
            activity.startActivity (intent);
            activity.overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}
