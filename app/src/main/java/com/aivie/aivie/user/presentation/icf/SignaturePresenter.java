package com.aivie.aivie.user.presentation.icf;

import android.content.Context;
import android.content.Intent;

import com.aivie.aivie.user.data.Constant;
import com.github.gcacace.signaturepad.views.SignaturePad;

class SignaturePresenter implements SignatureContract.signatureAction {

    private SignatureContract.signatureView signatureView;
    private SignatureRepository signatureRepository;

    SignaturePresenter(SignatureContract.signatureView view, SignatureRepository signatureRepository) {
        this.signatureView = view;
        this.signatureRepository = signatureRepository;

        InitActivityView();
    }

    private void InitActivityView() {
        signatureView.loadView();
        signatureView.disableBtnClear();
        signatureView.disableBtnConfirm();
        signatureView.hideProgressDialog();
    }

    @Override
    public void onSigned() {
        signatureView.enableBtnClear();
        signatureView.enableBtnConfirm();
    }

    @Override
    public void clickClear(SignaturePad mSignaturePad) {
        mSignaturePad.clear();

        signatureView.disableBtnClear();
        signatureView.disableBtnConfirm();
    }

    @Override
    public void clickConfirm(SignaturePad mSignaturePad) {

        signatureView.disablePad();
        signatureView.disableBtnClear();
        signatureView.disableBtnConfirm();
        signatureView.showProgressDialog("Uploading... Please wait");


        signatureRepository.SaveImgToFirebase(mSignaturePad, new SignatureContract.uploadToFireStorageCallback() {
            @Override
            public void onSuccess(String downloadUri) {
                goToIcfActivity(downloadUri);
            }

            @Override
            public void onFailure(String result) {
            }

            @Override
            public void onComplete() {
                signatureView.ToastLoginResultMsg("Signature saved.");
                signatureView.enablePad();
                signatureView.enableBtnClear();
                signatureView.enableBtnConfirm();
                signatureView.hideProgressDialog();
            }
        });
    }

    private void goToIcfActivity(String downloadUri) {
        Intent intent = new Intent((Context) signatureView, IcfActivity.class);
        intent.putExtra(Constant.SIGNATURE_URI, downloadUri);
        intent.putExtra(Constant.SIGNATURE_SIGNED, true);
        ((Context) signatureView).startActivity(intent);

        signatureView.finishView();
    }
}
