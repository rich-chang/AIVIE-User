package com.aivie.aivie.user.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.aivie.aivie.user.BuildConfig;
import com.aivie.aivie.user.data.Constant;
import com.aivie.aivie.user.presentation.account.LoginActivity;

public class MainPresenter implements MainContract.MainAction {

    private MainContract.MainView mainView;

    MainPresenter(MainContract.MainView view) {
        this.mainView = view;
    }

    @Override
    public void saveVersionToSp() {
        SharedPreferences sp = ((Context) mainView).getSharedPreferences(Constant.SP_APP_GENERAL, Context.MODE_PRIVATE);
        sp.edit().putString(Constant.APP_VERSION, String.valueOf(BuildConfig.VERSION_CODE)).apply();
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
