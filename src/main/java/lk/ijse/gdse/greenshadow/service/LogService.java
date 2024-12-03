package lk.ijse.gdse.greenshadow.service;

import lk.ijse.gdse.greenshadow.dto.impl.LogDTO;

import java.util.List;

public interface LogService {
    void saveLog(LogDTO logDTO);
    void deleteLog(String logId);
    void updateLog(LogDTO logDTO, String logId);
    List<LogDTO> getAllLogs();
    LogDTO getLog(String logId);

}
