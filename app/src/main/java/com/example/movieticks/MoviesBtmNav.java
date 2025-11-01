package com.example.movieticks;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MoviesBtmNav extends Fragment {

   private Toolbar toolbar;
   String currentLocation;

   private static MoviesBtmNav instance=null;

   private TextView selectedLocation;
   DatabaseReference databaseMInfo;

    public MoviesBtmNav() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
    }

    public static MoviesBtmNav getInstance(){
        return instance;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.movies_btm_nav,container,false);
        toolbar=view.findViewById(R.id.toolbar_movies);



        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id=item.getItemId();
                if(id==android.R.id.home)
                {
                 //   Toast.makeText(getActivity(),"Left Location Clicked",Toast.LENGTH_SHORT).show();
                }
                if(id==R.id.search) {
                    Toast.makeText(getActivity(), "Search Option Will Available From Next Update", Toast.LENGTH_SHORT).show();
                }
                if(id==R.id.ticketPass) {
                   // Toast.makeText(getActivity(), "Ticket Pass Clicked", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), MoviePassActivity.class);
                    startActivity(i);
                }
                //   return super.onOptionsItemSelected(item);
                return true;            }
        });


        NavigationView navigationView=view.findViewById(R.id.movies_nav_view);
        final Menu menu=navigationView.getMenu();
        databaseMInfo= FirebaseDatabase.getInstance().getReference("Cities");


        databaseMInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot citySnapshot: dataSnapshot.getChildren()) {
                    String cityName = citySnapshot.getKey();
                    menu.add(cityName);
                }
                selectedLocation=view.findViewById(R.id.SelectedLocation);
                currentLocation=menu.getItem(0).toString();
                selectedLocation.setText(currentLocation);
                NowShowing.getInstance().refreshMovies(menu.getItem(0).toString(),databaseMInfo);
               // TheatresBtmNav.getInstance().refreshTheatres(menu.getItem(0).toString(),databaseMInfo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DrawerLayout drawerLayout=view.findViewById(R.id.movies_drawer_layout);
        Drawable drawable= ResourcesCompat.getDrawable(getResources(),R.drawable.ic_edit_location_black_24dp,getActivity().getTheme());
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(drawable);
        drawerLayout.addDrawerListener(toggle);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
               /* if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }*/
            }
        });
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              // Toast.makeText(getContext(),item+" Location Selected  ",Toast.LENGTH_SHORT).show();
                currentLocation=item.toString();
                selectedLocation.setText(currentLocation);
                NowShowing.getInstance().refreshMovies(item.toString(),databaseMInfo);
                if(TheatresBtmNav.getInstance()==null)
                {
                   // Fragment newFragment = new TheatresBtmNav();
                  //  Fragment.instantiate(getContext(),TheatresBtmNav.class.getName());

                    // ((MainActivity)getActivity()).addFragment(new TheatresBtmNav(),TheatresBtmNav.class.getSimpleName());
//                    ((MainActivity)getActivity()).setFragement(new TheatresBtmNav(),TheatresBtmNav.class.getSimpleName());
                }
                if(TheatresBtmNav.getInstance()!=null)
                    TheatresBtmNav.getInstance().refreshTheatres(item.toString(),databaseMInfo);
                DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.movies_drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });



        final TabLayout tabLayout=view.findViewById(R.id.tablayout);
        final ViewPager TabViewPager=view.findViewById(R.id.TabViewPager);
        TabViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
              @Override
              public void run() {
                  tabLayout.setupWithViewPager(TabViewPager);
              }
          });
          return view;
    }



    //Following function can be used to communicate with NowShowing
    public void moviesFragLocation(String item,DatabaseReference databaseMInfo) {

        selectedLocation.setText(item);
        NowShowing.getInstance().refreshMovies(item,databaseMInfo);
    }

    public String setCurrentLocation(){
        return currentLocation;
    }


    static class MyAdapter extends FragmentPagerAdapter {
        MyAdapter(FragmentManager fm) {
            super(fm);
        }

         @NonNull
         @Override
         public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new NowShowing();

                case 1:
                    return new UpComing();
            }
             return null;
         }

         @Override
        public int getCount() {
            return 2;
        }


         @Override
         public CharSequence getPageTitle(int position) {

             switch (position) {
                 case 0:
                     return "Now Showing";
                 case 1:
                     return "Up Coming";
             }
             return null;
         }



    }

}
