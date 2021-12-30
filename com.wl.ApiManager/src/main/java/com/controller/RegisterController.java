package com.controller;

import com.api.CommonResult;
import com.api.IErrorCode;
import com.bean.User;

import com.service.RegisterService;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author: Eli Shaw
 * @Date: 2019-11-14 11:33:26
 * @Description：
 */
@RestController
public class RegisterController {

    @Resource
    private RegisterService registerService;

    @RequestMapping( value = "/admin/register", method = RequestMethod.POST)
    public CommonResult register(@RequestBody  User user) {
        System.out.println(user.getUsername()+" "+user.getPassword());
        int flag = registerService.registerUser(user);
        System.out.println(flag);
        if(flag > 0)
            return  CommonResult.success(user.getUsername());
        else
            return CommonResult.validateFailed();
//        if (user.getUsername().equals("张三") && user.getPassword().equals("123456"))
//            return CommonResult.success("张三");
//        else
//            return CommonResult.validateFailed();
    }
}
