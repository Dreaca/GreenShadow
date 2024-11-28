package lk.ijse.gdse.greenshadow.entity.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lk.ijse.gdse.greenshadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class VehicleEntity implements SuperEntity{
    @Id
    private String vehicleCode;
    private String LicansePlateNo;
    private String category;
    private String fuelType;
    private String status;
    @ManyToMany
    private List<StaffEntity> allocatedStaff;
    private String remarks;
}
