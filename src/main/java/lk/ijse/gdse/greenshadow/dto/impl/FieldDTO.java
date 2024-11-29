package lk.ijse.gdse.greenshadow.dto.impl;

import lk.ijse.gdse.greenshadow.dto.FieldStatus;
import lk.ijse.gdse.greenshadow.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldDTO implements FieldStatus {
    private String fieldCode;
    private String fieldName;
    private Point location;
    private Double extSizeofField;
    private CropDTO cropDTO;
    private List<StaffDTO> staff;
    private String fieldPicture1;
    private String fieldPicture2;
}
