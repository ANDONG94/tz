package com.tdkj.ServiceImpl.IpLoing;


import com.tdkj.dao.IpLogin.IpLoginMapper;
import com.tdkj.model.IpLogin;
import com.tdkj.service.IpLoing.IpLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpLoginServiceImpl implements IpLoginService {

    @Autowired
    private IpLoginMapper ipLoginMapper;

    @Override
    public int insert(IpLogin record) {
        return ipLoginMapper.insert(record);
    }
}
