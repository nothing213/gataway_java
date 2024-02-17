package com.service;

import com.bean.User;

public interface LoginService {

    Boolean findUser(String username, String password);
}
