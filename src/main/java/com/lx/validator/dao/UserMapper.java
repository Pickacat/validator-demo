package com.lx.validator.dao;

import com.lx.validator.controller.req.User;
import org.springframework.stereotype.Repository;

/**
 * 持久层
 * <p>
 * 这里的 @Validated 和 @Valid 可以标记到接口上。
 * 最佳实践是将检验注解写到对应实现类上，因为变换了实现类可能校验逻辑也会发生变化。
 * 而在接口上写死了，那么就意味着无论更换什么实现类都必须执行检验。
 */
@Repository
//@Validated
public class UserMapper implements IUserMapper {

    @Override
    public boolean checkUser(/*@Valid*/ User user) {
        System.out.println(user);
        return true;
    }

}
