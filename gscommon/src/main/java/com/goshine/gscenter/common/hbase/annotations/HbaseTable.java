package com.goshine.gscenter.common.hbase.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE })
@Inherited
public @interface HbaseTable {
    String tableName() default "";
}
