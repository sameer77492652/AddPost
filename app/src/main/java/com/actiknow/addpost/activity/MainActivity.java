package com.actiknow.addpost.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;

import com.actiknow.addpost.R;
import com.actiknow.addpost.adapter.CategoriesAdapter;
import com.actiknow.addpost.adapter.SubCategoriesAdapter;
import com.actiknow.addpost.fragment.SignInFragment;
import com.actiknow.addpost.fragment.SignUpFragment;
import com.actiknow.addpost.model.Banner;
import com.actiknow.addpost.model.Category;
import com.actiknow.addpost.model.ParentSubCategories;
import com.actiknow.addpost.model.SubCategories;
import com.actiknow.addpost.utils.Constants;
import com.actiknow.addpost.utils.SetTypeFace;
import com.actiknow.addpost.utils.SimpleDividerItemDecoration;
import com.actiknow.addpost.utils.TypefaceSpan;
import com.actiknow.addpost.utils.UserDetailsPref;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.ImageHolder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by actiknow on 9/19/17.
 */

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    ArrayList<Banner> bannerArrayList = new ArrayList<Banner> ();
    Bundle savedInstanceState;
    RecyclerView rvCategoryList;
    ArrayList<Category>categorylist = new ArrayList<>();
    CategoriesAdapter categoriesAdapter;
    ImageView ivNavigation;
    UserDetailsPref userDetailsPref;
    private SliderLayout slider;
    private Drawer result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
        initDrawer();
        isLogin();
        initSlider();
    }

    private void isLogin() {
        if(userDetailsPref.getStringPref(MainActivity.this, UserDetailsPref.USER_EMAIL).equalsIgnoreCase("")){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        if(userDetailsPref.getStringPref(MainActivity.this, UserDetailsPref.USER_EMAIL).equalsIgnoreCase(""))
            finish();
    }

    private void initListener() {
        ivNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.openDrawer();
            }
        });
    }


    private void initDrawer() {
        IProfile profile = new IProfile() {
            @Override
            public Object withName(String name) {
                return null;
            }

            @Override
            public StringHolder getName() {
                return null;
            }

            @Override
            public Object withEmail(String email) {
                return null;
            }

            @Override
            public StringHolder getEmail() {
                return null;
            }

            @Override
            public Object withIcon(Drawable icon) {
                return null;
            }

            @Override
            public Object withIcon(Bitmap bitmap) {
                return null;
            }

            @Override
            public Object withIcon(@DrawableRes int iconRes) {
                return null;
            }

            @Override
            public Object withIcon(String url) {
                return null;
            }

            @Override
            public Object withIcon(Uri uri) {
                return null;
            }

            @Override
            public Object withIcon(IIcon icon) {
                return null;
            }

            @Override
            public ImageHolder getIcon() {
                return null;
            }

            @Override
            public Object withSelectable(boolean selectable) {
                return null;
            }

            @Override
            public boolean isSelectable() {
                return false;
            }

            @Override
            public Object withIdentifier(long identifier) {
                return null;
            }

            @Override
            public long getIdentifier() {
                return 0;
            }
        };

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                if (uri != null) {
                    Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
                }
            }

            @Override
            public void cancel(ImageView imageView) {
                Glide.clear(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx, String tag) {
                //define different placeholders for different imageView targets
                //default tags are accessible via the DrawerImageLoader.Tags
                //custom ones can be checked via string. see the CustomUrlBasePrimaryDrawerItem LINE 111
                if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
                    return DrawerUIUtils.getPlaceHolder(ctx);
                } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(com.mikepenz.materialdrawer.R.color.colorPrimary).sizeDp(56);
                } else if ("customUrlItem".equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.md_white_1000);
                }

                //we use the default one for
                //DrawerImageLoader.Tags.PROFILE_DRAWER_ITEM.name()

                return super.placeholder(ctx, tag);
            }
        });


       /* if (buyerDetailsPref.getStringPref(MainActivity.this, BuyerDetailsPref.BUYER_IMAGE).length() != 0) {
            headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withCompactStyle(false)
                    .withTypeface(SetTypeFace.getTypeface(MainActivity.this))
                    .withTypeface(SetTypeFace.getTypeface(this))
                    .withPaddingBelowHeader(false)
                    .withSelectionListEnabled(false)
                    .withSelectionListEnabledForSingleProfile(false)
                    .withProfileImagesVisible(false)
                    .withOnlyMainProfileImageVisible(true)
                    .withDividerBelowHeader(true)
                    .withHeaderBackground(R.drawable.drawer_bg)
                    .withSavedInstance(savedInstanceState)
                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                        @Override
                        public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                            Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                            startActivity(intent);
                            return false;
                        }
                    })
                    .build();
            headerResult.addProfiles(new ProfileDrawerItem()
                    .withIcon(buyerDetailsPref.getStringPref(MainActivity.this, BuyerDetailsPref.BUYER_IMAGE))
                    .withName(buyerDetailsPref.getStringPref(MainActivity.this, BuyerDetailsPref.BUYER_NAME))
                    .withEmail(buyerDetailsPref.getStringPref(MainActivity.this, BuyerDetailsPref.BUYER_EMAIL)));
        } else {
            headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withCompactStyle(false)
                    .withTypeface(SetTypeFace.getTypeface(MainActivity.this))
                    .withTypeface(SetTypeFace.getTypeface(this))
                    .withPaddingBelowHeader(false)
                    .withSelectionListEnabled(false)
                    .withSelectionListEnabledForSingleProfile(false)
                    .withProfileImagesVisible(false)
                    .withOnlyMainProfileImageVisible(false)
                    .withDividerBelowHeader(true)
                    .withHeaderBackground(R.drawable.drawer_bg)
                    .withSavedInstance(savedInstanceState)
                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                        @Override
                        public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {http://actiknowdemo.com/dsgroup-new/
                            Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                            startActivity(intent);
                            return false;
                        }
                    })
                    .build();
            headerResult.addProfiles(new ProfileDrawerItem()
                    .withName(buyerDetailsPref.getStringPref(MainActivity.this, BuyerDetailsPref.BUYER_NAME))
                    .withEmail(buyerDetailsPref.getStringPref(MainActivity.this, BuyerDetailsPref.BUYER_EMAIL)));
     //   }*/

        result = new DrawerBuilder()
                .withActivity(this)
                //.withAccountHeader(headerResult)
//                .withToolbar (toolbar)
//                .withItemAnimator (new AlphaCrossFadeAnimator ())
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIcon(FontAwesome.Icon.faw_home).withIdentifier(1).withTypeface(SetTypeFace.getTypeface(MainActivity.this)),
                        new PrimaryDrawerItem().withName("Submit Ad").withIcon(FontAwesome.Icon.faw_audio_description).withIdentifier(2).withSelectable(false).withTypeface(SetTypeFace.getTypeface(MainActivity.this)),
                        new PrimaryDrawerItem().withName("About Us").withIcon(FontAwesome.Icon.faw_info).withIdentifier(7).withSelectable(false).withTypeface(SetTypeFace.getTypeface(MainActivity.this)),
                        new PrimaryDrawerItem().withName("Contact Us").withIcon(FontAwesome.Icon.faw_phone).withIdentifier(3).withSelectable(false).withTypeface(SetTypeFace.getTypeface(MainActivity.this)),
                        new PrimaryDrawerItem().withName("My Dashboard").withIcon(FontAwesome.Icon.faw_user).withIdentifier(4).withSelectable(false).withTypeface(SetTypeFace.getTypeface(MainActivity.this)),
                        new PrimaryDrawerItem().withName("Change Password").withIcon(FontAwesome.Icon.faw_key).withIdentifier(5).withSelectable(false).withTypeface(SetTypeFace.getTypeface(MainActivity.this)),
                        new PrimaryDrawerItem().withName("Sign Out").withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(6).withSelectable(false).withTypeface(SetTypeFace.getTypeface(MainActivity.this))
                )
                .withSavedInstance(savedInstanceState)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch ((int) drawerItem.getIdentifier()) {
                            case 2:
                                Intent intentSubmitAd = new Intent(MainActivity.this, SubmitAdActivity.class);
                                startActivity(intentSubmitAd);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                break;
                            case 3:

                                break;
                            case 4:

                                break;
                            case 5:


                                break;
                            case 6:
                                    showLogOutDialog();
                                break;

                            case 7:
                                Intent intentAboutUs = new Intent(MainActivity.this, AboutUsActivity.class);
                                startActivity(intentAboutUs);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                break;
                        }
                        return false;
                    }
                })
                .build();
//        result.getActionBarDrawerToggle ().setDrawerIndicatorEnabled (false);
    }

    private void initSlider () {
        slider.removeAllSliders ();
        for (int i = 0; i < bannerArrayList.size (); i++) {
            Banner banner = bannerArrayList.get (i);
            SpannableString s = new SpannableString (banner.getTitle ());
            s.setSpan (new TypefaceSpan(this, Constants.font_name), 0, s.length (), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            DefaultSliderView defaultSliderView = new DefaultSliderView (this);
            defaultSliderView
                    .image ( banner.getUrl ())
                    .setScaleType (BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener (this);


            defaultSliderView.bundle (new Bundle ());
            defaultSliderView.getBundle ().putString ("url", banner.getUrl ());
            slider.addSlider (defaultSliderView);
        }

        slider.setIndicatorVisibility (PagerIndicator.IndicatorVisibility.Visible);
        slider.setPresetTransformer (SliderLayout.Transformer.Default);
        slider.setCustomAnimation (new DescriptionAnimation());
        slider.setDuration (5000);
        slider.addOnPageChangeListener (this);
        slider.setCustomIndicator ((PagerIndicator) findViewById (R.id.custom_indicator));
        slider.setPresetIndicator (SliderLayout.PresetIndicators.Center_Bottom);
    }

    private void initView() {
        rvCategoryList = (RecyclerView) findViewById(R.id.rvCategoryList);
        slider = (SliderLayout) findViewById(R.id.slider);
        ivNavigation = (ImageView)findViewById(R.id.ivNavigation);
    }

    private void initData() {
        userDetailsPref = UserDetailsPref.getInstance();

        bannerArrayList.add(new Banner(1, "Banner", "Image", "https://helpadya.com/img/851491210277banner%202.jpg","1"));
        bannerArrayList.add(new Banner(2, "Banner", "Image", "https://helpadya.com/img/4091491211282BANNER2.jpg","2"));
        bannerArrayList.add(new Banner(3, "Banner", "Image", "https://helpadya.com/img/3391491211311banner%204.jpg","3"));
        bannerArrayList.add(new Banner(4, "Banner", "Image", "https://helpadya.com/img/4951491211418Homepage-Banner.jpg","4"));



        categorylist.add(new Category(1, "https://i.pinimg.com/originals/57/2e/4f/572e4f51d0a4d67669784df53026b5a7.png", "Clothes"));
        categorylist.add(new Category(2, "https://i.pinimg.com/originals/57/2e/4f/572e4f51d0a4d67669784df53026b5a7.png", "Pets"));
        categorylist.add(new Category(3, "https://i.pinimg.com/originals/57/2e/4f/572e4f51d0a4d67669784df53026b5a7.png", "Books"));
        categoriesAdapter = new CategoriesAdapter (MainActivity.this, categorylist);
        rvCategoryList.setAdapter (categoriesAdapter);
        rvCategoryList.setHasFixedSize (true);
        rvCategoryList.setLayoutManager (new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvCategoryList.addItemDecoration (new SimpleDividerItemDecoration(this));
        rvCategoryList.setItemAnimator (new DefaultItemAnimator());
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void showLogOutDialog() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .limitIconToDefaultSize()
                .content("Do you wish to Sign Out?")
                .positiveText("Yes")
                .negativeText("No")
                .typeface(SetTypeFace.getTypeface(MainActivity.this), SetTypeFace.getTypeface(MainActivity.this))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    //    LoginManager.getInstance().logOut();
                    //    LISessionManager.getInstance(getApplicationContext()).clearSession();

                        userDetailsPref.putStringPref(MainActivity.this, UserDetailsPref.USER_NAME, "");
                        userDetailsPref.putStringPref(MainActivity.this, UserDetailsPref.USER_EMAIL, "");
                        userDetailsPref.putStringPref(MainActivity.this, UserDetailsPref.USER_MOBILE, "");
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }).build();
        dialog.show();
    }
}
