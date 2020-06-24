package com.aivie.aivie.user.data.user;

import android.util.Log;

import androidx.annotation.NonNull;

import com.aivie.aivie.user.data.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserIcfManagement {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ArrayList<Map<String, Object>> icfList = new ArrayList<>();

    private static String ICF_ID = "Id";
    private static String ICF_DOC_REF = "DocRef";
    private static String ICF_SIGNED = "Signed";
    private static String ICF_SIGNED_DATE = "SignedDate";
    private static String ICF_SIGNATURE_URL = "SignatureUrl";

    public UserIcfManagement() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void getUserIcfHistory() {

        db.collection(Constant.FIRE_COLLECTION_USERS)
                .document(mAuth.getCurrentUser().getUid())
                .collection(Constant.FIRE_COLLECTION_ICF_HISTORY)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot queryDocumentSnapshot : Objects.requireNonNull(task.getResult())) {

                                // To ignore the initial one (index 0) in databse which is built when sign up
                                if (Integer.parseInt(queryDocumentSnapshot.getId()) > 0) {

                                    String icfID = (String) queryDocumentSnapshot.getData().get(ICF_ID);
                                    boolean icfSigned = (boolean) queryDocumentSnapshot.getData().get(ICF_SIGNED);

                                    SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
                                    Timestamp tsBirthday = (Timestamp) queryDocumentSnapshot.getData().get(ICF_SIGNED_DATE);
                                    Date dateBirthday = tsBirthday.toDate();
                                    String signedDate = sfd.format(dateBirthday);

                                    String signatureUrl = (String) queryDocumentSnapshot.getData().get(ICF_SIGNATURE_URL);

                                    Map<String, Object> icfDetail = addIcfObject(icfID, null, icfSigned, signedDate, signatureUrl);

                                    icfList.add(icfDetail);
                                }
                            }
                            Log.i(Constant.TAG, icfList.toString());
                            Log.i(Constant.TAG, icfList.get(icfList.size()-1).get(ICF_SIGNATURE_URL).toString());
                        } else {
                            Log.d(Constant.TAG, "getUserIcfHistory", task.getException());
                        }
                    }
                });
    }

    public int getIcfCount() {
        return icfList.size();
    }

    public ArrayList<Map<String, Object>> getIcfList() {
        return icfList;
    }

    private Map<String, Object> addIcfObject(String id, String docRef, boolean signed, String signedDate, String signatureUrl ) {

        Map<String, Object> icfDetailObj = new HashMap<>();

        icfDetailObj.put(ICF_ID, id);
        icfDetailObj.put(ICF_DOC_REF, docRef);
        icfDetailObj.put(ICF_SIGNED, signed);
        icfDetailObj.put(ICF_SIGNED_DATE, signedDate);
        icfDetailObj.put(ICF_SIGNATURE_URL, signatureUrl);

        return icfDetailObj;
    }

}
