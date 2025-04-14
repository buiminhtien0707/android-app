package com.app.mission.mood.ui.login;

import android.content.Intent;
import android.os.Bundle;

import com.app.mission.mood.base.BaseActivity;
import com.app.mission.mood.databinding.ActivityLoginBinding;
import com.app.mission.mood.ui.admin.AdminActivity;

import java.util.Objects;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
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
        binding.btnSignIn.setOnClickListener(v -> {
            String username = binding.edtUsername.getText().toString().trim();
            String password = binding.edtPassword.getText().toString().trim();

            if (validateForm(username, password)) {
                checkLogin(username, password);
            }
        });
    }

    private void checkLogin(String username, String password) {
        if (Objects.equals(username, "admin")
                && Objects.equals(password, "admin")) {
            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
            finish();
        } else {
            showToast("Thông tin không chính xác!");
        }
    }

    private boolean validateForm(String username, String password) {
        if (username.isEmpty()) {
            showToast("Nhập tài khoản!");
            return false;
        }
        if (password.isEmpty()) {
            showToast("Nhập mật khẩu!");
            return false;
        }
        return true;
    }

}