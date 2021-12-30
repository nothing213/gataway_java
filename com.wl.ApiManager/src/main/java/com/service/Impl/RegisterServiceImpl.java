package com.service.Impl;


import com.bean.User;
import com.dao.RegisterDao;
import com.service.RegisterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Resource
    RegisterDao registerDao;
    public int registerUser(User user)
    {
        return registerDao.registerUser(user);
    }
}
