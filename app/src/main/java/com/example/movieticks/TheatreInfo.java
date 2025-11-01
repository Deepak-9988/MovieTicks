package com.example.movieticks;

public class TheatreInfo {
    String theatreName,theatreAddress,CityName;
    public TheatreInfo(){

    }

    public TheatreInfo(String tName, String tCityName, String tAddress){
        this.theatreName=tName;
        this.theatreAddress=tAddress;
        this.CityName=tCityName;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public String getTheatreAddress() {
        return theatreAddress;
    }

    public String getCityName() {
        return CityName;
    }
}
