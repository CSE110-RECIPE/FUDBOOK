package com.example.fudbook.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
Fragment adapter, handles all fragments
 */


public class FragmentAdapter extends FragmentStateAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final Bundle fragmentBundle;

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, Bundle data, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        fragmentBundle = data;
    }

    public void addFragment(Fragment fragment, String title){
        fragment.setArguments(fragmentBundle); // handle bundle
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }

    // gets fragment position
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }
}
