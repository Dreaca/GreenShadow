package lk.ijse.gdse.greenshadow.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lk.ijse.gdse.greenshadow.dao.CropDao;
import lk.ijse.gdse.greenshadow.dao.FieldDao;
import lk.ijse.gdse.greenshadow.dao.StaffDao;
import lk.ijse.gdse.greenshadow.dto.impl.FieldDTO;
import lk.ijse.gdse.greenshadow.entity.impl.CropEntity;
import lk.ijse.gdse.greenshadow.entity.impl.FieldEntity;
import lk.ijse.gdse.greenshadow.entity.impl.StaffEntity;
import lk.ijse.gdse.greenshadow.service.FieldService;
import lk.ijse.gdse.greenshadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FieldServiceImpl implements FieldService {
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private Mapping mapping;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private CropDao cropDao;

    @Override
    public void saveField(FieldDTO fieldDTO) {
        FieldEntity fieldEntity = new FieldEntity();
        fieldEntity.setFieldCode(fieldDTO.getFieldCode());
        fieldEntity.setFieldName(fieldDTO.getFieldName());
        fieldEntity.setLocation(fieldDTO.getLocation());
        fieldEntity.setSize(fieldDTO.getSize());
        fieldEntity.setFieldPicture1(fieldDTO.getFieldPicture1());
        fieldEntity.setFieldPicture2(fieldDTO.getFieldPicture2());

        fieldEntity.setCrop(cropDao.getReferenceById(fieldDTO.getCrop()));

        List<StaffEntity> staffList = new ArrayList<>();

        fieldDTO.getStaff().forEach(staffId -> {
            // Trim any potential whitespace and check the format
            String trimmedStaffId = staffId.replaceAll("[\\[\\]\"]", "").trim(); // Remove brackets and quotes
            Optional<StaffEntity> staffOpt = staffDao.findById(trimmedStaffId);

            if (staffOpt.isPresent()) {
                staffList.add(staffOpt.get());
            } else {
                throw new EntityNotFoundException("StaffEntity with ID " + trimmedStaffId + " not found");
            }
        });

        fieldEntity.setStaff(staffList);
        fieldDao.save(fieldEntity);
    }

    @Override
    public void deleteField(String fieldCode) {
        fieldDao.deleteById(fieldCode);
    }

    @Override
    public void updateField(FieldDTO fieldDTO, String fieldCode) {
        Optional<FieldEntity> byId = fieldDao.findById(fieldCode);
        CropEntity referenceById = cropDao.getReferenceById(fieldDTO.getCrop());
        List<StaffEntity> staffList = new ArrayList<>();

        fieldDTO.getStaff().forEach(staffId -> {
            String trimmedStaffId = staffId.replaceAll("[\\[\\]\"]", "").trim();
            Optional<StaffEntity> staffOpt = staffDao.findById(trimmedStaffId);

            if (staffOpt.isPresent()) {
                staffList.add(staffOpt.get());
            } else {
                throw new EntityNotFoundException("StaffEntity with ID " + trimmedStaffId + " not found");
            }
        });
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Field with code " + fieldCode + " not found");
        }else{
            byId.get().setFieldName(fieldDTO.getFieldName());
            byId.get().setLocation(fieldDTO.getLocation());
            byId.get().setSize(fieldDTO.getSize());
            byId.get().setCrop(referenceById);
            byId.get().setStaff(staffList);
            byId.get().setFieldPicture1(fieldDTO.getFieldPicture1());
            byId.get().setFieldPicture2(fieldDTO.getFieldPicture2());
        }

    }

    @Override
    public FieldDTO getField(String fieldCode) {
        return mapping.toFieldDTO(fieldDao.getReferenceById(fieldCode));
    }

    @Override
    public List<FieldDTO> getAllFields() {
        return mapping.toFieldDTOList(fieldDao.findAll());
    }
}
