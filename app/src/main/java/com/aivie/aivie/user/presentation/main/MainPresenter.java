package com.aivie.aivie.user.presentation.main;

import android.content.Context;
import android.content.Intent;

import com.aivie.aivie.user.presentation.account.LoginActivity;

public class MainPresenter implements MainContract.MainAction {

    private MainContract.MainView mainView;

    MainPresenter(MainContract.MainView view) {
        this.mainView = view;
    }

    @Override
    public void goToLoginView() {
        Intent intent = new Intent((Context) mainView, LoginActivity.class);
        ((Context) mainView).startActivity(intent);
    }

    @Override
    public void goToHomeView() {
    }
}
