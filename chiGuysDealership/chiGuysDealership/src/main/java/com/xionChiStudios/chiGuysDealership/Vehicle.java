package com.xionChiStudios.chiGuysDealership;

import com.phoniixUtil.Phoniix;

public record Vehicle(int id, String make, String model, int year, String vin, int mileage, VehicleColor color, double price, VehicleStatus status) {

    public Vehicle(int id, String make, String model, int year, String vin, int mileage, VehicleColor color, double price, VehicleStatus status) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.vin = vin;
        this.mileage = mileage;
        this.color = color;
        this.price = price;
        this.status = status;
    }

    @Override
    public String toString() {
        String formatter = "%-10s|%-25s|%-25s|%-4s|%-17s|%-10s|%-11s|%-11s|%-11s";
        return String.format(formatter, this.id, this.make, this.model, this.year, this.vin, this.mileage, this.color, this.price, this.status);
    }

}
