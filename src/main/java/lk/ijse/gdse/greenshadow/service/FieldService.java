package lk.ijse.gdse.greenshadow.service;

import lk.ijse.gdse.greenshadow.dto.impl.FieldDTO;

import java.util.List;

public interface FieldService {
    void saveField(FieldDTO fieldDTO);
    void deleteField(String fieldCode);
    void updateField(FieldDTO fieldDTO, String fieldCode);
    FieldDTO getField(String fieldCode);
    List<FieldDTO> getAllFields();
}
