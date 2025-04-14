package com.app.mission.mood.ui.user;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.mission.mood.databinding.ActivityDetailMoodBinding;
import com.app.mission.mood.model.MessageModel;
import com.app.mission.mood.model.MissionModel;
import com.app.mission.mood.utils.DataUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class DetailMoodActivity extends AppCompatActivity {

    ActivityDetailMoodBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailMoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initData();
        initListener();

    }

    private void initView() {
        binding.txtTitle.setText(DataUtils.clickMood.getName());
    }

    private void initData() {
        DataUtils.listMessage.clear();
        FirebaseDatabase.getInstance().getReference()
                .child("mood_app")
                .child("message")
                .child(DataUtils.clickMood.getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<MessageModel> listData = new ArrayList<>();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            listData.add(
                                    postSnapshot.getValue(MessageModel.class)
                            );
                        }
                        DataUtils.listMessage.addAll(listData);
                        if (!DataUtils.listMessage.isEmpty()) {
                            Random random = new Random();
                            int index = random.nextInt(DataUtils.listMessage.size());
                            MessageModel randomItem = DataUtils.listMessage.get(index);
                            binding.txtMessage.setText(
                                    randomItem.getContent()
                            );
                        } else {
                            binding.txtMessage.setText(
                                    "Không có lời chúc nào gửi tới bạn!"
                            );
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        DataUtils.listMission.clear();
        FirebaseDatabase.getInstance().getReference()
                .child("mood_app")
                .child("mission")
                .child(DataUtils.clickMood.getId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<MissionModel> listData = new ArrayList<>();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            listData.add(
                                    postSnapshot.getValue(MissionModel.class)
                            );
                        }
                        DataUtils.listMission.addAll(listData);
                        showMission();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private int posMission = -1;

    private void showMission() {
        if (!DataUtils.listMission.isEmpty()) {

            if (posMission == -1) {
                Random random = new Random();
                posMission = random.nextInt(DataUtils.listMission.size());
                MissionModel randomItem = DataUtils.listMission.get(posMission);
                binding.txtMission.setText(
                        randomItem.getContent()
                );
            } else  {
                posMission++;
                if (posMission >= DataUtils.listMission.size()) {
                    posMission = 0;
                }
                MissionModel randomItem = DataUtils.listMission.get(posMission);
                binding.txtMission.setText(
                        randomItem.getContent()
                );
            }
            binding.btnAction.setVisibility(View.VISIBLE);
        } else {
            binding.txtMission.setText(
                    "Không có nhiệm vụ nào cho bạn!"
            );
            binding.btnAction.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        binding.imgBack.setOnClickListener(v -> onBackPressed());
        binding.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMission();
            }
        });
        binding.btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMission();
            }
        });
    }
}