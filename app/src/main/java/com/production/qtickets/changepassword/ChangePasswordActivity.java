package com.production.qtickets.changepassword;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.model.ChangePasswordData;
import com.production.qtickets.model.ChangePwdModel;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import com.production.qtickets.model.RegisterModel;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener,
        ChangePwdContracter.View, TextWatcher {

    EditText et_current_pwd, et_new_pwd, et_confirm_pwd;
    Button btn_save;
    SessionManager sessionManager;
    // private String str_current_pwd, str_new_pwd, str_confirm_pwd;
    ChangePwdPresenter presenter;
    TextInputLayout tl_user, tl_pass, tl_conf_pass;

    // TextView tv_toolbar_title;
    ImageView iv_back_arrow;

    List<RegisterModel> registerModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        //  QTUtils.setStatusBarGradiant(this);
        presenter = new ChangePwdPresenter();
        presenter.attachView(this);
        init();
    }

    public void init() {
        sessionManager = new SessionManager(ChangePasswordActivity.this);
        //     tv_toolbar_title = findViewById(R.id.tv_toolbar_title);
        //     tv_toolbar_title.setText(getResources().getString(R.string.changepwdTitle));
        iv_back_arrow = findViewById(R.id.iv_back_arrow);
        iv_back_arrow.setOnClickListener(this);

        et_current_pwd = (EditText) findViewById(R.id.edt_oldpwd);
        et_current_pwd.addTextChangedListener(this);
        et_new_pwd = (EditText) findViewById(R.id.edt_new_password);
        et_new_pwd.addTextChangedListener(this);
        et_confirm_pwd = (EditText) findViewById(R.id.edt_confirm_password);
        et_confirm_pwd.addTextChangedListener(this);

        tl_user = (TextInputLayout) findViewById(R.id.username_inputlayout);
        tl_pass = (TextInputLayout) findViewById(R.id.txt_input_new_password);
        tl_conf_pass = (TextInputLayout) findViewById(R.id.txt_input_confirm_password);

        btn_save = (Button) findViewById(R.id.button_save);
        btn_save.setOnClickListener(this);

    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(ChangePasswordActivity.this, true);
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
        } else if (throwable instanceof ConnectException) {
            showRetryDialog(call, callback, getResources().getString(R.string.internet));
        } else {
            showToast(getResources().getString(R.string.noresponse));
        }

    }

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {
        showPb();
        QTUtils.showAlertDialogbox(object1, object3, ChangePasswordActivity.this, message);
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        Intent i;
        switch (id) {

            case R.id.iv_back_arrow:
                onBackPressed();
                break;

            case R.id.button_save:
                //get the user id from shared pheferences
                String user_id = sessionManager.getUserId();
                Log.v("user_id", "" + user_id);
                String str_current_pwd = et_current_pwd.getText().toString().trim();
                String str_new_pwd = et_new_pwd.getText().toString().trim();
                String str_confirm_pwd = et_confirm_pwd.getText().toString().trim();
                String ses_password = sessionManager.getPassword();
                Log.v("ses_password", "" + ses_password);

                if (str_current_pwd.length() == 0) {
                  /*  et_current_pwd.requestFocus();
                    et_current_pwd.setError("Enter the current password");*/
                    tl_user.setError(getString(R.string.v_current_pass));

                } else if (str_new_pwd.length() == 0) {
                   /* et_confirm_pwd.requestFocus();
                    et_confirm_pwd.setError("Enter the new password");*/
                    tl_pass.setError(getString(R.string.v_new_pass));

                } else if (str_confirm_pwd.length() == 0) {
                   /* et_confirm_pwd.requestFocus();
                    et_confirm_pwd.setError("Enter the confirm password");*/
                    tl_conf_pass.setError(getString(R.string.v_confir_pass));

                } else if (!str_new_pwd.equals(str_confirm_pwd)) {
                   /* et_confirm_pwd.requestFocus();
                    et_confirm_pwd.setError("Enter the same password");*/
                    tl_conf_pass.setError(getString(R.string.v_same_pass));

                } else {
                    showPb();
                    //validateEditText();
                   /* et_current_pwd.setText("");
                    et_confirm_pwd.setText("");
                    et_new_pwd.setText("");*/
                    presenter.getChangepwd(ChangePasswordActivity.this, user_id, str_current_pwd, str_confirm_pwd);
                }

                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onDestroy() {
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();

    }


    @Override
    public void setChangePwd(ChangePasswordData response) {
        if (response.isSuccess && response.status.equals("OK")) {
           /* if(response.message.equals("Password Changed Successfully")){
                startActivity(new Intent(this,PasswordResetSucessFullyActivity.class));
            }else {
                QTUtils.showDialogbox(this,response.message);
            }*/
            startActivity(new Intent(this, PasswordResetSucessFullyActivity.class));
            finish();
        } else {
            QTUtils.showDialogbox(this, response.message);

        }

//        final List<ChangePwdModel> changePwdModels;
//
//        try {
//            changePwdModels = response;
//
//            if (changePwdModels.get(0).status.equalsIgnoreCase("True")) {
//
//                for (int i = 0; i < changePwdModels.size(); i++) {
//
//                    String msg = changePwdModels.get(0).errormsg;
//                    QTUtils.finishshowDialogbox(ChangePasswordActivity.this, msg);
//                }
//                  startActivity(new Intent(this,PasswordResetSucessFullyActivity.class));
//            } else {
//
//                QTUtils.showDialogbox(this, changePwdModels.get(0).errormsg);
//                //Toast.makeText(ChangePasswordActivity.this, getString(R.string.noresponse), Toast.LENGTH_LONG).show();
//            }


    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        validateEditText(charSequence);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void validateEditText(CharSequence charSequence) {
        tl_conf_pass.setError(null);
        tl_pass.setError(null);
        tl_user.setError(null);
    }


}

