package lk.ijse.gdse.greenshadow.service.impl;

import lk.ijse.gdse.greenshadow.customStatusCodes.GeneralErrorCode;
import lk.ijse.gdse.greenshadow.dao.StaffDao;
import lk.ijse.gdse.greenshadow.dao.UserDao;
import lk.ijse.gdse.greenshadow.dto.StaffStatus;
import lk.ijse.gdse.greenshadow.dto.impl.StaffDTO;
import lk.ijse.gdse.greenshadow.entity.impl.StaffEntity;
import lk.ijse.gdse.greenshadow.entity.impl.UserEntity;
import lk.ijse.gdse.greenshadow.exceptions.DataPersistException;
import lk.ijse.gdse.greenshadow.service.StaffService;
import lk.ijse.gdse.greenshadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveMember(StaffDTO member) {
        StaffEntity save = staffDao.save(mapping.toStaffEntity(member));

        if (save == null) {
            throw new DataPersistException("Could not save member");
        }else {
            Optional<UserEntity> byEmail = userDao.findByEmail(member.getEmail());
            byEmail.ifPresent(userEntity -> userEntity.setRole(member.getRole()));
        }
    }

    @Override
    public void deleteMember(String memberId) {
        Optional<StaffEntity> member = staffDao.findById(memberId);
        if (member.isPresent()) {
            staffDao.deleteById(memberId);
        }else {
            throw new DataPersistException("Could not delete member");
        }
    }

    @Override
    public void updateMember(StaffDTO member, String memberId) {
        Optional<StaffEntity> memberEntity = staffDao.findById(memberId);
        if (!memberEntity.isPresent()) {
            throw new DataPersistException("Could not update member");
        }else{
            memberEntity.get().setStaffId(memberId);
            memberEntity.get().setFirstName(member.getFirstName());
            memberEntity.get().setLastName(member.getLastName());
            memberEntity.get().setDesignation(member.getDesignation());
            memberEntity.get().setEmail(member.getEmail());
            memberEntity.get().setGender(member.getGender());
            memberEntity.get().setAddressLine1(member.getAddressLine1());
            memberEntity.get().setAddressLine2(member.getAddressLine2());
            memberEntity.get().setAddressLine3(member.getAddressLine3());
            memberEntity.get().setAddressLine4(member.getAddressLine4());
            memberEntity.get().setAddressLine5(member.getAddressLine5());
            memberEntity.get().setRole(member.getRole());
            memberEntity.get().setDOB(member.getDOB());
            memberEntity.get().setJoinedDate(member.getJoinedDate());
            memberEntity.get().setContactNo(member.getContactNo());

            Optional<UserEntity> byEmail = userDao.findByEmail(member.getEmail());
            byEmail.ifPresent(userEntity -> userEntity.setRole(member.getRole()));
        }
    }

    @Override
    public StaffStatus getMember(String memberId) {
        if(staffDao.existsById(memberId)){
            StaffEntity member = staffDao.getReferenceById(memberId);
            return mapping.toStaffDTO(member);
        }else {
            return new GeneralErrorCode(1,"User with id "+memberId+" does not exist");
        }
    }

    @Override
    public List<StaffDTO> getAllMembers() {
        return mapping.toStaffDTOList(staffDao.findAll());
    }

    @Override
    public StaffStatus getMemberByName(String name) {
        return mapping.toStaffDTO(staffDao.findByFirstName(name));
    }
}
