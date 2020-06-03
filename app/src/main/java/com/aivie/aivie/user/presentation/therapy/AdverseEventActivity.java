package com.aivie.aivie.user.presentation.therapy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.aivie.aivie.user.R;
import com.aivie.aivie.user.data.Constant;
import com.aivie.aivie.user.presentation.main.TherapyFragment;

import java.util.Arrays;

public class AdverseEventActivity extends AppCompatActivity {

    private String adverseEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adverse_event);

        initAdverseEvents();
    }

    private void initAdverseEvents() {

        final String[] adverseEvents = getResources().getStringArray(R.array.adverse_events);
        final EditText editTextAdverseEvent = findViewById(R.id.editTextAdverseEvent);
        final Integer[] selectedIndex = {0};

        editTextAdverseEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AdverseEventActivity.this);

                builder.setTitle("Adverse Events");
                //builder.setMessage("Please select one");
                builder.setSingleChoiceItems(getResources().getStringArray(R.array.adverse_events), -1, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedIndex[0] = i;
                    }
                }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adverseEvent = Arrays.asList(adverseEvents).get(selectedIndex[0]);
                        editTextAdverseEvent.setText(adverseEvent);
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });

    }

    public void btnClickCancel(View view) {
        finish();
    }

    public void btnClickConfirm(View view) {
        saveDataToSp();
        finish();
    }

    private void saveDataToSp() {
        //TODO:
        Log.i(Constant.TAG, "adverseEvent: " + adverseEvent);
    }
}
