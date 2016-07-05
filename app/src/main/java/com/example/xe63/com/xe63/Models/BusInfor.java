package com.example.xe63.com.xe63.Models;

import java.io.Serializable;

/**
 * Created by DatNT on 6/28/2016.
 */
public class BusInfor implements Serializable {
    private String carOwner;
    private String carPromote;
    private String fromPlace;
    private String toPlace;
    private String startTimeofDay;
    private String startTime;
    private String recepType;
    private String vehicleType;
    private int    price;

    public BusInfor(String carOwner, String carPromote, String fromPlace, String toPlace, String startTimeofDay, String startTime, String recepType, String vehicleType, int price) {
        this.carOwner = carOwner;
        this.carPromote = carPromote;
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.startTime = startTime;
        this.recepType = recepType;
        this.vehicleType = vehicleType;
        this.price = price;
        this.startTimeofDay = startTimeofDay;
    }

    public BusInfor() {
    }

    public String getStartTimeofDay() {
        return startTimeofDay;
    }

    public void setStartTimeofDay(String startTimeofDay) {
        this.startTimeofDay = startTimeofDay;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }

    public String getCarPromote() {
        return carPromote;
    }

    public void setCarPromote(String carPromote) {
        this.carPromote = carPromote;
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public String getToPlace() {
        return toPlace;
    }

    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getRecepType() {
        return recepType;
    }

    public void setRecepType(String recepType) {
        this.recepType = recepType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
