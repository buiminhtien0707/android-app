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
import com.app.mission.mood.click.ClickMood;
import com.app.mission.mood.model.MoodModel;

import java.util.Collections;
import java.util.List;

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.ViewHolder> {

    List<MoodModel> list;
    Context context;
    ClickMood click;
    Boolean isAdmin;

    public MoodAdapter(Context context, List<MoodModel> list, ClickMood click) {
        this.list = list;
        this.context = context;
        this.click = click;
        this.isAdmin = false;
    }

    public MoodAdapter(Context context, List<MoodModel> list, ClickMood click, Boolean isAdmin) {
        this.list = list;
        this.context = context;
        this.click = click;
        this.isAdmin = isAdmin;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListData(List<MoodModel> list) {
        this.list = list;
        Collections.reverse(this.list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_item_mood, parent, false);
        return new ViewHolder(view);
    }


    @Override
    @SuppressLint({"RecyclerView", "SetTextI18n"})
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MoodModel item = list.get(position);

        holder.txtContent.setText(item.getName());

        if (isAdmin) {
            holder.imgDelete.setVisibility(View.VISIBLE);
        } else {
            holder.imgDelete.setVisibility(View.GONE);
        }

        holder.txtContent.setOnClickListener(v -> click.clickMood(item));
        holder.imgDelete.setOnClickListener(v -> click.clickDelete(item));
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
