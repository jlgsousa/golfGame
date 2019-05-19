import entities.IAuthor;
import entities.IJournal;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppImpl extends UnicastRemoteObject implements App {
	private List<IAuthor> authors;
	private List<IJournal> journals;

	public AppImpl() throws RemoteException {
		authors = new ArrayList<>();
		journals = new ArrayList<>();
	}

	public boolean addAuthor(final IAuthor author) {

		boolean isNotRepeated = authors.stream()
				.noneMatch(a -> a.getName().equals(author.getName()));

		return isNotRepeated ? authors.add(author) : false;
	}
	
	public boolean addJournal(IJournal journal) {

		int validAuthors = authors.stream()
				.filter(a -> journal.getCreators().contains(a.getName()))
				.collect(Collectors.toList()).size();

		boolean allAuthorsValid = validAuthors == journal.getCreators().size();

		boolean isNotRepeated = journals.stream()
				.noneMatch(j -> j.getName().equals(journal.getName()));

		return allAuthorsValid && isNotRepeated ? journals.add(journal) : false;
	}

	public boolean removeJournal(String title) {

		List<IJournal> foundJournals = journals.stream().filter(j -> j.getName().equals(title))
				.collect(Collectors.toList());

		foundJournals.forEach(j -> journals.remove(j));

		return foundJournals.size() > 0;
	}

	public boolean removeAuthor(String name) {
		boolean noPapers = journals.stream().noneMatch(j -> j.getCreators().contains(name));

		if (noPapers){
			authors.stream().filter(a -> a.getName().equals(name))
					.forEach(a -> authors.remove(a));
			return true;
		}
		return false;
	}

	@Override
	public int getTotalAuthors() {
		return authors.size();
	}

	@Override
	public int getTotalJournals() {
		return journals.size();
	}

	@Override
	public double getAverageAuthorsPerJournal() {
		if (journals.isEmpty()) return 0;

		double result = journals.stream().mapToDouble(j -> j.getCreators().size()).sum();
		result /= journals.size();
		return result;
	}

	@Override
	public double getAverageJournalsPerAuthor() {
		double result = 0.0;

		for (IAuthor author : authors) {
			result += journals.stream().filter(j -> j.getCreators().contains(author.getName()))
					.collect(Collectors.toList()).size();
		}

		result /= authors.size();
		return result;
	}

	public List<String> listAuthors() {
		List<String> authorsList = new ArrayList<>();
		StringBuilder authorDetails = new StringBuilder();
		String journalsDetails = "";

		for (IAuthor author : authors){
			authorDetails.append("Author's name: ").append(author.getName()).append("\n");
			authorDetails.append("Belonging institution: ").append(author.getInstitution()).append("\n");
			authorDetails.append("Competences: ").append(author.getCompetences()).append("\n");
			authorDetails.append("E-mail: ").append(author.getEmail()).append("\n");
			authorDetails.append("Journals: \n");
			for (IJournal journal : journals) {
				if (journal.getCreators().contains(author.getName())) {
					journalsDetails +=  ("Title: " + journal.getName() + "\n"
							+ "Year: " + journal.getYear() + "\n"
							+ "Summary: " + journal.getSummary() + "\n");
				}
			}
			authorDetails.append(journalsDetails);
			authorsList.add(authorDetails.toString());
		}

		return authorsList;
	}

	public List<String> listAuthorsInInstitution(String institution) {
		List<String> authorsList = new ArrayList<>();

		authors.stream().filter(a -> a.getInstitution().equals(institution))
				.forEach(a -> authorsList.add(a.getName()));

		return authorsList;
	}
	
}

