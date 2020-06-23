package com.aivie.aivie.user.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aivie.aivie.user.R;
import com.aivie.aivie.user.data.Constant;
import com.aivie.aivie.user.data.user.UserProfileSpImpl;
import com.aivie.aivie.user.presentation.account.LoginActivity;
import com.aivie.aivie.user.presentation.icf.IcfListActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        View view = getView();
        if (view != null) {

            view.findViewById(R.id.ll_IcfSignedHistory).setOnClickListener(this);
            showUserName(view);

            try {
                getVisitPlan(view);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_IcfSignedHistory:
                Intent intent = new Intent(getContext(), IcfListActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void showUserName(View view) {
        UserProfileSpImpl userProfileSplmpl = new UserProfileSpImpl((MainActivity) getActivity());
        String userName = "Good day! " + userProfileSplmpl.getFirstName() + " " + userProfileSplmpl.getLastName();
        ((TextView) view.findViewById(R.id.textViewWelcome)).setText(userName);
    }

    private void getVisitPlan(View view) throws ParseException {

        try {
            UserProfileSpImpl userProfileSplmpl = new UserProfileSpImpl((MainActivity) getActivity());
            ArrayList<String> visitPlan = userProfileSplmpl.getVisitPlan();

            // Re-order
            Collections.sort(visitPlan);

            // Set text in UI
            setNextVisitDate(view, visitPlan);
            setVisitPlan(view, visitPlan);

        } catch (Exception e) {
            e.printStackTrace();

            // Force to log-out and back to login view
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private void setNextVisitDate(View view, ArrayList<String> visitPlan) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_SIMPLE, Locale.US);

        for (int i=0; i<visitPlan.size(); i++) {
            Date visitDate = sdf.parse(visitPlan.get(i));

            if (new Date().before(visitDate)) {
                ((TextView) view.findViewById(R.id.textViewNextVisit)).setText(visitPlan.get(i));
                break;
            }
        }
    }

    private void setVisitPlan(View view, ArrayList<String> visitPlan) {

        for (int i=0; i<visitPlan.size(); i++) {
            TextView tvVisitDate = new TextView(getContext());

            tvVisitDate.setText(visitPlan.get(i));
            tvVisitDate.setTextSize(16);
            tvVisitDate.setTextColor(Integer.parseInt("000000", 16)+0xFF000000);
            tvVisitDate.setPadding(0, 8, 0, 8);

            LinearLayout ll_visitPlan = (LinearLayout) view.findViewById(R.id.ll_visitPlan);
            ll_visitPlan.addView(tvVisitDate);
        }
    }
}
