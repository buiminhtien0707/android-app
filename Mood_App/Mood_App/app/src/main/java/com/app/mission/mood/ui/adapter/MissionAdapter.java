package com.app.mission.mood.ui.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mission.mood.R;
import com.app.mission.mood.click.ClickMission;
import com.app.mission.mood.click.ClickMood;
import com.app.mission.mood.model.MissionModel;
import com.app.mission.mood.model.MoodModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MissionAdapter extends RecyclerView.Adapter<MissionAdapter.ViewHolder> {

    List<MissionModel> list = new ArrayList<>();
    Context context;
    ClickMission click;

    public MissionAdapter(Context context, List<MissionModel> list, ClickMission click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListData(List<MissionModel> list) {
        this.list = list;
        Collections.reverse(this.list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_item_v2, parent, false);
        return new ViewHolder(view);
    }


    @Override
    @SuppressLint({"RecyclerView", "SetTextI18n"})
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MissionModel data = list.get(position);
        holder.txtContent.setText(data.getContent());

        holder.txtContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onClick(position, data);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onDelete(position, data);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtContent;
        ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.txtContent);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }

}
