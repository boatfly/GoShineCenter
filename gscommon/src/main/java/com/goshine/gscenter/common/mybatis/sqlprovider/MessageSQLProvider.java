package com.goshine.gscenter.common.mybatis.sqlprovider;

import com.goshine.gscenter.common.kafka.entity.Message;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class MessageSQLProvider {

    public String batchAdd(final Map<String,List<Message>> map){
        List<Message> messageList = map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO messages (msg,sendTime) VALUES ");
        MessageFormat mf = new MessageFormat(
                "(#'{'list[{0}].msg}, #'{'list[{0}].sendTime})"
        );

        for (int i = 0; i < messageList.size(); i++) {
            sb.append(mf.format(new Object[] {i}));
            if (i < messageList.size() - 1)
                sb.append(",");
        }
        return sb.toString();
    }
}
