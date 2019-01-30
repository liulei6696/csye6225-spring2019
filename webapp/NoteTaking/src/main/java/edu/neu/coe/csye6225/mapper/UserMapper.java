package edu.neu.coe.csye6225.mapper;

import edu.neu.coe.csye6225.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    List<User> getAllUsers();

    User getUserByUsername(String username);
    int updateUser(User user);
    int insertUser(User user);
    int deleteUserByUsername(String username);

}
