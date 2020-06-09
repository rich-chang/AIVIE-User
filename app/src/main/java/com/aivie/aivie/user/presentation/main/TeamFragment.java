package com.aivie.aivie.user.presentation.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.aivie.aivie.user.R;
import com.aivie.aivie.user.data.Constant;
import com.aivie.aivie.user.data.user.UserProfileSpImpl;

import org.w3c.dom.Text;

import java.util.Objects;

public class TeamFragment extends Fragment implements View.OnClickListener {

    private String siteContactPhone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_team, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        View view = getView();
        if (view != null) {

            displayTeamInfo(view);

            view.findViewById(R.id.buttonCallContact).setOnClickListener(this);
            view.findViewById(R.id.textViewHealthEduVideo).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.buttonCallContact:
                if (!hasCallPermission()) {

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE},
                            Constant.REQ_CODE_ASK_CALL_PHONE);
                } else {
                    callContactPhone();
                }
                break;

            case R.id.textViewHealthEduVideo:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "Rck-whljqTo"));
                startActivity(intent);
                break;
        }
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == Constant.REQ_CODE_ASK_CALL_PHONE) {
            // If request is cancelled, the result arrays are empty.

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                doCallPhone();
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }
    */

    private void callContactPhone() {
        String intentCallNumber = "tel:" + siteContactPhone;

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(intentCallNumber));
        startActivity(callIntent);
    }

    private void displayTeamInfo(View view) {

        UserProfileSpImpl userProfileSp = new UserProfileSpImpl(Objects.requireNonNull(getActivity()));

        ((TextView) view.findViewById(R.id.textViewSubjectNum)).setText(userProfileSp.getSubjectNum());
        ((TextView) view.findViewById(R.id.textViewSiteId)).setText(userProfileSp.getSiteId());
        ((TextView) view.findViewById(R.id.textViewSiteDoctor)).setText(userProfileSp.getSiteDoctor());
        ((TextView) view.findViewById(R.id.textViewSiteSC)).setText(userProfileSp.getSiteSC());

        siteContactPhone = userProfileSp.getSitePhone();
        ((TextView) view.findViewById(R.id.textViewSitePhone)).setText(siteContactPhone);
    }

    private boolean hasCallPermission() {
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
    }
}
