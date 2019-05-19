import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		String referenceName = "management";
		int port = 6767;
		if (args != null && args.length == 2) {
			referenceName = args[0];
			port = Integer.parseInt(args[1]);
		}

		App object = new AppImpl();

		Registry registry = LocateRegistry.createRegistry(port);
		registry.bind(referenceName, object);

		System.out.println("Server is on!");
	}
}
