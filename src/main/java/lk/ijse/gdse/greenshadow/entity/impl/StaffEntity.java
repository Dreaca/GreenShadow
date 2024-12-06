package lk.ijse.gdse.greenshadow.entity.impl;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lk.ijse.gdse.greenshadow.entity.Gender;
import lk.ijse.gdse.greenshadow.entity.Role;
import lk.ijse.gdse.greenshadow.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffEntity implements SuperEntity {
    @Id
    private String staffId;
    private String firstName;
    private String lastName;
    private String designation;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date joinedDate;
    private Date DOB;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String contactNo;
    private String email;
    private Role role;
    @ManyToMany
    private List<FieldEntity> fields;
    @ManyToMany(mappedBy = "allocatedStaff", fetch = FetchType.LAZY)
    private List<VehicleEntity> vehicles;
    @ManyToOne
    private EquipmentEntity equipment;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "staff_logs",
            joinColumns = @JoinColumn(name = "staff_id", referencedColumnName = "staffId"),
            inverseJoinColumns = @JoinColumn(name = "log_id", referencedColumnName = "logCode")
    )
    private List<LogEntity> logs;
    @OneToOne
    @JoinColumn(name = "user_email",referencedColumnName = "email")
    private UserEntity user;


}
