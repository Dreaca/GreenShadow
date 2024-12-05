package lk.ijse.gdse.greenshadow.entity.impl;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "vehicle_entity_allocated_staff_table",
            joinColumns = @JoinColumn(name = "vehicle_code"),
            inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    private List<StaffEntity> allocatedStaff;

    private String remarks;
}
