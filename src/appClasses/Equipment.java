package appClasses;

public class Equipment {
		  private String equipementId;
		  private String name;
		  private int quantity;
		  private double pricePerUnit;

		  public Equipment(String equipementId, String name, int quantity, double pricePerUnit) {
		    this.equipementId = equipementId;
		    this.name = name;
		    this.quantity = quantity;
		    this.pricePerUnit = pricePerUnit;
		  }

		  public String getEquipementId() {
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
