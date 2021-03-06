package impl.protocol;

import api.protocol.ISuecaOutputStream;
import api.protocol.message.IMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SuecaOutputStream extends ObjectOutputStream implements ISuecaOutputStream {
    public SuecaOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    public void writeMessage(IMessage message) throws IOException {
        this.writeObject(message);
    }

//    @Override
//    public void writeUTF(String str) throws IOException {
//        throw new IOException("Operation not supported");
//    }
}
