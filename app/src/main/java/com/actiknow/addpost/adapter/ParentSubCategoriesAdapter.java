package com.actiknow.addpost.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actiknow.addpost.R;
import com.actiknow.addpost.model.ParentSubCategories;
import com.actiknow.addpost.model.SubCategories;
import com.actiknow.addpost.utils.SetTypeFace;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by Rahul jain l on 27/04/2017.
 */


public class ParentSubCategoriesAdapter extends RecyclerView.Adapter<ParentSubCategoriesAdapter.ViewHolder> {
    OnItemClickListener mItemClickListener;
    private Activity activity;
    ProgressBar progressBar;

    private List<ParentSubCategories> parentSubCategoriesList = new ArrayList<ParentSubCategories>();

    public ParentSubCategoriesAdapter(Activity activity, List<ParentSubCategories> parentSubCategoriesList) {
        this.activity = activity;
        this.parentSubCategoriesList = parentSubCategoriesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.list_item_parent_sub_category, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//        runEnterAnimation (holder.itemView);
        final ParentSubCategories parentSubCategories = parentSubCategoriesList.get (position);
        progressBar = new ProgressBar(activity);
        holder.tvAdName.setTypeface (SetTypeFace.getTypeface (activity));
        holder.tvAdContact.setTypeface (SetTypeFace.getTypeface (activity));
        holder.tvAddress.setTypeface (SetTypeFace.getTypeface (activity));
        holder.tvAdAmount.setTypeface (SetTypeFace.getTypeface (activity));
        holder.tvDate.setTypeface (SetTypeFace.getTypeface (activity));


        holder.tvAdName.setText (parentSubCategories.getName () );
        holder.tvAdContact.setText (parentSubCategories.getContact () );
        holder.tvAddress.setText (parentSubCategories.getAddress () );
        holder.tvAdAmount.setText (parentSubCategories.getAmount () );
        holder.tvDate.setText (parentSubCategories.getTime () );

        Glide.with (activity)
                .load (parentSubCategories.getImage ())
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
                .into (holder.ivAdImage);
    }

    @Override
    public int getItemCount() {
        return parentSubCategoriesList.size ();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivAdImage;
        TextView tvAdName;
        TextView tvAdContact;
        TextView tvAddress;
        TextView tvAdAmount;
        TextView tvDate;


        public ViewHolder(View view) {
            super(view);
            ivAdImage = (ImageView) view.findViewById(R.id.ivAdImage);
            tvAdName = (TextView) view.findViewById(R.id.tvAdName);
            tvAdContact = (TextView) view.findViewById(R.id.tvAdContact);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvAdAmount = (TextView) view.findViewById(R.id.tvAdAmount);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            /*Event event = eventList.get (getLayoutPosition ());
            Intent intent = new Intent(activity, EventDetailActivity.class);
            intent.putExtra (AppConfigTags.EVENT_ID, event.getId ());
            activity.startActivity (intent);
            activity.overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);*/
        }
    }
}
