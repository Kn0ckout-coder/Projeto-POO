import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Write a description of class d here.
 *
 * @author Rodrigo Carneiro | Marco Carregal
 * @version 1
 */
public class OnlineStore {
    private ArrayList<Admin> admins;
    private List<StockItem> sells;
    private ArrayList<Client> clients;
    private HashMap<String, StockItem> stock;
    private ArrayList<Item> soldItems;

    public OnlineStore() {
        this.admins = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.stock = new HashMap<>();
        this.soldItems = new ArrayList<>();
        this.sells = new ArrayList<>();
        // inicializa admins
        admins.add(new Admin("adm1@vestbem.com", "123", false));
        admins.add(new Admin("adm2@vestbem.com", "123", true));
    }
    
    public User login(String email, String password) {
        for (Admin admin : admins) {
            if (admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        for (Client client : clients) {
            if (client.getEmail().equals(email) && client.getPassword().equals(password)) {
                return client;
            }
        }
        return null;
    }
    
    public void registerClient(String email, String password, String name, Gender gender) {
        clients.add(new Client(email, password, name, gender));
    }

    public void addItem(Admin admin, Item item, int quantity) {
        if (isAuthorizedAdmin(admin)) {
            stock.put(item.getCode(), new StockItem(item, quantity));
        } else {
            throw new SecurityException("admin nao autorizado a adicionar items.");
        }
    }

    public void removeItem(Admin admin, String itemCode) {
        if (isAuthorizedAdmin(admin)) {
            stock.remove(itemCode);
        } else {
            throw new SecurityException("admin nao autorizado a remover items.");
        }
    }

    public void changeItem(Admin admin, String itemCode, double newPurchasePrice, double newSalePrice) {
        if (isAuthorizedAdmin(admin)) {
            StockItem stockItem = stock.get(itemCode);
            if (stockItem != null) {
                stockItem.getItem().setPurchasePrice(newPurchasePrice);
                stockItem.getItem().setSalePrice(newSalePrice);
            } else {
                throw new IllegalArgumentException("Item não encontrado");
            }
        } else {
            throw new SecurityException("Admin não autorizado a mudar itens.");
        }
    }

    public void changeStockItem(Admin admin, String itemCode, int newQuantity) {
        if (isAuthorizedAdmin(admin)) {
            StockItem stockItem = stock.get(itemCode);
            if (stockItem != null) {
                stockItem.setQuantity(newQuantity);
            } else {
                throw new IllegalArgumentException("Item não encontrado");
            }
        } else {
            throw new SecurityException("Admin não autorizado a mudar itens");
        }
    }

    public List<StockItem> displayItemsWithLessStock(int threshold) {
        List<StockItem> itemsWithLessStock = new ArrayList<>();
        for (StockItem stockItem : stock.values()) {
            if (stockItem.getQuantity() < threshold) {
                itemsWithLessStock.add(stockItem);
            }
        }
        return itemsWithLessStock;
    }

    public List<Item> displayItemsSold(ItemType type, Gender category) {
        List<Item> filteredItems = new ArrayList<>();
        for (Item item : soldItems) {
            if (item.getType() == type && item.getCategory() == category) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public Admin getAdminByEmail(String email) {
        for (Admin admin : admins) {
            if (admin.getEmail().equals(email)) {
                return admin;
            }
        }
        return null;
    }

    public Client getClientByEmail(String email) {
        for (Client client : clients) {
            if (client.getEmail().equals(email)) {
                return client;
            }
        }
        return null;
    }

    public boolean isAuthorizedAdmin(Admin admin) {
        return admin != null && admin.isGlobalAdmin();
    }

    
    public List<StockItem> displayItemsWithStock(ItemType type, Gender category) {
        List<StockItem> availableItems = new ArrayList<>();
        for (StockItem stockItem : stock.values()) {
            Item item = stockItem.getItem();
            if (item.getType() == type && item.getCategory() == category && stockItem.getQuantity() > 0) {
                availableItems.add(stockItem);
            }
        }
        return availableItems;
    }
    
    public void exportItemsSold(Admin admin) {
        if (admin.isGlobalAdmin()) {
            try (FileWriter writer = new FileWriter("sold_items.txt")) {
                for (StockItem soldItem : sells) {
                    writer.write(soldItem.toString() + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new SecurityException("admin nao autorizado para exportar");
        }
    }
    

    public void addItemToCart(Client client, String itemCode) {
        StockItem stockItem = stock.get(itemCode);
        if (stockItem != null && stockItem.getQuantity() > 0) {
            client.getCart().add(stockItem.getItem());
            stockItem.setQuantity(stockItem.getQuantity() - 1);
        } else {
            throw new IllegalArgumentException("Item não está para venda");
        }
    }

    public void removeItemFromCart(Client client, String itemCode) {
        List<Item> cart = client.getCart();
        Item itemToRemove = null;
        for (Item item : cart) {
            if (item.getCode().equals(itemCode)) {
                itemToRemove = item;
                break;
            }
        }
        if (itemToRemove != null) {
            cart.remove(itemToRemove);
            StockItem stockItem = stock.get(itemCode);
            stockItem.setQuantity(stockItem.getQuantity() + 1);
        } else {
            throw new IllegalArgumentException("Item não está no carrinho.");
        }
    }

    public void checkout(Client client) {
        List<Item> cart = client.getCart();
        if (cart.isEmpty())
            System.out.println("Não tem nada no carrinho");
        else{
            client.getPurchaseHistory().addAll(cart);
            for (Item item : cart) {
                sells.add(new StockItem(item, 1));
            }
            soldItems.addAll(cart);
            cart.clear();
        }
    }

    public List<Item> displayItemsBought(Client client) {
        return client.getPurchaseHistory();
    }
}

