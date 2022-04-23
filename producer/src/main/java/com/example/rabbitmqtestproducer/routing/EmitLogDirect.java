package com.example.rabbitmqtestproducer.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class EmitLogDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    private final List<String> severities = Arrays.asList("info", "warning", "error");

    public void startProducer() throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
        ) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            String severity, message;

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter severity and message in this pattern: <severity>:<message>");

            while (sc.hasNext()) {
                String input = sc.nextLine();

                // extract input
                String[] extractResult = extractInput(input);
                if (extractResult == null) {
                    System.exit(1);
                }
                severity = extractResult[0];
                message = extractResult[1];

                channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + severity + "':'" + message + "'");
            }
        }
    }

    private String[] extractInput(String input) {
        String[] l = input.split(":");
        if (l.length != 2) {
            System.err.println("Wrong input pattern (split length != 2)");
            return null;
        }
        String severity = l[0];
        if(!severities.contains(severity)) {
            System.err.println("Wrong severity");
            return null;
        }
        return l;
    }



}
