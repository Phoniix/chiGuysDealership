package com.xianChiStudios.chiGuysDealership.objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VehicleController {
    VehicleDataManager VDM;

    @Autowired
    public VehicleController(VehicleDataManager vehicleDataManager) {
         this.VDM = vehicleDataManager;
    }

//    public static List<Vehicle> getAllVehicles () {
//        return VDM.
//    }
}
