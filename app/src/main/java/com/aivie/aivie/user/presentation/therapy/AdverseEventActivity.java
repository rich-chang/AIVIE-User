package com.aivie.aivie.user.presentation.therapy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.aivie.aivie.user.R;
import com.aivie.aivie.user.data.Constant;
import com.aivie.aivie.user.data.sqlite.DBHelper;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AdverseEventActivity extends AppCompatActivity {

    private DBHelper db;
    private String eventName;
    private String eventHappenedDate;
    private String eventDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adverse_event);

        db = new DBHelper(this);
        initAdverseEvents();
        initEventReportedDate();
        initEventDuration();
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
                builder.setSingleChoiceItems(getResources().getStringArray(R.array.adverse_events), -1, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedIndex[0] = i;
                    }
                }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eventName = Arrays.asList(adverseEvents).get(selectedIndex[0]);
                        editTextAdverseEvent.setText(eventName);
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

    private void initEventReportedDate() {

        final Calendar myCalendar = Calendar.getInstance();
        final EditText editTextReportedDate = findViewById(R.id.editTextHappenedDate);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_SIMPLE, Locale.US);
                eventHappenedDate = sdf.format(myCalendar.getTime());
                editTextReportedDate.setText(eventHappenedDate);
            }
        };

        editTextReportedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(),
                        date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void initEventDuration() {

        final String[] durationList = new String[30];   // set max duration day is 30
        for (int i=0; i<30; i++) {
            durationList[i] = String.valueOf(i);
        }

        final EditText editTextEventDuration= findViewById(R.id.editTextEventDuration);
        final Integer[] selectedIndex = {0};

        editTextEventDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AdverseEventActivity.this);

                builder.setTitle("Duration (day)");
                builder.setSingleChoiceItems(durationList, -1, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedIndex[0] = i;
                    }
                }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eventDuration = Arrays.asList(durationList).get(selectedIndex[0]);
                        editTextEventDuration.setText(eventDuration);
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

        if (eventName != null && eventHappenedDate != null && eventDuration != null) {
            saveDataToDb();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Do not leave empty field", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDataToDb() {
        db.insertEvent(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(),
                eventName,
                eventHappenedDate,
                eventDuration);
    }
}
