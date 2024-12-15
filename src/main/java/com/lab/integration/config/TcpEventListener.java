package com.lab.integration.config;

import org.springframework.context.event.EventListener;
import org.springframework.integration.ip.tcp.connection.TcpConnectionEvent;
import org.springframework.stereotype.Component;

@Component
public class TcpEventListener {

    @EventListener
    public void handleTcpConnectionEvent(TcpConnectionEvent event) {
        System.out.println("TCP Event: " + event);
    }
}