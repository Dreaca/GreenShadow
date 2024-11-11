package lk.ijse.gdse.greenshadow.dto;

import jakarta.persistence.ManyToOne;
import lk.ijse.gdse.greenshadow.entity.impl.FieldEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropDTO {
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;
    private String cropImage;
    private String category;
    private String season;
    private FieldDTO field;
}
