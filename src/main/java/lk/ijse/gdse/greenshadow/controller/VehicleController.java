package lk.ijse.gdse.greenshadow.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.gdse.greenshadow.customStatusCodes.GeneralErrorCode;
import lk.ijse.gdse.greenshadow.dto.VehicleStatus;
import lk.ijse.gdse.greenshadow.dto.impl.StaffDTO;
import lk.ijse.gdse.greenshadow.dto.impl.VehicleDTO;
import lk.ijse.gdse.greenshadow.exceptions.DataPersistException;
import lk.ijse.gdse.greenshadow.service.VehicleService;
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
@RequestMapping("api/v1/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATOR','SCIENTIST')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleDTO> getVehicles() {
       return vehicleService.getAllVehicles();
    }
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATOR')")
    @GetMapping(value = "/{vehicleCode}")
    public VehicleStatus getVehicle(@PathVariable("vehicleCode") String vehicleCode) {
        String regex = "^VID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(vehicleCode);
        if (!matcher.matches()) {
            return new GeneralErrorCode(1, "Could not find the field");
        }
        else return vehicleService.getVehicle(vehicleCode);
    }
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATOR')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createVehicle(
            @RequestParam("licensePlateNo") String licensePlateNo,
            @RequestParam("category") String category,
            @RequestParam("fuelType") String fuelType,
            @RequestParam("status") String status,
            @RequestParam("allocatedStaff") String staff,
            @RequestParam("remarks") String remarks
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<StaffDTO> staffDTOS = objectMapper.readValue(staff, new TypeReference<List<StaffDTO>>() {});
            VehicleDTO vehicleDTO = new VehicleDTO();

            vehicleDTO.setVehicleCode(Apputil.generateVehicleCode());
            vehicleDTO.setLicensePlateNo(licensePlateNo);
            vehicleDTO.setCategory(category);
            vehicleDTO.setFuelType(fuelType);
            vehicleDTO.setStatus(status);
            vehicleDTO.setAllocatedStaff(staffDTOS);
            vehicleDTO.setRemarks(remarks);

            vehicleService.saveVehicle(vehicleDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATOR')")
    @DeleteMapping(value = "/{vehicleCode}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("vehicleCode") String vehicleCode) {
        String regex = "^VID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(vehicleCode);
        if (!matcher.matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            try {
                vehicleService.deleteVehicle(vehicleCode);
                return new ResponseEntity<>(HttpStatus.OK);
            }catch (DataPersistException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATOR')")
    @PutMapping(value = "/{vehicleCode}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateVehicle(@PathVariable("vehicleCode")String vehicleCode
    , @RequestParam("licensePlateNo") String licensePlateNo
    , @RequestParam("category") String category
    , @RequestParam("fuelType") String fuelType
    , @RequestParam("status") String status
    , @RequestParam("allocatedStaff") String staff
    , @RequestParam("remarks") String remarks){
        String regex = "^VID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(vehicleCode);
        if (!matcher.matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            ObjectMapper objectMapper = new ObjectMapper();
            try{
                List<StaffDTO> staffDTOS = objectMapper.readValue(staff, new TypeReference<List<StaffDTO>>() {});
                VehicleDTO vehicleDTO = new VehicleDTO();
                vehicleDTO.setLicensePlateNo(licensePlateNo);
                vehicleDTO.setCategory(category);
                vehicleDTO.setFuelType(fuelType);
                vehicleDTO.setStatus(status);
                vehicleDTO.setAllocatedStaff(staffDTOS);
                vehicleDTO.setRemarks(remarks);

                System.out.println(vehicleDTO);
                vehicleService.updateVehicle(vehicleDTO,vehicleCode);
                return new ResponseEntity<>(HttpStatus.OK);
            }catch (DataPersistException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

}
