package com.aivie.aivie.user.presentation.account;

import com.google.firebase.firestore.DocumentSnapshot;

public interface LoginContract {

    interface LoginView {

        void setContentView();

        void showSpString();

        void ToastLoginResultMsg(String msg);

        void showProgressDialog(String message);

        void hideProgressDialog();

        void enableLoginEmail();

        void disableLoginEmail();

        void setLoginEmailError(String message);

        void enableLoginPassword();

        void disableLoginPassword();

        void setLoginPasswordError(String message);

        void enableLoginBtn();

        void disableLoginBtn();

        void enableNeedAccount();

        void disableNeedAccount();
    }

    interface LoginUserActions {

        void checkIfLogin();

        void clickLogin(String username, String password);

        void clickGoToSignup();
    }

    interface LoginCallback {

        void onSuccess(String resultMsg);

        void onFailure(String editTextErr, String msg);

        void onComplete();
    }

    interface GetUserProfileCallback {

        void onSuccess(DocumentSnapshot documentUser);
    }

    interface GetUserGenderCallback {
        void onSuccess();
    }

    interface GetUserRaceCallback {
        void onSuccess();
    }

    interface GetUserEthnicityCallback {
        void onSuccess();
    }

    interface GetUserStudyCallback {
        void onSuccess();
    }

    interface GetUserRoleCallback {
        void onSuccess();
    }

    interface GetUserAdverseEvents {
        void onSuccess(int lastIndexOfAdverseEvents);

        void onComplete();
    }
}
