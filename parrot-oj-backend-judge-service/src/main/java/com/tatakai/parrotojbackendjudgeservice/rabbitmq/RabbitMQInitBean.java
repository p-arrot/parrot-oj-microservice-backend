package com.tatakai.parrotojbackendjudgeservice.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Slf4j
@Component
public class RabbitMQInitBean {
    @Value("${spring.rabbitmq.host:localhost}")
    private String host;

    @PostConstruct
    public void init(){
        try{
            // 创建连接工厂
            ConnectionFactory factory=new ConnectionFactory();
            // 设置节点
            factory.setHost(host);
            // 创建连接
            Connection connection=factory.newConnection();
            // 创建信道
            Channel channel = connection.createChannel();
            // 创建交换机my_exchange，路由方式为direct
            String exchangeName="my_exchange";
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            // 创建队列
            String queueName="my_queue";
            channel.queueDeclare(queueName,true,false,false,null);
            // 绑定队列和交换机
            channel.queueBind(queueName,exchangeName,"my_routingKey");

            log.info("消息队列创建成功！！！");
        }catch (Exception e){
            log.error("消息队列创建失败！！！");
        }

    }

}
