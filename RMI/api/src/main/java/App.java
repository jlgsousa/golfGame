
import entities.IAuthor;
import entities.IJournal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface App extends Remote
{
	boolean addAuthor(IAuthor author) throws RemoteException;

	boolean addJournal(IJournal journal) throws RemoteException;

	boolean removeJournal(String title) throws RemoteException;

	boolean removeAuthor(String name) throws RemoteException;

	int getTotalAuthors() throws RemoteException;

	int getTotalJournals() throws RemoteException;

	double getAverageAuthorsPerJournal() throws RemoteException;

	double getAverageJournalsPerAuthor() throws RemoteException;
	
	List<String> listAuthors() throws RemoteException;
	
	List<String> listAuthorsInInstitution(String institution) throws RemoteException;

}
