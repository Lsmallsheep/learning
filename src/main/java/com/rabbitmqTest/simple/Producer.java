package com.rabbitmqTest.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author HASEE
 * @Date 2021/7/25 15:40
 */
public class Producer {

    public static void main(String[] args) {
        //rabbitmq采用amqp协议

        //创建连接工程
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("root");
        connectionFactory.setVirtualHost("/");
        //创建连接
        Connection connection = null;
        Channel channel = null;
        try {
            //创建连接
            connection = connectionFactory.newConnection("生产者");
            //通过连接获取连接通道
            channel = connection.createChannel();
            //通过创建交换机，声明队列，绑定关系，路由key，发送消息，接受消息
            String queueName = "queue1";
            /**
             * @parms 队列名字
             * @parms 是否持久化
             * @parms 是否自动删除
             * @parms 是否额外携带参数
             */
            channel.queueDeclare(queueName, false, false, false, null);
            //准备消息内容
            String message = "hello word";
            channel.basicPublish("", queueName, null, message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            if (channel!=null && channel.isOpen()){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (connection!=null && connection.isOpen()){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
