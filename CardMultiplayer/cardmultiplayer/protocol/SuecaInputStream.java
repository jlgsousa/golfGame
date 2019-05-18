package cardmultiplayer.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class SuecaInputStream extends ObjectInputStream {

    public SuecaInputStream(InputStream in) throws IOException {
        super(in);
    }

    public IMessage readMessage() throws IOException, ClassNotFoundException {
        return (IMessage) this.readObject();
    }

//    @Override
//    public String readUTF() throws IOException {
//        throw new IOException("Operation not supported");
//    }
}
