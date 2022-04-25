package com.example.consumer;

import com.example.consumer.routing.ReceiveLogsDirect;
import com.example.consumer.rpc.RpcServer;
import com.example.consumer.topic.ReceiveLogsTopic;
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
//        Recv recv = new Recv();
//        recv.startConsumer();

//        ReceiveLogs receiveLogs = new ReceiveLogs();
//        receiveLogs.startConsumer();

//        ReceiveLogsDirect receiveLogsDirect = new ReceiveLogsDirect();
//        receiveLogsDirect.startConsumer();

//        ReceiveLogsTopic receiveLogsTopic = new ReceiveLogsTopic();
//        receiveLogsTopic.startConsumer();

        RpcServer rpcServer = new RpcServer();
        rpcServer.startRpcServer();

    }
}
