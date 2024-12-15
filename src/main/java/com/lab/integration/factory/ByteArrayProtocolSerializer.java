package com.lab.integration.factory;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ByteArrayProtocolSerializer implements Deserializer<byte[]>, Serializer<byte[]> {
    @Override
    public byte[] deserialize(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nextByte;
        while ((nextByte = inputStream.read()) != -1) {
            buffer.write(nextByte);
            // Stop reading if protocol marker (e.g., <EOT>) is reached
            if (nextByte == 0x04) { // <EOT> in ASCII
                break;
            }
        }
        return buffer.toByteArray();
    }

    @Override
    public void serialize(byte[] data, OutputStream outputStream) throws IOException {
        outputStream.write(data);
        outputStream.flush();
    }
}