package com.example.movieticks;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.TashieLoader;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NowShowing extends Fragment  {
    ListView listViewMovies;

    private static NowShowing instance=null;

    public static String trailerKeySelectedMovie;

    String cityname="Chennai";

    Toolbar toolbar;

    TashieLoader materialProgressBar;
    LinearLayout ll_materialProgressBar;
    LinearLayout ll_no_Movies;


    final ArrayList<MovieInfo> movieInfoList=new ArrayList<MovieInfo>();
    DatabaseReference databaseMInfo;
    public NowShowing() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
    }
    
    public static NowShowing getInstance(){
        return instance;
    }

 /*   @Override
    public void onStart() {
        super.onStart();
        databaseMInfo=FirebaseDatabase.getInstance().getReference("Cities");
        databaseMInfo.child(cityname).child("Movies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<MovieInfo> movieInfoList=new ArrayList<MovieInfo>();
                movieInfoList.clear();
                for (DataSnapshot moviesSnapshot : dataSnapshot.getChildren()) {

                    MovieInfo movieInfo = moviesSnapshot.getValue(MovieInfo.class);
                    movieInfoList.add(movieInfo);
                }
                Movies_list adapter = new Movies_list(getActivity(), movieInfoList);
                listViewMovies.setDividerHeight(20);
                listViewMovies.setAdapter(adapter);

                Toast.makeText(getActivity(),"Movie Info Downloaded",Toast.LENGTH_SHORT).show();
                listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getActivity(), movieInfoList.get(position).getMovieName() +" movie is touched",Toast.LENGTH_SHORT).show();
                    }
                });
               /* listViewMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(MainActivity.this,movieInfoList.get(position).getMovieName()+" is Long clicked",Toast.LENGTH_SHORT).show();
                        EditMovieInfo obj=new EditMovieInfo(movieInfoList.get(position).getMovieName(),movieInfoList.get(position).getmCast(),movieInfoList.get(position).getMovieDuration().toString());
                        Intent i=new Intent(getBaseContext(),EditMovieInfo.class);
                        i.putExtra("Sample", obj);
                        startActivity(i);
/*                       EditMovieInfo obj=new EditMovieInfo(movieInfoList.get(position));
                        Intent i=new Intent(getBaseContext(),EditMovieInfo.class);
                        i.putExtra("Sample", obj);
                        startActivity(i);


                        return false;
                    }
                });*/
       /*     }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });



    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View view= inflater.inflate(R.layout.now_showing,container,false);

        ll_materialProgressBar=view.findViewById(R.id.ll_materialProgressBar);
        ll_no_Movies=view.findViewById(R.id.ll_no_Movies);
        /*TashieLoader tashie = new TashieLoader(getContext(), 5, 30, 10, ContextCompat.getColor(getContext(), R.color.colorAccent));

        tashie.setAnimDuration(100);
        tashie.setAnimDelay(50);
        tashie.setInterpolator(new LinearInterpolator());
*/
       // materialProgressBar.addView(tashie);
     //   MoviesBtmNav.getInstance().working();   // It can be used to use the function of MoviesBtmNav

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar=view.findViewById(R.id.toolbar_movies);
     

        listViewMovies = view.findViewById(R.id.listviewMovies);


        return view;
    }



    public void refreshMovies(final String cityname, DatabaseReference databaseMInfo) {

        
        databaseMInfo.child(cityname).child("Movies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  final ArrayList<MovieInfo> movieInfoList=new ArrayList<MovieInfo>();
                movieInfoList.clear();
                for (DataSnapshot moviesSnapshot : dataSnapshot.getChildren()) {

                    MovieInfo movieInfo = moviesSnapshot.getValue(MovieInfo.class);
                    movieInfoList.add(movieInfo);
                }

                if(movieInfoList.size()<=0) {
                    ll_materialProgressBar.setVisibility(View.GONE);
                    ll_no_Movies.setVisibility(View.VISIBLE);
                    listViewMovies.setVisibility(View.GONE);
                }

                else {
                    listViewMovies.setVisibility(View.VISIBLE);
                    ll_no_Movies.setVisibility(View.GONE);
                    Movies_list adapter = new Movies_list(getActivity(), movieInfoList);
                    listViewMovies.setDividerHeight(20);
                    listViewMovies.setAdapter(adapter);
                    ll_materialProgressBar.setVisibility(View.GONE);
                    // Toast.makeText(getActivity(),"Movie Info Downloaded",Toast.LENGTH_SHORT).show();

                    listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // listViewMovies.setOnItemClickListener(null);

                            listViewMovies.setClickable(false);

                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference uidRef = rootRef.child("Cities").child(cityname).child("Movies").child(movieInfoList.get(position).getmId());
                            ValueEventListener eventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String str = dataSnapshot.child("trailerKey").getValue(String.class);
                                    trailerKeySelectedMovie = str;

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            };
                            uidRef.addListenerForSingleValueEvent(eventListener);

                            Intent intent = new Intent(getActivity(), OnClickMovie.class);
                            OnClickMovie.sendMInfo(movieInfoList.get(position));
                            intent.putExtra("citySelected", cityname);
                            intent.putExtra("mTrailerKey", movieInfoList.get(position).mtrailerKey);
                            startActivity(intent);
                            //  Toast.makeText(getActivity(), movieInfoList.get(position).getMovieName() +" movie is touched",Toast.LENGTH_SHORT).show();


                            //  ((MainActivity) getActivity()).setFragement(theatresOnMovieSelected,TheatresOnMovieSelected.class.getSimpleName());

                        }
                    });
                }
               /* listViewMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(MainActivity.this,movieInfoList.get(position).getMovieName()+" is Long clicked",Toast.LENGTH_SHORT).show();
                        EditMovieInfo obj=new EditMovieInfo(movieInfoList.get(position).getMovieName(),movieInfoList.get(position).getmCast(),movieInfoList.get(position).getMovieDuration().toString());
                        Intent i=new Intent(getBaseContext(),EditMovieInfo.class);
                        i.putExtra("Sample", obj);
                        startActivity(i);
/*                       EditMovieInfo obj=new EditMovieInfo(movieInfoList.get(position));
                        Intent i=new Intent(getBaseContext(),EditMovieInfo.class);
                        i.putExtra("Sample", obj);
                        startActivity(i);


                        return false;
                    }
                });*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        listViewMovies.setClickable(true);

      /*  listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*listViewMovies.setOnItemClickListener(null);

                Intent intent = new Intent(getActivity(), OnClickMovie.class);
                startActivity(intent);
                Toast.makeText(getActivity(), movieInfoList.get(position).getMovieName() +" movie is touched",Toast.LENGTH_SHORT).show();
                OnClickMovie.sendMInfo(movieInfoList.get(position));

                listViewMovies.setClickable(true);

                //  ((MainActivity) getActivity()).setFragement(theatresOnMovieSelected,TheatresOnMovieSelected.class.getSimpleName());

            }
        });*/
       // listViewMovies.setOnItemClickListener(newsfeedClickHandler);

       // Toast.makeText(getContext(),"Back Pressed",Toast.LENGTH_SHORT).show();
    }
    /*private void setFragement(TheatresOnMovieSelected theatresOnMovieSelected, String simpleName) {
        FragmentTransaction fragmentTransaction=getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.youtube_fragment,theatresOnMovieSelected);
        fragmentTransaction.commit();

    }*/


    public static String setTrailerKeySelectedMovie(){
        return trailerKeySelectedMovie;
    }

}
