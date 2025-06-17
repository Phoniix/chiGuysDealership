package com.xianChiStudios.chiGuysDealership.objects;

import com.phoniixUtil.Phoniix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public record Vehicle(int id, String make, String model, int year, String vin, int mileage, VehicleColor color, double price, VehicleStatus status) {

    @Autowired
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

    public String colorString() {
        return Phoniix.autoCap(String.valueOf(this.color));
    }
    public String statusString() {
        return Phoniix.autoCap(String.valueOf(this.status));
    }

    @Override
    public String toString() {
        String formatter = "%-10s|%-25s|%-25s|%-4s|%-17s|%-10s|%-11s|%-11s|%-11s";
        return String.format(formatter, this.id, this.make, this.model, this.year, this.vin, this.mileage, this.color, this.price, this.status);
    }

}
