package lk.ijse.gdse.greenshadow.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lk.ijse.gdse.greenshadow.dao.FieldDao;
import lk.ijse.gdse.greenshadow.dao.StaffDao;
import lk.ijse.gdse.greenshadow.dto.impl.FieldDTO;
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
    @Override
    public void saveField(FieldDTO fieldDTO) {
        FieldEntity fieldEntity = mapping.toFieldEntity(fieldDTO);
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

    }

    @Override
    public void updateField(FieldDTO fieldDTO, String fieldCode) {

    }

    @Override
    public FieldDTO getField(String fieldCode) {
        return mapping.toFieldDTO(fieldDao.getReferenceById(fieldCode));
    }

    @Override
    public List<FieldDTO> getAllFields() {
        List<FieldEntity> all = fieldDao.findAll();
        List<FieldDTO> fieldDTOList = new ArrayList<>();
        all.forEach(fieldEntity -> {
           FieldDTO fieldDTO = mapping.toFieldDTO(fieldEntity);
           List<String> IdList = new ArrayList<>();
           fieldEntity.getStaff().forEach(staffEntity -> {
               IdList.add(fieldEntity.getFieldCode());
           });
           fieldDTO.setStaff(IdList);
           fieldDTOList.add(fieldDTO);
        });
        return fieldDTOList;
    }
}
