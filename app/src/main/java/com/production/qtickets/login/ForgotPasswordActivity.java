package com.production.qtickets.login;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.changepassword.otp.OTPFillResetPasswordActivity;
import com.production.qtickets.model.LoginResponse;
import com.production.qtickets.model.UserINfo;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import com.production.qtickets.model.RegisterModel;
import com.production.qtickets.utils.SessionManager;

public class ForgotPasswordActivity extends AppCompatActivity implements LoginContracter.View  {
    private  AlertDialog alertDialog;
    @BindView(R.id.iv_back_arrow)
    ImageView ivBackArrow;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.username_inputlayout)
    TextInputLayout usernameInputlayout;
    @BindView(R.id.submit)
    Button submit;
    LoginPresenter presenter;
    Intent i;
    SessionManager sessionManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(ForgotPasswordActivity.this);
        setContentView(R.layout.activity_forgot);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        presenter = new LoginPresenter();
        presenter.attachView(this);
        init();
    }

    @OnClick({R.id.iv_back_arrow, R.id.submit})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.iv_back_arrow:
                onBackPressed();
                finish();
                break;
            case R.id.submit:
                final String username = et_username.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    et_username.requestFocus();
                    et_username.setError(getResources().getString(R.string.username_valid));
                } else if (!username.matches(AppConstants.REGEX_EMAIL_ADDRESS)) {
                    et_username.requestFocus();
                    et_username.setError(getResources().getString(R.string.username_valid1));
                }
                else {
                    showPb();
                    presenter.getForgotPasswprd(ForgotPasswordActivity.this, username);
                }
                break;
        }
    }


    @Override
    public void setRegister(RegisterModel registerModels) {

    }

    @Override
    public void setlogin(LoginResponse response) {

    }

    @Override
    public void setForgotPassword(LoginResponse forgotPasswordData) {

        if(forgotPasswordData.message.equals("Success") && forgotPasswordData.isSuccess){
            Intent intent= new Intent(this,OTPFillResetPasswordActivity.class);
            intent.putExtra("emailaddress",forgotPasswordData.data.email);
            intent.putExtra("userID",forgotPasswordData.data.userId);
            startActivity(intent);
            finish();
        }else {
            QTUtils.showDialogbox(this,forgotPasswordData.message);

        }//        if(forgotPasswordData.size()>0){
//            if(forgotPasswordData.get(0).status.equalsIgnoreCase("true")){
//                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//                alertDialogBuilder.setMessage(forgotPasswordData.get(0).errormsg);
//                alertDialogBuilder.setPositiveButton("ok",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface arg0, int arg1) {
//                                i=new Intent(ForgotPasswordActivity.this,LoginActivity.class);
//                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(i);
//                            }
//                        });
//
//                alertDialog = alertDialogBuilder.create();
//                alertDialog.show();
//                alertDialog.setCanceledOnTouchOutside(false);
//            }
//            else {
//                QTUtils.showDialogbox(this,forgotPasswordData.get(0).errormsg);
//            }
//        }

    }

    @Override
    public void setUserDetails(UserINfo userDetails) {

    }

    @Override
    public void init() {
    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(ForgotPasswordActivity.this, true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Object call, Object throwable, Object callback, Object additionalData) {
        if (throwable instanceof SocketTimeoutException) {
            //  showToast("Socket Time out. Please try again.");
            showRetryDialog(call, callback, getResources().getString(R.string.internet));
        } else if (throwable instanceof UnknownHostException) {
            // showToast("No Internet");
            showRetryDialog(call, callback, getResources().getString(R.string.internet));
        }else if(throwable instanceof ConnectException){
            showRetryDialog(call, callback, getResources().getString(R.string.internet));
        }else {
            showToast(getResources().getString(R.string.noresponse));
        }
    }

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {
        showPb();
        QTUtils.showAlertDialogbox(object1, object3, ForgotPasswordActivity.this, message);
    }
    @Override
    public void onDestroy() {
        presenter.detach();
        QTUtils.dismissProgressDialog();
        super.onDestroy();
    }
}
