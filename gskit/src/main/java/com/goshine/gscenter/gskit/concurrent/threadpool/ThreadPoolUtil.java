package com.goshine.gscenter.gskit.concurrent.threadpool;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.goshine.gscenter.gskit.base.annotation.NotNull;
import com.goshine.gscenter.gskit.base.annotation.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具集
 *
 * 1. 优雅关闭线程池的(via guava)
 *
 * 2. 创建可自定义线程名的ThreadFactory(via guava)
 *
 * 3. 防止第三方Runnable未捕捉异常导致线程跑飞
 *
 *
 */
public class ThreadPoolUtil {

    /**
     * 创建ThreadFactory，使得创建的线程有自己的名字而不是默认的"pool-x-thread-y"
     *
     * 使用了Guava的工具类
     */
    public static ThreadFactory buildThreadFactory(@NotNull String threadNamePrefix) {
        return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").build();
    }

    /**
     * 可设定是否daemon, daemon线程在主线程已执行完毕时, 不会阻塞应用不退出, 而非daemon线程则会阻塞.
     *
     */
    public static ThreadFactory buildThreadFactory(@NotNull String threadNamePrefix, @NotNull boolean daemon) {
        return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").setDaemon(daemon).build();
    }
}
