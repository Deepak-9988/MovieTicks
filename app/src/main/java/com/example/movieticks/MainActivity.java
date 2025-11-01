package com.example.movieticks;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity  {
 //   private Toolbar toolbar_movies,toolbar_theatres;
    private BottomNavigationView bottomNavigationView;
    private MoviesBtmNav moviesBtmNav;
    private ProfileBtmNav profileBtmNav;
    private TheatresBtmNav theatresBtmNav;
    int backPress=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviesBtmNav=new MoviesBtmNav();
        theatresBtmNav=new TheatresBtmNav();
        profileBtmNav=new ProfileBtmNav();

        setFragement(moviesBtmNav,MoviesBtmNav.class.getSimpleName());

        // toolbar_movies=findViewById(R.id.toolbar_movies);
      //  toolbar_theatres=findViewById(R.id.toolbar_theatres);

        bottomNavigationView=findViewById(R.id.bottomNav);

        //bottomNavigationView.setItemTextColor(ColorStateList.valueOf(Color.RED));
        //bottomNavigationView.setItemIconTintList(ColorStateList.valueOf(Color.RED));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.movies: {
                        setFragement(moviesBtmNav,MoviesBtmNav.class.getSimpleName());
                      //  Toast.makeText(MainActivity.this, "Movies Clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    case R.id.theatres: {

                        setFragement(theatresBtmNav,TheatresBtmNav.class.getSimpleName());
                        //Toast.makeText(MainActivity.this, "Theatres Clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    case R.id.profile: {
                       // bottomNavigationView.setVisibility(View.GONE);
                        setFragement(profileBtmNav,ProfileBtmNav.class.getSimpleName());
                       // Toast.makeText(MainActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    default:
                        return false;

                }
            }
        });
        /*TabLayout tabLayout=findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Now Showing"));
        tabLayout.addTab(tabLayout.newTab().setText("Up Coming"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

       final ViewPager TabViewPager=findViewById(R.id.TabViewPager);
        final PagerAdapter adapter= new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        TabViewPager.setAdapter(adapter);




       /* bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.movies){
                    setFragement(moviesBtmNav);
                    TabViewPager.setAdapter(adapter);
                    Toast.makeText(MainActivity.this,"Movies Clicked",Toast.LENGTH_SHORT).show();
                       /* Intent i = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i);
                }

                if(id==R.id.theatres)
                {
                    TabViewPager.setAdapter(null);
                    setFragement(theatresBtmNav);
                    Toast.makeText(MainActivity.this,"Theatres Clicked",Toast.LENGTH_SHORT).show();
                }

                if(id==R.id.profile) {
                    TabViewPager.setAdapter(null);
                    Toast.makeText(MainActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                    setFragement(profileBtmNav);
                       /* final ViewPager ProfileViewPage=findViewById(R.id.ProfileViewPage);
                        final PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager(),3);
                        ProfileViewPage.setAdapter(adapter);

                        Toast.makeText(MainActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getBaseContext(), ProfileActivity.class);
                        startActivity(i);
                }
                return false;
            }
        });

      //  setSupportActionBar(toolbar);
 //       toolbar.setNavigationIcon(R.drawable.ic_edit_location_black_24dp);



      /*   TabViewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
    }
/*
    private void setFragement(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayout,fragment);
        fragmentTransaction.commit();
    }
*/

    public void setFragement(Fragment fragment, String tagFragmentName) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment currentFragment = mFragmentManager.getPrimaryNavigationFragment();
        /*if (currentFragment == null) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction2=fragmentManager.beginTransaction();
            fragmentTransaction2.add(R.id.FrameLayout,theatresBtmNav);
            fragmentTransaction2.commitNow();
            fragmentTransaction.setPrimaryNavigationFragment(theatresBtmNav);
            currentFragment=fragmentManager.getPrimaryNavigationFragment();
        }*/

        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }


        Fragment fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName);
        if (fragmentTemp == null) {
            fragmentTemp = fragment;
            fragmentTransaction.add(R.id.FrameLayout, fragmentTemp, tagFragmentName);
        } else {
            fragmentTransaction.show(fragmentTemp);
        }
        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();
    }


    public void addFragment(Fragment fragment,String tagFragmentName){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Fragment fragmentTemp=getSupportFragmentManager().findFragmentByTag(tagFragmentName);
        if (fragmentTemp == null) {
            fragmentTemp=theatresBtmNav;
            fragmentTransaction.add(R.id.FrameLayout,fragmentTemp,tagFragmentName);
        }
        else
            fragmentTransaction.show(fragmentTemp);


    }



    @Override
    public void onBackPressed() {

        if (backPress > 1)
            super.onBackPressed();
        else {
            backPress++;
            //Snackbar.make(findViewById(android.R.id.content), "Press again to exit", Snackbar.LENGTH_SHORT);
            Toast.makeText(getBaseContext(),"Press again to Exit",Toast.LENGTH_SHORT).show();

        }
    }
/*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
*/


    //BottomNavigation Coding starts Here

  /*  private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod=new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id=item.getItemId();
                    if(id==R.id.movies){
                        setFragement(moviesBtmNav);
                        Toast.makeText(MainActivity.this,"Movies Clicked",Toast.LENGTH_SHORT).show();
                       /* Intent i = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i);
                    }

                    if(id==R.id.theatres)
                    {
                        setFragement(theatresBtmNav);
                        Toast.makeText(MainActivity.this,"Theatres Clicked",Toast.LENGTH_SHORT).show();
                    }

                    if(id==R.id.profile) {
                        Toast.makeText(MainActivity.this,"Profile Clicked",Toast.LENGTH_SHORT).show();
                        setFragement(profileBtmNav);
                       /* final ViewPager ProfileViewPage=findViewById(R.id.TabViewPager);
                        final PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager(),3);
                        ProfileViewPage.setAdapter(adapter);

                       /* Toast.makeText(MainActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getBaseContext(), ProfileActivity.class);
                        startActivity(i);
                    }
                    return true;
                }
            };
*/


 /*   @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
        {
            Toast.makeText(this,"Left Location Clicked",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.search)
            Toast.makeText(this,"Search Clicked",Toast.LENGTH_SHORT).show();
        if(id==R.id.ticketPass)
            Toast.makeText(this,"Ticket Pass Clicked",Toast.LENGTH_SHORT).show();
        //   return super.onOptionsItemSelected(item);
        return true;

    }*/
}
