package appClasses;

public class Employee {
	  private int id;
	  private String firstName;
	  private String lastName;
	  private String email;
	  private String phoneNumber;
	  private String address;
	  private String role;
	  private String paymentInformation;

	  public Employee(int id, String firstName, String lastName, String email, String phoneNumber, String address, String role, String paymentInformation) {
	    this.id = id;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.email = email;
	    this.phoneNumber = phoneNumber;
	    this.address = address;
	    this.role = role;
	    this.paymentInformation = paymentInformation;
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

	  public String getRole() {
	    return role;
	  }

	  public void setRole(String role) {
	    this.role = role;
	  }

	  public String getPaymentInformation() {
	    return paymentInformation;
	  }

	  public void setPaymentInformation(String paymentInformation) {
	    this.paymentInformation = paymentInformation;
	  }
	}