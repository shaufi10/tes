package com.grinasia.transport;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.grinasia.transport.Adapter.ViewPagerAdapter1;
import com.grinasia.transport.Fragments.Fragment_Home;
import com.grinasia.transport.Fragments.Fragment_Informasi;
import com.grinasia.transport.Fragments.Fragment_Notifikasi;
import com.grinasia.transport.Fragments.Fragment_Opsi;
import com.grinasia.transport.Fragments.Fragment_Profile;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar1;
    private TabLayout tabLayout1;
    private ViewPager viewPager1;
    //private PageIndicatorView pageIndicatorView;
    private int[] tabIcon={
            R.drawable.ic_tab_opsi1,
            R.drawable.ic_tab_profile1,
            R.drawable.ic_tab_home1,
            R.drawable.ic_tab_notifikasi1,
            R.drawable.ic_tab_informasi1
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar1 = (Toolbar) findViewById(R.id.toolbar_parent);
        setSupportActionBar(toolbar1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        viewPager1 = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager1(viewPager1);

        //pageIndicatorView = (PageIndicatorView) findViewById(R.id.pageIndicatorView);
        //pageIndicatorView.setViewPager(viewPager);

        tabLayout1 = (TabLayout) findViewById(R.id.tabs);
        tabLayout1.setupWithViewPager(viewPager1);
        setupTabIcons();

    }

    /*@Override
    public void onFragmentInteraction(String title) {
        getSupportActionBar().setTitle(title);
    }*/

    private void setupTabIcons(){
        tabLayout1.getTabAt(0).setIcon(tabIcon[0]);
        tabLayout1.getTabAt(1).setIcon(tabIcon[1]);
        tabLayout1.getTabAt(2).setIcon(tabIcon[2]);
        tabLayout1.getTabAt(3).setIcon(tabIcon[3]);
        tabLayout1.getTabAt(4).setIcon(tabIcon[4]);
    }
    private void setupViewPager1(ViewPager viewPager){
        ViewPagerAdapter1 adapter1 = new ViewPagerAdapter1(getSupportFragmentManager());
        adapter1.addFragment(new Fragment_Opsi());
        adapter1.addFragment(new Fragment_Profile());
        adapter1.addFragment(new Fragment_Home());
        adapter1.addFragment(new Fragment_Notifikasi());
        adapter1.addFragment(new Fragment_Informasi());
        viewPager.setAdapter(adapter1);
        viewPager.setCurrentItem(2, false);
    }
}


