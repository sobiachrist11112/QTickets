package com.production.qtickets.changepassword.otp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.production.qtickets.R;
import com.production.qtickets.changepassword.PasswordResetSucessFullyActivity;
import com.production.qtickets.login.ForgotPasswordActivity;
import com.production.qtickets.login.LoginPresenter;
import com.production.qtickets.model.ChangePasswordData;
import com.production.qtickets.model.LoginResponse;
import com.production.qtickets.model.ResetPasswordSucessFully;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;

public class OTPFillResetPasswordActivity extends AppCompatActivity implements View.OnClickListener, OTPContracted.View {

    EditText et_enterotp;
    EditText edt_new_password;
    EditText edt_confirm_password;
    Button button_save;
    TextView tv_clicktoresend;
    String email = "";
    Integer userID = 0;
    TextView tv_resetpasswordemail;
    OTPPresenter otpPresenter;
    private ImageView iv_back_arrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpfill);
        initview();
        listeners();
    }

    private void listeners() {
        button_save.setOnClickListener(this);
        tv_clicktoresend.setOnClickListener(this);
    }

    private void initview() {
        otpPresenter = new OTPPresenter();
        otpPresenter.attachView(this);
        if (getIntent() != null) {
            email = getIntent().getStringExtra("emailaddress");
            userID = getIntent().getIntExtra("userID", 0);
        }
        iv_back_arrow = findViewById(R.id.iv_back_arrow);
        et_enterotp = findViewById(R.id.et_enterotp);
        tv_clicktoresend = findViewById(R.id.tv_clicktoresend);
        edt_new_password = findViewById(R.id.edt_new_password);
        tv_resetpasswordemail = findViewById(R.id.tv_resetpasswordemail);
        edt_confirm_password = findViewById(R.id.edt_confirm_password);
        button_save = findViewById(R.id.button_save);
        tv_resetpasswordemail.setText(getResources().getString(R.string.passwordsend) + "\n" + email);
        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(OTPFillResetPasswordActivity.this, ForgotPasswordActivity.class));
                finish();
            }

        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save: {
                if (TextUtils.isEmpty(et_enterotp.getText().toString())) {
                    et_enterotp.requestFocus();
                    et_enterotp.setError(getResources().getString(R.string.enterotp));
                    return;
                }

                if (TextUtils.isEmpty(edt_new_password.getText().toString())) {
                    edt_new_password.requestFocus();
                    edt_new_password.setError(getResources().getString(R.string.enternewpassword));
                    return;
                }
                if (TextUtils.isEmpty(edt_confirm_password.getText().toString())) {
                    edt_confirm_password.requestFocus();
                    edt_confirm_password.setError(getResources().getString(R.string.enternconfirmpassword));
                    return;
                }

                if (!edt_new_password.getText().toString().equals(edt_confirm_password.getText().toString())) {
                    edt_new_password.requestFocus();
                    edt_new_password.setError(getResources().getString(R.string.doesnotmatch));
                    return;

                }
                showPb();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("userId", userID);
                    jsonObject.put("email", email);
                    jsonObject.put("password", edt_new_password.getText().toString());
                    jsonObject.put("confirmPassword", edt_confirm_password.getText().toString());
                    jsonObject.put("otp", Integer.parseInt(et_enterotp.getText().toString()));
                    jsonObject.put("statusFlag", 0);
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
                    otpPresenter.changedForgotPassword(body);
                    et_enterotp.setText("");
                    edt_confirm_password.setText("");
                    edt_new_password.setText("");
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }

            }
            break;

            case R.id.tv_clicktoresend: {
                if (email.equals("")) {
                    QTUtils.showDialogbox(this, "Email does'nt exit");
                } else {
                    showPb();
                    otpPresenter.getForgotPasswprd(this, email);
                }
            }
            break;
        }
    }

    @Override
    public void setChangePwd(ResetPasswordSucessFully changePwdModels) {
        QTUtils.showProgressDialog(this, false);
        if (changePwdModels.isSuccess && changePwdModels.status.equals("OK")) {
            if (changePwdModels.message.equals("Password Updated Successfully")) {

//              startActivity(new Intent(this, PasswordResetSucessFullyActivity.class));

                //7oct as discussed navigate it to the login screen...

                finish();

            } else {
                QTUtils.showDialogbox(this, changePwdModels.message);

            }
        } else {
            QTUtils.showDialogbox(this, changePwdModels.message);

        }
    }

    @Override
    public void setForgotPassword(LoginResponse forgotPasswordData) {
        dismissPb();
        if (forgotPasswordData.message.equals("Success") && forgotPasswordData.isSuccess) {
            QTUtils.showDialogbox(this, "OTP send Sucessfully in the registered mail.");
        } else {
            QTUtils.showDialogbox(this, forgotPasswordData.message);
        }
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
        QTUtils.showProgressDialog(OTPFillResetPasswordActivity.this, true);
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void onFailure(Object call, Object throwable, Object callback, Object additionalData) {

    }

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {

    }
}
