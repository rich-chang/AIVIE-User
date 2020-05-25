package com.aivie.aivie.user.presentation.icf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import com.aivie.aivie.user.data.Constant;
import com.aivie.aivie.user.data.user.UserProfileDetail;
import com.aivie.aivie.user.presentation.account.LoginActivity;
import com.aivie.aivie.user.presentation.main.MainActivity;

public class IcfPresenter implements IcfContract.IcfAction {

    private IcfContract.IcfView icfView;
    private IcfRepository icfRepository;
    private String signatureUri = null;
    private boolean isSigned = false;

    IcfPresenter(IcfContract.IcfView icfView, IcfRepository icfRepository) {
        this.icfView = icfView;
        this.icfRepository = icfRepository;

        InitActivityView();
    }

    private void InitActivityView() {
        icfView.loadView();

        getIntentData();
        icfView.updateSignatureImg(signatureUri);

        icfView.disableSignature();
        if (isSigned) {
            icfView.disableCheckAgreement();
            icfView.setCheckAgreement(true);
            icfView.enableBtnConfirm();
        } else {
            icfView.enableCheckAgreement();
            icfView.setCheckAgreement(false);
            icfView.disableBtnConfirm();
        }
    }

    private void getIntentData() {
        Intent intent = ((Activity) icfView).getIntent();

        signatureUri = intent.getStringExtra(Constant.SIGNATURE_URI);
        isSigned = intent.getBooleanExtra(Constant.SIGNATURE_SIGNED, false);

        if (signatureUri != null) {
            Log.i(Constant.TAG, "signatureUri: " + signatureUri);
        }
    }

    @Override
    public void checkAgreement(boolean checked) {
        if (checked) {
            icfView.enableSignature();
        } else {
            icfView.disableSignature();
        }
    }

    @Override
    public void clickSign(UserProfileDetail userProfileDetail) {
        Intent intent = new Intent((Context) icfView, SignatureActivity.class);
        if (userProfileDetail != null) { intent.putExtra(Constant.USER_PROFILE_DETAIL, (Parcelable) userProfileDetail);}
        ((Context) icfView).startActivity(intent);
    }

    @Override
    public void clickConfirm(UserProfileDetail userProfileDetail) {
        Intent intent = new Intent((Context) icfView, MainActivity.class);
        if (userProfileDetail != null) { intent.putExtra(Constant.USER_PROFILE_DETAIL, (Parcelable) userProfileDetail);}
        ((Context) icfView).startActivity(intent);
    }
}
