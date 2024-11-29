package lk.ijse.gdse.greenshadow.service;

import lk.ijse.gdse.greenshadow.dto.impl.StaffDTO;

import java.util.List;

public interface StaffService {
    void saveMember(StaffDTO member);
    void deleteMember(String memberId);
    void updateMember(StaffDTO member, String memberId);
    StaffDTO getMember(String memberId);
    List<StaffDTO> getAllMembers();
}
