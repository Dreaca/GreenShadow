package lk.ijse.gdse.greenshadow.util;

import lk.ijse.gdse.greenshadow.dto.impl.*;
import lk.ijse.gdse.greenshadow.entity.impl.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
       return modelMapper.map(vehicleEntity,new TypeToken<VehicleDTO>() {}.getType());
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
    public List<StaffEntity> toStaffEntityList(List<StaffDTO> staffDTOList) {
        return modelMapper.map(staffDTOList, new TypeToken<List<StaffEntity>>() {}.getType());
    }
    public List<CropDTO> toCropDTOList(List<CropEntity> cropEntityList) {
      return modelMapper.map(cropEntityList, new TypeToken<List<CropDTO>>() {}.getType());
    }
    public List<CropEntity> toCropEntityList(List<CropDTO> cropDTOList) {
        return modelMapper.map(cropDTOList, new TypeToken<List<CropEntity>>() {}.getType());
    }

    public UserEntity toUserEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }
    public UserDTO toUserDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }
    public List<EquipmentEntity> toEquipmentEntityList(List<EquipmentDTO> equipmentDTOList) {
        return modelMapper.map(equipmentDTOList, new TypeToken<List<EquipmentEntity>>() {}.getType());
    }
    public List<EquipmentDTO> toEquipmentDTOList(List<EquipmentEntity> equipmentEntityList) {
       return modelMapper.map(equipmentEntityList, new TypeToken<List<EquipmentDTO>>() {}.getType());
    }
    public List<FieldEntity> toFieldEntityList(List<FieldDTO> fieldDTOList) {
        return modelMapper.map(fieldDTOList, new TypeToken<List<FieldEntity>>() {}.getType());
    }
    public List<FieldDTO> toFieldDTOList(List<FieldEntity> fieldEntityList) {
        return  modelMapper.map(fieldEntityList, new TypeToken<List<FieldDTO>>() {}.getType());
    }
    public List<LogEntity> toLogEntityList(List<LogDTO> logDTOList) {
        return modelMapper.map(logDTOList, new TypeToken<List<LogEntity>>() {}.getType());
    }
    public List<LogDTO> toLogDTOList(List<LogEntity> logEntityList) {
        return modelMapper.map(logEntityList, new TypeToken<List<LogDTO>>() {}.getType());
    }
    public List<UserDTO> toUserDTOList(List<UserEntity>userEntityList ) {
        return modelMapper.map(userEntityList, new TypeToken<List<UserDTO>>() {}.getType());
    }
    public List<UserEntity> toUserEntityList(List<UserEntity>userEntityList) {
        return modelMapper.map(userEntityList, new TypeToken<List<UserEntity>>() {}.getType());
    }
    public List<VehicleDTO> toVehicleDTOList(List<VehicleEntity> vehicleEntities){
        return modelMapper.map(vehicleEntities, new TypeToken<List<VehicleDTO>>() {}.getType());
    }
    public List<VehicleEntity> toVehicleEntityList(List<VehicleDTO> vehicleDTOList){
        return modelMapper.map(vehicleDTOList,new TypeToken<List<VehicleEntity>>(){}.getType());
    }
}
