package edu.neu.coe.csye6225.dao;

import edu.neu.coe.csye6225.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
        int deleteByPrimaryKey(Integer id);

        int insert(User record);

        int insertSelective(User record);

        User selectByPrimaryKey(Integer id);

        int updateByPrimaryKeySelective(User record);

        int updateByPrimaryKey(User record);


}
