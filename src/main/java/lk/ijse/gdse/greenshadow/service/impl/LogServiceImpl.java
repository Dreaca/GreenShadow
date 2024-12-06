package lk.ijse.gdse.greenshadow.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lk.ijse.gdse.greenshadow.dao.CropDao;
import lk.ijse.gdse.greenshadow.dao.FieldDao;
import lk.ijse.gdse.greenshadow.dao.LogDao;
import lk.ijse.gdse.greenshadow.dao.StaffDao;
import lk.ijse.gdse.greenshadow.dto.LogStatus;
import lk.ijse.gdse.greenshadow.dto.impl.LogDTO;
import lk.ijse.gdse.greenshadow.entity.impl.CropEntity;
import lk.ijse.gdse.greenshadow.entity.impl.FieldEntity;
import lk.ijse.gdse.greenshadow.entity.impl.LogEntity;
import lk.ijse.gdse.greenshadow.entity.impl.StaffEntity;
import lk.ijse.gdse.greenshadow.exceptions.DataPersistException;
import lk.ijse.gdse.greenshadow.service.LogService;
import lk.ijse.gdse.greenshadow.util.Apputil;
import lk.ijse.gdse.greenshadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;
    @Autowired
    private Mapping mapping;
    @Autowired
    private CropDao cropDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private FieldDao fieldDao;
    @Override
    public void saveLog(LogDTO logDTO) {
        LogEntity save = logDao.save(mapping.toLogEntity(logDTO));
        if(save == null) {
            throw new DataPersistException("could not save log");
        }
    }

    @Override
    public void deleteLog(String logId) {
        Optional<LogEntity> byId = logDao.findById(logId);
        if (byId.isPresent()) {
            logDao.delete(byId.get());
        }else throw new DataPersistException("Log not found");
    }

    @Override
    public void updateLog(LogDTO logDTO, String logId) {
        Optional<LogEntity> byId = logDao.findById(logId);
        if (byId.isPresent()) {
            byId.get().setLogDate(logDTO.getLogdate());
            byId.get().setLogImage(logDTO.getLogImage());
            byId.get().setObservation(logDTO.getObservation());
            byId.get().setCrop(mapping.toCropEntity(logDTO.getCrop()));
            byId.get().setField(mapping.toFieldEntity(logDTO.getFields()));
            byId.get().setStaff(mapping.toStaffEntityList(logDTO.getStaff()));
        }
    }

    @Override
    public List<LogDTO> getAllLogs() {
        return mapping.toLogDTOList(logDao.findAll());
    }

    @Override
    public LogStatus getLog(String logId) {
        if (logDao.findById(logId).isPresent()) {
            return mapping.toLogDTO(logDao.findById(logId).get());
        }else throw new DataPersistException("Log not found");
    }
    public List<StaffEntity> getStaffList(List<String> idList){
        List<StaffEntity> list = new ArrayList<>();

        idList.forEach(staffId -> {
            String trimmedStaffId = Apputil.trimmedId(staffId);
            Optional<StaffEntity> staffOpt = staffDao.findById(trimmedStaffId);

            if (staffOpt.isPresent()) {
                list.add(staffOpt.get());
            } else {
                throw new EntityNotFoundException("StaffEntity with ID " + trimmedStaffId + " not found");
            }
        });
        return list;
    }

}
