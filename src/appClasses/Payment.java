package appClasses;

import java.util.Date;

public class Payment {
  private String paymentId;
  private double amount;
  private Date date;
  private String paymentMethod;
  private String paymentStatus;
  public Member member;

  public Payment(String paymentId, Date date, String paymentMethod, String paymentStatus, Member member) {
    this.paymentId = paymentId;
    this.amount = this.member.getMembershipType().getPrice();
    this.date = date;
    this.paymentMethod = paymentMethod;
    this.paymentStatus = paymentStatus;
    this.member = member;
  }

  public String getPaymentId() {
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

  public String getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(String paymentStatus) {
    this.paymentStatus = paymentStatus;
  }
}

