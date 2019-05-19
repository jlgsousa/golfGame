import entities.Author;
import entities.IAuthor;
import entities.IJournal;
import entities.Journal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Printer {
    private Scanner scanner;

    Printer() {
        scanner = new Scanner(System.in);
    }

    void printPrompt() {
        System.out.println("*****************  Welcome to data archive  *******************");
        System.out.println("*To add an author - enter 1                                   *");
        System.out.println("*To add a journal - enter 2                                   *");
        System.out.println("*To remove a journal - enter 3                                *");
        System.out.println("*To remover an author - enter 4                               *");
        System.out.println("*To get a list of authors details - enter 5                   *");
        System.out.println("*To get the authors of an institution - enter 6               *");
        System.out.println("*To get statistic data - enter 7                              *");
        System.out.println("*To exit the application - enter   \"exit\"                     *");
        System.out.println("***************************************************************");
        System.out.print("->");
    }

    String getInput() {
        return scanner.nextLine();
    }

    IAuthor getAuthor() {
        IAuthor author = new Author();
        System.out.println("Nome do autor: ");
        String nome = scanner.nextLine();
        System.out.println(("Empresa a que pertence: "));
        String empresa = scanner.nextLine();
        System.out.println("Competencias do autor: ");
        String competencias = scanner.nextLine();
        System.out.println("E-mail: ");
        String email = scanner.nextLine();
        author.init(nome, empresa, competencias, email);

        return author;
    }

    void printResultAddAuthor(boolean success) {
        System.out.println(success ? "Author added successfully" : "Error: existing author");
        System.out.println(" ");
    }

    IJournal getJournal() {
        IJournal journal = new Journal();
        System.out.println("Title: ");
        String nome = scanner.nextLine();
        System.out.println(("Summary: "));
        String resumo = scanner.nextLine();
        System.out.println("Year: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        System.out.println("How many authors does the journal have? ");
        int num = scanner.nextInt();
        scanner.nextLine();
        List<String> creators = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            System.out.println("Author's name:");
            String autor = scanner.nextLine();
            creators.add(autor);
        }
        journal.init(nome, resumo, ano, creators);
        return journal;
    }

    void printResultAddJournal(boolean success) {
        System.out.println(success ? "Journal added successfully" : "Error: non existing author or existing journal");
        System.out.println(" ");
    }

    String getJournalTitle() {
        System.out.println("Titulo: ");
        return scanner.nextLine();
    }

    void printResultRemoveJournal(boolean success) {
        System.out.println(success ? "Journal removed successfully" : "Error: non existing journal");
        System.out.println(" ");
    }

    String getAuthorName() {
        System.out.println("Nome do autor: ");
        return scanner.nextLine();
    }

    void printResultRemoveAuthor(boolean success) {
        System.out.println(success ? "Author removed successfully" : "Error: author has journals or is non existing");
        System.out.println(" ");
    }

    void printAuthors(List<String> authors) {
        if (authors == null || authors.size() == 0) {
            System.out.println("No authors recorded yet");
            return;
        }
        authors.forEach(System.out::println);
    }

    String getInstitution() {
        System.out.println("Institution name: ");
        return scanner.nextLine();
    }

    void printAuthorsInInstitution(List<String> authorNames, String institution) {
        if (authorNames == null || authorNames.size() == 0) {
            System.out.println("No author is part of " + institution);
            return;
        }
        System.out.println("Authors belonging to institution " + institution);
        authorNames.forEach(System.out::println);
    }

    void printStatistics(int totalAuthors, int totalJournals, double avgAuthorPerJournal, double avgJournalPerAuthor) {
        if (totalAuthors == 0) {
            System.out.println("No authors recorded yet");
        } else {
            System.out.println("Total number of authors:");
            System.out.println(totalAuthors);
            System.out.println("Total number of journals: ");
            System.out.println(totalJournals);
            System.out.println("Average number of authors per journal: ");
            System.out.println(avgAuthorPerJournal);
            System.out.println("Average number of journals per author: ");
            System.out.println(avgJournalPerAuthor);
        }
    }
}
