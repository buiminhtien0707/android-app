package com.app.mission.mood.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.mission.mood.click.ClickMessage;
import com.app.mission.mood.click.OnEnterListener;
import com.app.mission.mood.databinding.FragmentMessageBinding;
import com.app.mission.mood.dialog.EnterDialog;
import com.app.mission.mood.model.MessageModel;
import com.app.mission.mood.model.MissionModel;
import com.app.mission.mood.ui.adapter.MessageAdapter;
import com.app.mission.mood.utils.DataUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MessageFragment extends Fragment implements ClickMessage {


    public MessageFragment() {

    }

    FragmentMessageBinding binding;
    MessageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMessageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        adapter = new MessageAdapter(requireContext(), new ArrayList<>(), this);

        initView();
        initData();
        initListener();

    }

    private void initView() {
        binding.rcyData.setAdapter(adapter);
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
                        adapter.setListData(listData);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void initListener() {
        binding.imgAdd.setOnClickListener(v -> {
            new EnterDialog(requireContext()).showDialog(value ->{
                DataUtils.listMessage.add(new MessageModel(
                        System.currentTimeMillis()+"", DataUtils.clickMood.getId(), value
                ));
                adapter.setListData(DataUtils.listMessage);
            });
        });
    }

    @Override
    public void onClick(int pos, MessageModel item) {
        new EnterDialog(requireContext()).showDialog(item.getContent(), value ->{
            DataUtils.listMessage.set(pos, new MessageModel(
                    item.getId(), item.getMood_id(), value
            ));
            adapter.setListData(DataUtils.listMessage);
        });
    }

    @Override
    public void onDelete(int pos, MessageModel item) {
        DataUtils.listMessage.remove(pos);
        adapter.setListData(DataUtils.listMessage);
    }
}