package com.lx.validator.controller.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Company {

    /** 公司名称 */
    @NotBlank(groups = User.Create.class)
//    @NotBlank
    private String name;

    /** 职位 */
    @NotBlank(groups = User.Update.class)
    private String jobTitle;

}
