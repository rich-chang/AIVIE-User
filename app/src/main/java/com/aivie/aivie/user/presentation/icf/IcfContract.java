package com.aivie.aivie.user.presentation.icf;

public interface IcfContract {

    interface IcfView {

        void loadView();

        void disableSignature();

        void enableSignature();

        void disableBtnConfirm();

        void enableBtnConfirm();

        void updateSignatureImg(String signatureUri);

        void disableCheckAgreement();

        void enableCheckAgreement();

        void setCheckAgreement(boolean checked);
    }

    interface IcfAction {

        void checkAgreement(boolean checked);

        void clickSign();

        void clickConfirm();
    }
}
