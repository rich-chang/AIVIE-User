package com.aivie.aivie.user.presentation.account;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignupPresenter implements SignupContract.SignupUserActions {

    private SignupRepository signupRepository;
    private SignupContract.SignupView signupView;

    SignupPresenter(SignupContract.SignupView signupView, SignupRepository signupRepository) {
        this.signupView = signupView;
        this.signupRepository = signupRepository;

        InitActivityView();
    }

    private void InitActivityView() {
        signupView.setContentView();
        signupView.showSpString();

        unlockUserInterface();
    }

    @Override
    public void getInputUserFullName(String firstName, String lastName) {
        signupRepository.setUserFullName(firstName, lastName);
    }

    @Override
    public void clickSignup(String username, String password) {

        lockUserInterface();

        signupRepository.userSignup((Context) signupView, username, password, new SignupContract.SignupCallback() {
            @Override
            public void onSuccess(String resultMsg) {

                signupRepository.createTempUserDataInFireDB(new SignupContract.CreateTempDataCallback() {
                    @Override
                    public void onSuccess(String resultMsg) {

                        signupRepository.initAdverseEventsCollection(new SignupContract.InitUserAdverseEventsCallback() {
                            @Override
                            public void onSuccess(String resultMsg) {
                                signupView.ToastLoginResultMsg(resultMsg);
                                clearPreviousToken();
                                clickGoToLogin();
                            }

                            @Override
                            public void onFailure(String resultMsg) {
                                signupView.ToastLoginResultMsg(resultMsg);
                            }

                            @Override
                            public void onComplete() {
                                unlockUserInterface();
                            }
                        });
                    }

                    @Override
                    public void onFailure(String resultMsg) {
                        signupView.ToastLoginResultMsg(resultMsg);
                        unlockUserInterface();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
            }

            @Override
            public void onFailure(String editTextErr, String resultMsg) {
                signupView.ToastLoginResultMsg(resultMsg);
                if (editTextErr != null) {
                    signupView.setLoginEmailError(editTextErr);
                }
                unlockUserInterface();
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @Override
    public void clickGoToLogin() {
        Intent intent = new Intent((Context) signupView, LoginActivity.class);
        ((Context) signupView).startActivity(intent);
    }

    private void clearPreviousToken() {
        FirebaseAuth.getInstance().signOut();
    }

    private void lockUserInterface() {
        signupView.showProgressDialog("Signing up ... Please wait");
        signupView.disableLoginEmail();
        signupView.disableLoginPassword();
        signupView.disableSignupBtn();
        signupView.disableHaveAccount();
    }

    private void unlockUserInterface() {
        signupView.hideProgressDialog();
        signupView.enableLoginEmail();
        signupView.enableLoginPassword();
        signupView.enableSignupBtn();
        signupView.enableHaveAccount();
    }
}
