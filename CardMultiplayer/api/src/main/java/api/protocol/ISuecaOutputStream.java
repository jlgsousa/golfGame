package api.protocol;

import api.protocol.message.IMessage;

import java.io.IOException;

public interface ISuecaOutputStream {
    void writeMessage(IMessage message) throws IOException;
}
