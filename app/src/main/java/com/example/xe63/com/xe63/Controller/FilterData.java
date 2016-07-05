package com.example.xe63.com.xe63.Controller;

import com.example.xe63.com.xe63.Models.BusInfor;

import java.util.ArrayList;

/**
 * Created by DatNT on 6/28/2016.
 */
public class FilterData {


    private ArrayList<BusInfor> vehicles;
    public FilterData(ArrayList<BusInfor> vehicles)
    {
        this.vehicles = vehicles;
    }

    public ArrayList<String> filterVehicleName()
    {
        ArrayList<String> vehicleName   = new ArrayList<>();
        vehicleName.add("Tất cả");
        for (BusInfor infor : vehicles){
            boolean isEqual = false;
            for (int i=0; i < vehicleName.size(); i++){
                if(infor.getCarOwner().equals(vehicleName.get(i))) {
                    isEqual = true;
                    break;
                }
            }
            if (!isEqual)
                vehicleName.add(infor.getCarOwner());

        }
        return vehicleName;
    }

    public ArrayList<String> filterFromPlace()
    {
        ArrayList<String> FromPlace   = new ArrayList<>();
        FromPlace.add("Tất cả");
        for (BusInfor infor : vehicles){
            boolean isEqual = false;
            for (int i=0; i < FromPlace.size(); i++){
                if(infor.getFromPlace().equals(FromPlace.get(i))) {
                    isEqual = true;
                    break;
                }
            }
            if (!isEqual)
                FromPlace.add(infor.getFromPlace());

        }
        return FromPlace;
    }

    public ArrayList<String> filterToPlace()
    {
        ArrayList<String> ToPlace   = new ArrayList<>();
        ToPlace.add("Tất cả");
        for (BusInfor infor : vehicles){
            boolean isEqual = false;
            for (int i=0; i < ToPlace.size(); i++){
                if(infor.getToPlace().equals(ToPlace.get(i))) {
                    isEqual = true;
                    break;
                }
            }
            if (!isEqual)
                ToPlace.add(infor.getToPlace());

        }
        return ToPlace;
    }


    public ArrayList<String> filterStartTime()
    {
        ArrayList<String> StartTime   = new ArrayList<>();
        StartTime.add("Tất cả");
        for (BusInfor infor : vehicles){
            boolean isEqual = false;
            for (int i=0; i < StartTime.size(); i++){
                if(infor.getStartTimeofDay().equals(StartTime.get(i))) {
                    isEqual = true;
                    break;
                }
            }
            if (!isEqual)
                StartTime.add(infor.getStartTimeofDay());

        }
        return StartTime;
    }

    public ArrayList<String> filterRecepType()
    {
        ArrayList<String> RecepType   = new ArrayList<>();
        RecepType.add("Tất cả");
        for (BusInfor infor : vehicles){
            boolean isEqual = false;
            for (int i=0; i < RecepType.size(); i++){
                if(infor.getRecepType().equals(RecepType.get(i))) {
                    isEqual = true;
                    break;
                }
            }
            if (!isEqual)
                RecepType.add(infor.getRecepType());

        }
        return RecepType;
    }

    public ArrayList<String> filterVehicleType()
    {
        ArrayList<String> VehicleType   = new ArrayList<>();
        VehicleType.add("Tất cả");
        for (BusInfor infor : vehicles){
            boolean isEqual = false;
            for (int i=0; i < VehicleType.size(); i++){
                if(infor.getVehicleType().equals(VehicleType.get(i))) {
                    isEqual = true;
                    break;
                }
            }
            if (!isEqual)
                VehicleType.add(infor.getVehicleType());

        }
        return VehicleType;
    }
    public ArrayList<String> filterPrice()
    {
        ArrayList<String> Price   = new ArrayList<>();
        Price.add("Mặc định");
        Price.add("Xếp giảm dần");
        Price.add("Xếp tăng dần");
        return Price;
    }
}
