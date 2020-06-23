package com.aivie.aivie.user.presentation.account;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.aivie.aivie.user.data.Constant;
import com.aivie.aivie.user.data.sqlite.AdverseEventDBHelper;
import com.aivie.aivie.user.data.user.UserProfileDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

class LoginRepository {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private AdverseEventDBHelper dbLocal;
    private String userId;
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
    private String studyNumber;
    private String editionNumber;
    private String versionNumber;
    private boolean hasUnsignedIcf;
    private String siteId;
    private String siteDoctor;
    private String siteSC;
    private String sitePhone;
    private ArrayList<String> visitPlan = new ArrayList<String>();

    LoginRepository(Context context) {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        dbLocal = new AdverseEventDBHelper(context);
    }

    void userLogin(Context context, String username, String password, final LoginContract.LoginCallback loginCallback) {

        if (username.equals("") || password.equals("")) {
            loginCallback.onFailure(null, "Email or password can't be empty.");
            loginCallback.onComplete();
            return;
        }

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userId = mAuth.getCurrentUser().getUid();
                            Log.i(Constant.TAG, "signInWithEmail success: " + mAuth.getCurrentUser().getEmail() + " " + userId);
                            loginCallback.onSuccess("signInWithEmail:success");
                        } else {

                            try {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                switch (errorCode) {
                                    case "ERROR_INVALID_EMAIL":
                                        loginCallback.onFailure("The email address is badly formatted.", "The email address is badly formatted.\n\nPlease try again.");
                                        break;
                                    case "ERROR_WRONG_PASSWORD":
                                        loginCallback.onFailure("Password is incorrect ", "The password is invalid or the user does not have a password.\n\nPlease try again.");
                                        break;
                                    case "ERROR_USER_DISABLED":
                                        loginCallback.onFailure(null, "The user account has been disabled by an administrator.\n\nPlease contact administrator.");
                                        break;
                                    case "ERROR_USER_NOT_FOUND":
                                        loginCallback.onFailure(null, "This user (email) is not found.\n\nPlease sign up a new one.");
                                        break;
                                    default:
                                        loginCallback.onFailure(null, task.getException().toString() + "\n\nPlease try again.");
                                        break;
                                }
                                Log.i(Constant.TAG, String.format("signInWithEmail failure (%s) %s", errorCode, task.getException()));

                            } catch (Exception e) {
                                e.printStackTrace();

                                if (e.toString().contains("com.google.firebase.FirebaseNetworkException")) {
                                    loginCallback.onFailure(null, "No available internet connection.\nPlease try again.");
                                } else {
                                    loginCallback.onFailure(null, e.toString());
                                }
                            }
                        }
                        loginCallback.onComplete();
                    }
                });
    }

    void getUserProfile(final LoginContract.GetUserProfileCallback getUserProfileCallback) {

        DocumentReference docRefUser = db.collection(Constant.FIRE_COLLECTION_USERS).document(userId);
        docRefUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot documentUser = task.getResult();
                    if (Objects.requireNonNull(documentUser).exists()) {

                        lastName = (String) documentUser.get(Constant.FIRE_COLUMN_LASTNAME);
                        firstName = (String) documentUser.get(Constant.FIRE_COLUMN_FIRSTNAME);
                        displayName = (String) documentUser.get(Constant.FIRE_COLUMN_DISPLAYNAME);

                        subjectNum = (String) documentUser.get(Constant.FIRE_COLUMN_SUBJECTNUM);
                        hasUnsignedIcf = (boolean) documentUser.get(Constant.FIRE_COLUMN_HAS_UNSIGNED_ICF);

                        // Get user birthday
                        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
                        Timestamp tsBirthday = (Timestamp) documentUser.get(Constant.FIRE_COLUMN_DOB);
                        Date dateBirthday = tsBirthday.toDate();
                        dateOfBirth = sfd.format(dateBirthday);

                        siteId = (String) documentUser.get(Constant.FIRE_COLUMN_SITE_ID);
                        siteDoctor = (String) documentUser.get(Constant.FIRE_COLUMN_SITE_DOCTOR);
                        siteSC = (String) documentUser.get(Constant.FIRE_COLUMN_SITE_SC);
                        sitePhone = (String) documentUser.get(Constant.FIRE_COLUMN_SITE_PHONE);

                        getUserProfileCallback.onSuccess(documentUser);
                    } else {
                    }
                } else {
                }
            }
        });
    }

    void getUserGender(DocumentSnapshot documentUser, final  LoginContract.GetUserGenderCallback getUserGenderCallback) {

        DocumentReference docRefGender = (DocumentReference) documentUser.get(Constant.FIRE_COLUMN_GENDER);
        docRefGender.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot documentUserGender = task.getResult();
                    if(Objects.requireNonNull(documentUserGender).exists()) {
                        gender = (String) documentUserGender.get(Constant.FIRE_COLUMN_TITLE);

                        getUserGenderCallback.onSuccess();
                    }
                }
            }
        });
    }

    void getUserRace(DocumentSnapshot documentUser, final  LoginContract.GetUserRaceCallback getUserRaceCallback) {

        DocumentReference docRefRace = (DocumentReference) documentUser.get(Constant.FIRE_COLUMN_RACE);
        docRefRace.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot documentUserRace = task.getResult();
                    if(Objects.requireNonNull(documentUserRace).exists()) {
                        race = (String) documentUserRace.get(Constant.FIRE_COLUMN_TITLE);

                        getUserRaceCallback.onSuccess();
                    }
                }
            }
        });
    }

    void getUserEthinicity(DocumentSnapshot documentUser, final  LoginContract.GetUserEthnicityCallback getUserEthnicityCallback) {

        DocumentReference docRefRace = (DocumentReference) documentUser.get(Constant.FIRE_COLUMN_ETHICITY);
        docRefRace.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot documentUserEthnicity = task.getResult();
                    if(Objects.requireNonNull(documentUserEthnicity).exists()) {
                        ethnicity = (String) documentUserEthnicity.get(Constant.FIRE_COLUMN_TITLE);

                        getUserEthnicityCallback.onSuccess();
                    }
                }
            }
        });
    }

    void getUserStudy(DocumentSnapshot documentUser, final  LoginContract.GetUserStudyCallback getUserStudyCallback) {

        DocumentReference docRefStudy = (DocumentReference) documentUser.get(Constant.FIRE_COLUMN_PATIENT_OF_STUDY);
        docRefStudy.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot documentUserStudy = task.getResult();
                    if(Objects.requireNonNull(documentUserStudy).exists()) {

                        patientOfStudy = (String) documentUserStudy.get(Constant.FIRE_COLUMN_TITLE);
                        studyNumber = (String) documentUserStudy.get(Constant.FIRE_COLUMN_STUDY_NUMBER);
                        editionNumber = (String) documentUserStudy.get(Constant.FIRE_COLUMN_EDITION_NUMBER);
                        versionNumber = (String) documentUserStudy.get(Constant.FIRE_COLUMN_VERSION_NUMBER);

                        List<Timestamp> visitsDate = (List<Timestamp>) documentUserStudy.getData().get(Constant.FIRE_COLUMN_STUDY_VISIT_PLAN);

                        for (int i=0; i<visitsDate.size(); i++) {

                            Timestamp tm = visitsDate.get(i);
                            Date date = tm.toDate();

                            SimpleDateFormat sfdFull = new SimpleDateFormat(Constant.DATE_FORMAT_SIMPLE, Locale.US);
                            visitPlan.add(sfdFull.format(date));

                            // TODO: Register notification for each date
                        }

                        getUserStudyCallback.onSuccess();
                    }
                }
            }
        });
    }

    void getUserRole(DocumentSnapshot documentUser, final LoginContract.GetUserRoleCallback getUserRoleCallback ) {

        DocumentReference docRefRole = (DocumentReference) documentUser.get(Constant.FIRE_COLUMN_ROLE);
        docRefRole.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot documentUserRole = task.getResult();
                    if(Objects.requireNonNull(documentUserRole).exists()) {

                        role = (String) documentUserRole.get(Constant.FIRE_COLUMN_ID);

                        getUserRoleCallback.onSuccess();
                    } else {
                    }
                } else {
                }
            }
        });
    }

    boolean hasUnsignedIcf() {
        return hasUnsignedIcf;
    }

    UserProfileDetail initUserProfileDetail() {

        return new UserProfileDetail(firstName, lastName, displayName, dateOfBirth,
                gender, race, ethnicity, subjectNum, role, patientOfStudy, studyNumber, editionNumber, versionNumber,
                hasUnsignedIcf, siteId, siteDoctor, siteSC, sitePhone, visitPlan);
    }

    void getUserAdverseEvents(final LoginContract.GetUserAdverseEvents getUserAdverseEvents) {

        db.collection(Constant.FIRE_COLLECTION_USERS)
                .document(userId)
                .collection(Constant.FIRE_COLLECTION_ADVERSE_EVENTS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    removeAllAdverseEventsOnLocal();

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        getUserAdverseEvents.onSuccess(Integer.parseInt(document.getId()));

                        if (Integer.parseInt(document.getId()) > 0) {
                            saveUserAdverseEventsToLocalDb(userId,
                                    document.get(Constant.FIRE_AEH_COLUMN_EVENT_NAME).toString(),
                                    document.get(Constant.FIRE_AEH_COLUMN_EVENT_HAPPENED).toString(),
                                    document.get(Constant.FIRE_AEH_COLUMN_EVENT_DURATION).toString());
                        }
                    }
                } else {
                    Log.d(Constant.TAG, "getUserAdverseEvents", task.getException());
                }
            }
        });
    }

    private void saveUserAdverseEventsToLocalDb(String userId, String eventName, String eventHappened, String eventDuration) {
        dbLocal.insertEvent(userId, eventName, eventHappened, eventDuration);
    }

    private void removeAllAdverseEventsOnLocal() {
        dbLocal.removeAll();
    }
}
