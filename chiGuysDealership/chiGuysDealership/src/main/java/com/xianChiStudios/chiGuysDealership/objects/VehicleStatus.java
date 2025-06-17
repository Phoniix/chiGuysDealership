package com.xianChiStudios.chiGuysDealership.objects;

import com.phoniixUtil.Phoniix;

public enum VehicleStatus {
    AVAILABLE,
    MAINTENANCE,
    SOLD;

    public String getVehicleStatusString (VehicleStatus vehicleStatus) {
        return Phoniix.autoCap(String.valueOf(vehicleStatus));
    }
}
