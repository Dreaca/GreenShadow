package lk.ijse.gdse.greenshadow.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.gdse.greenshadow.customStatusCodes.GeneralErrorCode;
import lk.ijse.gdse.greenshadow.dto.EquipmentStatus;
import lk.ijse.gdse.greenshadow.dto.impl.EquipmentDTO;
import lk.ijse.gdse.greenshadow.dto.impl.FieldDTO;
import lk.ijse.gdse.greenshadow.dto.impl.StaffDTO;
import lk.ijse.gdse.greenshadow.exceptions.DataPersistException;
import lk.ijse.gdse.greenshadow.service.EquipmentService;
import lk.ijse.gdse.greenshadow.util.Apputil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/equipment")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATOR','SCIENTIST')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDTO> getAllEquipment() {
        return equipmentService.getAllEquipment();
    }
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATOR')")
    @GetMapping(value = "/{equipCode}")
    public EquipmentStatus getEquipment(@PathVariable("equipCode") String equipCode) {
        String regex = "^EID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(equipCode);
        if (!matcher.matches()) {
            return new GeneralErrorCode(1,"Equipment with id "+equipCode+"could not be found");
        }else return equipmentService.getEquipmentById(equipCode);
    }
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATOR')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEquipment(
            @RequestParam("name") String equipmentName,
            @RequestParam("type") String type,
            @RequestParam("status") String status,
            @RequestParam("staffList") String staff,
            @RequestParam("fieldList") String fields) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            EquipmentDTO equipmentDTO = new EquipmentDTO();
            List<FieldDTO> fieldList = objectMapper.readValue(fields, new TypeReference<List<FieldDTO>>() {});
            List<StaffDTO> staffDTOS = objectMapper.readValue(staff, new TypeReference<List<StaffDTO>>() {});
            equipmentDTO.setEquipmentCode(Apputil.generateEquipmentCode());
            equipmentDTO.setName(equipmentName);
            equipmentDTO.setType(type);
            equipmentDTO.setStatus(status);
            equipmentDTO.setStaffList(staffDTOS);
            equipmentDTO.setFieldList(fieldList);

            equipmentService.saveEquipment(equipmentDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATOR')")
    @DeleteMapping(value = "/{equipCode}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("equipCode") String equipCode) {
        String regex = "^EID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(equipCode);
        if (!matcher.matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                equipmentService.deleteEquipment(equipCode);
                return new ResponseEntity<>(HttpStatus.OK);
            }catch (DataPersistException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATOR')")
    @PutMapping(value = "/{equipCode}")
    public ResponseEntity<Void> updateEquipment(
            @PathVariable("equipCode") String equipCode,
            @RequestParam("name") String equipmentName,
            @RequestParam("type") String type,
            @RequestParam("status") String status,
            @RequestParam("staffList") String staff,
            @RequestParam("fieldList") String fields
    ){
        String regex = "(?i)^EID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(equipCode);
        if (!matcher.matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                EquipmentDTO update = new EquipmentDTO();

                List<FieldDTO> fieldList = objectMapper.readValue(fields, new TypeReference<List<FieldDTO>>() {});
                List<StaffDTO> staffDTOS = objectMapper.readValue(staff, new TypeReference<List<StaffDTO>>() {});


                update.setName(equipmentName);
                update.setType(type);
                update.setStatus(status);
                update.setStaffList(staffDTOS);
                update.setFieldList(fieldList);

                equipmentService.updateEquipment(update,equipCode);
                return new ResponseEntity<>(HttpStatus.OK);
            }catch (DataPersistException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
