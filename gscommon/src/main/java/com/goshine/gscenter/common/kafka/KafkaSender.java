package com.goshine.gscenter.common.kafka;

import com.goshine.gscenter.common.hbase.instance.entity.HServiceComplaint;
import com.goshine.gscenter.common.hbase.instance.service.HMessageService;
import com.goshine.gscenter.common.hbase.instance.service.HServiceComplaintService;
import com.goshine.gscenter.common.hbase.instance.simulator.HServiceComplaintDataSimulator;
import com.goshine.gscenter.common.hbase.instance.threads.HServiceComplaintConsumer;
import com.goshine.gscenter.common.hbase.instance.threads.HbaseConsumer;
import com.goshine.gscenter.common.kafka.entity.Message;
import com.goshine.gscenter.common.mybatis.mapper.MessageMapper;
import com.goshine.gscenter.common.theads.ThreadGeneralPool;
import com.goshine.gscenter.common.theads.ThreadPoolConfig;
import com.goshine.gscenter.common.theads.ThreadPoolMarshall;
import com.goshine.gscenter.gskit.mapper.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

@Component
public class KafkaSender {
    private static Logger LOGGER = LoggerFactory.getLogger(KafkaSender.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private HMessageService hMessageService;
    @Autowired
    private HServiceComplaintService hServiceComplaintService;

    ThreadGeneralPool consumerPool;
    @Resource
    KafkaConsumerConfig consumerConfig;
    @Resource
    ThreadPoolConfig threadPoolConfig;

    @PostConstruct
    void initd(){

//        ConsumerGroup consumerThreadTomysql = new ConsumerGroup("group-1","gscenter_fi_devicestatus_records",consumerConfig);
//        ConsumerGroup consumerThreadOfHMessages = new ConsumerGroup("group-2","gscenter_fi_devicestatus_records", consumerConfig);
        ConsumerGroup consumerThreadOfHServiceComplaints = new ConsumerGroup("group-servicecomplaint","gscenter_fi_servicescomplaint_records", consumerConfig);

        consumerPool = new ThreadGeneralPool(threadPoolConfig.coreSize,threadPoolConfig.maximumPoolSize,threadPoolConfig.keepAliveTime,"kafka-servicecomplaint-thread");

        ThreadPoolMarshall threadPoolMarshall = ThreadPoolMarshall.getInstance();
        threadPoolMarshall.getPoolmap().put("kafka-consumer-pool",consumerPool);

        /**
         * 启动消费者
         */
//        consumerPool.SubmitConsumerPool(new Consumer(consumerThreadTomysql,messageMapper));
//        consumerPool.SubmitConsumerPool(new Consumer(consumerThreadTomysql,messageMapper));
//
//        consumerPool.SubmitConsumerPool(new HbaseConsumer(consumerThreadOfHMessages,hMessageService));

        consumerPool.SubmitConsumerPool(new HServiceComplaintConsumer(consumerThreadOfHServiceComplaints,hServiceComplaintService));
    }

    //发送消息方法
    public void send() {
        for (int i=0;i<1;i++){
//            Message message = new Message();
//            message.setId(System.currentTimeMillis());
//            message.setMsg(UUID.randomUUID().toString());
//            message.setSendTime(new Date());
//            LOGGER.info("+++++++++++++++++++++  message = {}", JsonMapper.INSTANCE.toJson(message));
//            kafkaTemplate.send("gscenter_fi_devicestatus_records", JsonMapper.INSTANCE.toJson(message));
            HServiceComplaint hServiceComplaint = HServiceComplaintDataSimulator.generatorOne();
            String jsonContent =JsonMapper.INSTANCE.toJson(hServiceComplaint);
            LOGGER.info("+++++++++++++++++++++  message = {}", jsonContent);
            kafkaTemplate.send("gscenter_fi_servicescomplaint_records", jsonContent);
        }
    }

    @PreDestroy
    public void fin(){
        LOGGER.info("###############thread pool shutdown......");
        //consumerPool.shutdown();
        ThreadPoolMarshall threadPoolMarshall = ThreadPoolMarshall.getInstance();
        Iterator<String> itr = threadPoolMarshall.getPoolmap().keySet().iterator();
        while (itr.hasNext()){
            ThreadGeneralPool pool = threadPoolMarshall.getPoolmap().get(itr.next());
            pool.shutdown();
        }
    }
}
