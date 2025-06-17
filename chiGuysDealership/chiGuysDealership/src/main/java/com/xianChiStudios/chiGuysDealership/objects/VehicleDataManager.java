package com.xianChiStudios.chiGuysDealership.objects;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class VehicleDataManager {
    private static final Logger logger = Logger.getLogger(VehicleDataManager.class.getName());
    private static final String URL = "jdbc:mysql://localhost:3306/dealership?user=root&password=yearup";
    private static final BasicDataSource vehicleInventory = new BasicDataSource();

    public VehicleDataManager(String url) {
        vehicleInventory.setUrl(url == null
                ? URL // True, parameter is empty
                : url // False, parameter has replacement url. Is this a test?
        );
    }

    public static List<Vehicle> getVehicles() {
        List<Vehicle> allvehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicle";

        try (Connection connection = vehicleInventory.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String make = resultSet.getString("make");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                String vin = resultSet.getString("vin");
                int mileage = resultSet.getInt("mileage");
                double price = resultSet.getDouble("price");
                VehicleColor color = VehicleColor.valueOf(resultSet.getString("color").toUpperCase());
                VehicleStatus status = VehicleStatus.valueOf(resultSet.getString("status").toUpperCase());
                Vehicle newVehicle = new Vehicle(id, make, model, year,  vin, mileage, color, price, status);
                allvehicles.add(newVehicle);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "ERROR DISPLAYING VEHICLE", e);
        }
        return allvehicles;
    }

    public boolean insertVehicle (Vehicle vehicle) throws SQLException {
        String SQL = "INSERT INTO vehicle (make, model, year, vin, mileage, color, price, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = (vehicleInventory).getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, vehicle.make());
            statement.setString(2, vehicle.model());
            statement.setInt(3, vehicle.year());
            statement.setString(4, vehicle.vin());
            statement.setInt(5, vehicle.mileage());
            statement.setString(6, vehicle.colorString());
            statement.setDouble(7, vehicle.price());
            statement.setString(8, vehicle.statusString());
            statement.executeUpdate();
            System.out.println("✅Vehicle added!");
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "ERROR INSERTING VEHICLE", e);
            return false;
        }
    }

    public boolean updateVehicle (Vehicle vehicle) {
        String SQL = "UPDATE vehicle SET make = ?, model = ?, year = ?, mileage = ?, color = ?, price = ?, status = ? WHERE vin = ?";

        try (Connection connection = vehicleInventory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(8, vehicle.vin());
            statement.setString(1, vehicle.make());
            statement.setString(2, vehicle.model());
            statement.setInt(3, vehicle.year());
            statement.setInt(4, vehicle.mileage());
            statement.setString(5, vehicle.colorString());
            statement.setDouble(6, vehicle.price());
            statement.setString(7, vehicle.statusString());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅Vehicle Updated!");
                return true;
            } else {
                logger.warning("⚠️ No vehicle found with ID " + vehicle.id());
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "ERROR Updating QUERY");
            return false;
        }
    }

    public boolean deleteVehicle (Vehicle vehicle) {
        String SQl = "DELETE FROM vehicle WHERE vin = ?";

        try (Connection connection = vehicleInventory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQl)) {
            statement.setString(1, vehicle.vin());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅Vehicle Has Been Removed From Inventory!");
                return true;
            } else {
                logger.warning("⚠️ No Vehicle Matching: " + vehicle.vin() + " " + "Found");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Vehicle getVehicleByVIN (Vehicle vehicle, String url) {
        boolean found = false;
        for (Vehicle v : VehicleDataManager.getVehicles()) {
            if (vehicle.vin() == v.vin()) {
                found = true;
                return vehicle;
            }
        }
        if (!found) System.out.println("No matching record for VIN: " + vehicle.vin());
        return null;
    }

}


