package com.service.Impl;

import com.dao.LoginDao;
import com.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    LoginDao loginDao;

    public Boolean findUser(String username, String password) {
        return loginDao.findUser(username, password);
    }
}
