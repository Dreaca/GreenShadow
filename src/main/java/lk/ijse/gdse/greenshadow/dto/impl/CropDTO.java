package lk.ijse.gdse.greenshadow.dto.impl;

import lk.ijse.gdse.greenshadow.dto.CropStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropDTO implements CropStatus {
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;
    private String cropImage;
    private String category;
    private String season;
    private List<FieldDTO> fields;
    private List<LogDTO> logs;
}
