package appClasses;

public class Member {
	  private int id;
	  private String firstName;
	  private String lastName;
	  private String email;
	  private String phoneNumber;
	  private String address;
	  private MembershipOffer membershipType;
	  private String paymentInformation;
	  private String emergencyContactInfo;
	  private Program program;

	  public Member(int id, String firstName, String lastName, String email, String phoneNumber, String address, MembershipOffer membershipType, String paymentInformation, String emergencyContactInfo) {
	    this.id = id;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.email = email;
	    this.phoneNumber = phoneNumber;
	    this.address = address;
	    this.membershipType = membershipType;
	    this.paymentInformation = paymentInformation;
	    this.emergencyContactInfo = emergencyContactInfo;
	  }

	  public int getId() {
	    return id;
	  }

	  public void setId(int id) {
	    this.id = id;
	  }

	  public String getFirstName() {
	    return firstName;
	  }

	  public void setFirstName(String firstName) {
	    this.firstName = firstName;
	  }

	  public String getLastName() {
	    return lastName;
	  }

	  public void setLastName(String lastName) {
	    this.lastName = lastName;
	  }

	  public String getEmail() {
	    return email;
	  }

	  public void setEmail(String email) {
	    this.email = email;
	  }

	  public String getPhoneNumber() {
	    return phoneNumber;
	  }

	  public void setPhoneNumber(String phoneNumber) {
	    this.phoneNumber = phoneNumber;
	  }

	  public String getAddress() {
	    return address;
	  }

	  public void setAddress(String address) {
	    this.address = address;
	  }

	  public MembershipOffer getMembershipType() {
	    return membershipType;
	  }

	  public void setMembershipType(MembershipOffer membershipType) {
	    this.membershipType = membershipType;
	  }

	  public String getPaymentInformation() {
	    return paymentInformation;
	  }

	  public void setPaymentInformation(String paymentInformation) {
	    this.paymentInformation = paymentInformation;
	  }

	  public String getEmergencyContactInfo() {
	    return emergencyContactInfo;
	  }

	  public void setEmergencyContactInfo(String emergencyContactInfo) {
	    this.emergencyContactInfo = emergencyContactInfo;
	  }
	  
	  public Program getProgram() {
		    return program;
	  }

	 public void setProgram(Program program) {
		    this.program = program;
	  }
	}
