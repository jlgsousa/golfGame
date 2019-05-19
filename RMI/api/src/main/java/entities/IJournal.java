package entities;

import java.util.List;

public interface IJournal {
    String getName();
    String getSummary();
    int getYear();
    List<String> getCreators();
    void init(String name, String brief, int year, List<String> creators);
}
