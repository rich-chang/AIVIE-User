package com.aivie.aivie.user.presentation.icf;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.aivie.aivie.user.data.Constant;
import com.aivie.aivie.user.presentation.account.SignupContract;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class SignatureRepository {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private StorageReference storageRef;
    private StorageReference signatureRef;
    private StorageReference signatureImgRef;

    SignatureRepository() {
        initFireStorage();
    }

    private void initFireStorage() {

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String filename;
        if (mAuth.getCurrentUser() == null) {
            filename = "Signature.jpg";
        } else {
            filename = "Signature_" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() + ".jpg";
        }

        // Create a storage reference from our app
        storageRef = FirebaseStorage.getInstance().getReference();

        // Create a reference to "mountains.jpg"
        signatureRef = storageRef.child(filename);

        // Create a reference to 'images/mountains.jpg'
        signatureImgRef = storageRef.child("ICF_Signature/"+filename);
    }

    void SaveImgToFirebase(SignaturePad mSignaturePad, final SignatureContract.uploadToFireStorageCallback uploadToFireStorageCallback) {

        // Upload from data in memory //

        Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        signatureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        final UploadTask uploadTask = signatureImgRef.putBytes(data);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                signatureImgRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()) {
                            uploadToFireStorageCallback.onSuccess(task.getResult().toString());
                        } else {
                            uploadToFireStorageCallback.onFailure(task.getException().toString());
                        }
                        uploadToFireStorageCallback.onComplete();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                uploadToFireStorageCallback.onFailure(e.toString());
            }
        });
    }

    void updateIcfFlagInUserProfile(final boolean hasUnsignedIcf, final SignatureContract.updateSignedFlagCallback updateSignedFlagCallback) {

        final DocumentReference docRefSignedICF = db.collection(Constant.FIRE_COLLECTION_USERS).document(mAuth.getCurrentUser().getUid());

        db.runTransaction(new Transaction.Function<Void>() {

            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {

                transaction.update(docRefSignedICF, Constant.FIRE_COLUMN_HAS_UNSIGNED_ICF, hasUnsignedIcf);
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
                updateSignedFlagCallback.onSuccess();
            }
        })
        .addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                updateSignedFlagCallback.onFailure(e.toString());
            }
        });
    }

    void updateIcfHistory (String signatureUrl, String nextUnsignedIcf, final SignatureContract.InitUserIcfHistoryCallback initUserIcfHistoryCallback) {

        Map<String, Object> userAdverseEventsData = new HashMap<>();

        Log.i("richc", "nextUnsignedIcf"+nextUnsignedIcf);
        userAdverseEventsData.put(Constant.FIRE_COLUMN_ID, nextUnsignedIcf);
        userAdverseEventsData.put(Constant.FIRE_COLUMN_DOC_REFERENCE, db.collection(Constant.FIRE_COLLECTION_ICF).document(nextUnsignedIcf));
        userAdverseEventsData.put(Constant.FIRE_COLUMN_SIGNATURE_URL, signatureUrl);
        userAdverseEventsData.put(Constant.FIRE_COLUMN_SIGNED, true);
        userAdverseEventsData.put(Constant.FIRE_COLUMN_SIGNED_DATE, new Timestamp(new Date().getTime()));

        db.collection(Constant.FIRE_COLLECTION_USERS).document(mAuth.getCurrentUser().getUid())
                .collection(Constant.FIRE_COLLECTION_ICF_HISTORY).document(nextUnsignedIcf)
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
