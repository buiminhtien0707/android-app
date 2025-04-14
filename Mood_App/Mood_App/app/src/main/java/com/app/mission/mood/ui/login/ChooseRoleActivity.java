package com.app.mission.mood.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.mission.mood.base.BaseActivity;
import com.app.mission.mood.databinding.ActivityChooseRoleBinding;
import com.app.mission.mood.ui.user.MainActivity;

public class ChooseRoleActivity extends BaseActivity {

    ActivityChooseRoleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseRoleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initData();
        initListener();

    }

    private void initView() {

    }

    private void initData() {

    }

    private void initListener() {
        binding.btnAdmin.setOnClickListener(v -> {
            startActivity(new Intent(ChooseRoleActivity.this, LoginActivity.class));
        });

        binding.btnUser.setOnClickListener(v -> {
            startActivity(new Intent(ChooseRoleActivity.this, MainActivity.class));
            finish();
        });
    }

    private boolean clickBack = false;

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (clickBack) {
            finish();
        } else {
            clickBack = true;
            showToast("Click back again to back!");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    clickBack = false;
                }
            }, 1000L);
        }
    }
}