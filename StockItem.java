
/**
 * Write a description of class d here.
 *
 * @author Rodrigo Carneiro | Marco Carregal
 * @version 1
 */
public class StockItem {
    private Item item;
    private int quantity;

    public StockItem(Item item, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser menos de 0");
        }
        this.item = item;
        this.quantity = quantity;
    }

    // Getters
    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setItem(Item item) {
        this.item = item;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser menos de 0");
        }
        this.quantity = quantity;
    }
    
    public String toString() {
        return  "{Item=" + item + ", Quantidade=" + quantity + "}";
    }
}

