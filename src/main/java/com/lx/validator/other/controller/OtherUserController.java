package com.lx.validator.other.controller;

import com.lx.validator.controller.req.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 RestControllerAdvice 指定作用范围
 */
@RestController
public class OtherUserController {

    @PostMapping(value = "other/create-user")
    public String createUser(@RequestBody @Validated User user) {

        System.out.println(user);

        return "OK";
    }

}
