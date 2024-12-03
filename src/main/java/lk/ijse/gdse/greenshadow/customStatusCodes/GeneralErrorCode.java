package lk.ijse.gdse.greenshadow.customStatusCodes;

import lk.ijse.gdse.greenshadow.dto.FieldStatus;
import lk.ijse.gdse.greenshadow.dto.StaffStatus;
import lk.ijse.gdse.greenshadow.dto.UserStatus;
import lk.ijse.gdse.greenshadow.dto.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralErrorCode implements StaffStatus, FieldStatus, UserStatus, VehicleStatus {
    private int statusCode;
    private String statusMessage;
}
