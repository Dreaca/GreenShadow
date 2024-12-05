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
        CropDTO cropDTO = new CropDTO();
        List<String> fields = new ArrayList<>();
        List<String> logs = new ArrayList<>();
        cropDTO.setCropCode(cropEntity.getCropCode());
        cropDTO.setCropCommonName(cropEntity.getCropCommonName());
        cropDTO.setSeason(cropEntity.getSeason());
        cropDTO.setCropScientificName(cropEntity.getCropScientificName());
        cropDTO.setCropImage(cropEntity.getCropImage());
        cropDTO.setCategory(cropEntity.getCategory());
        cropEntity.getFields().forEach(field -> {
            fields.add(field.getFieldCode());
        });
        cropDTO.setFields(fields);
        cropEntity.getLogs().forEach(log -> {
            logs.add(log.getLogcode());
        });
        cropDTO.setLogs(logs);
        return cropDTO;
    }
    public FieldEntity toFieldEntity(FieldDTO fieldDTO) {
        return modelMapper.map(fieldDTO, FieldEntity.class);
    }
    public FieldDTO toFieldDTO(FieldEntity fieldEntity) {
        FieldDTO fieldDTO = new FieldDTO();
        List<String> list = new ArrayList<>();

        fieldDTO.setFieldCode(fieldEntity.getFieldCode());
        fieldDTO.setFieldName(fieldEntity.getFieldName());
        fieldDTO.setFieldPicture1(fieldEntity.getFieldPicture1());
        fieldDTO.setFieldPicture2(fieldEntity.getFieldPicture2());
        fieldDTO.setSize(fieldEntity.getSize());
        fieldDTO.setLocation(fieldEntity.getLocation());
        fieldDTO.setCrop(fieldEntity.getCrop().getCropCode());

        fieldEntity.getStaff().forEach(staffEntity -> {
            list.add(staffEntity.getStaffId());
        });
        fieldDTO.setStaff(list);

        return fieldDTO;
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
    public List<StaffEntity> toStaffEntityList(List<StaffDTO> staffDTOList) {
        return modelMapper.map(staffDTOList, new TypeToken<List<StaffEntity>>() {}.getType());
    }
    public List<CropDTO> toCropDTOList(List<CropEntity> cropEntityList) {
       List<CropDTO> list = new ArrayList<>();
        List<String> fieldIds = new ArrayList<>();
        List<String> logIds = new ArrayList<>();
       cropEntityList.forEach(entity -> {
           CropDTO cropDTO = new CropDTO();
           cropDTO.setCropCode(entity.getCropCode());
           cropDTO.setCropCommonName(entity.getCropCommonName());
           cropDTO.setCropScientificName(entity.getCropScientificName());
           cropDTO.setSeason(entity.getSeason());
           cropDTO.setCropImage(entity.getCropImage());
           cropDTO.setCategory(entity.getCategory());

           entity.getFields().forEach(fieldEntity -> {
               fieldIds.add(fieldEntity.getFieldCode());
           });
           entity.getLogs().forEach(logEntity -> {
              logIds.add(logEntity.getLogcode());
           });
           cropDTO.setFields(fieldIds);
           cropDTO.setLogs(logIds);
           list.add(cropDTO);
       });
       return list;
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
        List<EquipmentDTO> sending = new ArrayList<>();
        List<String> staffIds = new ArrayList<>();
        ArrayList<String> fields = new ArrayList<>();

        equipmentEntityList.forEach(entity -> {
            EquipmentDTO equipmentDTO = new EquipmentDTO();
            equipmentDTO.setEquipmentCode(entity.getEquipmentCode());
            equipmentDTO.setName(entity.getName());
            equipmentDTO.setType(entity.getType());
            equipmentDTO.setStatus(entity.getStatus());
            entity.getStaff().forEach(staffEntity -> {
                staffIds.add(staffEntity.getStaffId());
            });
            entity.getField().forEach(fieldEntity -> {
                fields.add(fieldEntity.getFieldCode());
            });
            equipmentDTO.setStaffList(staffIds);
            equipmentDTO.setFieldList(fields);
            sending.add(equipmentDTO);
        });
        return sending;
    }
    public List<FieldEntity> toFieldEntityList(List<FieldDTO> fieldDTOList) {
        return modelMapper.map(fieldDTOList, new TypeToken<List<FieldEntity>>() {}.getType());
    }
    public List<FieldDTO> toFieldDTOList(List<FieldEntity> fieldEntityList) {
        List<FieldDTO> fieldDTOList = new ArrayList<>();
        fieldEntityList.forEach(fieldEntity -> {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setFieldCode(fieldEntity.getFieldCode());
            fieldDTO.setFieldName(fieldEntity.getFieldName());
            fieldDTO.setLocation(fieldEntity.getLocation());
            fieldDTO.setSize(fieldEntity.getSize());
            fieldDTO.setFieldPicture1(fieldEntity.getFieldPicture1());
            fieldDTO.setFieldPicture2(fieldEntity.getFieldPicture2());
            fieldDTO.setCrop(fieldEntity.getCrop().getCropCode());

            List<String> staffList = new ArrayList<>();
            fieldEntity.getStaff().forEach(staffEntity -> {
                staffList.add(
                        staffEntity.getStaffId()
                );
            });
            fieldDTO.setStaff(staffList);
            List<String> equipmentlist = new ArrayList<>();
            fieldEntity.getEquipment().forEach(equipmentEntity -> {
                equipmentlist.add(equipmentEntity.getEquipmentCode());
            });
            fieldDTO.setEquipment(equipmentlist);
            fieldDTOList.add(fieldDTO);

        });
        return fieldDTOList;
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
        return modelMapper.map(vehicleEntities,new TypeToken<List<VehicleDTO>>(){}.getType());
    }
    public List<VehicleEntity> toVehicleEntityList(List<VehicleDTO> vehicleDTOList){
        return modelMapper.map(vehicleDTOList,new TypeToken<List<VehicleEntity>>(){}.getType());
    }
}
