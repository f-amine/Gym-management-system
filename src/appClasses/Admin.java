package appClasses;

public class Admin extends Receptionist {
	  public Admin(int id, String firstName, String lastName, String email, String phoneNumber, String address, String role, String paymentInformation) {
	    super(id, firstName, lastName, email, phoneNumber, address, role, paymentInformation);
	  }

	  public void addReceptionist(Receptionist receptionist) {
	    // code to add a new receptionist to the database
	  }

	  public void updateReceptionist(Receptionist receptionist) {
	    // code to update a receptionist's information in the database
	  }

	  public void deleteReceptionist(Receptionist receptionist) {
	    // code to delete a receptionist from the database
	  }

	  public void addInstructor(Instructor instructor) {
	    // code to add a new instructor to the database
	  }

	  public void updateInstructor(Instructor instructor) {
	    // code to update an instructor's information in the database
	  }

	  public void deleteInstructor(Instructor instructor) { 
	    // code to delete an instructor from the database
	  }
	}