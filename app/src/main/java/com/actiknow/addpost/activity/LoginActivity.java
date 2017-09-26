package com.actiknow.addpost.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.actiknow.addpost.R;
import com.actiknow.addpost.fragment.SignInFragment;
import com.actiknow.addpost.fragment.SignUpFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        SignInFragment signInFragment = new SignInFragment();

        fragmentTransaction.replace(R.id.frameLayout, signInFragment);
        fragmentTransaction.commit(); // save the changes
    }
}
