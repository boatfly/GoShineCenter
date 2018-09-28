package com.goshine.gscenter.common.hbase.instance.threads;

import com.goshine.gscenter.common.hbase.instance.entity.HMessage;
import com.goshine.gscenter.common.hbase.instance.service.HMessageService;
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

public class HbaseConsumer extends ShutdownableThread {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private HMessageService hMessageService;
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

    public HbaseConsumer(ConsumerGroup consumerGroup, HMessageService hMessageService) {
        super("",false);
        this.consumer = consumerGroup.getConsumer();
        this.topic = consumerGroup.getTopic();
        this.groupId = consumerGroup.getA_groupId();
        this.hMessageService = hMessageService;
    }

    @Override
    public void doWork() {
        consumer.subscribe(Collections.singletonList(this.topic));
        ConsumerRecords<Integer, String> records = consumer.poll(1000);
        List<HMessage> messages = new ArrayList<>();
        for (ConsumerRecord<Integer, String> record : records) {
            Message message = JsonMapper.INSTANCE.fromJson(record.value(),Message.class);
            //messages.add(message);
            HMessage hMessage = new HMessage();
            hMessage.setMsg(message.getMsg());
            hMessage.setSendTime(DateFormatUtil.formatDate(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND,message.getSendTime()));
            hMessage.setId("id"+System.currentTimeMillis());
            //hMessageService.save(hMessage);
            messages.add(hMessage);
            logger.info("Thread: "+Thread.currentThread().getName()
                    +"Received message: (" + this.groupId + ", " + record.value() + ") at offset "
                    + record.offset()+" partition : "+records.partitions());
        }
        if (messages.size()>0)
            hMessageService.batchAdd(messages);
    }

}

