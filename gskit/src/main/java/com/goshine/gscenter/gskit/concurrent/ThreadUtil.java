package com.goshine.gscenter.gskit.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * 线程相关工具类.
 *
 * 1. 处理了InterruptedException的sleep
 *
 */
public class ThreadUtil {
    /**
     * sleep等待, 单位为毫秒, 已捕捉并处理InterruptedException.
     */
    public static void sleep(long durationMillis) {
        try {
            Thread.sleep(durationMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * sleep等待，已捕捉并处理InterruptedException.
     */
    public static void sleep(long duration, TimeUnit unit) {
        try {
            Thread.sleep(unit.toMillis(duration));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
