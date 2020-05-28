package com.aivie.aivie.user.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aivie.aivie.user.R;
import com.aivie.aivie.user.presentation.setting.SettingActivity;
import com.aivie.aivie.user.presentation.setting.UserProfileActivity;
import com.aivie.aivie.user.presentation.therapy.AdverseEventActivity;

public class TherapyFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //return super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_therapy, container, false);

        root.findViewById(R.id.imageViewAdverseEvent).setOnClickListener(this);
        root.findViewById(R.id.textViewAdverseEvent).setOnClickListener(this);

        setHasOptionsMenu(true);
        setAdverseEventTitle(root);
        displayAdverseEvent(root);

        return root;
    }

    private void setAdverseEventTitle(View view) {
        LinearLayout ll_adverseEvents = (LinearLayout) view.findViewById(R.id.ll_adverseEvents);
        LinearLayout ll_adverseEventTitle = new LinearLayout(getContext());

        ll_adverseEventTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll_adverseEventTitle.setOrientation(LinearLayout.HORIZONTAL);

        TextView adverseEventTitle = new TextView(getContext());
        adverseEventTitle.setText("Event");
        adverseEventTitle.setTextSize(16);
        adverseEventTitle.setTextColor(Integer.parseInt("000000", 16)+0xFF000000);
        adverseEventTitle.setPadding(0, 8, 0, 8);
        adverseEventTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        TextView eventTimeTitle = new TextView(getContext());
        eventTimeTitle.setText("Reported");
        eventTimeTitle.setTextSize(16);
        eventTimeTitle.setTextColor(Integer.parseInt("000000", 16)+0xFF000000);
        eventTimeTitle.setPadding(0, 8, 0, 8);
        eventTimeTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        TextView durationTitle = new TextView(getContext());
        durationTitle.setText("Duration (day)");
        durationTitle.setTextSize(16);
        durationTitle.setTextColor(Integer.parseInt("000000", 16)+0xFF000000);
        durationTitle.setPadding(0, 8, 0, 8);
        durationTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        ll_adverseEventTitle.addView(adverseEventTitle);
        ll_adverseEventTitle.addView(eventTimeTitle);
        ll_adverseEventTitle.addView(durationTitle);

        ll_adverseEvents.addView(ll_adverseEventTitle);
    }

    private void displayAdverseEvent(View view) {
        // TODO: Implement sample data by adding LinearLayout in LinearLayout

        setAdverseEvent(view, "Headache", "2020-05-03", "2");
        setAdverseEvent(view, "Loss Appetite", "2020-05-11", "1");
        setAdverseEvent(view, "Tremer", "2020-05-15", "3");
        setAdverseEvent(view, "Drowsiness", "2020-05-22", "2");
        setAdverseEvent(view, "Insomnia", "2020-05-28", "5");
        setAdverseEvent(view, "Loss Appetite", "2020-05-31", "1");
    }

    private void setAdverseEvent (View view, String eventName, String reportedDate, String eventDuration) {
        // TODO: Implement sample data by adding LinearLayout in LinearLayout

        LinearLayout ll_adverseEvents = (LinearLayout) view.findViewById(R.id.ll_adverseEvents);
        LinearLayout ll_adverseEvent = new LinearLayout(getContext());

        ll_adverseEvent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll_adverseEvent.setOrientation(LinearLayout.HORIZONTAL);

        TextView adverseEvent = new TextView(getContext());
        adverseEvent.setText(eventName);
        adverseEvent.setTextSize(16);
        adverseEvent.setPadding(0, 8, 0, 8);
        adverseEvent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        TextView eventTime = new TextView(getContext());
        eventTime.setText(reportedDate);
        eventTime.setTextSize(16);
        eventTime.setPadding(0, 8, 0, 8);
        eventTime.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        TextView duration = new TextView(getContext());
        duration.setText(eventDuration);
        duration.setTextSize(16);
        duration.setPadding(0, 8, 0, 8);
        duration.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        ll_adverseEvent.addView(adverseEvent);
        ll_adverseEvent.addView(eventTime);
        ll_adverseEvent.addView(duration);

        ll_adverseEvents.addView(ll_adverseEvent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.imageViewAdverseEvent:
            case R.id.textViewAdverseEvent:
                Intent intent = new Intent(getContext(), AdverseEventActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menu_setting) {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
