package org.cwm3.mgrsystem.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    String value() default "";
    //操作描述
    String desc() default"";
    //操作数据库表
    String table() default "";
    //业务实体主键
    String idField() default "id";
    //类型(data、system、power、other)
    FuncLogEnum type() default FuncLogEnum.OPERATE;
}
