package com.GDLearn.controller;


import com.GDLearn.entity.User;
import com.GDLearn.lang.Result;
import com.GDLearn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 作者名会被放到这里
 * @since 2021-10-14
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/index")
    public Object index(){
        User usr = userService.getById(1L);
        return Result.sucess(usr);
    }


    @PostMapping("/save")
    public Result save(@Valid @RequestBody User user){
        return Result.sucess(user);
    }
}
