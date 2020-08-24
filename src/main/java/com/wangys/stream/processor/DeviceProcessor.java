package com.wangys.stream.processor;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * DeviceProcessor
 *
 * @author wangys
 * @date 2020-08-24 16:52
 */
public interface DeviceProcessor {

    String deviceInput = "deviceInput";
    String deviceOutput = "deviceOutput";

    @Input(deviceInput)
    SubscribableChannel deviceInput();

    @Output(deviceOutput)
    MessageChannel deviceOutput();

}
