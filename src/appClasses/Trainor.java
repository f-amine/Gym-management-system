package appClasses;

public class Trainor extends Employee {

	public Trainor(int id, String firstName, String lastName, String email, String phoneNumber, String address,
			String paymentInformation) {
		  super(id, firstName, lastName, email, phoneNumber, address, paymentInformation);
		  role = "trainor";
	}

	public void assignProgram(Member member, Program program) {
		  member.setProgram(program);
	  }
	  public void addProgram() {
		  
	  }
	}

