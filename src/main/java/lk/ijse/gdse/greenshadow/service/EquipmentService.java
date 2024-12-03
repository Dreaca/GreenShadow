package lk.ijse.gdse.greenshadow.service;

import lk.ijse.gdse.greenshadow.dto.impl.EquipmentDTO;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(EquipmentDTO equipment);
    List<EquipmentDTO> getAllEquipment();
    EquipmentDTO getEquipmentById(String equipmentId);
    void deleteEquipment(String equipmentId);
    void updateEquipment(EquipmentDTO equipment, String equipmentId);
}
