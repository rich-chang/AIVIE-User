package com.aivie.aivie.user.presentation.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aivie.aivie.user.R;
import com.aivie.aivie.user.data.Constant;
import com.aivie.aivie.user.data.user.UserProfileSpImpl;

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
        getVisitPlan();

        return root;
    }

    private void showUserName(View view) {
        UserProfileSpImpl userProfileSplmpl = new UserProfileSpImpl((MainActivity) getActivity());
        String userName = "Good day! " + userProfileSplmpl.getFirstName() + " " + userProfileSplmpl.getLastName();
        ((TextView) view.findViewById(R.id.textViewWelcome)).setText(userName);
    }

    private void getVisitPlan() {
        ArrayList<String> visitPlan = new ArrayList<String>();

        UserProfileSpImpl userProfileSplmpl = new UserProfileSpImpl((MainActivity) getActivity());
        visitPlan = userProfileSplmpl.getVisitPlan();

        Collections.sort(visitPlan);
    }
}
