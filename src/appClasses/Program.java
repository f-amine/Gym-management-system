package appClasses;

public class Program {
	  private int id;
	  private String name;
	  private String description;

	  public Program(int id, String name, String description, Member member, Instructor instructor) {
	    this.id = id;
	    this.name = name;
	    this.description = description;
	  }

	  public int getId() {
	    return id;
	  }

	  public void setId(int id) {
	    this.id = id;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public String getDescription() {
	    return description;
	  }

	  public void setDescription(String description) {
	    this.description = description;
	  }
	}
