package lk.ijse.gdse.greenshadow.dto;

import lk.ijse.gdse.greenshadow.entity.impl.StaffEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {
    private String vehicleCode;
    private String LicansePlateNo;
    private String category;
    private String fuelType;
    private String status;
    private StaffDTO allocatedStaff;
    private String remarks;
}
