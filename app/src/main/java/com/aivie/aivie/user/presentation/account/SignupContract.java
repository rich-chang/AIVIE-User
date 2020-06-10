package com.aivie.aivie.user.presentation.account;

public interface SignupContract {

    interface SignupView {

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

        void enableSignupBtn();

        void disableSignupBtn();

        void enableHaveAccount();

        void disableHaveAccount();
    }

    interface SignupUserActions {

        void clickSignup(String username, String password);

        void getInputUserFullName(String firstName, String lastName);

        void clickGoToLogin();
    }

    interface SignupCallback {

        void onSuccess(String resultMsg);

        void onFailure(String editTextErr, String resultMsg);

        void onComplete();
    }

    interface CreateTempDataCallback {

        void onSuccess(String resultMsg);

        void onFailure(String resultMsg);

        void onComplete();
    }

    interface InitUserAdverseEventsCallback {

        void onSuccess(String resultMsg);

        void onFailure(String resultMsg);

        void onComplete();
    }

    interface InitUserIcfHistoryCallback {

        void onSuccess(String resultMsg);

        void onFailure(String resultMsg);

        void onComplete();
    }

}
