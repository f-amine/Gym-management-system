package appClasses;

public class Equipment {
		  private int equipementId;
		  private String name;
		  private int quantity;
		  private double pricePerUnit;

		  public Equipment(int equipementId, String name, int quantity, double pricePerUnit) {
		    this.equipementId = equipementId;
		    this.name = name;
		    this.quantity = quantity;
		    this.pricePerUnit = pricePerUnit;
		  }

		  public int getEquipementId() {
		    return equipementId;
		  }

		  public String getName() {
		    return name;
		  }

		  public int getQuantity() {
		    return quantity;
		  }

		  public double getPricePerUnit() {
		    return pricePerUnit;
		  }

		  public void setQuantity(int quantity) {
		    this.quantity = quantity;
		  }

		  public boolean isAvailable() {
		    return quantity > 0;
		  }
		}
