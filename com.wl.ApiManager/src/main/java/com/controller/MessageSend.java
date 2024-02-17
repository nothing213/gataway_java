//package com.controller;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.messaging.Processor;
//import org.springframework.integration.support.MessageBuilder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//public class MessageSend {
//
//    @Autowired
//    private Processor pipe;
//
//    @GetMapping("/send")
//    public void send(@RequestParam String message){
//        pipe.output().send(MessageBuilder.withPayload(message).build());
//    }
//}