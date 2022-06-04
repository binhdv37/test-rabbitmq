package com.example.consumer.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.Scanner;

public class ReceiveLogsTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public void startConsumer() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter binding key: (<color>.<animal>)");
        String bindingKey = sc.nextLine();

        if (!validateBindingKey(bindingKey)) {
           System.err.println("Wrong binding key pattern");
           System.exit(1);
        }

        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

    private boolean validateBindingKey(String bindingKey) {
        String[] l = bindingKey.split("\\.");
        if (l.length != 2) {
           return false;
        }
        return true;
    }

}
