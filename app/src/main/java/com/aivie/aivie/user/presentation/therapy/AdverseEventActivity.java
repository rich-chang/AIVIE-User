package com.aivie.aivie.user.presentation.therapy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.aivie.aivie.user.R;
import com.aivie.aivie.user.data.Constant;
import com.aivie.aivie.user.data.sqlite.AdverseEventDBHelper;
import com.aivie.aivie.user.data.user.UserProfileSpImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class AdverseEventActivity extends AppCompatActivity {

    private AdverseEventDBHelper db;
    private String eventName;
    private String eventHappenedDate;
    private String eventDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adverse_event);

        db = new AdverseEventDBHelper(this);
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
            saveDataToFirebase();
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

    private void saveDataToFirebase() {

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final int currentIndex = getLastIndexOfAdverseEvents() + 1;

        Map<String, Object> userAdverseEventsData = new HashMap<>();
        userAdverseEventsData.put(Constant.FIRE_AEH_COLUMN_ID, currentIndex);
        userAdverseEventsData.put(Constant.FIRE_AEH_COLUMN_USER_ID, userId);
        userAdverseEventsData.put(Constant.FIRE_AEH_COLUMN_EVENT_NAME, eventName);
        userAdverseEventsData.put(Constant.FIRE_AEH_COLUMN_EVENT_HAPPENED, eventHappenedDate);
        userAdverseEventsData.put(Constant.FIRE_AEH_COLUMN_EVENT_DURATION, eventDuration);

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_FULL, Locale.US);
        userAdverseEventsData.put(Constant.FIRE_AEH_COLUMN_EVENT_REPORTED, sdf.format(new Date()));

        db.collection(Constant.FIRE_COLLECTION_USERS).document(userId)
                .collection(Constant.FIRE_COLLECTION_ADVERSE_EVENTS).document(String.valueOf(currentIndex))
                .set(userAdverseEventsData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (Constant.DEBUG) Log.d(Constant.TAG, "saveDataToFirebase: successfully written!");
                        saveLastIndexOfAdverseEvents(currentIndex);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (Constant.DEBUG) Log.w(Constant.TAG, "saveDataToFirebase: Error writing document", e);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (Constant.DEBUG) Log.d(Constant.TAG, "saveDataToFirebase: complete");
                    }
                });
    }

    private int getLastIndexOfAdverseEvents() {
        UserProfileSpImpl userProfileSplmpl = new UserProfileSpImpl(this);
        return userProfileSplmpl.getLastIndexOfAdverseEvents();
    }

    private void saveLastIndexOfAdverseEvents(int lastIndexOfAdverseEvents) {
        UserProfileSpImpl userProfileSplmpl = new UserProfileSpImpl((Context) this);
        userProfileSplmpl.saveLastIndexOfAdverseEvents(lastIndexOfAdverseEvents);
    }
}
