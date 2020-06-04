package com.aivie.aivie.user.data.user;

import java.util.ArrayList;

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

    boolean isIcfSigned();

    void setIcfSigned(boolean signed);

    String getSiteId();

    String getSiteDoctor();

    String getSiteSC();

    String getSitePhone();

    ArrayList<String> getVisitPlan();

    int getLastIndexOfAdverseEvents();

    void saveLastIndexOfAdverseEvents(int lastIndexOfAdverseEvents);
}
