package com.example.movieticks;

public class MovieInfo {
    String movieName,movieDuration,movieImg,mCast,mId,mLanguage,mCertificate,mtrailerKey;
    public MovieInfo(){

    }

    public MovieInfo(String movieName, String movieDuration, String movieImg, String mcast, String mlanguage, String mcertificate, String id,String trailerKey) {
        this.movieName = movieName;
        this.movieDuration = movieDuration;
        this.movieImg=movieImg;
        this.mCast=mcast;
        this.mLanguage=mlanguage;
        this.mCertificate=mcertificate;
        this.mId=id;
        this.mtrailerKey=trailerKey;
    }
    public String getMovieDuration() {
        return movieDuration;
    }
    public String getMovieName() {
        return movieName;
    }
    public String getMovieImg(){
        return movieImg;
    }

    public String getmLanguage() {
        return mLanguage;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setMovieDuration(String movieDuration) {
        this.movieDuration = movieDuration;
    }

    public void setMovieImg(String movieImg) {
        this.movieImg = movieImg;
    }

    public void setmCast(String mCast) {
        this.mCast = mCast;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public void setmLanguage(String mLanguage) {
        this.mLanguage = mLanguage;
    }

    public void setmCertificate(String mCertificate) {
        this.mCertificate = mCertificate;
    }

    public String getmCertificate() {
        return mCertificate;
    }

    public String getmCast() {
        return mCast;
    }

    public String getMtrailerKey() {
        return mtrailerKey;
    }

    public void setMtrailerKey(String mtrailerKey) {
        this.mtrailerKey = mtrailerKey;
    }

    public String getmId() {
        return mId;
    }
}
