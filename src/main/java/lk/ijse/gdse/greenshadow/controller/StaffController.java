package lk.ijse.gdse.greenshadow.controller;

import lk.ijse.gdse.greenshadow.customStatusCodes.StaffErrorCode;
import lk.ijse.gdse.greenshadow.dto.StaffStatus;
import lk.ijse.gdse.greenshadow.dto.impl.StaffDTO;
import lk.ijse.gdse.greenshadow.entity.Gender;
import lk.ijse.gdse.greenshadow.entity.Role;
import lk.ijse.gdse.greenshadow.exceptions.DataPersistException;
import lk.ijse.gdse.greenshadow.service.StaffService;
import lk.ijse.gdse.greenshadow.service.UserService;
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
@RequestMapping("auth/v1/staff")
@RequiredArgsConstructor
@CrossOrigin
public class StaffController {
    @Autowired
    private StaffService staffService;
    @PostMapping
    public ResponseEntity<Void> saveUser(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("designation") String designation,
            @RequestParam("gender") String gender,
            @RequestParam("joinedDate") String joinedDate,
            @RequestParam("dob") String dob,
            @RequestParam("role") String role,
            @RequestParam("address1") String address1,
            @RequestParam("address2") String address2,
            @RequestParam("address3") String address3,
            @RequestParam("address4") String address4,
            @RequestParam("address5") String address5,
            @RequestParam("contactNo") String contactNo,
            @RequestParam("email") String email
    ){
        StaffDTO member = new StaffDTO();
        member.setStaffId(Apputil.generateStaffID());
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setDesignation(designation);
        member.setGender(Gender.valueOf(gender.toUpperCase()));
        member.setJoinedDate(Date.valueOf(joinedDate));
        member.setDOB(Date.valueOf(dob));
        member.setRole(Role.valueOf(role.toUpperCase()));
        member.setAddressLine1(address1);
        member.setAddressLine2(address2);
        member.setAddressLine3(address3);
        member.setAddressLine4(address4);
        member.setAddressLine5(address5);
        member.setContactNo(contactNo);
        member.setEmail(email);
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
            return new StaffErrorCode(1,"Selected member does not exist");
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