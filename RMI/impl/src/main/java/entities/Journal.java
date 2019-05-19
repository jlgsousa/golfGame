package entities;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;

public class Journal implements IJournal, Serializable {
	private String name;
	private String summary;
	private int year;
	private List<String> creators = new ArrayList<>();
	
	public String getName()
	{
		return name;
	}
	public String getSummary()
	{
		return summary;
	}
	public int getYear()
	{
		return year;
	}
	public List<String> getCreators()
	{
		return creators;
	}

	public void init(String name, String brief, int year, List<String> creators) {
		this.name = name;
		this.summary = brief;
		this.year = year;
		this.creators =  creators;
	}
}

