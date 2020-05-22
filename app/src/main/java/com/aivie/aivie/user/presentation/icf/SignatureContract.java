package com.aivie.aivie.user.presentation.icf;

import com.github.gcacace.signaturepad.views.SignaturePad;

public interface SignatureContract {

    interface signatureView {

        void loadView();

        void disableBtnClear();

        void enableBtnClear();

        void disableBtnConfirm();

        void enableBtnConfirm();

        void ToastLoginResultMsg(String msg);

        void disablePad();

        void enablePad();

        void finishView();

        void showProgressDialog(String message);

        void hideProgressDialog();
    }

    interface signatureAction {

        void onSigned();

        void clickClear(SignaturePad mSignaturePad);

        void clickConfirm(SignaturePad mSignaturePad);
    }

    interface uploadToFireStorageCallback {

        void onSuccess(String downloadUri);

        void onFailure(String result);

        void onComplete();
    }

    interface updateSignedFlagCallback {

        void onSuccess();

        void onFailure(String result);

        void onComplete();

    }

}
