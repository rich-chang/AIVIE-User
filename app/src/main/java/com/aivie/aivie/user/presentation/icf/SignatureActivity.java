package com.aivie.aivie.user.presentation.icf;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aivie.aivie.user.R;
import com.aivie.aivie.user.data.user.UserProfileDetail;
import com.aivie.aivie.user.presentation.utils.ProgressDialogUtil;
import com.github.gcacace.signaturepad.views.SignaturePad;

public class SignatureActivity extends AppCompatActivity implements SignatureContract.signatureView {

    private SignaturePresenter signaturePresenter;
    private SignaturePad mSignaturePad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signaturePresenter = new SignaturePresenter(this, new SignatureRepository());
        responseToSignature();
    }

    private void responseToSignature() {
        mSignaturePad = findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
            }

            @Override
            public void onSigned() {
                signaturePresenter.onSigned();
            }

            @Override
            public void onClear() {
            }
        });
    }

    public void clickBtnClear(View view) {
        signaturePresenter.clickClear(mSignaturePad);
    }

    public void clickBtnConfirm(View view) {
        signaturePresenter.clickConfirm(mSignaturePad);
    }

    @Override
    public void ToastLoginResultMsg(String msg) {
        //Log.d(Constant.TAG, "ToastLoginResultMsg: "+msg);
        Toast.makeText(SignatureActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadView() {
        setContentView(R.layout.activity_signature);
    }

    @Override
    public void disableBtnClear() {
        findViewById(R.id.clear_button).setEnabled(false);
    }

    @Override
    public void enableBtnClear() {
        findViewById(R.id.clear_button).setEnabled(true);
    }

    @Override
    public void disableBtnConfirm() {
        findViewById(R.id.confirm_signed_button).setEnabled(false);
    }

    @Override
    public void enableBtnConfirm() {
        findViewById(R.id.confirm_signed_button).setEnabled(true);
    }

    @Override
    public void disablePad() {
        mSignaturePad.setEnabled(false);
    }

    @Override
    public void enablePad() {
        mSignaturePad.setEnabled(true);
    }

    @Override
    public void finishView() {
        finish();
    }

    @Override
    public void showProgressDialog(String message) {
        ProgressDialogUtil.showProgressDialog(this, message);
    }

    @Override
    public void hideProgressDialog() {
        ProgressDialogUtil.dismiss();
    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true); // disable going back to the MainActivity
        enableBtnClear();
        enableBtnConfirm();
        hideProgressDialog();
    }
}
