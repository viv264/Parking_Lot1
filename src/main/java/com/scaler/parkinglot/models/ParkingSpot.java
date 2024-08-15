package com.scaler.parkinglot.models;

import java.util.List;

public class ParkingSpot extends BaseModel{
    private int number;
    private List<VehicleType> supportedVehicle;
    private SpotStatus spotStatus;
    private Vehicle vehicle;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<VehicleType> getSupportedVehicle() {
        return supportedVehicle;
    }

    public void setSupportedVehicle(List<VehicleType> supportedVehicle) {
        this.supportedVehicle = supportedVehicle;
    }

    public SpotStatus getSpotStatus() {
        return spotStatus;
    }

    public void setSpotStatus(SpotStatus spotStatus) {
        this.spotStatus = spotStatus;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
