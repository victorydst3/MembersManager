package com.asiantech.membersmanager.activity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.asiantech.membersmanager.MainActivity_;
import com.asiantech.membersmanager.R;
import com.asiantech.membersmanager.dialog.DialogForgotFragment;
import com.asiantech.membersmanager.dialog.DialogForgotFragment_;
import com.asiantech.membersmanager.utils.ProgressGenerator;
import com.asiantech.membersmanager.utils.SoftKeyboardStateWatcher;
import com.dd.processbutton.iml.ActionProcessButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FocusChange;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Copyright © 2015 AsianTech inc.
 * Created by VinhHlb on 10/5/15.
 */
@EActivity(R.layout.login_activity)
public class LoginActivity extends AppCompatActivity implements ProgressGenerator.OnCompleteListener {
    private Handler mHandler;
    private Handler mHandlerShowLogo;
    private Runnable mRunable;
    @AnimationRes(R.anim.translate_left)
    Animation mAnimLeft;

    @AnimationRes(R.anim.translate_right)
    Animation mAnimRight;

    @AnimationRes(R.anim.translate_down)
    Animation mAnimDown;

    @AnimationRes(R.anim.translate_up)
    Animation mAnimUp;

    @AnimationRes(R.anim.translate_logo)
    Animation mAnimLogo;

    @ViewById(R.id.imgAsian)
    ImageView mImgAsian;

    @ViewById(R.id.imgTech)
    ImageView mImgTech;

    @ViewById(R.id.llImgLogo)
    LinearLayout mLlImgLogo;

    @ViewById(R.id.btnForgot)
    Button mBtnForgot;

    @ViewById(R.id.etEmailSignIn)
    EditText mEdtEmailSignIn;

    @ViewById(R.id.etPasswordSignIn)
    EditText mEdtPasswordSignIn;

    @ViewById(R.id.btnSignIn)
    ActionProcessButton mBtnSignIn;

    @ViewById(R.id.llEmail)
    LinearLayout mLlEmail;

    @ViewById(R.id.llPassword)
    LinearLayout mLlPassword;

    @ViewById(R.id.rlSignIn)
    RelativeLayout mRlSignIn;

    @ViewById(R.id.pbLoadingSignIn)
    ProgressBar mPbLoadingSignIn;

    @AfterViews
    public void afterView() {
        setDelayStartAnim();
        setListener();
    }

    private void setDelayStartAnim() {
        mHandlerShowLogo = new Handler();
        mRunable = new Runnable() {
            @Override
            public void run() {
                mLlImgLogo.setVisibility(View.VISIBLE);
                startAnimation();
            }
        };
        mHandlerShowLogo.postDelayed(mRunable, 1000);
    }

    private void setListener() {
        SoftKeyboardStateWatcher softKeyboardStateWatcher
                = new SoftKeyboardStateWatcher(findViewById(R.id.login_activity));
        softKeyboardStateWatcher.addSoftKeyboardStateListener(
                new SoftKeyboardStateWatcher.SoftKeyboardStateListener() {
                    @Override
                    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                        setMarginWhenShow(true);
                    }

                    @Override
                    public void onSoftKeyboardClosed() {
                        setMarginWhenShow(false);
                    }
                });
    }

    private void setMarginWhenShow(boolean isShow) {
        int paddingPixel = 100;
        int paddingPixelBtn = 20;
        float density = LoginActivity.this.getResources().getDisplayMetrics().density;
        int paddingDp = (int) (paddingPixel * density);
        int paddingDpBtn = (int) (paddingPixelBtn * density);
        if (isShow) {
            mImgTech.setPadding(0, paddingDp, 0, 0);
            mBtnForgot.setPadding(0, paddingDpBtn, 0, 0);

        } else {
            mImgTech.setPadding(0, 0, 0, paddingDp);
            mBtnForgot.setPadding(0, 0, 0, paddingDpBtn);
        }
    }

    private void startAnimation() {
        if (mHandlerShowLogo != null && mRunable != null) {
            mHandlerShowLogo.removeCallbacks(mRunable);
        }
        ColorStateList colorStateList = ContextCompat
                .getColorStateList(LoginActivity.this, R.color.button_signup_forgot);
        mBtnForgot.setTextColor(colorStateList);
        mImgAsian.startAnimation(mAnimLeft);
        mImgTech.startAnimation(mAnimRight);
        mHandler = new Handler(callback);
        // Send handler message
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                Bundle send = new Bundle();
                send.putInt("message", 1);
                msg.setData(send);
                mHandler.sendMessage(msg);
            }
        }, 1000);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                Bundle send = new Bundle();
                send.putInt("message", 2);
                msg.setData(send);
                mHandler.sendMessage(msg);
            }
        }, 1000);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                Bundle send = new Bundle();
                send.putInt("message", 3);
                msg.setData(send);
                mHandler.sendMessage(msg);
            }
        }, 1500);
    }

    @FocusChange({R.id.etEmailSignIn})
    void focusChangedEdtEmail(boolean hasFocus) {
        if (hasFocus) {
            mLlEmail.setBackground(ContextCompat
                    .getDrawable(LoginActivity.this, R.drawable.bg_edit_text_focus));
        } else {
            mLlEmail.setBackground(ContextCompat
                    .getDrawable(LoginActivity.this, R.drawable.bg_edit_text_normal));
        }
    }

    @FocusChange({R.id.etPasswordSignIn})
    void focusChangedEdtPass(boolean hasFocus) {
        if (hasFocus) {
            mLlPassword.setBackground(ContextCompat
                    .getDrawable(LoginActivity.this, R.drawable.bg_edit_text_focus));
        } else {
            mLlPassword.setBackground(ContextCompat
                    .getDrawable(LoginActivity.this, R.drawable.bg_edit_text_normal));
        }
    }

    @Click(R.id.btnSignIn)
    void clickSignIn() {
        ProgressGenerator mProgressGenerator = new ProgressGenerator(this, mEdtEmailSignIn.getText().toString().trim());
        mProgressGenerator.start(mBtnSignIn);
        mBtnSignIn.setEnabled(false);
        mEdtEmailSignIn.setEnabled(false);
        mEdtPasswordSignIn.setEnabled(false);
    }

    @Click(R.id.btnForgot)
    void clickForgot() {
        DialogForgotFragment dialogRegister = new DialogForgotFragment_();
        FragmentManager fmForgot = getSupportFragmentManager();
        FragmentTransaction ftForgot = fmForgot.beginTransaction();
        dialogRegister.show(ftForgot, "Forgot Dialog");
    }

    // Init handler message
    private final Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle receive = msg.getData();
            int initMsg = receive.getInt("message");
            switch (initMsg) {
                case 1:
                    mLlImgLogo.startAnimation(mAnimLogo);
                    break;
                case 2:
                    break;
                case 3:
                    mLlEmail.setVisibility(View.VISIBLE);
                    mLlEmail.startAnimation(mAnimUp);
                    mLlPassword.setVisibility(View.VISIBLE);
                    mLlPassword.setAnimation(mAnimUp);
                    mRlSignIn.setVisibility(View.VISIBLE);
                    mRlSignIn.startAnimation(mAnimUp);
                    mBtnForgot.setVisibility(View.VISIBLE);
                    mBtnForgot.startAnimation(mAnimUp);
                    break;
            }
            return false;
        }
    };

    @Override
    public void onComplete() {
        MainActivity_.intent(LoginActivity.this).start();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        finish();
    }

    @Override
    public void onFail() {
        mBtnSignIn.setEnabled(true);
        mEdtEmailSignIn.setEnabled(true);
        mEdtPasswordSignIn.setEnabled(true);
        Toast.makeText(LoginActivity.this, "Id or Password not correct! "
                , Toast.LENGTH_SHORT).show();
    }
}
