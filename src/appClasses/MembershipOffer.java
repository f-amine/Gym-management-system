package appClasses;

public class MembershipOffer {
	  private int id;
	  private String name;
	  private int duration;
	  private double price;

	  public MembershipOffer(int id, String name, int duration, double price) {
	    this.id = id;
	    this.name = name;
	    this.duration = duration;
	    this.price = price;
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

	  public int getDuration() {
	    return duration;
	  }

	  public void setDuration(int duration) {
	    this.duration = duration;
	  }

	  public double getPrice() {
	    return price;
	  }

	  public void setPrice(double price) {
	    this.price = price;
	  }
	}
