package com.app.mission.mood.ui.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.mission.mood.R;
import com.app.mission.mood.base.BaseActivity;
import com.app.mission.mood.click.ClickMood;
import com.app.mission.mood.databinding.ActivityAdminBinding;
import com.app.mission.mood.databinding.ActivityMainBinding;
import com.app.mission.mood.dialog.LogoutDialog;
import com.app.mission.mood.model.MoodModel;
import com.app.mission.mood.ui.adapter.MoodAdapter;
import com.app.mission.mood.ui.admin.AdminActivity;
import com.app.mission.mood.ui.admin.EditMoodActivity;
import com.app.mission.mood.ui.login.ChooseRoleActivity;
import com.app.mission.mood.utils.DataUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements ClickMood {

    ActivityMainBinding binding;
    MoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new MoodAdapter(this, new ArrayList<>(), this);

        initView();
        initData();
        initListener();

    }

    private void initView() {
        binding.rcyData.setAdapter(adapter);
    }

    private void initData() {
        FirebaseDatabase.getInstance().getReference()
                .child("mood_app")
                .child("mood")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<MoodModel> listData = new ArrayList<MoodModel>();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            listData.add(
                                    postSnapshot.getValue(MoodModel.class)
                            );
                        }
                        DataUtils.listMood.clear();
                        DataUtils.listMood.addAll(listData);
                        filterSearch();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void initListener() {


        binding.imgLogout.setOnClickListener(v -> {
            new LogoutDialog(MainActivity.this).showDialog(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, ChooseRoleActivity.class));
                    finish();
                }
            });
        });

        binding.imgClear.setOnClickListener(v -> {
            binding.edtTimKiem.setText("");
        });

        binding.edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void clickMood(MoodModel item) {
        Intent intent = new Intent(MainActivity.this, DetailMoodActivity.class);
        DataUtils.clickMood = item;
        startActivity(intent);
    }

    @Override
    public void clickDelete(MoodModel item) {

    }


    private void filterSearch() {
        String value = binding.edtTimKiem.getText().toString();
        if (value.isEmpty()) {
            adapter.setListData(DataUtils.listMood);
        } else {
            ArrayList<MoodModel> listData = new ArrayList<>();
            for (MoodModel item : DataUtils.listMood) {
                if (DataUtils.deAccent(item.getName()).toLowerCase()
                        .contains(DataUtils.deAccent(value).toLowerCase())) {
                    listData.add(item);
                }
            }
            adapter.setListData(listData);
        }
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