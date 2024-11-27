package lk.ijse.gdse.greenshadow.dto.impl;

import lk.ijse.gdse.greenshadow.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO implements SuperDTO {
    private String vehicleCode;
    private String LicansePlateNo;
    private String category;
    private String fuelType;
    private String status;
    private StaffDTO allocatedStaff;
    private String remarks;
}
