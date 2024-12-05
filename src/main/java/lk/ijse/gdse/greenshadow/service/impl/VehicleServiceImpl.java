package lk.ijse.gdse.greenshadow.service.impl;

import lk.ijse.gdse.greenshadow.customStatusCodes.GeneralErrorCode;
import lk.ijse.gdse.greenshadow.dao.VehicleDao;
import lk.ijse.gdse.greenshadow.dto.VehicleStatus;
import lk.ijse.gdse.greenshadow.dto.impl.VehicleDTO;
import lk.ijse.gdse.greenshadow.entity.impl.VehicleEntity;
import lk.ijse.gdse.greenshadow.exceptions.DataPersistException;
import lk.ijse.gdse.greenshadow.service.VehicleService;
import lk.ijse.gdse.greenshadow.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleDao vehicleDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveVehicle(VehicleDTO vehicle) {
        VehicleEntity save = vehicleDao.save(mapping.toVehicleEntity(vehicle));
        if (save == null) {
            throw new DataPersistException("Error saving vehicle");
        }
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
            byId.get().setLicansePlateNo(vehicle.getLicensePlateNo());
            byId.get().setCategory(vehicle.getCategory());
            byId.get().setFuelType(vehicle.getFuelType());
            byId.get().setStatus(vehicle.getStatus());
            byId.get().setAllocatedStaff(mapping.toStaffEntityList(vehicle.getAllocatedStaff()));
            byId.get().setRemarks(vehicle.getRemarks());
        }else throw new DataPersistException("Vehicle with id "+vehicleCode+" not found");
    }
}
