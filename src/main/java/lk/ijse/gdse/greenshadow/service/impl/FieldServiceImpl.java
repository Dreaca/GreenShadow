package lk.ijse.gdse.greenshadow.service.impl;

import lk.ijse.gdse.greenshadow.dao.FieldDao;
import lk.ijse.gdse.greenshadow.dto.impl.FieldDTO;
import lk.ijse.gdse.greenshadow.service.FieldService;
import lk.ijse.gdse.greenshadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FieldServiceImpl implements FieldService {
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveField(FieldDTO fieldDTO) {

    }

    @Override
    public void deleteField(String fieldCode) {

    }

    @Override
    public void updateField(FieldDTO fieldDTO, String fieldCode) {

    }

    @Override
    public FieldDTO getField(String fieldCode) {
        return null;
    }

    @Override
    public List<FieldDTO> getAllFields() {
        return List.of();
    }
}
