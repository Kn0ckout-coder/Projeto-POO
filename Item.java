
/**
 * Write a description of class d here.
 *
 * @author Rodrigo Carneiro | Marco Carregal
 * @version 1
 * pra criares vais no normal e poes as coisas
 * string é string
 * itemtype poes ItemType.CAMISA por exemplo
 * gender poes Gender.MULHER
 * o resto ja sabes
 **/
public class Item {
    private String code;
    private ItemType type;
    private Gender category;
    private String description;
    private double purchasePrice;
    private double salePrice;

    public Item(String code, ItemType type, Gender category, double purchasePrice, double salePrice) {
        this.code = code;
        this.type = type;
        this.category = category;
        this.description = "";
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
    }
    
    //getters 
    public String getCode() {
        return code;
    }

    public ItemType getType() {
        return type;
    }

    public Gender getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }
    
    public double getSalePrice() {
        return salePrice;
    }
    
    // setters
    public void setDescription(String description) {
        this.description = description;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }
}
