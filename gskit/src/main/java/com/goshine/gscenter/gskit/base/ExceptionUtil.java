package com.goshine.gscenter.gskit.base;

import com.goshine.gscenter.gskit.base.annotation.Nullable;

import java.lang.reflect.UndeclaredThrowableException;

public class ExceptionUtil {
    public static RuntimeException unchecked(@Nullable Throwable t) {
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        }
        if (t instanceof Error) {
            throw (Error) t;
        }

        throw new UncheckedException(t);
    }

    /**
     * 如果是著名的包裹类，从cause中获得真正异常. 其他异常则不变.
     *
     * Future中使用的ExecutionException 与 反射时定义的InvocationTargetException， 真正的异常都封装在Cause中
     *
     * 前面 unchecked() 使用的UncheckedException同理.
     */
    public static Throwable unwrap(@Nullable Throwable t) {
        if (t instanceof UncheckedException || t instanceof java.util.concurrent.ExecutionException
                || t instanceof java.lang.reflect.InvocationTargetException
                || t instanceof UndeclaredThrowableException) {
            return t.getCause();
        }

        return t;
    }

    /**
     * 组合unwrap与unchecked，用于处理反射/Callable的异常
     */
    public static RuntimeException unwrapAndUnchecked(@Nullable Throwable t) {
        throw unchecked(unwrap(t));
    }
}
