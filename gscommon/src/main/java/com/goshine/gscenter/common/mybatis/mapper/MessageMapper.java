package com.goshine.gscenter.common.mybatis.mapper;

import com.goshine.gscenter.common.kafka.entity.Message;
import com.goshine.gscenter.common.mybatis.sqlprovider.MessageSQLProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;

import java.util.List;

public interface MessageMapper {
    @Insert("insert into messages(msg,sendTime) values(#{msg},#{sendTime})")
    int add(Message message);

    @InsertProvider(type = MessageSQLProvider.class,method = "batchAdd")
    int batchAdd(List<Message> messageList);
}
