package com.example.consumer.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ReceiveLogsDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    private final List<String> severities = Arrays.asList("info", "warning", "error");

    public void startConsumer() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter severity in this pattern: <severity 1>,<severity 2>,...");

        String input = sc.nextLine(); // info,warning,error
        List<String> listSeverity = Arrays.asList(input.split(","));

        if (listSeverity.size() < 1) {
            System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
            System.exit(1);
        }

        if (listSeverity.stream().anyMatch(s -> !severities.contains(s))) {
            System.out.println("Severiry must be one of these : " + severities);
            System.exit(1);
        }

        for (String severity : listSeverity) {
            channel.queueBind(queueName, EXCHANGE_NAME, severity); // binding all input key for queue to exchange
        }
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

}
