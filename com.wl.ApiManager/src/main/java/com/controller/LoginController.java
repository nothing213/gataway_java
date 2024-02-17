package com.controller;

import com.api.CommonResult;
import com.api.IErrorCode;
import com.bean.User;
import com.dao.LoginDao;
import com.service.LoginService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Eli Shaw
 * @Date: 2019-11-14 11:33:26
 * @Description：
 */
@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @ResponseBody
    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public CommonResult login(@RequestBody User user) {
        Boolean flag = loginService.findUser(user.getUsername(), user.getPassword());
        if (flag != null)
            return CommonResult.success(user.getUsername());
        else
            return CommonResult.validateFailed();
    }
}
