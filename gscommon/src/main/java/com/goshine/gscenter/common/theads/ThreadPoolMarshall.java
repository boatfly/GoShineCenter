package com.goshine.gscenter.common.theads;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadPoolMarshall {
    private static final Log log = LogFactory.getLog(ThreadPoolMarshall.class);

    private static volatile ThreadPoolMarshall instance;
    private ThreadPoolMarshall() {}

    private Map<String,ThreadGeneralPool> poolmap = new ConcurrentHashMap<>();

    public static ThreadPoolMarshall getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolMarshall.class) {
                if (instance == null) {
                    instance = new ThreadPoolMarshall();
                }
            }
        }
        return instance;
    }

    public Map<String, ThreadGeneralPool> getPoolmap() {
        return poolmap;
    }
}
