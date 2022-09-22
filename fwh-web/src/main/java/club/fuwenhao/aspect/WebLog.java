package club.fuwenhao.aspect;

import java.lang.annotation.*;

/**
 * @program: fwh-parent
 * @description: 日志输出接口
 * @author: fwh
 * @date: 2021-04-29 15:33
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface WebLog {
    String description() default "";

    boolean needReuestUrlLog() default true;

    boolean needRequestLog() default true;

    boolean needResponseLog() default true;
}
