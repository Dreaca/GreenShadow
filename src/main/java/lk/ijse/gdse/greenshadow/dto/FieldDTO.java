package lk.ijse.gdse.greenshadow.dto;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lk.ijse.gdse.greenshadow.entity.impl.CropEntity;
import lk.ijse.gdse.greenshadow.entity.impl.StaffEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldDTO {
    private String fieldCode;
    private String fieldName;
    private Point location;
    private Double extSizeofField;
    private List<CropDTO> crops;
    private List<StaffDTO> staff;
    private String fieldPicture1;
    private String fieldPicture2;
}
