package com.aivie.aivie.user.data;

import com.aivie.aivie.user.BuildConfig;

public interface Constant {

    // Debuggin Log
    boolean DEBUG = BuildConfig.DEBUG;
    String TAG = "richc";

    String USER_PROFILE_DETAIL = "UserProfileDetail";

    // SharedPreferences
    String SP_USERPROFILE_FILENAME = "user_profile";   // SP file is located in "/data/data/[package.name]/shared_prefs/"
    String SP_USERPROFILE_DEFAULT_VALUE = "Default Value";

    String SIGNATURE_URI = "SignatureUri";
    String SIGNATURE_SIGNED = "SignatureSigned";

    String FIRE_COLLECTION_USERS = "users";
    String FIRE_COLLECTION_GENDER = "gender";
    String FIRE_COLLECTION_RACE = "race";
    String FIRE_COLLECTION_ETHNICITY = "ethnicity";
    String FIRE_COLLECTION_ROLES = "roles";
    String FIRE_COLLECTION_STUDIES = "studies";
    String FIRE_COLLECTION_ICF = "icf";

    String FIRE_COLUMN_ID = "Id";
    String FIRE_COLUMN_TITLE = "Title";
    String FIRE_COLUMN_FIRSTNAME = "FirstName";
    String FIRE_COLUMN_LASTNAME = "LastName";
    String FIRE_COLUMN_DISPLAYNAME = "DisplayName";
    String FIRE_COLUMN_DOB = "DOB";
    String FIRE_COLUMN_GENDER = "Gender";
    String FIRE_COLUMN_RACE = "Race";
    String FIRE_COLUMN_ETHICITY = "Ethnicity";
    String FIRE_COLUMN_SUBJECTNUM = "SubjectNumber";
    String FIRE_COLUMN_ROLE = "Role";
    String FIRE_COLUMN_PATIENT_OF_STUDY = "PatientOfStudy";
    String FIRE_COLUMN_EICF = "eICF";
    String FIRE_COLUMN_EICF_SIGNED = "eICF_Signed";
    String FIRE_COLUMN_SITE_ID = "SiteId";
    String FIRE_COLUMN_SITE_DOCTOR = "SiteDoctor";
    String FIRE_COLUMN_SITE_SC = "SiteSC";
    String FIRE_COLUMN_SITE_PHONE = "SitePhone";
}
