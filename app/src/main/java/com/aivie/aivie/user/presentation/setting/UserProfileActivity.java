package com.aivie.aivie.user.presentation.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.aivie.aivie.user.R;
import com.aivie.aivie.user.data.user.UserProfileSpImpl;

import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        displayUserProfile();
    }

    private void displayUserProfile() {
        UserProfileSpImpl userProfileSp = new UserProfileSpImpl(this);

        ((TextView) findViewById(R.id.textViewUserName)).setText(TextUtils.concat(userProfileSp.getFirstName(), " ", userProfileSp.getLastName()));
        ((TextView) findViewById(R.id.textViewBirth)).setText(userProfileSp.getDateOfBirth());
        ((TextView) findViewById(R.id.textViewGender)).setText(userProfileSp.getGender());
        ((TextView) findViewById(R.id.textViewRace)).setText(userProfileSp.getRace());
        ((TextView) findViewById(R.id.textViewEthinicity)).setText(userProfileSp.getEthnicity());
    }
}
