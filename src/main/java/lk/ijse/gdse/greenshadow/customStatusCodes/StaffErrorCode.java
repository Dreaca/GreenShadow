package lk.ijse.gdse.greenshadow.customStatusCodes;

import lk.ijse.gdse.greenshadow.dto.StaffStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffErrorCode implements StaffStatus {
    private int statusCode;
    private String statusMessage;
}
