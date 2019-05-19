import entities.IAuthor;
import entities.IJournal;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {

        String ip = "localhost";
        int port = 6767;
        String referenceName = "management";

        if (args != null && args.length == 3) {
            ip = args[0];
            port = Integer.parseInt(args[1]);
            referenceName = args[2];
        }

        Printer printer = new Printer();
        String option = " ";
        int attempt = 0;

        while (!option.equals("exit")) {
            try {
                Registry registry = LocateRegistry.getRegistry(ip, port);
                App rmiApp = (App) registry.lookup(referenceName);

                printer.printPrompt();

                option = printer.getInput();

                switch (option) {
                    case "1": {
                        IAuthor author = printer.getAuthor();
                        printer.printResultAddAuthor(rmiApp.addAuthor(author));
                        break;
                    }
                    case "2": {
                        IJournal journal = printer.getJournal();
                        printer.printResultAddJournal(rmiApp.addJournal(journal));
                        break;
                    }
                    case "3": {
                        String title = printer.getJournalTitle();
                        printer.printResultRemoveJournal(rmiApp.removeJournal(title));
                        break;
                    }
                    case "4": {
                        String author = printer.getAuthorName();
                        printer.printResultRemoveAuthor(rmiApp.removeAuthor(author));
                        System.out.println("Nome do autor: ");
                        break;
                    }
                    case "5":
                        printer.printAuthors(rmiApp.listAuthors());
                        break;
                    case "6": {
                        String institution = printer.getInstitution();
                        printer.printAuthorsInInstitution(rmiApp.listAuthorsInInstitution(institution), institution);
                        break;
                    }
                    case "7": {
                        printer.printStatistics(rmiApp.getTotalAuthors(),
                                rmiApp.getTotalJournals(),
                                rmiApp.getAverageAuthorsPerJournal(),
                                rmiApp.getAverageJournalsPerAuthor());
                        break;
                    }
                }

            } catch (Exception e) {
                if (attempt == 5) {
                    break;
                }
                attempt++;
                e.printStackTrace();
            }
        }
    }
}
