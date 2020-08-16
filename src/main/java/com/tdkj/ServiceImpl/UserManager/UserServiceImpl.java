package com.tdkj.ServiceImpl.UserManager;

import com.tdkj.dao.UserManager.UserMapper;
import com.tdkj.model.User;
import com.tdkj.service.UserManager.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by len on 2019-04-30.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return 0;
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }

    /**
     * 根据行政区划撤销user用户数量
     * @return
     */
    @Override
    public int countUserByAab301(String aab301) {
        return userMapper.countUserByAab301(aab301);
    }

    /**
     * 根据用户名查询该用户是否已经存在
     * @return
     */
    @Override
    public User queryUserByUserName(String username) {
        return userMapper.queryUserByUserName(username);
    }

    /**
     * 根据用户名密码登录系统
     * @return
     */
    @Override
    public User LoginByUserNameAndPwd(String username, String password) {
        return userMapper.LoginByUserNameAndPwd(username,password);
    }
}
