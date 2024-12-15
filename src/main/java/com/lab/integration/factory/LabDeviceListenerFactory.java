package com.lab.integration.factory;

import com.lab.integration.converter.Hl7Converter;
import com.lab.integration.model.LabDevice;

import com.lab.integration.parser.dynamicparser.DynamicMessageParser;
import com.lab.integration.parser.MessageParser;
import com.lab.integration.parser.MessageParserFactory;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;


@Component
@AllArgsConstructor
public class LabDeviceListenerFactory {

    private ApplicationEventPublisher applicationEventPublisher;
    private MessageParserFactory messageParserFactory;
    private Hl7Converter hl7Converter;

    public IntegrationFlow createListener(LabDevice device) {
        TcpNetServerConnectionFactory connectionFactory = new TcpNetServerConnectionFactory(device.getPort());
        connectionFactory.setSerializer(new ByteArrayProtocolSerializer());
        connectionFactory.setDeserializer(new ByteArrayProtocolSerializer());
        connectionFactory.setSoTimeout(15000);
        connectionFactory.setApplicationEventPublisher(applicationEventPublisher);


        return IntegrationFlow.from(Tcp.inboundGateway(connectionFactory))
                .handle((payload, headers)-> {
                    System.out.println("reading data");
                    System.out.println("Raw Data: " + Arrays.toString((byte[]) payload));
                    String message = new String((byte[]) payload).trim();
                    System.out.println("Received from device " + device.getDeviceId() +"::" +message  );
                    try {
                        // Handle the establishment phase
                        if (message.contains("<ENQ>")) {
                            System.out.println("Establishment phase started with <ENQ>");
                            return "<ACK>"; // Respond with Acknowledgment
                        }

                        // Handle the termination phase
                        if (message.contains("<EOT>")) {
                            System.out.println("Termination phase started with <EOT>");
                            cleanupSession(device.getDeviceId());
                            return "<ACK>"; // Acknowledge termination
                        }

                        // Handle the message transfer phase
                        String completeMessage = extractAndValidateMessage(message);
                        MessageParser messageParser = new DynamicMessageParser(messageParserFactory.getParser( device.getParserType()));
                        Map<String, Object> result = messageParser.parse(completeMessage);
                        hl7Converter.convert(result);
                        // need to saved result to be parsed latest

                        // Log or process the parsed message
                        System.out.println("Parsed Message: " + result);


                        return "<ACK>";
                    } catch (IllegalArgumentException e) {
                        // Handle message errors
                        System.err.println("Message Error: " + e.getMessage());
                        return "<NAK>"; // Negative acknowledgment
                    }
                })
                .get();
    }

    private String extractAndValidateMessage(String payload) {
        // Check for Start of Text
        if (!payload.startsWith("<STX>")) {
            throw new IllegalArgumentException("Message does not start with <STX>");
        }

        // Locate End of Text
        int endIndex = payload.indexOf("<ETX>");
        if (endIndex == -1) {
            endIndex = payload.indexOf("<ETB>");
            if (endIndex == -1) {
                throw new IllegalArgumentException("Message does not contain <ETX> or <ETB>");
            }
        }

        // Extract the message body (excluding <STX> and <ETX>/<ETB>)
        String messageBody = payload.substring(5, endIndex);

        // Validate checksum (last two characters after <ETX>/<ETB>)
        String checksum = payload.substring(endIndex + 5, endIndex + 7);
        if (!validateChecksum(messageBody, checksum)) {
            throw new IllegalArgumentException("Invalid checksum");
        }

        return messageBody;
    }

    private boolean validateChecksum(String messageBody, String checksum) {
        int calculatedSum = 0;
        for (char c : messageBody.toCharArray()) {
            calculatedSum += c;
        }
        calculatedSum %= 256; // Modulo 256 as per specification
        String calculatedChecksum = String.format("%02X", calculatedSum);
        return calculatedChecksum.equalsIgnoreCase(checksum);
    }

    private void cleanupSession(String deviceId) {
        // Log termination and release resources
        System.out.println("Cleaning up session for device: " + deviceId);

        // Add logic to free up session resources (e.g., buffers, connections)
        // Example: Remove session-specific data or logs
    }
}