package com.production.qtickets.changepassword;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.production.qtickets.R;

public class PasswordResetSucessFullyActivity extends AppCompatActivity implements View.OnClickListener {


    Button button_save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordresetsucessfully);
        init();
        liseteners();
    }

    private void liseteners() {
        button_save.setOnClickListener(this);
    }

    private void init() {
        button_save = findViewById(R.id.button_save);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save: {
                finish();
            }
            break;
            case R.id.iv_back: {
                finish();
            }
            break;
        }
    }

}
