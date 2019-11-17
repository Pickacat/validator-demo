package com.lx.validator.dao;

import com.lx.validator.controller.req.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface IUserMapper {

    /**
     * 按组校验顺序做校验，且带有fast-fail，前面的校验不通过，后面的校验就不执行
     * @param user 用户实体
     */
    @Validated(User.OrderValid.class)
    boolean checkUser(@Valid User user);
}
