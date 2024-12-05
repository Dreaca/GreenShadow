package lk.ijse.gdse.greenshadow.controller;

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
            @RequestParam("staffList") List<String> staff,
            @RequestParam("fieldList") List<String> fields) {
        try{
            EquipmentDTO equipmentDTO = new EquipmentDTO();

            equipmentDTO.setEquipmentCode(Apputil.generateEquipmentCode());
            equipmentDTO.setName(equipmentName);
            equipmentDTO.setType(type);
            equipmentDTO.setStatus(status);
            equipmentDTO.setStaffList(staff);
            equipmentDTO.setFieldList(fields);

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
            @RequestParam("stafListf") List<String> staff,
            @RequestParam("fieldList") List<String> fields
    ){
        String regex = "^EID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(equipCode);
        if (!matcher.matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                EquipmentDTO equipmentDTO = new EquipmentDTO();

                equipmentDTO.setEquipmentCode(Apputil.generateEquipmentCode());
                equipmentDTO.setName(equipmentName);
                equipmentDTO.setType(type);
                equipmentDTO.setStatus(status);
                equipmentDTO.setStaffList(staff);
                equipmentDTO.setFieldList(fields);

                equipmentService.updateEquipment(equipmentDTO,equipCode);
                return new ResponseEntity<>(HttpStatus.OK);
            }catch (DataPersistException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
