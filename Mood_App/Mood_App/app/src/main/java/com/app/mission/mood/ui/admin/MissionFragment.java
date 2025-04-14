package com.app.mission.mood.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.mission.mood.click.ClickMission;
import com.app.mission.mood.databinding.FragmentMissionBinding;
import com.app.mission.mood.dialog.EnterDialog;
import com.app.mission.mood.model.MissionModel;
import com.app.mission.mood.ui.adapter.MissionAdapter;
import com.app.mission.mood.utils.DataUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MissionFragment extends Fragment implements ClickMission {

    public MissionFragment() {

    }

    FragmentMissionBinding binding;
    MissionAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMissionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new MissionAdapter(requireContext(), new ArrayList<>(), this);

        initView();
        initData();
        initListener();

    }

    private void initView() {
        binding.rcyData.setAdapter(adapter);
    }

    private void initData() {
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
                        adapter.setListData(listData);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void initListener() {
        binding.imgAdd.setOnClickListener(v -> {
            new EnterDialog(requireContext()).showDialog(value -> {
                DataUtils.listMission.add(new MissionModel(
                        System.currentTimeMillis() + "", DataUtils.clickMood.getId(), value
                ));
                adapter.setListData(DataUtils.listMission);
            });
        });
    }

    @Override
    public void onClick(int pos, MissionModel item) {
        new EnterDialog(requireContext()).showDialog(item.getContent(), value -> {
            DataUtils.listMission.set(pos, new MissionModel(
                    item.getId(), item.getMood_id(), value
            ));
            adapter.setListData(DataUtils.listMission);
        });
    }

    @Override
    public void onDelete(int pos, MissionModel item) {
        DataUtils.listMission.remove(pos);
        adapter.setListData(DataUtils.listMission);
    }
}