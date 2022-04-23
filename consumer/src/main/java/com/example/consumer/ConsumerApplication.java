package com.example.consumer;

import com.example.consumer.component.Recv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        Recv recv = new Recv();
        recv.startConsumer();
    }
}
