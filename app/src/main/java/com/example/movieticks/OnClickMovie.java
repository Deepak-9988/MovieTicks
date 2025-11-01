package com.example.movieticks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static android.view.View.VISIBLE;

public  class OnClickMovie extends AppCompatActivity {
    private static MovieInfo movieInfo;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private YouTubePlayer youTubePlayer;

    TextView movieTitle;
    String citySelected;
    String mTrailerKey;

    private YouTubePlayerSupportFragmentX youTubePlayerSupportFragment;
    TextView tvMName,tvMLanguage,tvMCast,tvMCertificate,tvMDuration;
    ImageView movieImage, playBtn,thumbnail;
    DatabaseReference databaseRef;
    Button btn_book_tickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_click_movie);
        mTrailerKey=NowShowing.setTrailerKeySelectedMovie();

        initializeYoutubePlayer();


        citySelected=getIntent().getStringExtra("citySelected");

        tvMName=findViewById(R.id.Mname);
        tvMLanguage=findViewById(R.id.mlanguage);
        tvMCast=findViewById(R.id.Mcast);
        tvMCertificate=findViewById(R.id.mCertificate);
        movieImage=findViewById(R.id.MovieImg);
        tvMDuration=findViewById(R.id.mDuration);

        btn_book_tickets=findViewById(R.id.btn_book_tickets);


        if(movieInfo!=null) {
            tvMName.setText(movieInfo.getMovieName());
            tvMLanguage.setText(movieInfo.getmLanguage());
            tvMCertificate.setText(movieInfo.getmCertificate());
            Picasso.with(getBaseContext()).load(movieInfo.getMovieImg()).into(movieImage);
            tvMCast.setText(movieInfo.getmCast());

            tvMDuration.setText(movieInfo.getMovieDuration());


            btn_book_tickets.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookTickets.sendMInfo(movieInfo);
                    Intent i = new Intent(OnClickMovie.this, BookTickets.class);
                    i.putExtra("mId", movieInfo.getmId());
                    i.putExtra("citySelected", citySelected);

                    startActivity(i);
                    btn_book_tickets.setClickable(false);

                }
            });
        }


    }

    private void initializeYoutubePlayer() {
        youTubePlayerSupportFragment = (YouTubePlayerSupportFragmentX) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);

        if (youTubePlayerSupportFragment == null)
            return;

        youTubePlayerSupportFragment.initialize("api key", new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    youTubePlayer = player;
                    youTubePlayer.cueVideo(mTrailerKey);

                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                youTubePlayerSupportFragment.getView().setVisibility(View.GONE);
                Toast.makeText(getBaseContext(),"Youtube Player initialization failed ",Toast.LENGTH_SHORT).show();
            }
        });


    }





/*
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/yu7yIgER6-c")));
        Log.i("Video", "Video Playing....");*/
    //}






    public static void sendMInfo(MovieInfo SelectedmovieInfo){
        movieInfo=SelectedmovieInfo;
    }


    @Override
    protected void onResume() {
        super.onResume();
        btn_book_tickets.setClickable(true);
    }
}
