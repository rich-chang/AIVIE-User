package com.aivie.aivie.user.presentation.icf;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.aivie.aivie.user.data.Constant;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

class SignatureRepository {

    private FirebaseAuth mAuth;
    private StorageReference storageRef;
    private StorageReference signatureRef;
    private StorageReference signatureImgRef;

    SignatureRepository() {
        initFireStorage();
    }

    private void initFireStorage() {

        mAuth = FirebaseAuth.getInstance();

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

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(Constant.TAG, "uploadTask.addOnFailureListener");
                e.printStackTrace();
                uploadToFireStorageCallback.onFailure(e.toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                signatureImgRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()) {
                            uploadToFireStorageCallback.onSuccess(task.getResult().toString());
                        }
                    }
                });

            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Log.i(Constant.TAG, "uploadTask.addOnCompleteListener");
                uploadToFireStorageCallback.onComplete();
            }
        });
    }
}
