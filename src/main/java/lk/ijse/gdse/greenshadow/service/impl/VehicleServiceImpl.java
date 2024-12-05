package lk.ijse.gdse.greenshadow.service.impl;

import lk.ijse.gdse.greenshadow.customStatusCodes.GeneralErrorCode;
import lk.ijse.gdse.greenshadow.dao.StaffDao;
import lk.ijse.gdse.greenshadow.dao.VehicleDao;
import lk.ijse.gdse.greenshadow.dto.VehicleStatus;
import lk.ijse.gdse.greenshadow.dto.impl.VehicleDTO;
import lk.ijse.gdse.greenshadow.entity.impl.StaffEntity;
import lk.ijse.gdse.greenshadow.entity.impl.VehicleEntity;
import lk.ijse.gdse.greenshadow.exceptions.DataPersistException;
import lk.ijse.gdse.greenshadow.service.StaffService;
import lk.ijse.gdse.greenshadow.service.VehicleService;
import lk.ijse.gdse.greenshadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleDao vehicleDao;
    @Autowired
    private Mapping mapping;
    @Autowired
    private StaffDao staffDao;
    @Override
    public void saveVehicle(VehicleDTO vehicle) {
        VehicleEntity vehicleEntity = new VehicleEntity();

        List<StaffEntity> list = new ArrayList<>();
        vehicleEntity.setVehicleCode(vehicle.getVehicleCode());
        vehicleEntity.setCategory(vehicle.getCategory());
        vehicleEntity.setStatus(vehicle.getStatus());
        vehicleEntity.setRemarks(vehicle.getRemarks());
        vehicleEntity.setFuelType(vehicle.getFuelType());
        vehicleEntity.setLicansePlateNo(vehicle.getLicensePlateNo());
        vehicle.getAllocatedStaff().forEach(allocatedStaff -> {
            String trimmed = allocatedStaff.replaceAll("[\\[\\]\"]", "").trim();
            list.add(staffDao.getReferenceById(trimmed));
        });
        vehicleEntity.setAllocatedStaff(list);
        vehicleDao.save(vehicleEntity);

    }

    @Override
    public VehicleStatus getVehicle(String vehicleCode) {
        Optional<VehicleEntity> byId = vehicleDao.findById(vehicleCode);
        if (byId.isPresent()) {
            return mapping.toVehicleDTO(vehicleDao.getReferenceById(vehicleCode));
        }else return new GeneralErrorCode(1,"Vehicle with id "+vehicleCode+" not found");
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return mapping.toVehicleDTOList(vehicleDao.findAll());
    }

    @Override
    public void deleteVehicle(String vehicleCode) {
        Optional<VehicleEntity> byId = vehicleDao.findById(vehicleCode);
        if (byId.isPresent()) {
            vehicleDao.deleteById(vehicleCode);
        }else throw new DataPersistException("Vehicle with id "+vehicleCode+" not found");
    }

    @Override
    public void updateVehicle(VehicleDTO vehicle, String vehicleCode) {
        Optional<VehicleEntity> byId = vehicleDao.findById(vehicleCode);
        if (byId.isPresent()) {
            List<StaffEntity> list = new ArrayList<>();
            vehicle.getAllocatedStaff().forEach(allocatedStaff -> {
                String trimmed = allocatedStaff.replaceAll("[\\[\\]\"]", "").trim();
                list.add(staffDao.getReferenceById(trimmed));
            });
            byId.get().setLicansePlateNo(vehicle.getLicensePlateNo());
            byId.get().setCategory(vehicle.getCategory());
            byId.get().setFuelType(vehicle.getFuelType());
            byId.get().setStatus(vehicle.getStatus());
            byId.get().setAllocatedStaff(list);
            byId.get().setRemarks(vehicle.getRemarks());
        }else throw new DataPersistException("Vehicle with id "+vehicleCode+" not found");
    }
}
