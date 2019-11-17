package com.lx.validator.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class User {

    /**
     * 技术点 - 组序列校验：
     *  验证顺序有先后的时候或者想快速失败时，采用 组序列 校验。
     *  某个组的验证比较耗时，CPU 和内存的使用率相对比较大，最优的选择是将其放在最后进行验证。
     *  因为在使用组序列验证的时候，如果序列前边的组验证失败，则后面的组将不再给予验证。
     */
    @GroupSequence({Create.class, Check.class})
    public interface OrderValid{}

    /** 创建User时使用的分组 */
    public interface Create{}

    /** 更新User时使用的分组 */
    public interface Update{}

    /** 检查User时使用的分组 */
    public interface Check{}

    /** 名字 */
    @Length(min = 2, max = 4, message = "名字长度2到4个字", groups = {Create.class, Update.class})
    private String name;

    /** 性别 1男 0女 */
    @NotNull(message = "性别必须传", groups = {Check.class})
    private Integer sex;

    /** 出生日期
     *
     * 技术点 - 组校验：
     * 没有使用 groups 指定组的，都是 Default 组。
     * 规则是 没有指定组，使用 Default 组，
     * 指定 一个或者一批组 使用这些组的校验规则，且校验顺序是不确定的。
     */
    @PastOrPresent
    @NotNull
    private LocalDateTime birthday;

    /** 兴趣爱好 */
    @NotNull
    @Size(min = 1, message = "兴趣爱好请至少填写一个")
    private List<String> hobbies;

    /**
     * 技术点 - 级联校验（嵌套校验）：
     * 公司信息，只有@Valid能用在成员字段上，所以嵌套检验时只能用@Valid
     * private List<@Valid Company> company 也是可以的，因为本质上是校验集合中的实体。
     * 同样其他集合也可以使用 @Valid 做嵌套校验。
     *
     * 技术点 - 校验组转换：
     * 如果方法上指定对User采用 Update 校验，那么 List<Company> 将会使用 Update 组校验。
     * 如果启用 @ConvertGroup(from = Update.class, to = Create.class) 注解，
     * 那么当方法上指定对User采用 Update 校验，那么 List<Company> 将会使用 Create 组校验。
     *
     * 注解 @ConvertGroup.List 指定一批 组转换规则
     */
    @Valid
    @NotNull
//    @ConvertGroup(from = Update.class, to = Create.class)
    @ConvertGroup.List(
            value = {@ConvertGroup(from = Update.class, to = Create.class),
                    @ConvertGroup(from = Default.class, to = Create.class)
            })
    private List<Company> company;

}
