package com.aivie.aivie.user.presentation.account;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.aivie.aivie.user.data.user.UserProfileDetail;
import com.aivie.aivie.user.presentation.icf.IcfActivity;
import com.google.firebase.firestore.DocumentSnapshot;

public class LoginPresenter implements LoginContract.LoginUserActions {

    private LoginContract.LoginView loginView;
    private LoginRepository loginRepository;
    private UserProfileDetail userProfileDetail;

    LoginPresenter(LoginContract.LoginView loginView, LoginRepository loginRepository) {
        this.loginView = loginView;
        this.loginRepository = loginRepository;
        this.userProfileDetail = null;

        InitActivityView();
    }

    private void InitActivityView() {
        loginView.setContentView();
        loginView.showSpString();
    }

    @Override
    public void clickLogin(String username, String password) {

        loginView.showProgress();
        loginView.disableLoginBtn();
        loginView.disableNeedAccount();

        loginRepository.userLogin((Context) loginView, username, password, new LoginContract.LoginCallback() {
            @Override
            public void onSuccess(String resultMsg) {
                //loginView.ToastLoginResultMsg(resultMsg);
            }

            @Override
            public void onFailure(String resultMsg) {
                //loginView.ToastLoginResultMsg(resultMsg);
            }

            @Override
            public void onComplete() {

                loginRepository.getUserProfile(new LoginContract.GetUserProfileCallback() {
                    @Override
                    public void onSuccess(final DocumentSnapshot documentUser) {

                        final boolean isIcfSigned = loginRepository.isIcfSigned();

                        loginRepository.getUserGender(documentUser, new LoginContract.GetUserGenderCallback() {
                            @Override
                            public void onSuccess() {

                                loginRepository.getUserRace(documentUser, new LoginContract.GetUserRaceCallback() {
                                    @Override
                                    public void onSuccess() {

                                        loginRepository.getUserEthinicity(documentUser, new LoginContract.GetUserEthnicityCallback() {
                                            @Override
                                            public void onSuccess() {

                                                loginRepository.getUserStudy(documentUser, new LoginContract.GetUserStudyCallback() {
                                                    @Override
                                                    public void onSuccess() {

                                                        loginRepository.getUserRole(documentUser, new LoginContract.GetUserRoleCallback() {
                                                            @Override
                                                            public void onSuccess() {

                                                                loginView.hideProgress();
                                                                loginView.enableLoginBtn();
                                                                loginView.enableNeedAccount();

                                                                initUserProfileDetail();

                                                                if (isIcfSigned) {
                                                                    goToUserHome();
                                                                } else {
                                                                    goToSignICF();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });

                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void clickGoToSignup() {
        Intent intent = new Intent((Context) loginView, SignupActivity.class);
        ((Context) loginView).startActivity(intent);
    }

    private void goToSignICF() {
        Intent intent = new Intent((Context) loginView, IcfActivity.class);
        //intent.putExtra("UserID", loginRepository.getUserId());
        ((Context) loginView).startActivity(intent);
    }

    private void goToUserHome() {
        //Intent intent = new Intent((Context) loginView, HomeUserActivity.class);
        //intent.putExtra("UserProfileDetail", (Parcelable) userProfileDetail);
        //((Context) loginView).startActivity(intent);
    }

    private void goToAdmHome() {
        //Intent intent = new Intent((Context) loginView, HomeAdmActivity.class);
        //((Context) loginView).startActivity(intent);
    }

    private void initUserProfileDetail() {
        userProfileDetail = loginRepository.initUserProfileDetail();
    }

}
