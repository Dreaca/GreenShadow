package lk.ijse.gdse.greenshadow.entity.impl;

import jakarta.persistence.*;
import lk.ijse.gdse.greenshadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CropEntity implements SuperEntity {
    @Id
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;

    @Column(columnDefinition = "LONGTEXT")
    private String cropImage;

    private String category;
    private String season;

    @OneToMany(mappedBy = "crop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FieldEntity> fields; // Bidirectional relationship

    @OneToMany(mappedBy = "crop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LogEntity> logs; // Bidirectional relationship
}
