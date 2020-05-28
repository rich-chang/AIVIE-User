package com.aivie.aivie.user.presentation.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.aivie.aivie.user.R;
import com.aivie.aivie.user.data.Constant;
import com.aivie.aivie.user.data.user.UserProfileSpImpl;
import com.google.type.Color;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //return super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        showUserName(root);

        try {
            getVisitPlan(root);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return root;
    }

    private void showUserName(View view) {
        UserProfileSpImpl userProfileSplmpl = new UserProfileSpImpl((MainActivity) getActivity());
        String userName = "Good day! " + userProfileSplmpl.getFirstName() + " " + userProfileSplmpl.getLastName();
        ((TextView) view.findViewById(R.id.textViewWelcome)).setText(userName);
    }

    private void getVisitPlan(View view) throws ParseException {

        UserProfileSpImpl userProfileSplmpl = new UserProfileSpImpl((MainActivity) getActivity());
        ArrayList<String> visitPlan = userProfileSplmpl.getVisitPlan();

        // Re-order
        Collections.sort(visitPlan);

        // Set text in UI
        setNextVisitDate(view, visitPlan);
        setVisitPlan(view, visitPlan);
    }

    private void setNextVisitDate(View view, ArrayList<String> visitPlan) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_FULL, Locale.US);

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
