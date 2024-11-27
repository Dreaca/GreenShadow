package lk.ijse.gdse.greenshadow.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.gdse.greenshadow.dao.UserDao;
import lk.ijse.gdse.greenshadow.dto.UserStatus;
import lk.ijse.gdse.greenshadow.dto.impl.UserDTO;
import lk.ijse.gdse.greenshadow.entity.impl.UserEntity;
import lk.ijse.gdse.greenshadow.exceptions.DataPersistException;
import lk.ijse.gdse.greenshadow.exceptions.UserNotFoundException;
import lk.ijse.gdse.greenshadow.service.UserService;
import lk.ijse.gdse.greenshadow.util.Mapping;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private Mapping mapper;
    @Override
    public void saveUser(UserDTO user) {
        UserEntity save = userDao.save(mapper.toUserEntity(user));
        if (save == null) {
            throw new DataPersistException("Unable to save user");
        }
    }

    @Override
    public UserDetailsService getUserDetailsService() {
        return userName -> userDao.findByEmail(userName)
                .orElseThrow(()->new UserNotFoundException("User with id"+userName+" is not available"));

    }

    @Override
    public List<UserDTO> getAllUsers() {
        return List.of();
    }

    @Override
    public UserStatus getUser(String userId) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public void updateUser(String userId, UserDTO user) {

    }
}
