package com.es.service;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;
import java.nio.file.*;

@MessageDriven(mappedName = "jms/dest", activationConfig = {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class Consumer implements MessageListener {
    private final Path PATH = Paths.get("E:\\Programs\\glassfish5\\file.txt");

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            Files.write(PATH, text.getBytes());
        } catch (JMSException | IOException e) {
            e.printStackTrace();
        }
    }
}
