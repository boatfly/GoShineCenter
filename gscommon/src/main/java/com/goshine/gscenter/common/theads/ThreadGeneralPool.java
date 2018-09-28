package com.goshine.gscenter.common.theads;

import com.goshine.gscenter.gskit.concurrent.threadpool.ThreadPoolUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadGeneralPool {

    /**
     * 日志处理
     */
    private static final Log log = LogFactory.getLog(ThreadGeneralPool.class);

    /**
     *  线程池
     */
    private ExecutorService executor;

    private int coreSize;
    private String threadNamePrefix;
    private int keepAliveTime;
    private int maximumPoolSize;

    public ThreadGeneralPool(int coreSize,int maximumPoolSize,int keepAliveTime,String threadNamePrefix){
        this.coreSize = coreSize;
        this.threadNamePrefix = threadNamePrefix;
        this.keepAliveTime = keepAliveTime;
        this.maximumPoolSize = maximumPoolSize;
        init();
    }

    /**
     * 初始化线程
     */
    //@PostConstruct
    void init(){
        executor = new ThreadPoolExecutor(coreSize,maximumPoolSize,keepAliveTime, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(), ThreadPoolUtil.buildThreadFactory(threadNamePrefix));
    }

    /**
     * 提交新的消费者
     *
     * @param shutdownableThread
     */
    public void SubmitConsumerPool(ShutdownableThread shutdownableThread) {
        executor.execute(shutdownableThread);
    }

    /**
     * 程序关闭,关闭线程池
     *
     */
    //@PreDestroy
    void fin(){
        shutdown();
    }

    public void shutdown() {
        if (executor != null) executor.shutdown();
        try {
            if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                log.info("Timed out waiting for consumer threads to shut down, exiting uncleanly");
            }
        } catch (InterruptedException e) {
            log.info("Interrupted during shutdown, exiting uncleanly");
        }
    }
}
