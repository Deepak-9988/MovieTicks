package com.example.movieticks;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UpComing extends Fragment {
    ListView listViewMovies;

    LinearLayout ll_materialProgressBar;
    LinearLayout ll_no_upComingMovies;

    DatabaseReference databaseMInfo;
    final ArrayList<MovieInfo> movieInfoList=new ArrayList<MovieInfo>();

    private static UpComing instance=null;

    public UpComing() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
    }

    public static UpComing getInstance(){
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.up_coming,container,false);

        ll_materialProgressBar=view.findViewById(R.id.ll_materialProgressBar);
        listViewMovies = view.findViewById(R.id.listviewMovies);
        ll_no_upComingMovies=view.findViewById(R.id.ll_no_upComingMovies);

        databaseMInfo= FirebaseDatabase.getInstance().getReference("All UpComing Movies");


        databaseMInfo.addValueEventListener(new ValueEventListener() {
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
                    ll_no_upComingMovies.setVisibility(View.VISIBLE);
                    listViewMovies.setVisibility(View.GONE);
                }
                else {
                    Movies_list adapter = new Movies_list(getActivity(), movieInfoList);

                    ll_materialProgressBar.setVisibility(View.GONE);
                    listViewMovies.setDividerHeight(20);
                    listViewMovies.setAdapter(adapter);
                    listViewMovies.setClickable(false);
                    listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(getActivity(),"Movie Not Released Yet", Toast.LENGTH_SHORT).show();
                        }
                    });
                    // Toast.makeText(getActivity(),"Movie Info Downloaded",Toast.LENGTH_SHORT).show();
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









        return view;
    }
}
