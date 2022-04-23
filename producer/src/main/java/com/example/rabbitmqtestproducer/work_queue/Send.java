package com.example.rabbitmqtestproducer.work_queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

public class Send {

    private final static String QUEUE_NAME = "hello";
    private final static String TASK_QUEUE = "task_queue";

    public void startProducer() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
        ) {
            boolean durable = true; // declare queue as 'durable' => message not lost when rabbitmq node stop
            channel.queueDeclare(TASK_QUEUE, durable, false, false, null);

            Scanner sc = new Scanner(System.in);
            while (sc.hasNext()) {
                String message = sc.nextLine();
                channel.basicPublish("", TASK_QUEUE,
                        MessageProperties.PERSISTENT_TEXT_PLAIN, // mark message as persistent
                        message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }

}
