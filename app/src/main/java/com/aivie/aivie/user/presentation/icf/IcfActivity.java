package com.aivie.aivie.user.presentation.icf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.aivie.aivie.user.R;
import com.aivie.aivie.user.data.Constant;
import com.bumptech.glide.Glide;

public class IcfActivity extends AppCompatActivity implements IcfContract.IcfView {

    private IcfPresenter icfPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        icfPresenter = new IcfPresenter(this, new IcfRepository());
        responseToClick();
    }

    @Override
    public void loadView() {
        setContentView(R.layout.activity_icf);
    }

    private void responseToClick() {

        CheckBox checkAgreement;
        checkAgreement = findViewById(R.id.checkBoxAgreement);
        checkAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icfPresenter.checkAgreement(((CheckBox) view).isChecked());
            }
        });

        ImageView imageSignature;
        imageSignature = findViewById(R.id.imageViewSignature);
        imageSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icfPresenter.clickSign();
            }
        });
    }

    @Override
    public void disableSignature() {
        findViewById(R.id.imageViewSignature).setEnabled(false);
    }

    @Override
    public void enableSignature() {
        findViewById(R.id.imageViewSignature).setEnabled(true);
    }

    @Override
    public void disableBtnConfirm() {
        findViewById(R.id.buttonConfirm).setEnabled(false);
    }

    @Override
    public void enableBtnConfirm() {
        findViewById(R.id.buttonConfirm).setEnabled(true);
    }

    @Override
    public void updateSignatureImg(String signatureUri) {
        if (signatureUri != null) {
            Glide.with(this).load(signatureUri).into((ImageView) findViewById(R.id.imageViewSignature));
        } else {
            // set placeholder image
        }
    }

    @Override
    public void disableCheckAgreement() {
        findViewById(R.id.checkBoxAgreement).setEnabled(false);
    }

    @Override
    public void enableCheckAgreement() {
        findViewById(R.id.checkBoxAgreement).setEnabled(true);
    }

    @Override
    public void setCheckAgreement(boolean checked) {
        ((CheckBox) findViewById(R.id.checkBoxAgreement)).setChecked(checked);
    }
}
