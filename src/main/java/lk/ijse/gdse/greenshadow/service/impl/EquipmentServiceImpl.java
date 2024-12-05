package lk.ijse.gdse.greenshadow.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lk.ijse.gdse.greenshadow.dao.EquipmentDao;
import lk.ijse.gdse.greenshadow.dao.FieldDao;
import lk.ijse.gdse.greenshadow.dao.StaffDao;
import lk.ijse.gdse.greenshadow.dto.EquipmentStatus;
import lk.ijse.gdse.greenshadow.dto.impl.EquipmentDTO;
import lk.ijse.gdse.greenshadow.entity.impl.EquipmentEntity;
import lk.ijse.gdse.greenshadow.entity.impl.FieldEntity;
import lk.ijse.gdse.greenshadow.entity.impl.StaffEntity;
import lk.ijse.gdse.greenshadow.exceptions.DataPersistException;
import lk.ijse.gdse.greenshadow.service.EquipmentService;
import lk.ijse.gdse.greenshadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentDao equipmentDao;
    @Autowired
    private Mapping mapping;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private FieldDao fieldDao;
    @Override
    public void saveEquipment(EquipmentDTO equipment) {
        EquipmentEntity equipmentEntity = new EquipmentEntity();
        List<StaffEntity> list = new ArrayList<>();
        List<FieldEntity> flist = new ArrayList<>();

        equipmentEntity.setName(equipment.getName());
        equipmentEntity.setStatus(equipment.getStatus());
        equipmentEntity.setEquipmentCode(equipment.getEquipmentCode());
        equipmentEntity.setType(equipment.getType());

        if(equipment.getStaffList() != null) {
            equipment.getStaffList().forEach(staffId -> {
                // Trim any potential whitespace and check the format
                String trimmedStaffId = staffId.replaceAll("[\\[\\]\"]", "").trim(); // Remove brackets and quotes
                Optional<StaffEntity> staffOpt = staffDao.findById(trimmedStaffId);

                if (staffOpt.isPresent()) {
                    list.add(staffOpt.get());
                } else {
                    throw new EntityNotFoundException("StaffEntity with ID " + trimmedStaffId + " not found");
                }
            });
        }else {
            equipment.setStaffList(new ArrayList<>());
        }
        if(equipment.getFieldList() != null) {
            equipment.getFieldList().forEach(fieldId -> {
                String trimmed = fieldId.replaceAll("[\\[\\]\"]", "").trim();
                fieldDao.findById(trimmed).ifPresent(flist::add);
            });
        }else {
             equipment.setFieldList(new ArrayList<>());
        }
        equipmentEntity.setField(flist);
        equipmentEntity.setStaff(list);
        equipmentDao.save(equipmentEntity);
    }

    @Override
    public List<EquipmentDTO> getAllEquipment() {
        return mapping.toEquipmentDTOList(equipmentDao.findAll());
    }

    @Override
    public EquipmentStatus getEquipmentById(String equipmentId) {
        Optional<EquipmentEntity> byId = equipmentDao.findById(equipmentId);
        if (byId.isPresent()) {
            return mapping.toEquipmentDTO(byId.get());
        }else throw new DataPersistException("Can't find equipment with id: " + equipmentId);
    }

    @Override
    public void deleteEquipment(String equipmentId) {
        Optional<EquipmentEntity> byId = equipmentDao.findById(equipmentId);
        if (byId.isPresent()) {
            equipmentDao.delete(byId.get());
        }else throw new DataPersistException("Can't find equipment with id: " + equipmentId);
    }

    @Override
    public void updateEquipment(EquipmentDTO equipment, String equipmentId) {
        Optional<EquipmentEntity> byId = equipmentDao.findById(equipmentId);
        if (byId.isPresent()) {
            byId.get().setName(equipment.getName());
            byId.get().setType(equipment.getType());
            //byId.get().setField(mapping.toFieldEntityList(equipment.getFieldList()));
            byId.get().setStatus(equipment.getStatus());
            //byId.get().setStaff(mapping.toStaffEntityList(equipment.getStaffList()));
        }
    }
}
