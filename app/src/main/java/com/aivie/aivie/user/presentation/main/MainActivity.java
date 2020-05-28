package com.aivie.aivie.user.presentation.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.aivie.aivie.user.BuildConfig;
import com.aivie.aivie.user.R;
import com.aivie.aivie.user.data.Constant;
import com.aivie.aivie.user.data.user.UserProfileDetail;
import com.aivie.aivie.user.presentation.account.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private MainPresenter mainPresenter;
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFrag = null;

                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFrag = new HomeFragment();
                            getSupportActionBar().setTitle("Home");
                            break;
                        case R.id.navigation_progress:
                            selectedFrag = new ProgressFragment();
                            getSupportActionBar().setTitle("Progress");
                            break;
                        case R.id.navigation_team:
                            selectedFrag = new TeamFragment();
                            getSupportActionBar().setTitle("Team");
                            break;
                        case R.id.navigation_therapy:
                            selectedFrag = new TherapyFragment();
                            getSupportActionBar().setTitle("MyTherapy");
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFrag).commit();

                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        mainPresenter = new MainPresenter(this);
        mainPresenter.saveVersionToSp();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {

            mainPresenter.goToLoginView();
        } else {

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            getSupportActionBar().setTitle("Home");
        }
    }
}
