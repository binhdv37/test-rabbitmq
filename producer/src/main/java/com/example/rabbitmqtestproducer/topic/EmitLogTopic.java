package com.example.rabbitmqtestproducer.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public void startProducer() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
        ) {

            String routingKey, message;

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter routing key and message in this pattern: <routingKey>:<message>");
            System.out.println("Routing key in this pattern : <color>.<animal>");

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            while (sc.hasNext()) {
                String input = sc.nextLine();

                // extract input
                String[] extractResult = extractInput(input);
                if (extractResult == null) {
                    System.exit(1);
                }
                routingKey = extractResult[0];
                message = extractResult[1];

                channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
            }

        }
    }

    private String[] extractInput(String input) {
        String[] l = input.split(":");
        if (l.length != 2) {
            System.err.println("Wrong input pattern (split length != 2)");
            return null;
        }
        return l;
    }


}
