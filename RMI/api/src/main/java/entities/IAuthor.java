package entities;

public interface IAuthor {
    String getName();
    String getInstitution();
    String getCompetences();
    String getEmail();
    void init(String name, String institution, String competences, String email);
}
