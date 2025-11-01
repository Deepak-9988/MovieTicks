package com.example.movieticks;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class TheatresBtmNav extends Fragment {

  private Toolbar toolbar;

  String currentLocation;
  public static TheatresBtmNav instance=null;

  DatabaseReference databaseTInfo;
  ListView listViewTheatres;
    private TextView selectedLocation;
    DatabaseReference databaseMInfo;

    public TheatresBtmNav() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
    }

    public static TheatresBtmNav getInstance(){
        return instance;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.theatres_btm_nav,container,false);
        toolbar=view.findViewById(R.id.toolbar_theatres);
        listViewTheatres=view.findViewById(R.id.listViewTheatres);
        databaseTInfo=FirebaseDatabase.getInstance().getReference("Cities");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.search)
                    Toast.makeText(getActivity(),"Search Option Will Available From Next Update",Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        NavigationView navigationView=view.findViewById(R.id.theatres_nav_view);
        final Menu menu=navigationView.getMenu();
        databaseMInfo= FirebaseDatabase.getInstance().getReference("Cities");
     //   selectedLocation=view.findViewById(R.id.SelectedLocation);


        databaseMInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot citySnapshot: dataSnapshot.getChildren()) {
                   // TheatreInfo theatreInfo=citySnapshot.getValue(TheatreInfo.class);
                    menu.add(citySnapshot.getKey());
                }
                selectedLocation=view.findViewById(R.id.SelectedLocation);
                currentLocation= MoviesBtmNav.getInstance().setCurrentLocation();
                selectedLocation.setText(currentLocation);
   //             databaseTInfo.child(menu.getItem(0).toString()).child("Theatres").addValueEventListener(new ValueEventListener() {
                databaseTInfo=FirebaseDatabase.getInstance().getReference("Cities");
               // MoviesBtmNav.getInstance().setCurrentLocation();
                databaseTInfo.child(currentLocation).child("Theatres").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final ArrayList<TheatreInfo> theatreInfoList=new ArrayList<TheatreInfo>();
                        theatreInfoList.clear();
                        for(DataSnapshot theatresSnapshot:dataSnapshot.getChildren()){

                            TheatreInfo theatreInfo=theatresSnapshot.getValue(TheatreInfo.class);
                            theatreInfoList.add(theatreInfo);
                        }
                        Theatres_list adapter =new Theatres_list(getActivity(),theatreInfoList);
                        listViewTheatres.setDividerHeight(20);

                        listViewTheatres.setHeaderDividersEnabled(true);
                        listViewTheatres.setFooterDividersEnabled(true);

                        listViewTheatres.setAdapter(adapter);
                        listViewTheatres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(getActivity(), theatreInfoList.get(position).getTheatreName() +" theatre is touched",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
             //   selectedLocation.setText("Working");
             //   NowShowing.getInstance().refreshMovies(menu.getItem(0).toString(),databaseMInfo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       /* databaseTInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<TheatreInfo> theatreInfoList=new ArrayList<TheatreInfo>();
                theatreInfoList.clear();
                for(DataSnapshot theatresSnapshot:dataSnapshot.getChildren()){

                    TheatreInfo theatreInfo=theatresSnapshot.getValue(TheatreInfo.class);
                    theatreInfoList.add(theatreInfo);
                }
                Theatres_list adapter =new Theatres_list(getActivity(),theatreInfoList);
                listViewTheatres.setDividerHeight(20);
                listViewTheatres.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        final DrawerLayout drawerLayout=view.findViewById(R.id.theatres_drawer_layout);
     //   Drawable drawable= ResourcesCompat.getDrawable(getResources(),R.drawable.ic_edit_location_black_24dp,getActivity().getTheme());
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
     //   toggle.setHomeAsUpIndicator(drawable);
        drawerLayout.addDrawerListener(toggle);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
           }
        });
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(getContext(),item+" Location Selected  ",Toast.LENGTH_SHORT).show();
                selectedLocation.setText(item.toString());
                MoviesBtmNav.getInstance().moviesFragLocation(item.toString(),databaseTInfo);

                //   NowShowing.getInstance().refreshMovies(item.toString(),databaseMInfo);
//                databaseTInfo=FirebaseDatabase.getInstance().getReference("Cities");
                databaseTInfo.child(item.toString()).child("Theatres").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final ArrayList<TheatreInfo> theatreInfoList=new ArrayList<TheatreInfo>();
                        theatreInfoList.clear();
                        for(DataSnapshot theatresSnapshot:dataSnapshot.getChildren()){

                            TheatreInfo theatreInfo=theatresSnapshot.getValue(TheatreInfo.class);
                            theatreInfoList.add(theatreInfo);
                        }
                        Theatres_list adapter =new Theatres_list(getActivity(),theatreInfoList);
                        listViewTheatres.setDividerHeight(20);
                        listViewTheatres.setAdapter(adapter);
                           /* if(theatreInfoList.isEmpty())
                        {
                            EmptyAdapter emptyAdapter=new EmptyAdapter(getActivity());
                            //listViewTheatres.setAdapter(emptyAdapter);
                            listViewTheatres.setEmptyView(View.inflate(getActivity(),R.layout.empty_state,null));
                        }
                        else*/
                        listViewTheatres.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(getActivity(), theatreInfoList.get(position).getTheatreName() +" theatre is touched",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.theatres_drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        return view;
    }

    public void refreshTheatres(final String cityname, DatabaseReference databaseTInfo) {

        databaseTInfo.child(cityname).child("Theatres").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<TheatreInfo> theatreInfoList=new ArrayList<TheatreInfo>();
                theatreInfoList.clear();
                for(DataSnapshot theatresSnapshot:dataSnapshot.getChildren()){

                    TheatreInfo theatreInfo=theatresSnapshot.getValue(TheatreInfo.class);
                    theatreInfoList.add(theatreInfo);
                }
                selectedLocation.setText(cityname);
                Theatres_list adapter =new Theatres_list(getActivity(),theatreInfoList);
                listViewTheatres.setDividerHeight(20);
                listViewTheatres.setAdapter(adapter);
                           /* if(theatreInfoList.isEmpty())
                        {
                            EmptyAdapter emptyAdapter=new EmptyAdapter(getActivity());
                            //listViewTheatres.setAdapter(emptyAdapter);
                            listViewTheatres.setEmptyView(View.inflate(getActivity(),R.layout.empty_state,null));
                        }
                        else*/
                listViewTheatres.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getActivity(), theatreInfoList.get(position).getTheatreName() +" theatre is touched",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    //To use it later
    public String setCurrentLocation(){
        return currentLocation;
    }
}
