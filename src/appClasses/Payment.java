package appClasses;

import java.util.Date;

public class Payment {
  private int paymentId;
  private double amount;
  private Date date;
  private String paymentMethod;
  public Member member;
  private String memberName;

  public Payment(int paymentId, Date date, String paymentMethod, Member member) {
    this.paymentId = paymentId;
    this.amount = this.member.getMembershipType().getPrice();
    this.date = date;
    this.paymentMethod = paymentMethod;
    this.member = member;
  }
  

  public Payment(int paymentId,String memberName, double amount, Date date, String paymentMethod) {
	this.paymentId = paymentId;
	this.amount = amount;
	this.date = date;
	this.paymentMethod = paymentMethod;
	this.memberName = memberName;
}


public int getPaymentId() {
    return paymentId;
  }

  public double getAmount() {
    return amount;
  }

  public Date getDate() {
    return date;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }


public String getMemberName() {
	return memberName;
}


public void setMemberName(String memberName) {
	this.memberName = memberName;
}
  
}

