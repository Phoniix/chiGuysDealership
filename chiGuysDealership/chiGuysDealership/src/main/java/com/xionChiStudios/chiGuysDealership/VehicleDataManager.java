package com.xionChiStudios.chiGuysDealership;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleDataManager {
    private static final Logger logger = Logger.getLogger(VehicleDataManager.class.getName());
    private static final String URL = "jdbc:mysql://localhost:3306/dealership?user=root&password=yearup";
    private static final BasicDataSource vehicleInventory = new BasicDataSource();


    public static List<Vehicle> getAllVehicles(boolean displayList) {
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
                if (displayList) System.out.println(newVehicle);
                allvehicles.add(newVehicle);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "ERROR DISPLAYING VEHICLE", e);
        }
        return allvehicles;
    }

    public void insertVehicle (Vehicle vehicle) throws SQLException {
        String SQL = "INSERT INTO vehicle (id, make, model, year, vin, mileage, color, price, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,)";
        try (Connection connection = vehicleInventory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, vehicle.id());
            statement.setString(2, vehicle.make());
            statement.setString(3, vehicle.model());
            statement.setInt(4, vehicle.year());
            statement.setString(5, vehicle.vin());
            statement.setInt(6, vehicle.mileage());
            statement.setString(7, String.valueOf(vehicle.color()));
            statement.setDouble(8, vehicle.price());
            statement.setString(9, String.valueOf(vehicle.status()));
            logger.info("✅Vehicle added!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "ERROR INSERTING VEHICLE", e);
        }
    }

    public void updateVehicle (Vehicle vehicle) {
        String SQL = "UPDATE vehicle SET make = ?, model = ?, year = ?, vin = ?, mileage = ?, color = ?, price = ?, status = ? WHERE id = ?";

        try (Connection connection = vehicleInventory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, vehicle.id());
            statement.setString(2, vehicle.make());
            statement.setString(3, vehicle.model());
            statement.setInt(4, vehicle.year());
            statement.setString(5, vehicle.vin());
            statement.setInt(6, vehicle.mileage());
            statement.setString(7, String.valueOf(vehicle.color()));
            statement.setDouble(8, vehicle.price());
            statement.setString(9, String.valueOf(vehicle.status()));

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("✅Vehicle Updated!");
            } else {
                logger.warning("⚠️ No vehicle found with ID " + vehicle.id());
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "ERROR Updating QUERY");
        }
    }

    public void deleteVehicle(int id) {
        String SQl = "DELETE FROM vehicle WHERE id = ?";

        try (Connection connection = vehicleInventory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQl);
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("✅Vehicle Has Been Removed From Inventory!");
            } else {
                logger.warning("⚠️ No Vehicle Matching: " + id + " " + "Found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Vehicle getVehicleById (int id) {
        String sql = "SELECT * FROM vehicle WHERE id = ?";
        try (Connection connection = vehicleInventory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                String make = set.getString("make");
                String model = set.getString("model");
                int year = set.getInt("year");
                String vin = set.getString("vin");
                int mileage = set.getInt("mileage");
                String colorStr = set.getString("color");
                VehicleColor color = colorStr != null ? VehicleColor.valueOf(set.getString("color").toUpperCase()) : VehicleColor.UNKNOWN;
                double price = set.getDouble("price");
                VehicleStatus status = VehicleStatus.valueOf(set.getString("status").toUpperCase());

                return new Vehicle(id, make, model,year,vin,mileage,color,price,status);
            } else {
                logger.warning("No Vehicle Found with ID: " + id);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error Finding Match For Vehicle with: " + id, e);
        }
        return null;
    }
}


