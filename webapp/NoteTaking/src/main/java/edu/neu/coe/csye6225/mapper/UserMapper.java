package edu.neu.coe.csye6225.mapper;

import edu.neu.coe.csye6225.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    List<User> getAllUsers();

    User getUserByUsername(String username);

    void updateUser(User user);

}
