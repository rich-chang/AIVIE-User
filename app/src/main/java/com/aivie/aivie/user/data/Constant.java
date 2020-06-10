package com.aivie.aivie.user.data;

import com.aivie.aivie.user.BuildConfig;

public interface Constant {

    // Debuggin Log
    boolean DEBUG = BuildConfig.DEBUG;
    String TAG = "richc";

    String USER_PROFILE_DETAIL = "UserProfileDetail";
    String DATE_FORMAT_SIMPLE = "yyyy-MM-dd";
    String DATE_FORMAT_FULL = "yyyy/MM/dd HH:mm";

    // SharedPreferences
    String SP_USERPROFILE_FILENAME = "user_profile";   // SP file is located in "/data/data/[package.name]/shared_prefs/"
    String SP_USERPROFILE_DEFAULT_VALUE = "Default Value";
    String SP_ADVERSE_EVENTS_LAST_INDEX = "LastIndexOfAdverseEvents";
    String SP_APP_GENERAL = "app_general";
    String APP_VERSION = "version";

    String SIGNATURE_URI = "SignatureUri";
    String SIGNATURE_SIGNED = "SignatureSigned";

    String FIRE_COLLECTION_USERS = "users";
    String FIRE_COLLECTION_GENDER = "gender";
    String FIRE_COLLECTION_RACE = "race";
    String FIRE_COLLECTION_ETHNICITY = "ethnicity";
    String FIRE_COLLECTION_ROLES = "roles";
    String FIRE_COLLECTION_STUDIES = "studies";
    String FIRE_COLLECTION_ICF = "icf";
    String FIRE_COLLECTION_ADVERSE_EVENTS = "adverse_events";
    String FIRE_COLLECTION_ICF_HISTORY = "icf_history";

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
    //String FIRE_COLUMN_EICF_SIGNED = "eICF_Signed";
    String FIRE_COLUMN_HAS_UNSIGNED_ICF = "HasUnsignedIcf";
    String FIRE_COLUMN_SITE_ID = "SiteId";
    String FIRE_COLUMN_SITE_DOCTOR = "SiteDoctor";
    String FIRE_COLUMN_SITE_SC = "SiteSC";
    String FIRE_COLUMN_SITE_PHONE = "SitePhone";
    String FIRE_COLUMN_STUDY_VISIT_PLAN = "VisitPlan";
    // Adverse Events
    String FIRE_AEH_COLUMN_ID = "Id";
    String FIRE_AEH_COLUMN_USER_ID = "UserId";
    String FIRE_AEH_COLUMN_EVENT_NAME = "EventName";
    String FIRE_AEH_COLUMN_EVENT_HAPPENED = "EventHappened";   // happened time
    String FIRE_AEH_COLUMN_EVENT_DURATION = "EventDuration";
    String FIRE_AEH_COLUMN_EVENT_REPORTED = "EventReported";   // now time
    // ICF History
    String FIRE_COLUMN_DOC_REFERENCE = "DocRef";
    String FIRE_COLUMN_SIGNATURE_URL = "SignatureUrl";
    String FIRE_COLUMN_SIGNED = "Signed";
    String FIRE_COLUMN_SIGNED_DATE = "SignedDate";

    // Request Permission
    Integer REQ_CODE_ASK_CALL_PHONE = 100;
}
