package lk.ijse.gdse.greenshadow.util;

import lk.ijse.gdse.greenshadow.dto.*;
import lk.ijse.gdse.greenshadow.entity.impl.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    public StaffEntity toStaffEntity(StaffDTO staffDTO) {
        return modelMapper.map(staffDTO, StaffEntity.class);
    }
    public StaffDTO toStaffDTO(StaffEntity staffEntity) {
        return modelMapper.map(staffEntity, StaffDTO.class);
    }
    public CropEntity toCropEntity(CropDTO cropDTO) {
        return modelMapper.map(cropDTO, CropEntity.class);
    }
    public CropDTO toCropDTO(CropEntity cropEntity) {
        return modelMapper.map(cropEntity, CropDTO.class);
    }
    public FieldEntity toFieldEntity(FieldDTO fieldDTO) {
        return modelMapper.map(fieldDTO, FieldEntity.class);
    }
    public FieldDTO toFieldDTO(FieldEntity fieldEntity) {
        return modelMapper.map(fieldEntity, FieldDTO.class);
    }
    public EquipmentDTO toEquipmentDTO(EquipmentEntity equipmentEntity) {
        return modelMapper.map(equipmentEntity, EquipmentDTO.class);
    }
    public EquipmentEntity toEquipmentEntity(EquipmentDTO equipmentDTO) {
        return modelMapper.map(equipmentDTO, EquipmentEntity.class);
    }
    public VehicleEntity toVehicleEntity (VehicleDTO vehicleDTO) {
        return modelMapper.map(vehicleDTO, VehicleEntity.class);
    }
    public VehicleDTO toVehicleDTO(VehicleEntity vehicleEntity) {
        return modelMapper.map(vehicleEntity, VehicleDTO.class);
    }
    public LogEntity toLogEntity(LogDTO logDTO) {
        return modelMapper.map(logDTO, LogEntity.class);
    }
    public LogDTO toLogDTO(LogEntity logEntity) {
        return modelMapper.map(logEntity, LogDTO.class);
    }
    public List<StaffDTO> toStaffDTOList(List<StaffEntity> staffEntityList) {
        return modelMapper.map(staffEntityList, new TypeToken<List<StaffDTO>>() {}.getType());
    }
    public List<CropDTO> toCropDTOList(List<CropEntity> cropEntityList) {
        return modelMapper.map(cropEntityList, new TypeToken<List<CropDTO>>() {}.getType());
    }
    public List<FieldDTO> toFieldDTOList(List<FieldEntity> fieldEntityList) {
        return modelMapper.map(fieldEntityList, new TypeToken<List<FieldDTO>>() {}.getType());
    }

}
