package appClasses;

import java.sql.Date;

public class Member {
	  private int id;
	  private MembershipOffer membershipType;
	  private Program program;
	  private String firstName;
	  private String lastName;
	  private String email;
	  private String phoneNumber;
	  private String address;
	  private String paymentInformation;
	  private String emergencyContactInfo;
	  private Date membershipExpiration;
	  private String membershipoffer;
	  private String ProgramName;


	  public Member(int id, MembershipOffer membershipType, Program program, String firstName, String lastName,
			String email, String phoneNumber, String address, String paymentInformation, String emergencyContactInfo,
			Date membershipExpiration) {

		this.id = id;
		this.membershipType = membershipType;
		this.program = program;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.paymentInformation = paymentInformation;
		this.emergencyContactInfo = emergencyContactInfo;
		this.membershipExpiration = membershipExpiration;
	}

	public Member(int id, String membershiptype, String program, String firstName, String lastname, String email,
			String phonenumber, String address, String paymentInformation, String emergencycontactinfo, Date membershipexpiration) {
		this.id = id;
		this.membershipoffer = membershiptype;
		this.ProgramName = program;
		this.firstName = firstName;
		this.lastName = lastname;
		this.email = email;
		this.phoneNumber = phonenumber;
		this.address = address;
		this.paymentInformation = paymentInformation;
		this.emergencyContactInfo = emergencycontactinfo;
		this.membershipExpiration = membershipexpiration;
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

	public Date getMembershipExpiration() {
		return membershipExpiration;
	}

	public void setMembershipExpiration(Date membershipExpiration) {
		this.membershipExpiration = membershipExpiration;
	}

	public String getMembershipoffer() {
		return membershipoffer;
	}

	public void setMembershipoffer(String membershipoffer) {
		this.membershipoffer = membershipoffer;
	}
	
	public String getProgramName() {
		return ProgramName;
	}

	public void setProgramName(String programName) {
		ProgramName = programName;
	}
	 
	}
