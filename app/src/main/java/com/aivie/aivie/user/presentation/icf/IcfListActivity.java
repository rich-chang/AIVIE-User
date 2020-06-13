package com.aivie.aivie.user.presentation.icf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aivie.aivie.user.R;

public class IcfListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icf_list);

        getSupportActionBar().setTitle("eICF Document History");
    }
}
