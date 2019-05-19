package entities;

import java.io.Serializable;

public class Author implements IAuthor, Serializable {
	private String name;
	private String institution;
	private String competences;
	private String email;
	
	public String getName()
	{
		return name;
	}
	public String getInstitution()
	{
		return institution;
	}
	public String getCompetences()
	{
		return competences;
	}
	public String getEmail()
	{
		return email;
	}

	public void init(String name, String institution, String competences, String email) {
		this.name =name;
		this.institution =institution;
		this.competences =competences;
		this.email=email;
	}
}

