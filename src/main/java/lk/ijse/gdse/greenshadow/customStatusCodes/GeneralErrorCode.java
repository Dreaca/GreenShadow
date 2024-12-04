package lk.ijse.gdse.greenshadow.customStatusCodes;

import lk.ijse.gdse.greenshadow.dto.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralErrorCode implements StaffStatus, FieldStatus, UserStatus, VehicleStatus, EquipmentStatus,LogStatus,CropStatus {
    private int statusCode;
    private String statusMessage;
}
