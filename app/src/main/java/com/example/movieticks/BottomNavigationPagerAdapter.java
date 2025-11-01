package com.example.movieticks;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class BottomNavigationPagerAdapter extends FragmentPagerAdapter {
    int NoOfTabs;
    public BottomNavigationPagerAdapter(FragmentManager fm, int NumberOfTabs){
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.NoOfTabs=NumberOfTabs;
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {


            case 1:
                NowShowing nowShowing=new NowShowing();
                return nowShowing;
            case 2:
                UpComing upComing=new UpComing();
                return upComing;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return NoOfTabs;
    }
}
