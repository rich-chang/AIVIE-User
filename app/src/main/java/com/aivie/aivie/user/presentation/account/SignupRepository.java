package com.aivie.aivie.user.presentation.account;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.aivie.aivie.user.data.Constant;
import com.aivie.aivie.user.presentation.icf.SignatureContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

class SignupRepository {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userId;
    private String lastName;
    private String firstName;

    SignupRepository() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = "";
    }

    void setUserFullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    void userSignup(Context context, String username, String password, final SignupContract.SignupCallback signupCallback) {

        if (username.equals("") || password.equals("")) {
            signupCallback.onFailure(null, "Email or password can't be empty.");
            signupCallback.onComplete();
            return;
        }

        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userId = mAuth.getCurrentUser().getUid();
                            signupCallback.onSuccess("Create account successfully.");
                        } else {

                            try {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                switch (errorCode) {
                                    case "ERROR_INVALID_EMAIL":
                                        signupCallback.onFailure("The email address is badly formatted.", "The email address is badly formatted.\n\nPlease try again.");
                                        break;
                                    default:
                                        signupCallback.onFailure(null, task.getException().toString() + "\n\nPlease try again.");
                                        break;
                                }
                                Log.i(Constant.TAG, String.format("signInWithEmail failure (%s) %s", errorCode, task.getException()));

                            } catch (Exception e) {
                                e.printStackTrace();

                                if (e.toString().contains("com.google.firebase.FirebaseNetworkException")) {
                                    signupCallback.onFailure(null, "No available internet connection.\nPlease try again.");
                                } else {
                                    signupCallback.onFailure(null, e.toString());
                                }
                            }
                        }
                        signupCallback.onComplete();
                    }
                });
    }

    void createTempUserDataInFireDB(final SignupContract.CreateTempDataCallback createTempDataCallback) {

        Map<String, Object> userData = new HashMap<>();

        userData.put(Constant.FIRE_COLUMN_DISPLAYNAME, lastName);
        userData.put(Constant.FIRE_COLUMN_FIRSTNAME, firstName);
        userData.put(Constant.FIRE_COLUMN_LASTNAME, lastName);
        userData.put(Constant.FIRE_COLUMN_DOB, new Timestamp(new Date().getTime()));

        userData.put(Constant.FIRE_COLUMN_GENDER, db.collection(Constant.FIRE_COLLECTION_GENDER).document("UNKNOWN"));
        userData.put(Constant.FIRE_COLUMN_RACE, db.collection(Constant.FIRE_COLLECTION_RACE).document("O"));
        userData.put(Constant.FIRE_COLUMN_ETHICITY, db.collection(Constant.FIRE_COLLECTION_ETHNICITY).document("UNKNOWN"));

        userData.put(Constant.FIRE_COLUMN_SUBJECTNUM, "UNKNOWN");
        userData.put(Constant.FIRE_COLUMN_ROLE, db.collection(Constant.FIRE_COLLECTION_ROLES).document("PT"));
        userData.put(Constant.FIRE_COLUMN_PATIENT_OF_STUDY, db.collection(Constant.FIRE_COLLECTION_STUDIES).document("000001"));
        userData.put(Constant.FIRE_COLUMN_EICF, db.collection(Constant.FIRE_COLLECTION_ICF).document("0001"));
        userData.put(Constant.FIRE_COLUMN_EICF_SIGNED, false);

        userData.put(Constant.FIRE_COLUMN_SITE_ID, "S001");
        userData.put(Constant.FIRE_COLUMN_SITE_DOCTOR, "Steven Jackson");
        userData.put(Constant.FIRE_COLUMN_SITE_SC, "Kelly");
        userData.put(Constant.FIRE_COLUMN_SITE_PHONE, "+886-2-12345678");

        db.collection(Constant.FIRE_COLLECTION_USERS).document(userId).set(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        createTempDataCallback.onSuccess("Create account successfully.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        createTempDataCallback.onFailure(e.toString());
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        createTempDataCallback.onComplete();
                    }
                });
    }

    void initAdverseEventsCollection(final SignupContract.InitUserAdverseEventsCallback initUserAdverseEventsCallback) {

        Map<String, Object> userAdverseEventsData = new HashMap<>();

        userAdverseEventsData.put(Constant.FIRE_AEH_COLUMN_ID, 0);
        userAdverseEventsData.put(Constant.FIRE_AEH_COLUMN_USER_ID, userId);
        userAdverseEventsData.put(Constant.FIRE_AEH_COLUMN_EVENT_NAME, "");
        userAdverseEventsData.put(Constant.FIRE_AEH_COLUMN_EVENT_HAPPENED, "");
        userAdverseEventsData.put(Constant.FIRE_AEH_COLUMN_EVENT_DURATION, "");
        userAdverseEventsData.put(Constant.FIRE_AEH_COLUMN_EVENT_REPORTED, "");

        db.collection(Constant.FIRE_COLLECTION_USERS).document(userId)
                .collection(Constant.FIRE_COLLECTION_ADVERSE_EVENTS).document("0")
                .set(userAdverseEventsData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        initUserAdverseEventsCallback.onSuccess("Create account successfully.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        initUserAdverseEventsCallback.onFailure(e.toString());
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (Constant.DEBUG) Log.d(Constant.TAG, "initAdverseEventsCollection: complete");
                        initUserAdverseEventsCallback.onComplete();
                    }
                });
    }

    void initIcfHistoryCollection(final SignupContract.InitUserIcfHistoryCallback initUserIcfHistoryCallback) throws ParseException {

        Map<String, Object> userAdverseEventsData = new HashMap<>();

        userAdverseEventsData.put(Constant.FIRE_COLUMN_ID, "0000");
        userAdverseEventsData.put(Constant.FIRE_COLUMN_DOC_REFERENCE, db.collection(Constant.FIRE_COLLECTION_ICF).document("0001"));
        userAdverseEventsData.put(Constant.FIRE_COLUMN_SIGNATURE_URL, "");
        userAdverseEventsData.put(Constant.FIRE_COLUMN_SIGNED, false);

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_SIMPLE, Locale.US);
        Date date = (Date)sdf.parse("1990-01-01");
        userAdverseEventsData.put(Constant.FIRE_COLUMN_SIGNED_DATE, new Timestamp(date.getTime()));

        db.collection(Constant.FIRE_COLLECTION_USERS).document(userId)
                .collection(Constant.FIRE_COLLECTION_ICF_HISTORY).document("0")
                .set(userAdverseEventsData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        initUserIcfHistoryCallback.onSuccess("Create account successfully.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        initUserIcfHistoryCallback.onFailure(e.toString());
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (Constant.DEBUG) Log.d(Constant.TAG, "initAdverseEventsCollection: complete");
                        initUserIcfHistoryCallback.onComplete();
                    }
                });
    }
}
