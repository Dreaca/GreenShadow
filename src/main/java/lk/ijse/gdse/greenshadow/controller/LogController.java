package lk.ijse.gdse.greenshadow.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.gdse.greenshadow.customStatusCodes.GeneralErrorCode;
import lk.ijse.gdse.greenshadow.dto.LogStatus;
import lk.ijse.gdse.greenshadow.dto.impl.CropDTO;
import lk.ijse.gdse.greenshadow.dto.impl.FieldDTO;
import lk.ijse.gdse.greenshadow.dto.impl.LogDTO;
import lk.ijse.gdse.greenshadow.dto.impl.StaffDTO;
import lk.ijse.gdse.greenshadow.exceptions.DataPersistException;
import lk.ijse.gdse.greenshadow.service.LogService;
import lk.ijse.gdse.greenshadow.util.Apputil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/logs")
public class LogController {
    @Autowired
    private LogService logService;
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @DeleteMapping(value = "/{logCode}")
    public ResponseEntity<Void> delete(@PathVariable("logCode") String logCode) {
        String regex = "^LOG[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(logCode);
        if(!matcher.matches()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try{
                logService.deleteLog(logCode);
                return new ResponseEntity<>(HttpStatus.OK);
            }catch(DataPersistException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch(Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @GetMapping(value = "/{logCode}")
    public LogStatus get(@PathVariable("logCode") String logCode) {
        String regex = "^LOG[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(logCode);
        if(!matcher.matches()){
            return new GeneralErrorCode(1,"Could not find the log with the id "+logCode);
        }else return logService.getLog(logCode);
    }
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @GetMapping
    public List<LogDTO> getLogs() {
        return logService.getAllLogs();
    }
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> save(
            @RequestParam("logDate")String logDate,
            @RequestParam("observation") String observation,
            @RequestParam("logImage") MultipartFile image,
            @RequestParam("field") String fields,
            @RequestParam("crop") String crop,
            @RequestParam("staff") String staff) {
        String fieldImage = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            FieldDTO fieldList = objectMapper.readValue(fields, new TypeReference<FieldDTO>() {});
            List<StaffDTO> staffDTOS = objectMapper.readValue(staff, new TypeReference<List<StaffDTO>>() {});
            CropDTO cropDTO = objectMapper.readValue(crop,new TypeReference<CropDTO>(){});
            byte[] imageBytes = image.getBytes();
            fieldImage = Apputil.convertToBase64(imageBytes);

            LogDTO logDTO = new LogDTO();

            logDTO.setLogcode(Apputil.generateLogCode());
            logDTO.setLogdate(java.sql.Date.valueOf(logDate));
            logDTO.setObservation(observation);
            logDTO.setLogImage(fieldImage);
            logDTO.setFields(fieldList);
            logDTO.setCrop(cropDTO);
            logDTO.setStaff(staffDTOS);

            logService.saveLog(logDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PutMapping(value = "/{logCode}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(
            @RequestParam("logDate")String logDate,
            @RequestParam("observation") String observation,
            @RequestParam("logImage") MultipartFile image,
            @RequestParam("field") String fields,
            @RequestParam("crop") String crop,
            @RequestParam("staff") String staff,
            @PathVariable("logCode") String logCode
    ){
        String regex = "^LOG[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(logCode);
        if(!matcher.matches()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            String fieldImage = null;
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                FieldDTO fieldList = objectMapper.readValue(fields, new TypeReference<FieldDTO>() {});
                List<StaffDTO> staffDTOS = objectMapper.readValue(staff, new TypeReference<List<StaffDTO>>() {});
                CropDTO cropDTO = objectMapper.readValue(crop,new TypeReference<CropDTO>(){});
                byte[] imageBytes = image.getBytes();
                fieldImage = Apputil.convertToBase64(imageBytes);

                LogDTO logDTO = new LogDTO();


                logDTO.setLogdate(java.sql.Date.valueOf(logDate));
                logDTO.setObservation(observation);
                logDTO.setLogImage(fieldImage);
                logDTO.setFields(fieldList);
                logDTO.setCrop(cropDTO);
                logDTO.setStaff(staffDTOS);

                logService.updateLog(logDTO,logCode);
                return new ResponseEntity<>(HttpStatus.OK);

            }catch (DataPersistException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
