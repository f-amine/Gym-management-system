package appClasses;

public class Receptionist extends Employee {
	  public Receptionist(int id, String firstName, String lastName, String email, String phoneNumber, String address, String paymentInformation) {
	    super(id, firstName, lastName, email, phoneNumber, address, paymentInformation);
	    this.role ="receptionist";
	  }

	  public void addMember(Member member) {
	    // code to add a new member to the database
	  }

	  public void updateMember(Member member) {
	    // code to update a member's information in the database
	  }

	  public void deleteMember(Member member) {
	    // code to delete a member from the database
	  }

	  public void addMembershipOffer(MembershipOffer offer) {
	    // code to add a new membership offer to the database
	  }

	  public void updateMembershipOffer(MembershipOffer offer) {
	    // code to update a membership offer in the database
	  }

	  public void deleteMembershipOffer(MembershipOffer offer) {
	    // code to delete a membership offer from the database
	  }
	}
