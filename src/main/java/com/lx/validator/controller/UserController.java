package com.lx.validator.controller;

import com.lx.validator.controller.req.User;
import com.lx.validator.rest.Result;
import com.lx.validator.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


/**
 * 1、JSR-303 JSR-349 JSR-380 规范了解一下
 *      javax.validation.Valid;  javax提供的基于标准的规范
 *      org.hibernate.validator.constraints.Length;  hibernate拓展校验
 *
 *      beanvalidation资料 https://beanvalidation.org/
 *
 *      https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/
 *
 * 2、validator 简单校验（Default 组校验）
 *
 * 3、不同的校验异常的处理，参见 ControllerErrorHandler 类
 *
 * 4、嵌套校验（级联校验），支持对bean中的其他成员变量实体做校验
 *
 * 5、分组校验，指定校验规则组
 *
 * 6、转换组校验，支持改变校验规则组
 *
 * 7、自定义注解校验 TODO
 *
 * 8、集合校验
 *
 * 9、组序列校验，支持指定多个组的校验先后顺序且为fast-fail模式
 */
@RestController
@Validated
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 创建用户。
     *
     * 技术点 - @Valid 和 @Validated 的区别：
     * 在controller中的方法参数上使用 @Valid 或者 @Validated 都可以，
     * 但是在实体类中嵌套校验的时候，只能用@Valid，因为@Validated不能用在成员字段上。
     * 如果想要使用分组校验功能，那么只能使用 @Validated
     *
     * @param user 用户
     */
    @PostMapping(value = "create-user")
    public Result createUser(@RequestBody @Validated({User.Create.class}) User user) {

        System.out.println(user);

        return Result.ok();
    }

    /**
     * 测试 Service 层中做校验
     * @param user 用户实体
     */
    @PostMapping(value = "update-user")
    public Result updateUser(@RequestBody User user) {
        // 在Service层校验参数
        userService.updateUser(user);
        return Result.ok();
    }

    @PostMapping(value = "check-user")
    public Result checkUser(@RequestBody User user) {
        // 在Service层校验参数
        userService.checkUser(user);
        return Result.ok();
    }

    /**
     * 知识点 - 集合中元素校验：
     * 这种校验方式会导致
     */
    @PostMapping(value = "batch-check-user")
    @Validated({User.Check.class})
    public Result batchCheckUser(@RequestBody List<@Valid User> users) {
        userService.batchCheckUser(users);
        return Result.ok();
    }

}
