package com.aivie.aivie.user.presentation.main;

public interface MainContract {

    interface MainView {

    }

    interface MainAction {

        void goToLoginView();

        void goToHomeView();
    }
}
