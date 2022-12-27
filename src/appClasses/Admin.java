package appClasses;

public class Admin extends Receptionist {
	  public Admin(int id, String firstName, String lastName, String email, String phoneNumber, String address, String paymentInformation) {
	    super(id, firstName, lastName, email, phoneNumber, address, paymentInformation);
	    this.role="Admin";
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

	  public void addInstructor(Trainor trainor) {
	    // code to add a new instructor to the database
	  }

	  public void updateInstructor(Trainor trainor) {
	    // code to update an instructor's information in the database
	  }

	  public void deleteInstructor(Trainor trainor) { 
	    // code to delete an instructor from the database
	  }
	}