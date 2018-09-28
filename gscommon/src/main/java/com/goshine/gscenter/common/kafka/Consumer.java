package com.goshine.gscenter.common.kafka;

import com.goshine.gscenter.common.kafka.entity.Message;
import com.goshine.gscenter.common.mybatis.mapper.MessageMapper;
import com.goshine.gscenter.common.theads.ShutdownableThread;
import com.goshine.gscenter.gskit.mapper.JsonMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Consumer extends ShutdownableThread {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MessageMapper messageMapper;

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


    public Consumer(ConsumerGroup consumerGroup,MessageMapper messageMapper) {
        super("",false);
        this.consumer = consumerGroup.getConsumer();
        this.topic = consumerGroup.getTopic();
        this.groupId = consumerGroup.getA_groupId();
        this.messageMapper = messageMapper;
    }

    @Override
    public void doWork() {
        consumer.subscribe(Collections.singletonList(this.topic));
        ConsumerRecords<Integer, String> records = consumer.poll(1000);
        List<Message> messages = new ArrayList<>();
        for (ConsumerRecord<Integer, String> record : records) {
            Message message = JsonMapper.INSTANCE.fromJson(record.value(),Message.class);
            messages.add(message);
            logger.info("Thread: "+Thread.currentThread().getName()
                    +"Received message: (" + this.groupId + ", " + record.value() + ") at offset "
                    + record.offset()+" partition : "+records.partitions());
        }
        if (messages.size()>0)
            messageMapper.batchAdd(messages);
    }
}
