package lk.ijse.gdse.greenshadow.controller;

import lk.ijse.gdse.greenshadow.customStatusCodes.GeneralErrorCode;
import lk.ijse.gdse.greenshadow.dto.StaffStatus;
import lk.ijse.gdse.greenshadow.dto.impl.StaffDTO;
import lk.ijse.gdse.greenshadow.entity.Gender;
import lk.ijse.gdse.greenshadow.entity.Role;
import lk.ijse.gdse.greenshadow.exceptions.DataPersistException;
import lk.ijse.gdse.greenshadow.service.StaffService;
import lk.ijse.gdse.greenshadow.util.Apputil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/staff")
@RequiredArgsConstructor
@CrossOrigin
public class StaffController {
    @Autowired
    private StaffService staffService;
    @PostMapping("/save")
    public ResponseEntity<Void> saveUser(@RequestBody StaffDTO staffDTO){
        StaffDTO member = new StaffDTO();
        member.setStaffId(Apputil.generateStaffID());
        member.setFirstName(staffDTO.getFirstName());
        member.setLastName(staffDTO.getLastName());
        member.setDesignation(staffDTO.getDesignation());
        member.setGender(Gender.valueOf(staffDTO.getGender().toString().toUpperCase()));
        member.setJoinedDate(staffDTO.getJoinedDate());
        member.setDOB(staffDTO.getDOB());
        member.setRole(Role.valueOf(staffDTO.getRole().toString().toUpperCase()));
        member.setAddressLine1(staffDTO.getAddressLine1());
        member.setAddressLine2(staffDTO.getAddressLine2());
        member.setAddressLine3(staffDTO.getAddressLine3());
        member.setAddressLine4(staffDTO.getAddressLine4());
        member.setAddressLine5(staffDTO.getAddressLine5());
        member.setContactNo(staffDTO.getContactNo());
        member.setEmail(staffDTO.getEmail());
        try {
            staffService.saveMember(member);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public List<StaffDTO> getStaff() {
        return staffService.getAllMembers();
    }
    @GetMapping(value = "/{staffId}")
    public StaffStatus getStaffById(@PathVariable("staffId") String staffId) {
        String regexForStaffID = "^SID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForStaffID);
        Matcher matcher = regexPattern.matcher(staffId);
        if (!matcher.matches()) {
            return new GeneralErrorCode(1,"Selected member does not exist");
        }
          return staffService.getMember(staffId);
    }
    @PutMapping(value = "/{staffId}")
    public ResponseEntity<Void> updateStaff(@RequestBody StaffDTO member,@PathVariable("staffId") String staffId) {
        String regexForStaffID = "^SID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForStaffID);
        Matcher matcher = regexPattern.matcher(staffId);
        try{
            if (!matcher.matches()|| member == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            staffService.updateMember(member,staffId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteStaff(@RequestParam("staffId") String staffId) {
        String regexForStaffID = "^SID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForStaffID);
        Matcher matcher = regexPattern.matcher(staffId);
        try{
            if (!matcher.matches()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            staffService.deleteMember(staffId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
