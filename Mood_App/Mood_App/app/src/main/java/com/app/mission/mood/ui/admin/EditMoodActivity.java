package com.app.mission.mood.ui.admin;

import android.os.Bundle;
import android.os.Handler;

import com.app.mission.mood.base.BaseActivity;
import com.app.mission.mood.databinding.ActivityEditMoodBinding;
import com.app.mission.mood.dialog.LoadingDialog;
import com.app.mission.mood.model.MessageModel;
import com.app.mission.mood.model.MissionModel;
import com.app.mission.mood.utils.DataUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.FirebaseDatabase;

public class EditMoodActivity extends BaseActivity {

    ActivityEditMoodBinding binding;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditMoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadingDialog = new LoadingDialog(this);

        initView();
        initData();
        initListener();

    }

    private void initView() {
        binding.vpgMain.setUserInputEnabled(false);
        binding.vpgMain.setAdapter(
                new TabMoodAdapter(getSupportFragmentManager(), getLifecycle())
        );

        binding.edtCapacity.setText(DataUtils.clickMood.getName());
    }

    private void initData() {

    }

    private void initListener() {
        binding.imgBack.setOnClickListener(v -> {
            onBackPressed();
        });


        binding.tabLayoutMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.vpgMain.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.imgSave.setOnClickListener(v -> {
            String name = binding.edtCapacity.getText().toString().trim();
            if (name.isEmpty()) {
                showToast("Nhập tên tâm trạng!");
                return;
            }

            loadingDialog.showDialog();

            addMood(name);

            FirebaseDatabase.getInstance().getReference()
                    .child("mood_app")
                    .child("mission")
                    .child(DataUtils.clickMood.getId())
                    .removeValue()
                    .addOnCompleteListener(task -> {
                        addMission();
                    });

            FirebaseDatabase.getInstance().getReference()
                    .child("mood_app")
                    .child("message")
                    .child(DataUtils.clickMood.getId())
                    .removeValue()
                    .addOnCompleteListener(task -> {
                        addMessage();
                    });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showToast("Lưu thành công!");
                    loadingDialog.hideDialog();
                    finish();
                }
            }, 1000L);
        });

    }

    private void addMood(String name) {
        DataUtils.clickMood.setName(name);
        FirebaseDatabase.getInstance().getReference()
                .child("mood_app")
                .child("mood")
                .child(DataUtils.clickMood.getId()).setValue(
                        DataUtils.clickMood
                )
                .addOnCompleteListener(task -> {

                });
    }

    private void addMessage() {
        for (MessageModel item : DataUtils.listMessage) {
            FirebaseDatabase.getInstance().getReference()
                    .child("mood_app")
                    .child("message")
                    .child(DataUtils.clickMood.getId())
                    .child(item.getId())
                    .setValue(item);
        }

    }

    private void addMission() {
        for (MissionModel item : DataUtils.listMission) {
            FirebaseDatabase.getInstance().getReference()
                    .child("mood_app")
                    .child("mission")
                    .child(DataUtils.clickMood.getId())
                    .child(item.getId())
                    .setValue(item);
        }
    }

}