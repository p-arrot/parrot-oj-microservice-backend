package com.tatakai.parrotojbackendquestionservice.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class MyMessageProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息到指定的交换机和路由键
     *
     * @param message 要发送的消息内容
     * @param exchange 指定的交换机名称
     * @param routekey 指定的路由键
     */
    public void sendMessage(String message,String exchange,String routekey){
        log.info("发送消息"+message);

        rabbitTemplate.convertAndSend(exchange,routekey,message);
    }
    

}
