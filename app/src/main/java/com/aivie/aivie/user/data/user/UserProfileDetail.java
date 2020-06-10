package com.aivie.aivie.user.data.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class UserProfileDetail implements Parcelable {

    private String firstName;
    private String lastName;
    private String displayName;
    private String dateOfBirth;
    private String gender;
    private String race;
    private String ethnicity;
    private String subjectNum;
    private String role;
    private String patientOfStudy;
    private boolean hasUnsignedIcf;
    private String siteId;
    private String siteDoctor;
    private String siteSC;
    private String sitePhone;
    private ArrayList<String> visitPlan = new ArrayList<String>();

    /* everything below here is for implementing Parcelable */

    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        //out.writeInt(mData);
        out.writeString(this.firstName);
        out.writeString(this.lastName);
        out.writeString(this.displayName);
        out.writeString(this.dateOfBirth);
        out.writeString(this.gender);
        out.writeString(this.race);
        out.writeString(this.ethnicity);
        out.writeString(this.subjectNum);
        out.writeString(this.role);
        out.writeString(this.patientOfStudy);
        out.writeByte(this.hasUnsignedIcf? (byte) 1: (byte) 0);
        out.writeString(this.siteId);
        out.writeString(this.siteDoctor);
        out.writeString(this.siteSC);
        out.writeString(this.sitePhone);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<UserProfileDetail> CREATOR = new Parcelable.Creator<UserProfileDetail>() {
        public UserProfileDetail createFromParcel(Parcel in) {
            return new UserProfileDetail(in);
        }

        public UserProfileDetail[] newArray(int size) {
            return new UserProfileDetail[size];
        }
    };

    private UserProfileDetail(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.displayName = in.readString();
        this.dateOfBirth = in.readString();
        this.gender = in.readString();
        this.race = in.readString();
        this.ethnicity = in.readString();
        this.subjectNum = in.readString();
        this.role = in.readString();
        this.patientOfStudy = in.readString();
        this.hasUnsignedIcf = in.readByte() != 0;
        this.siteId = in.readString();
        this.siteDoctor = in.readString();
        this.siteSC = in.readString();
        this.sitePhone = in.readString();
    }
    /* Parcelable  */


    public UserProfileDetail(final String firstName, String lastName, String displayName, String dateOfBirth,
                             String gender, String race, String ethnicity,
                             String subjectNum, String role, String patientOfStudy, boolean hasUnsignedIcf,
                             String siteId, String siteDoctor, String siteSC, String sitePhone, ArrayList<String> visitPlan) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.race = race;
        this.ethnicity = ethnicity;
        this.subjectNum = subjectNum;
        this.role = role;
        this.patientOfStudy = patientOfStudy;
        this.hasUnsignedIcf = hasUnsignedIcf;
        this.siteId = siteId;
        this.siteDoctor = siteDoctor;
        this.siteSC = siteSC;
        this.sitePhone = sitePhone;
        this.visitPlan = visitPlan;
    }

    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }

    String getDisplayName() {
        return displayName;
    }

    String getDateOfBirth() {
        return dateOfBirth;
    }

    String getGender() {
        return gender;
    }

    String getRace() {
        return race;
    }

    String getEthnicity() {
        return ethnicity;
    }

    String getSubjectNum() {
        return subjectNum;
    }

    String getRole() {
        return role;
    }

    String getPatientOfStudy() {
        return patientOfStudy;
    }

    public boolean hasUnsignedIcf() {
        return hasUnsignedIcf;
    }

    String getSiteId() {
        return siteId;
    }

    String getSiteDoctor() {
        return siteDoctor;
    }

    String getSiteSC() {
        return siteSC;
    }

    String getSitePhone() {
        return sitePhone;
    }

    public void updateIcfSigned(boolean hasUnsignedIcf) {
        this.hasUnsignedIcf = hasUnsignedIcf;
    }

    ArrayList<String> getVisitPlan() {
        return visitPlan;
    }
}

