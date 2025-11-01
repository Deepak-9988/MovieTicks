package com.example.movieticks;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatMapInfo {

  //  Map<String, Map<String, Object>> Date_Time_and_Availability = new HashMap<String, Map<String, Object>>();

    Map<String,Integer> SeatRows = new HashMap<String,Integer>();
    Map<String,Integer> Seats_per_rows = new HashMap<String,Integer>();
    Map<String,Integer> Ticket_price = new HashMap<String,Integer>();
    Map<String, ArrayList<String>> seatAvailability = new HashMap<String, ArrayList<String>>();

    public Map<String, ArrayList<String>> getSeatAvailability() {
        return seatAvailability;
    }

    public void setSeatAvailability(Map<String, ArrayList<String>> seatAvailability) {
        this.seatAvailability = seatAvailability;
    }

    public Map<String, Integer> getTicket_price() {
        return Ticket_price;
    }

    public void setTicket_price(Map<String, Integer> ticket_price) {
        this.Ticket_price = ticket_price;
    }

    public Map<String, Integer> getSeats_per_rows() {
        return Seats_per_rows;
    }

    public void setSeats_per_rows(Map<String, Integer> seats_per_rows) {
        this.Seats_per_rows = seats_per_rows;
    }

    public Map<String, Integer> getSeatRows() {
        return SeatRows;
    }

    public void setSeatRows(Map<String, Integer> seatRows) {
        this.SeatRows = seatRows;
    }
   // ArrayList<SeatRows> seatRows=new ArrayList<SeatRows>();

    ArrayList<String> showTimesList=new ArrayList<String>();
    ArrayList<String> seatEnableDisableList=new ArrayList<String>();

  //  ArrayList<String> seatAvailability=new ArrayList<String>();

    public void setSeatEnableDisableList(ArrayList<String> seatEnableDisableList) {
        this.seatEnableDisableList = seatEnableDisableList;
    }

  /*  public ArrayList<String> getSeatAvailability() {
        return seatAvailability;
    }

    public void setSeatAvailability(ArrayList<String> seatAvailability) {
        this.seatAvailability = seatAvailability;
    }
*/
    // ArrayList<String> selectedDatesString=new ArrayList<String>();
    List<CalendarDay> selectedDatesList=new ArrayList<CalendarDay>();

/*
    public void initialize_seat_rows(int silver_seat_rows, int gold_seat_rows, int platinum_seat_rows){
        this.silver_seat_rows=silver_seat_rows;
        this.gold_seat_rows=gold_seat_rows;
        this.platinum_seat_rows=platinum_seat_rows;

       // seatRows.add(new SeatRows(silver_seat_rows,gold_seat_rows,platinum_seat_rows));
    }


    public void initialize_seats_per_row(  int seats_per_row_silver,int seats_per_row_gold,int seats_per_row_platinum){
        this.seats_per_row_silver=seats_per_row_silver;
        this.seats_per_row_gold=seats_per_row_gold;
        this.seats_per_row_platinum=seats_per_row_platinum;
        dictionary.put("seats_per_row_silver",seats_per_row_silver);
        dictionary.put("seats_per_row_gold",seats_per_row_gold);
        dictionary.put("seats_per_row_platinum",seats_per_row_platinum);
    }

    public void initialize_ticket_price( int silver_ticket_price,int gold_ticket_price,int platinum_ticket_price){
        this.silver_ticket_price=silver_ticket_price;
        this.gold_ticket_price=gold_ticket_price;
        this.platinum_ticket_price=platinum_ticket_price;


    }
*/
    public void initialize_seatEnableDisableList(ArrayList<String> seatEnableDisableList){
        this.seatEnableDisableList=seatEnableDisableList;
    }

   /* public Map<String, Map<String, Object>> getDate_Time_and_Availability() {
        return Date_Time_and_Availability;
    }

    public void setDate_Time_and_Availability(Map<String, Map<String, Object>> date_Time_and_Availability) {
        Date_Time_and_Availability = date_Time_and_Availability;
    }
*/
    public void setSelectedDatesList(List<CalendarDay> selectedDatesList){
        this.selectedDatesList=selectedDatesList;
    }

    public void setShowTimesList(ArrayList<String> showTimesList){
        this.showTimesList=showTimesList;
    }




    /*public int getSilver_seat_rows() {
        return silver_seat_rows;
    }

    public int getGold_seat_rows() {
        return gold_seat_rows;
    }

    public int getPlatinum_seat_rows() {
        return platinum_seat_rows;
    }

    public int getSeats_per_row_silver() {
        return seats_per_row_silver;
    }

    public int getSeats_per_row_gold() {
        return seats_per_row_gold;
    }

    public int getSeats_per_row_platinum() {
        return seats_per_row_platinum;
    }

    public int getSilver_ticket_price() {
        return silver_ticket_price;
    }

    public int getGold_ticket_price() {
        return gold_ticket_price;
    }

    public int getPlatinum_ticket_price() {
        return platinum_ticket_price;
    }
*/
    public ArrayList<String> getShowTimesList() {
        return showTimesList;
    }

    public List<CalendarDay> getSelectedDatesList() {
        return selectedDatesList;
    }

    public ArrayList<String> getSeatEnableDisableList() {
        return seatEnableDisableList;
    }



   /* public class SeatRows{
        int silver_seat_rows=0,gold_seat_rows=0,platinum_seat_rows=0;

        public SeatRows(int silver_seat_rows,int gold_seat_rows,int platinum_seat_rows){
            this.silver_seat_rows=silver_seat_rows;
            this.gold_seat_rows=gold_seat_rows;
            this.platinum_seat_rows=platinum_seat_rows;

        }

        public SeatRows() {

        }
    }*/

}



