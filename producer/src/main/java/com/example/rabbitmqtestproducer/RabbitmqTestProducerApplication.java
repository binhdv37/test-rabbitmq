package com.example.rabbitmqtestproducer;

import com.example.rabbitmqtestproducer.component.Send;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqTestProducerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqTestProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Send send = new Send();
        send.startProducer();
    }
}
