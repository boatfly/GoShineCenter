package com.goshine.gscenter.common.kafka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

public class ConsumerGroup  {
    private static final Log log = LogFactory.getLog(ConsumerGroup.class);

    private final String topic;

    /**
     *  公共连接属性
     */
    private Properties props ;

    /**
     * 消费者组
     */
    private final String groupId;


    public ConsumerGroup(String groupId, String topic, KafkaConsumerConfig consumerConfig) {
        createConsumerConfig(groupId,consumerConfig);
        this.topic = topic;
        this.groupId = groupId;
    }


    private Properties createConsumerConfig(String groupId, KafkaConsumerConfig consumerConfig) {
        props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,consumerConfig.servers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerConfig.enableAutoCommit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, consumerConfig.autoCommitInterval);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, consumerConfig.sessionTimeout);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerConfig.autoOffsetReset);
        // 其他配置再配置
        return props;
    }

    public KafkaConsumer getConsumer() {
        return new KafkaConsumer(props);
    }

    public String getTopic() {
        return topic;
    }

    public String getA_groupId() {
        return groupId;
    }
}
