package lk.ijse.gdse.greenshadow.service.impl;

import lk.ijse.gdse.greenshadow.dao.LogDao;
import lk.ijse.gdse.greenshadow.dto.impl.LogDTO;
import lk.ijse.gdse.greenshadow.entity.impl.LogEntity;
import lk.ijse.gdse.greenshadow.exceptions.DataPersistException;
import lk.ijse.gdse.greenshadow.service.LogService;
import lk.ijse.gdse.greenshadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveLog(LogDTO logDTO) {
        LogEntity save = logDao.save(mapping.toLogEntity(logDTO));
        if (save == null) {
            throw new DataPersistException("Cannot save log");
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
            byId.get().setLogdate(logDTO.getLogdate());
            byId.get().setLogImage(logDTO.getLogImage());
            byId.get().setField(mapping.toFieldEntity(logDTO.getFields()));
            byId.get().setCrop(mapping.toCropEntity(logDTO.getCrop()));
            byId.get().setStaff(mapping.toStaffEntityList(logDTO.getStaff()));
        }
    }

    @Override
    public List<LogDTO> getAllLogs() {
        return mapping.toLogDTOList(logDao.findAll());
    }

    @Override
    public LogDTO getLog(String logId) {
        if (logDao.findById(logId).isPresent()) {
            return mapping.toLogDTO(logDao.findById(logId).get());
        }else throw new DataPersistException("Log not found");
    }
}
