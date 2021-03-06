package com.aivie.aivie.user.data.user;

import android.content.Context;
import android.content.SharedPreferences;

import com.aivie.aivie.user.data.Constant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UserProfileSpImpl implements UserProfileSp {

    private SharedPreferences sp;

    public UserProfileSpImpl(Context context) {
        sp = context.getSharedPreferences(Constant.SP_USERPROFILE_FILENAME, Context.MODE_PRIVATE);
    }

    @Override
    public void saveToSp(UserProfileDetail userProfileDetail) {
        sp.edit()
                .putString(Constant.FIRE_COLUMN_FIRSTNAME, userProfileDetail.getFirstName())
                .putString(Constant.FIRE_COLUMN_LASTNAME, userProfileDetail.getLastName())
                .putString(Constant.FIRE_COLUMN_DISPLAYNAME, userProfileDetail.getDisplayName())
                .putString(Constant.FIRE_COLUMN_DOB, userProfileDetail.getDateOfBirth())
                .putString(Constant.FIRE_COLUMN_GENDER, userProfileDetail.getGender())
                .putString(Constant.FIRE_COLUMN_RACE, userProfileDetail.getRace())
                .putString(Constant.FIRE_COLUMN_ETHICITY, userProfileDetail.getEthnicity())
                .putString(Constant.FIRE_COLUMN_SUBJECTNUM, userProfileDetail.getSubjectNum())
                .putString(Constant.FIRE_COLUMN_ROLE, userProfileDetail.getRole())
                .putString(Constant.FIRE_COLUMN_PATIENT_OF_STUDY, userProfileDetail.getPatientOfStudy())
                .putString(Constant.FIRE_COLUMN_STUDY_NUMBER, userProfileDetail.getStudyNumber())
                .putString(Constant.FIRE_COLUMN_EDITION_NUMBER, userProfileDetail.getEditionNumber())
                .putString(Constant.FIRE_COLUMN_VERSION_NUMBER, userProfileDetail.getVersionNumber())
                .putBoolean(Constant.FIRE_COLUMN_HAS_UNSIGNED_ICF, userProfileDetail.hasUnsignedIcf())
                .putString(Constant.FIRE_COLUMN_SITE_ID, userProfileDetail.getSiteId())
                .putString(Constant.FIRE_COLUMN_SITE_DOCTOR, userProfileDetail.getSiteDoctor())
                .putString(Constant.FIRE_COLUMN_SITE_SC, userProfileDetail.getSiteSC())
                .putString(Constant.FIRE_COLUMN_SITE_PHONE, userProfileDetail.getSitePhone())
        .apply();

        // Handle string list for visit plan
        ArrayList<String> visitPlan = userProfileDetail.getVisitPlan();
        Set<String> set = new HashSet<String>();

        for (int i=0; i<visitPlan.size(); i++) {
            set.add(visitPlan.get(i));
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet(Constant.FIRE_COLUMN_STUDY_VISIT_PLAN, set);
        editor.apply();
    }

    @Override
    public String getFirstName() {
        return sp.getString(Constant.FIRE_COLUMN_FIRSTNAME, "");
    }

    @Override
    public String getLastName() {
        return sp.getString(Constant.FIRE_COLUMN_LASTNAME, "");
    }

    @Override
    public String getDisplayName() {
        return sp.getString(Constant.FIRE_COLUMN_DISPLAYNAME, "");
    }

    @Override
    public String getDateOfBirth() {
        return sp.getString(Constant.FIRE_COLUMN_DOB, "");
    }

    @Override
    public String getGender() {
        return sp.getString(Constant.FIRE_COLUMN_GENDER, "");
    }

    @Override
    public String getRace() {
        return sp.getString(Constant.FIRE_COLUMN_RACE, "");
    }

    @Override
    public String getEthnicity() {
        return sp.getString(Constant.FIRE_COLUMN_ETHICITY, "");
    }

    @Override
    public String getSubjectNum() {
        return sp.getString(Constant.FIRE_COLUMN_SUBJECTNUM, "");
    }

    @Override
    public String getRole() {
        return sp.getString(Constant.FIRE_COLUMN_ROLE, "");
    }

    @Override
    public String getPatientOfStudy() {
        return sp.getString(Constant.FIRE_COLUMN_PATIENT_OF_STUDY, "");
    }

    @Override
    public String getStudyNumber() {
        return sp.getString(Constant.FIRE_COLUMN_STUDY_NUMBER, "");
    }

    @Override
    public String getEditionNumber() {
        return sp.getString(Constant.FIRE_COLUMN_EDITION_NUMBER, "");
    }

    @Override
    public String getVersionNumber() {
        return sp.getString(Constant.FIRE_COLUMN_VERSION_NUMBER, "");
    }


    @Override
    public boolean hasUnsignedIcf() {
        return sp.getBoolean(Constant.FIRE_COLUMN_HAS_UNSIGNED_ICF, false);
    }

    @Override
    public void setIcfSigned(boolean signed) {
        sp.edit().putBoolean(Constant.FIRE_COLUMN_HAS_UNSIGNED_ICF, signed).apply();
    }

    @Override
    public String getSiteId() {
        return sp.getString(Constant.FIRE_COLUMN_SITE_ID, "");
    }

    @Override
    public String getSiteDoctor() {
        return sp.getString(Constant.FIRE_COLUMN_SITE_DOCTOR, "");
    }

    @Override
    public String getSiteSC() {
        return sp.getString(Constant.FIRE_COLUMN_SITE_SC, "");
    }

    @Override
    public String getSitePhone() {
        return sp.getString(Constant.FIRE_COLUMN_SITE_PHONE, "");
    }

    @Override
    public ArrayList<String> getVisitPlan() {
        Set<String> fetch = sp.getStringSet(Constant.FIRE_COLUMN_STUDY_VISIT_PLAN, null);

        ArrayList<String> visitPlan = new ArrayList<String>();

        assert fetch != null;
        visitPlan.addAll(fetch);
            
        return visitPlan;
    }

    @Override
    public int getLastIndexOfAdverseEvents() {
        return sp.getInt(Constant.SP_ADVERSE_EVENTS_LAST_INDEX, 0);
    }

    @Override
    public void saveLastIndexOfAdverseEvents(int lastIndexOfAdverseEvents) {
        sp.edit().putInt(Constant.SP_ADVERSE_EVENTS_LAST_INDEX, lastIndexOfAdverseEvents).apply();
    }
}
