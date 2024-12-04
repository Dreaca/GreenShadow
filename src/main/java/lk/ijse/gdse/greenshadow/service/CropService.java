package lk.ijse.gdse.greenshadow.service;

import lk.ijse.gdse.greenshadow.dto.CropStatus;
import lk.ijse.gdse.greenshadow.dto.impl.CropDTO;

import java.util.List;

public interface CropService {
    void saveCrop(CropDTO cropDTO);
    CropStatus getCrop(String cropId);
    List<CropDTO> getAllCrops();
    void deleteCrop(String cropId);
    void updateCrop(CropDTO cropDTO, String cropId);
}
