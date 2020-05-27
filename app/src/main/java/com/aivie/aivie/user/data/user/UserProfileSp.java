package com.aivie.aivie.user.data.user;

public interface UserProfileSp {

    void saveToSp(UserProfileDetail userProfileDetail);

    String getFirstName();

    String getLastName();

    String getDisplayName();

    String getDateOfBirth();

    String getGender();

    String getRace();

    String getEthnicity();

    String getSubjectNum();

    String getRole();

    String getPatientOfStudy();

    String getSiteId();

    String getSiteDoctor();

    String getSiteSC();

    String getSitePhone();
}
