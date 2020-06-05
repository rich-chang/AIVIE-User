package com.aivie.aivie.user.presentation.account;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aivie.aivie.user.data.Constant;
import com.aivie.aivie.user.data.user.UserProfileDetail;
import com.aivie.aivie.user.data.user.UserProfileSpImpl;
import com.aivie.aivie.user.presentation.icf.IcfActivity;
import com.aivie.aivie.user.presentation.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

public class LoginPresenter implements LoginContract.LoginUserActions {

    private LoginContract.LoginView loginView;
    private LoginRepository loginRepository;
    //private UserProfileDetail userProfileDetail;

    LoginPresenter(LoginContract.LoginView loginView, LoginRepository loginRepository) {
        this.loginView = loginView;
        this.loginRepository = loginRepository;
        //this.userProfileDetail = null;

        InitActivityView();
    }

    private void InitActivityView() {
        loginView.setContentView();
        loginView.showSpString();
        loginView.hideProgressDialog();
    }

    @Override
    public void checkIfLogin() {

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Log.i(Constant.TAG, "checkIfLogin: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
            goToUserHome();
        }
    }

    @Override
    public void clickLogin(String username, String password) {

        loginView.showProgressDialog("Logging in ... Please wait");
        loginView.disableLoginBtn();
        loginView.disableNeedAccount();

        loginRepository.userLogin((Context) loginView, username, password, new LoginContract.LoginCallback() {
            @Override
            public void onSuccess(String resultMsg) {
                
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

                                                                loginRepository.getUserAdverseEvents(new LoginContract.GetUserAdverseEvents() {
                                                                    @Override
                                                                    public void onSuccess(int lastIndexOfAdverseEvents) {

                                                                        loginView.hideProgressDialog();
                                                                        loginView.enableLoginBtn();
                                                                        loginView.enableNeedAccount();

                                                                        UserProfileDetail userProfileDetail = initUserProfileDetail();
                                                                        saveUserProfileToSP(userProfileDetail);
                                                                        saveLastIndexOfAdverseEventsToSp(lastIndexOfAdverseEvents);

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
            public void onFailure(String resultMsg) {
                loginView.ToastLoginResultMsg(resultMsg);
            }

            @Override
            public void onComplete() {
                //
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
        ((Context) loginView).startActivity(intent);
    }

    private void goToUserHome() {
        Intent intent = new Intent((Context) loginView, MainActivity.class);
        ((Context) loginView).startActivity(intent);
    }

    private UserProfileDetail initUserProfileDetail() {
        return loginRepository.initUserProfileDetail();
    }

    private void saveUserProfileToSP(UserProfileDetail userProfileDetail) {
        UserProfileSpImpl userProfileSplmpl = new UserProfileSpImpl((Context) loginView);
        userProfileSplmpl.saveToSp(userProfileDetail);
    }

    private void saveLastIndexOfAdverseEventsToSp(int lastIndexOfAdverseEvents) {
        UserProfileSpImpl userProfileSplmpl = new UserProfileSpImpl((Context) loginView);
        userProfileSplmpl.saveLastIndexOfAdverseEvents(lastIndexOfAdverseEvents);
    }
}
