package lk.ijse.gdse.greenshadow.service.impl;

import lk.ijse.gdse.greenshadow.dao.EquipmentDao;
import lk.ijse.gdse.greenshadow.dto.impl.EquipmentDTO;
import lk.ijse.gdse.greenshadow.entity.impl.EquipmentEntity;
import lk.ijse.gdse.greenshadow.exceptions.DataPersistException;
import lk.ijse.gdse.greenshadow.service.EquipmentService;
import lk.ijse.gdse.greenshadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentDao equipmentDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveEquipment(EquipmentDTO equipment) {
        EquipmentEntity save = equipmentDao.save(mapping.toEquipmentEntity(equipment));
        if (equipment == null) {
            throw new DataPersistException("Can't save equipment");
        }
    }

    @Override
    public List<EquipmentDTO> getAllEquipment() {
        return mapping.toEquipmentDTOList(equipmentDao.findAll());
    }

    @Override
    public EquipmentDTO getEquipmentById(String equipmentId) {
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
            byId.get().setField(mapping.toFieldEntityList(equipment.getField()));
            byId.get().setStatus(equipment.getStatus());
            byId.get().setStaff(mapping.toStaffEntityList(equipment.getStaff()));
        }
    }
}
