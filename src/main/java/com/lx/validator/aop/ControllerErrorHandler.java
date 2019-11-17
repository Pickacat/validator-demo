package com.lx.validator.aop;

import com.lx.validator.rest.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 控制层异常处理类。
 * basePackages 可以设置只处理某些包下的controller，
 * 也可以使用 basePackageClasses 指定controller类
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.lx.validator.controller"})
//@RestControllerAdvice(basePackageClasses = {OtherUserController.class})
public class ControllerErrorHandler {

    /**
     * path分割符
     */
    private static String SPLIT_COMMA = ".";

    /**
     * 控制层参数校验异常。
     * org.springframework.web.bind.MethodArgumentNotValidException
     * 该包下还有其他异常类型
     *
     * @param e 异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> collect = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField, fieldError -> fieldError.getDefaultMessage() == null ? "" : fieldError.getDefaultMessage())
                );

        return Result.fail(2111, "参数检验错误", collect);
    }


    /**
     * 非 controller 层的异常。
     * javax.validation.ConstraintViolationException 违反约束条件异常。
     *
     * controller 层，对集合做校验的时候（知识点 - 集合中元素校验：），也会抛出 ConstraintViolationException，
     * 这种情况下，如果 cv.getPropertyPath() 截取掉前2个.path，会导致信息不完整。所以 getFieldFromPath 只去掉方法，保留后面的路径
     *
     * @param e 异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result constraintViolationExceptionHandler(ConstraintViolationException e) {
        Map<String, String> collect = e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        cv -> getFieldFromPath(cv.getPropertyPath()), ConstraintViolation::getMessage)
                );

        return Result.fail(2222, "参数错误", collect);
    }


    /**
     * 默认异常处理
     *
     * @param e 异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result defaultExceptionHandler(Exception e) {

        return Result.fail(2333, "系统异常");
    }

    /**
     * 将path截取出错误字段，保持和MethodArgumentNotValidException一样的返回结构
     *
     * @param path 路径
     */
    private static String getFieldFromPath(Path path) {
        String pathStr = path.toString();
        int firstCommaIdx = pathStr.indexOf(SPLIT_COMMA);
        return pathStr.substring(firstCommaIdx + 1);

//        int secondCommaIdx = substring.indexOf(SPLIT_COMMA);
//        return substring.substring(secondCommaIdx + 1);
    }
}
