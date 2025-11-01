package com.example.movieticks;

import java.util.ArrayList;

public class Theatre_and_showtimes {


    String day,month,year,date;
    String date_string,theatreUid,theatreName,theatreAddress,cityName;

    ArrayList<String> showTimesList=new ArrayList<String>();



    public String getTheatreName() {
        return theatreName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getTheatreAddress() {
        return theatreAddress;
    }

    public void setTheatreAddress(String theatreAddress) {
        this.theatreAddress = theatreAddress;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public String getDate_string() {
        return date_string;
    }

    public void setDate_string(String date_string) {
        this.date_string = date_string;
    }

    public String getTheatreUid() {
        return theatreUid;
    }

    public void setTheatreUid(String theatreUid) {
        this.theatreUid = theatreUid;
    }

    public ArrayList<String> getShowTimesList() {
        return showTimesList;
    }

    public void setShowTimesList(ArrayList<String> showTimesList) {
        this.showTimesList = showTimesList;
    }
}
