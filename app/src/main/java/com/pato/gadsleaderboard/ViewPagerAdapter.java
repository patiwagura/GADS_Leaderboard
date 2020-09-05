package com.pato.gadsleaderboard;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.pato.gadsleaderboard.views.TabsFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private static final String TAG = "ViewPagerAdapter";

    // fragment List to add
    List<Fragment> lst_myFragments = new ArrayList<>();

    // constructor.
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> lstFragments) {
        super(fragmentActivity);
        this.lst_myFragments = lstFragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.e(TAG, "createFragment: position : " + position);

        // Return a new Fragment instance.
        Fragment fragment = lst_myFragments.get(position);

        // Data => sent to fragment.
        Bundle args = new Bundle();
        args.putInt(TabsFragment.ARG_TABS_FRAG_POSITION, position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getItemCount() {
        // number of fragments created.
        return 2;
    }
}