package it_test.object.streams;

import java.io.Serializable;

public class Message implements Serializable {

    private String messageString;

    public Message(String messageString) {
        this.messageString = messageString;
    }

    public String getMessageString() {
        return messageString;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }
}
