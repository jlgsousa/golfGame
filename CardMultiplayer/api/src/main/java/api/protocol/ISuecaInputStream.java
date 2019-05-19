package api.protocol;

import api.protocol.message.IMessage;

import java.io.IOException;

public interface ISuecaInputStream {

    IMessage readMessage() throws IOException, ClassNotFoundException;
}
