package com.aivie.aivie.user.presentation.therapy;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.aivie.aivie.user.R;
import com.aivie.aivie.user.data.Constant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AdverseEventDialog extends DialogFragment implements View.OnClickListener{

    private final Calendar myCalendar = Calendar.getInstance();

    public AdverseEventDialog() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_adverse_event, container, false);

        initActionButton(view);
        initAdverseEvents(view);
        initEventReportedDate(view);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.buttonCancel:
                dismiss();
                break;
            case R.id.buttonConfirm:
                saveDataToSp();
                dismiss();
                break;
        }
    }

    private void initActionButton(View view) {
        Button btnCancel = view.findViewById(R.id.buttonCancel);
        Button btnConfirm = view.findViewById(R.id.buttonConfirm);

        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    private void initAdverseEvents(View view) {

        ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.adverse_events, android.R.layout.simple_spinner_item );

        Spinner adverseEvents = view.findViewById(R.id.spinnerAdverseEvent);
        adverseEvents.setAdapter(nAdapter);
    }

    private void initEventReportedDate(View view) {

        final EditText editTextReportedDate = view.findViewById(R.id.editTextReportDate);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_SIMPLE, Locale.US);
                editTextReportedDate.setText(sdf.format(myCalendar.getTime()));
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

    private void saveDataToSp() {
        //TODO:
    }

}
