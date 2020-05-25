package com.aivie.aivie.user.presentation.icf;

import com.aivie.aivie.user.data.user.UserProfileDetail;

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

        void clickSign(UserProfileDetail userProfileDetail);

        void clickConfirm(UserProfileDetail userProfileDetail);
    }
}
