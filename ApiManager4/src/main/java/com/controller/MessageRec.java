package com.controller;//package com.controller;
//
//import lombok.extern.slf4j.Slf4j;
//
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Processor;
//import org.springframework.stereotype.Component;
//
//
//
//@Component
//@Slf4j
//@EnableBinding(Processor.class)
//public class MessageRec {
//
//    @StreamListener(Processor.INPUT)
//    public void process(String message){
//        log.info("hahahah : "+message);
//        System.out.println("这里接收者 : "+message);
//    }
//}