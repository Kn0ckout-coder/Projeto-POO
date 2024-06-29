import java.util.ArrayList;

/**
 * Write a description of class d here.
 *
 * @author Rodrigo Carneiro | Marco Carregal
 * @version 1
 */
public class Client extends AbstractUser {
    private String name;
    private Gender gender;
    private ArrayList<Item> purchaseHistory;
    private ArrayList<Item> cart;

    public Client(String email, String password, String name, Gender gender) {
        super(email, password);
        this.name = name;
        this.gender = gender;
        this.purchaseHistory = new ArrayList<>();
        this.cart = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public ArrayList<Item> getPurchaseHistory() {
        return purchaseHistory;
    }

    public ArrayList<Item> getCart() {
        return cart;
    }
}

