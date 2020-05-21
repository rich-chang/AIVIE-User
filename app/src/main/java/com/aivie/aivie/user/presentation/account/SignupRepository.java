package com.aivie.aivie.user.presentation.account;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.aivie.aivie.user.data.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
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
            signupCallback.onFailure("Email or password can't be empty.");
            signupCallback.onComplete();
            return;
        }

        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userId = mAuth.getCurrentUser().getUid();
                            signupCallback.onSuccess("Success");
                        } else {
                            signupCallback.onFailure(String.format("Signup failure : %s", task.getException()));
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
        userData.put(Constant.FIRE_COLUMN_DOB, new Timestamp(new Date()));

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
                        if (Constant.DEBUG) Log.d(Constant.TAG, "DocumentSnapshot successfully written!");
                        createTempDataCallback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (Constant.DEBUG) Log.w(Constant.TAG, "Error writing document", e);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        createTempDataCallback.onComplete();
                    }
                });

    }
}
