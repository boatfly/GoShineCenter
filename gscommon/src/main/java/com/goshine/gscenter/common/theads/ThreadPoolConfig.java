package com.goshine.gscenter.common.theads;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ThreadPoolConfig {
    @Value("${gscommon.threadpool.coreSize}")
    public int coreSize;
    @Value("${gscommon.threadpool.maximumPoolSize}")
    public int maximumPoolSize;
    @Value("${gscommon.threadpool.keepAliveTime.seconds}")
    public int keepAliveTime;
}
