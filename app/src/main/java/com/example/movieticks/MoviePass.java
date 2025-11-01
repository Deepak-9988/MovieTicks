package com.example.movieticks;

import java.util.ArrayList;
import java.util.Date;

public class MoviePass {

    MovieInfo movieInfo;
    String timeAndDate, theatreName, theatreAddress;
    String ticketDetails,seatsSelected;
    int ttlPrice;
    Date dateObj;


    /*int silverTicketsCount,goldTicketsCount,platinumTicketsCount;



    private  ArrayList<String> selectedSeatsListSilver=new ArrayList<String>();
    private  ArrayList<String> selectedSeatsListGold=new ArrayList<String>();
    private  ArrayList<String> selectedSeatsListPlatinum=new ArrayList<String>();
 */

    public MoviePass(MovieInfo movieInfo,String timeAndDate,String theatreName,String theatreAddress,String ticketDetails,String seatsSelected, int ttlPrice,Date dateObj){
        this.timeAndDate=timeAndDate;
        this.theatreName=theatreName;
        this.theatreAddress=theatreAddress;
        this.ticketDetails=ticketDetails;
        this.seatsSelected=seatsSelected;
        this.ttlPrice=ttlPrice;
        this.movieInfo=movieInfo;
        this.dateObj=dateObj;
    }





    public MoviePass(){

    }

    public MovieInfo getMovieInfo() {
        return movieInfo;
    }

    public void setMovieInfo(MovieInfo movieInfo) {
        this.movieInfo = movieInfo;
    }

    public String getTimeAndDate() {
        return timeAndDate;
    }

    public void setTimeAndDate(String timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public String getTheatreName() {
        return theatreName;
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

    public String getTicketDetails() {
        return ticketDetails;
    }

    public void setTicketDetails(String ticketDetails) {
        this.ticketDetails = ticketDetails;
    }

    public String getSeatsSelected() {
        return seatsSelected;
    }

    public void setSeatsSelected(String seatsSelected) {
        this.seatsSelected = seatsSelected;
    }

    public int getTtlPrice() {
        return ttlPrice;
    }

    public void setTtlPrice(int ttlPrice) {
        this.ttlPrice = ttlPrice;
    }

    public Date getDateObj() {
        return dateObj;
    }

    public void setDateObj(Date dateObj) {
        this.dateObj = dateObj;
    }



/*  public MoviePass(String timeAndDate, String theatreName,String theatreAddress,int silverTicketsCount,int goldTicketsCount, int platinumTicketsCount,ArrayList<String> selectedSeatsListSilver,ArrayList<String> selectedSeatsListGold,ArrayList<String> selectedSeatsListPlatinum,int ttlPrice ,MovieInfo movieInfo){
        this.timeAndDate=timeAndDate;
        this.theatreName=theatreName;
        this.theatreAddress=theatreAddress;
        this.silverTicketsCount=silverTicketsCount;
        this.goldTicketsCount=goldTicketsCount;
        this.platinumTicketsCount=platinumTicketsCount;
        this.selectedSeatsListSilver=selectedSeatsListSilver;
        this.selectedSeatsListGold=selectedSeatsListGold;
        this.selectedSeatsListPlatinum=selectedSeatsListPlatinum;
        this.ttlPrice=ttlPrice;
        this.movieInfo=movieInfo;

    }

    public String getTimeAndDate() {
        return timeAndDate;
    }

    public void setTimeAndDate(String timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public String getTheatreName() {
        return theatreName;
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

    public int getSilverTicketsCount() {
        return silverTicketsCount;
    }

    public void setSilverTicketsCount(int silverTicketsCount) {
        this.silverTicketsCount = silverTicketsCount;
    }

    public int getGoldTicketsCount() {
        return goldTicketsCount;
    }

    public void setGoldTicketsCount(int goldTicketsCount) {
        this.goldTicketsCount = goldTicketsCount;
    }

    public int getPlatinumTicketsCount() {
        return platinumTicketsCount;
    }

    public void setPlatinumTicketsCount(int platinumTicketsCount) {
        this.platinumTicketsCount = platinumTicketsCount;
    }

    public MovieInfo getMovieInfo() {
        return movieInfo;
    }

    public void setMovieInfo(MovieInfo movieInfo) {
        this.movieInfo = movieInfo;
    }

    public ArrayList<String> getSelectedSeatsListSilver() {
        return selectedSeatsListSilver;
    }

    public void setSelectedSeatsListSilver(ArrayList<String> selectedSeatsListSilver) {
        this.selectedSeatsListSilver = selectedSeatsListSilver;
    }

    public ArrayList<String> getSelectedSeatsListGold() {
        return selectedSeatsListGold;
    }

    public void setSelectedSeatsListGold(ArrayList<String> selectedSeatsListGold) {
        this.selectedSeatsListGold = selectedSeatsListGold;
    }

    public ArrayList<String> getSelectedSeatsListPlatinum() {
        return selectedSeatsListPlatinum;
    }

    public void setSelectedSeatsListPlatinum(ArrayList<String> selectedSeatsListPlatinum) {
        this.selectedSeatsListPlatinum = selectedSeatsListPlatinum;
    }

    public int getTtlPrice() {
        return ttlPrice;
    }

    public void setTtlPrice(int ttlPrice) {
        this.ttlPrice = ttlPrice;
    }


    public String getTicketDetails() {
        return ticketDetails;
    }

    public void setTicketDetails(String ticketDetails) {
        this.ticketDetails = ticketDetails;
    }

    public String getSeatsSelected() {
        return seatsSelected;
    }

    public void setSeatsSelected(String seatsSelected) {
        seatsSelected = seatsSelected;
    }
*/


}
