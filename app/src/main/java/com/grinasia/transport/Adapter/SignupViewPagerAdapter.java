package com.grinasia.transport.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coder on 24-Jan-17.
 */

public class SignupViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> signupPageList = new ArrayList<>();

    public SignupViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return signupPageList.get(position);
    }

    @Override
    public int getCount() {
        return signupPageList.size();
    }

    public void addFragment(Fragment fragment){
        signupPageList.add(fragment);
    }

}
