package lk.ijse.gdse.greenshadow.service.impl;

import lk.ijse.gdse.greenshadow.dao.CropDao;
import lk.ijse.gdse.greenshadow.dto.CropStatus;
import lk.ijse.gdse.greenshadow.dto.impl.CropDTO;
import lk.ijse.gdse.greenshadow.entity.impl.CropEntity;
import lk.ijse.gdse.greenshadow.exceptions.DataPersistException;
import lk.ijse.gdse.greenshadow.service.CropService;
import lk.ijse.gdse.greenshadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CropServiceImpl implements CropService {

    @Autowired
    private CropDao cropDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveCrop(CropDTO cropDTO) {
        CropEntity save = cropDao.save(mapping.toCropEntity(cropDTO));
        if (save == null) {
            throw new DataPersistException("Could not save crop");
        }
    }

    @Override
    public CropStatus getCrop(String cropId) {
        if(cropDao.existsById(cropId)) {
            return mapping.toCropDTO(cropDao.getReferenceById(cropId));
        }else {
            throw new DataPersistException("Could not find crop with id: " + cropId);
        }
    }

    @Override
    public List<CropDTO> getAllCrops() {
        return mapping.toCropDTOList(cropDao.findAll());
    }

    @Override
    public void deleteCrop(String cropId) {
        Optional<CropEntity> byId = cropDao.findById(cropId);
        if (byId.isPresent()) {
            cropDao.delete(byId.get());
        }else {
            throw new DataPersistException("Could not find crop with id: " + cropId);
        }
    }

    @Override
    public void updateCrop(CropDTO cropDTO, String cropId) {
        Optional<CropEntity> byId = cropDao.findById(cropId);
        if (byId.isPresent()) {
            byId.get().setCropCode(cropDTO.getCropCode());
            byId.get().setCategory(cropDTO.getCategory());
            byId.get().setCropCommonName(cropDTO.getCropCommonName());
            byId.get().setCropScientificName(cropDTO.getCropScientificName());
            byId.get().setSeason(cropDTO.getSeason());
            byId.get().setCropImage(cropDTO.getCropImage());
            byId.get().setFields(mapping.toFieldEntityList(cropDTO.getFields()));
            byId.get().setLogs(mapping.toLogEntityList(cropDTO.getLogs()));
        }
    }



}
