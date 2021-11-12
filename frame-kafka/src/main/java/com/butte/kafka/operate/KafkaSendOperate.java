package com.butte.kafka.operate;

import com.butte.kafka.entity.SendMsgVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import javax.annotation.Resource;

/**
 * 消息发送基础封装
 * @author 公众号:知了一笑
 * @since 2021-08-07 19:39
 */
@Component
public class KafkaSendOperate {

    protected static final Logger logger = LoggerFactory.getLogger(KafkaSendOperate.class) ;

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate ;

    public void send (SendMsgVO entry) {
        kafkaTemplate.send(entry.getTopic(),entry.getKey(),entry.getMsgBody()) ;
    }

    public void sendCallback (SendMsgVO entry) {
        ListenableFuture<SendResult<String, String>> callback =
                kafkaTemplate.send(entry.getTopic(),entry.getKey(),entry.getMsgBody()) ;
        try {
            callback.addCallback(
                    sus -> { logger.info("kafka topic:{},success...",entry.getTopic()); },
                    exe -> { logger.info("kafka topic:{},fail:{}...",entry.getTopic(),exe.getMessage());});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
