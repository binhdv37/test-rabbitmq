package com.example.rabbitmqtestproducer;

import com.example.rabbitmqtestproducer.hello_world.SendHelloWorld;
import com.example.rabbitmqtestproducer.pub_sub.EmitLog;
import com.example.rabbitmqtestproducer.routing.EmitLogDirect;
import com.example.rabbitmqtestproducer.rpc.RpcClient;
import com.example.rabbitmqtestproducer.topic.EmitLogTopic;
import com.example.rabbitmqtestproducer.work_queue.Send;
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
        SendHelloWorld sendHelloWorld = new SendHelloWorld();
        sendHelloWorld.startProducer();

//        Send send = new Send();
//        send.startProducer();

//        EmitLog emitLog = new EmitLog();
//        emitLog.startProducer();

//        EmitLogDirect emitLogDirect = new EmitLogDirect();
//        emitLogDirect.startProducer();

//        EmitLogTopic emitLogTopic = new EmitLogTopic();
//        emitLogTopic.startProducer();

//        RpcClient rpcClient = new RpcClient();
//        rpcClient.startRpcClient();
    }
}
