package com.aivie.aivie.user.presentation.therapy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aivie.aivie.user.R;

public class PatientReportActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_report);

        findViewById(R.id.ll_LabReport).setOnClickListener(this);
        findViewById(R.id.ll_ImageReport).setOnClickListener(this);
        findViewById(R.id.ll_ECG).setOnClickListener(this);
        findViewById(R.id.ll_ePRO).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_LabReport:
            case R.id.ll_ImageReport:
            case R.id.ll_ECG:
            case R.id.ll_ePRO:
                startActivity(new Intent(this, ImageGalleryActivity.class));
                break;
        }
    }
}
