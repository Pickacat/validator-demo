package com.lx.validator.service;

import com.lx.validator.controller.req.User;
import com.lx.validator.dao.IUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;

/**
 * service层（非controller层做中参数校验）
 * 需要在类上注解 @Validated
 * 然后在方法参数上注解 @Valid
 *
 * 技术点 - 组校验：
 * 方法上注解 @Validated(User.Update.class) 指定了采用User将使用 Update 组校验规则
 */
@Service
@Validated // 实际上是通过 MethodValidationPostProcessor 对UserService开启了方法级别的校验
public class UserService {

    @Resource
    private IUserMapper userMapper;

    /**
     * 更新User对象
     *
     * 添加 @Validated(User.Update.class) 表示当前方法采用 Update组校验规则。
     * Default 用于默认分组，也就是校验注解上没有分配组的，都是默认组。
     *
     * @param user 用户实体
     */
    @Validated(User.Update.class)
//    @Validated({User.Update.class, Default.class})
    public boolean updateUser(@Valid User user) {

        System.out.println(user);

        return true;
    }

    public boolean checkUser(User user){
        return userMapper.checkUser(user);
    }

    /**
     * 注解 @Valid 写到 List前面还是List泛型实体前面都可以
     *
     * @param users 一批用户
     */
//    @Validated(User.Update.class)
//    public boolean batchCheckUser(@Valid List<User> users){
    public boolean batchCheckUser(List<@Valid User> users){

        return true;
    }
}
