package com.tdkj.ServiceImpl.UserManager;

import com.tdkj.dao.UserManager.FpUserMapper;
import com.tdkj.model.FpUser;
import com.tdkj.model.SysRole;
import com.tdkj.service.UserManager.FpUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-05-03.
 */
@Service
public class FpUserServiceImpl implements FpUserService {
    @Autowired
    private FpUserMapper fpUserMapper;

    @Override
    public List<SysRole>  findRole(String id) {
        return fpUserMapper.findRole(id);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return fpUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(FpUser record) {
        return 0;
    }

    @Override
    public int insertSelective(FpUser record) {
        return fpUserMapper.insertSelective(record);
    }

    @Override
    public FpUser selectByPrimaryKey(String id) {
        return fpUserMapper.selectByPrimaryKey(id);
    }

    public FpUser selectIdcard(String idcard,String mobilephome){
        return fpUserMapper.selectIdcard(idcard,mobilephome);
    };
    @Override
    public int updateByPrimaryKeySelective(FpUser record) {
        return fpUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(FpUser record) {
        return 0;
    }

    @Override
    public String countUserByAab301(String aab301) {
        return fpUserMapper.countUserByAab301(aab301);
    }

    @Override
    public FpUser queryUserByUserName(String username) {
        return fpUserMapper.queryUserByUserName(username);
    }

    @Override
    public FpUser LoginByUserNameAndPwd(String username, String password) {
        return fpUserMapper.LoginByUserNameAndPwd(username,password);
    }


    @Override
    public List<FpUser> queryFpUserByAab301(String aab301, String username, String realname, String idcard, String startRecord, String pageSize) {
        return fpUserMapper.queryFpUserByAab301(aab301,username,realname,idcard,startRecord,pageSize);
    }

    @Override
    public String queryAllByAab301(String aab301, String username, String realname, String idcard) {
        return fpUserMapper.queryAllByAab301(aab301,username,realname,idcard);
    }
}
