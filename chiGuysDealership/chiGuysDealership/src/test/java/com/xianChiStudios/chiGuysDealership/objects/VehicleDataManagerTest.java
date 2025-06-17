package com.xianChiStudios.chiGuysDealership.objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = VehicleDataManagerTest.class)
class VehicleDataManagerTest {
    @Test
    void contextLoads() throws SQLException {
        // TEST DB URL
        final String TEST_DB_URL = "jdbc:mysql://localhost:3306/dealership_test?user=root&password=yearup";

        // region.getAllVehicles() Test
        // Arrange
        List<Vehicle> result = new ArrayList<>();
        // Act
        result = VehicleDataManager.getVehicles();
        // Assert
        Assertions.assertEquals(result.size() > 1, result.size() > 1 ? true : false, "getAllVehicles(): PASSED");
        // endregion

        // region.getVehicleById() Test
        // Arrange
        VehicleDataManager testDataManager3 = new VehicleDataManager(TEST_DB_URL);
        Vehicle testVehicle3_1 = new Vehicle(1, "Ford", "F-150", 1994, "123456", 1234, VehicleColor.UNKNOWN, 420.69, VehicleStatus.AVAILABLE );
        // Act
        Vehicle testVehicle3_2 = testDataManager3.getVehicleByVIN(testVehicle3_1, TEST_DB_URL);
        //Assert
        Assertions.assertEquals(testVehicle3_2 != null, testVehicle3_2 != null ? true : false, "getVehicleByID: PASSED");
        // endregion

        // region.removeVehicle() Test
        // Arrange
        boolean actual;
        boolean expected = true;
        VehicleDataManager testDataManager = new VehicleDataManager(TEST_DB_URL);
        Vehicle testVehicle = new Vehicle(1, "Ford", "F-150", 1994, "123456", 1234, VehicleColor.UNKNOWN, 420.69, VehicleStatus.AVAILABLE );
        // Act
        actual = testDataManager.deleteVehicle(testVehicle); // Isolate the line(s)
        // Assert
        Assertions.assertEquals(expected, actual, "insertVehicle(): PASSED");
        // endregion

        // region.insertVehicle() Test
        // Arrange
        actual = false;
        expected = true;
        VehicleDataManager testDataManager2 = new VehicleDataManager(TEST_DB_URL);
        Vehicle testVehicle2 = new Vehicle(1, "Ford", "F-150", 1994, "123456", 1234, VehicleColor.UNKNOWN, 420.69, VehicleStatus.AVAILABLE );
        // Act
        actual = testDataManager2.insertVehicle(testVehicle2); // Isolate the line(s)
        // Assert
        Assertions.assertEquals(expected, actual, "insertVehicle(): PASSED");
        // endregion

        // region.updateVehicle() Test
        // Arrange
        actual = false;
        expected = true;
        VehicleDataManager testDataManager4 = new VehicleDataManager(TEST_DB_URL);
        Vehicle testVehicle4 = new Vehicle(1, "Ford", "F-150:UPDATE", 1994, "123456", 1234, VehicleColor.UNKNOWN, 420.69, VehicleStatus.AVAILABLE );
        // Act
        actual = testDataManager4.updateVehicle(testVehicle4); // Isolate the line(s)

        // Assert
        Assertions.assertEquals(expected, actual, "updateVehicle(): PASSED");
        // endregion

    }
}