package lk.ijse.gdse.greenshadow.dto.impl;

import lk.ijse.gdse.greenshadow.dto.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO implements VehicleStatus {
    private String vehicleCode;
    private String licensePlateNo;
    private String category;
    private String fuelType;
    private String status;
    private List<StaffDTO> allocatedStaff;
    private String remarks;
}
