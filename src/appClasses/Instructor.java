package appClasses;

public class Instructor extends Employee {
	  public Instructor(int id, String firstName, String lastName, String email, String phoneNumber, String address, String role, String paymentInformation) {
	    super(id, firstName, lastName, email, phoneNumber, address, role, paymentInformation);
	  }

	  public void assignProgram(Member member, Program program) {
		  member.setProgram(program);
	  }
	  public void addProgram() {
		   // code to add a new program to the database
	  }
	}

