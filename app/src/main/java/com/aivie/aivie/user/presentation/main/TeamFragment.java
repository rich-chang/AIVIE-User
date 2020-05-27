package com.aivie.aivie.user.presentation.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aivie.aivie.user.R;
import com.aivie.aivie.user.data.user.UserProfileSpImpl;

import org.w3c.dom.Text;

import java.util.Objects;

public class TeamFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //return super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_team, container, false);

        displayTeamInfo(root);

        return root;
    }

    private void displayTeamInfo(View view) {

        UserProfileSpImpl userProfileSp = new UserProfileSpImpl(Objects.requireNonNull(getActivity()));

        ((TextView) view.findViewById(R.id.textViewSiteId)).setText(userProfileSp.getSiteId());
        ((TextView) view.findViewById(R.id.textViewSiteDoctor)).setText(userProfileSp.getSiteDoctor());
        ((TextView) view.findViewById(R.id.textViewSiteSC)).setText(userProfileSp.getSiteSC());
        ((TextView) view.findViewById(R.id.textViewSitePhone)).setText(userProfileSp.getSitePhone());
    }
}
