package com.goshine.gscenter.common.hbase.instance.threads;

import com.goshine.gscenter.common.hbase.instance.entity.HMessage;
import com.goshine.gscenter.common.hbase.instance.entity.HServiceComplaint;
import com.goshine.gscenter.common.hbase.instance.service.HMessageService;
import com.goshine.gscenter.common.hbase.instance.service.HServiceComplaintService;
import com.goshine.gscenter.common.kafka.ConsumerGroup;
import com.goshine.gscenter.common.kafka.entity.Message;
import com.goshine.gscenter.common.theads.ShutdownableThread;
import com.goshine.gscenter.gskit.mapper.JsonMapper;
import com.goshine.gscenter.gskit.time.DateFormatUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HServiceComplaintConsumer extends ShutdownableThread {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private HServiceComplaintService hServiceComplaintService;
    /**
     * kafka 消费者
     */
    private KafkaConsumer consumer;

    /**
     *  topic
     */
    private  String topic;

    /**
     *  组id
     */
    private  String groupId;

    public HServiceComplaintConsumer(ConsumerGroup consumerGroup, HServiceComplaintService hServiceComplaintService) {
        super("",false);
        this.consumer = consumerGroup.getConsumer();
        this.topic = consumerGroup.getTopic();
        this.groupId = consumerGroup.getA_groupId();
        this.hServiceComplaintService = hServiceComplaintService;
    }

    @Override
    public void doWork() {
        consumer.subscribe(Collections.singletonList(this.topic));
        ConsumerRecords<Integer, String> records = consumer.poll(1000);
        List<HServiceComplaint> messages = new ArrayList<>();
        for (ConsumerRecord<Integer, String> record : records) {
            HServiceComplaint message = JsonMapper.INSTANCE.fromJson(record.value(),HServiceComplaint.class);
            messages.add(message);
            logger.info("Thread: "+Thread.currentThread().getName()
                    +"Received message: (" + this.groupId + ", " + record.value() + ") at offset "
                    + record.offset()+" partition : "+records.partitions());
        }
        if (messages.size()>0)
            hServiceComplaintService.batchAdd(messages);
    }

}