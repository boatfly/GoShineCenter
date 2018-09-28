package com.goshine.gscenter.common.hbase.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD })
@Inherited
public @interface HbaseColumn {
    String family() default "";

    String qualifier() default "";

    boolean timestamp() default false;
}
