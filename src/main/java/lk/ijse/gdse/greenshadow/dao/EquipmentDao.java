package lk.ijse.gdse.greenshadow.dao;

import lk.ijse.gdse.greenshadow.entity.impl.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface EquipmentDao extends JpaRepository<EquipmentEntity,String> {
}
