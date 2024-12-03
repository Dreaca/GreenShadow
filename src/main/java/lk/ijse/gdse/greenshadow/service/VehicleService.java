package lk.ijse.gdse.greenshadow.service;

import lk.ijse.gdse.greenshadow.dto.VehicleStatus;
import lk.ijse.gdse.greenshadow.dto.impl.VehicleDTO;

import java.util.List;

public interface VehicleService {
    void saveVehicle(VehicleDTO vehicle);
    VehicleStatus getVehicle(String vehicleCode);
    List<VehicleDTO> getAllVehicles();
    void deleteVehicle(String vehicleCode);
    void updateVehicle(VehicleDTO vehicle, String vehicleCode);
}
