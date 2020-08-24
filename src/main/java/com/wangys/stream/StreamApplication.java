package com.wangys.stream;

import com.wangys.stream.bean.Device;
import com.wangys.stream.processor.DeviceProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.util.Date;

/**
 * Sink 自带input
 * Source 自带output
 */
@EnableBinding(value = {Sink.class, Source.class, DeviceProcessor.class})
@SpringBootApplication
public class StreamApplication implements CommandLineRunner {

    @Autowired
    @Qualifier("output")
    MessageChannel output;


    @Autowired
    @Qualifier("deviceOutput")
    MessageChannel deviceOutput;

    /**
     * 监听 binding 为 Sink.INPUT 的消息
     * @param message
     */
    @StreamListener(Sink.INPUT)
    public void input(Message<String> message) {
        System.out.println("监听收到：" + message.getPayload());
    }


    /**
     * 监听 binding 为 DeviceProcessor.deviceInput 的消息
     * @param device
     */
    @StreamListener(DeviceProcessor.deviceInput)
    public void deviceInput(Device device) {
        System.out.println(DeviceProcessor.deviceInput + "监听收到：" + device);
    }


    public static void main(String[] args) {
        SpringApplication.run(StreamApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 字符串类型发送MQ
        System.out.println("字符串信息发送");
        output.send(MessageBuilder.withPayload("大家好").build());

        //发送对象
        Device device = new Device(1, "xxx", "123");
        deviceOutput.send(MessageBuilder.withPayload(device).build());
    }


    /**
     * 定时轮询发送消息到 binding 为 Processor.OUTPUT
     * @return
     */
    @Bean
    @InboundChannelAdapter(value = Processor.OUTPUT, poller = @Poller(fixedDelay = "3000", maxMessagesPerPoll = "1"))
    public MessageSource<String> timerMessageSource() {
        return () -> MessageBuilder.withPayload("短消息-" + new Date()).build();
    }



}
