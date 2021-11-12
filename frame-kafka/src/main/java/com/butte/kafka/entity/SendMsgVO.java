package com.butte.kafka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息发送
 * @author 公众号:知了一笑
 * @since 2021-08-07 19:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMsgVO {
    /**
     * 消息Topic
     */
    private String topic ;
    /**
     * 消息Key
     */
    private String key ;
    /**
     * 消息主体JSON格式
     */
    private String msgBody ;
}
