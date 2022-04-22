package com.example.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApplication implements CommandLineRunner {

    private final static String QUEUE_NAME = "hello";
    private final static String TASK_QUEUE = "task_queue";

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean durable = true;
        channel.queueDeclare(TASK_QUEUE, durable, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");


        // fair dispatch
        int prefetchCount = 1;
        channel.basicQos(prefetchCount); // accept only one unack-ed message at a time (see below)
        // if does not set basicQos(1) => rabbitmq dispatch message sequencely, mean :
        // eg: if has 2 worker, 1 worker only receive odd message, 1 worker only receive even message

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
            } catch (Exception e) {
              e.printStackTrace();
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); // ack that message has been processed successful => rabbitmq can remove this message
            }
        };
        boolean autoAck = false; // acknowledgment is covered below
        channel.basicConsume(TASK_QUEUE, autoAck, deliverCallback, consumerTag -> { });
    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);
        }
    }
}
