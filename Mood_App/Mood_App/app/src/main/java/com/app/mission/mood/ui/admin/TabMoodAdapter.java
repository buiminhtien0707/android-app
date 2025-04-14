package com.app.mission.mood.ui.admin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabMoodAdapter extends FragmentStateAdapter {

    public TabMoodAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new MissionFragment();
        }
        return new MessageFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
