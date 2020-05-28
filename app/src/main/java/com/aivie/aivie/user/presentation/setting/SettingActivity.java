package com.aivie.aivie.user.presentation.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aivie.aivie.user.R;
import com.aivie.aivie.user.presentation.account.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        findViewById(R.id.textViewUserProfile).setOnClickListener(this);
    }

    public void clickLogout(View view) {

        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.textViewUserProfile:
                Intent intent = new Intent(this, UserProfileActivity.class);
                startActivity(intent);
                break;
        }
    }
}
