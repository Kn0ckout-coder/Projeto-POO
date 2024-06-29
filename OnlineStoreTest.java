import java.util.Scanner;
import java.util.List;

/**
 * Write a description of class d here.
 *
 * @author Rodrigo Carneiro | Marco Carregal
 * @version 1
 */
public class OnlineStoreTest {
    private static OnlineStore store = new OnlineStore();
    private static Scanner scanner = new Scanner(System.in);
    private static Client currentClient = null;
    private static Admin currentAdmin = null;
    public void main() {
        while (true) {
            showInitialMenu();
        }
    }
    
    private static void showInitialMenu() {
        while (true) {
            System.out.println("Bem-vindo à Loja Online");
            System.out.println("1. Registar Cliente");
            System.out.println("2. Login Cliente");
            System.out.println("3. Login Admin");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    registerClient();
                    break;
                case 2:
                    loginClient();
                    break;
                case 3:
                    loginAdmin();
                    break;
                case 4:
                    System.out.println("A sair...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
    
    private static void loginClient() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String password = scanner.nextLine();
        User user = store.login(email, password);
        if (user instanceof Client) {
            currentClient = (Client) user;
            showClientMenu(currentClient);
        } else {
            System.out.println("Email ou senha inválidos.");
        }
    }

    private static void loginAdmin() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String password = scanner.nextLine();
        User user = store.login(email, password);
        if (user instanceof Admin) {
            currentAdmin = (Admin) user;
            showAdminMenu(currentAdmin);
        } else {
            System.out.println("Email ou senha inválidos.");
        }
    }

    private static void registerClient() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Nome: ");
        String name = scanner.nextLine();
        System.out.print("Categoria (Homem/Mulher): ");
        char categoryChar = scanner.nextLine().charAt(0);
        Gender category = (categoryChar == 'M' || categoryChar == 'm') ? Gender.HOMEM : Gender.MULHER;

        store.registerClient(email, password, name, category);
        System.out.println("Cliente registado com sucesso.");
    }

    private static void showAdminMenu(Admin admin) {
        while (true) {
            System.out.println("Menu de Administrador:");
            System.out.println("1. Adicionar nova peça de vestuário");
            System.out.println("2. Remover peça de vestuário");
            System.out.println("3. Alterar dados de uma peça de vestuário");
            System.out.println("4. Alterar quantidades de um item em stock");
            System.out.println("5. Mostrar peças com poucas unidades disponíveis");
            System.out.println("6. Mostrar peças vendidas");
            System.out.println("8. Mostra peças em stock");
            if (admin.isGlobalAdmin()) {
                System.out.println("7. Exportar ficheiro com todas as vendas realizadas");
            } 
            System.out.println("9. Logout");
            System.out.print("Escolha uma opção: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addItem(currentAdmin);
                    break;
                case 2:
                    removeItem(currentAdmin);
                    break;
                case 3:
                    changeItem(currentAdmin);
                    break;
                case 4:
                    changeStockItem(currentAdmin);
                    break;
                case 5:
                    displayItemsWithLessStock();
                    break;
                case 6:
                    displayItemsSold();
                    break;
                case 7:
                    exportItemsSold(currentAdmin);
                    break;
                case 8:
                    displayItemsWithStock();
                    break;
                case 9:
                    System.out.println("Logout...");
                    currentAdmin = null;
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void showClientMenu(Client client) {
        while (true) {
            System.out.println("Menu de Cliente:");
            System.out.println("1. Mostrar peças disponíveis para venda");
            System.out.println("2. Adicionar peça ao carrinho de compras");
            System.out.println("3. Remover peça do carrinho de compras");
            System.out.println("4. Finalizar compra");
            System.out.println("5. Visualizar histórico das compras");
            System.out.println("6. Logout");
            System.out.print("Escolha uma opção: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayItemsWithStock();
                    break;
                case 2:
                    addItemToCart(client);
                    break;
                case 3:
                    removeItemFromCart(client);
                    break;
                case 4:
                    checkout(client);
                    break;
                case 5:
                    displayItemsBought(client);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
    
    private static void addItem(Admin admin) {
        System.out.print("Código: ");
        String code = scanner.nextLine();
        System.out.print("Descrição: ");
        String description = scanner.nextLine();
        System.out.print("Tipo (e.g., Calças, Camisas): ");
        String typeStr = scanner.nextLine();
        ItemType type = ItemType.valueOf(typeStr.toUpperCase());
        System.out.print("Categoria (M/F): ");
        char categoryChar = scanner.nextLine().charAt(0);
        Gender category = (categoryChar == 'M' || categoryChar == 'm') ? Gender.HOMEM : Gender.MULHER;
        System.out.print("Valor de Compra: ");
        double purchasePrice = scanner.nextDouble();
        System.out.print("Valor de Venda: ");
        double salePrice = scanner.nextDouble();
        System.out.print("Quantidade: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); 

        Item item = new Item(code, type, category, purchasePrice, salePrice);

        store.addItem(admin, item, quantity);
        System.out.println("Item adicionado com sucesso.");
    }

    private static void removeItem(Admin admin) {
        System.out.print("Código do item a remover: ");
        String code = scanner.nextLine();
        store.removeItem(admin, code);
        System.out.println("Item removido com sucesso.");
    }

    private static void changeItem(Admin admin) {
        System.out.print("Código do item a alterar: ");
        String code = scanner.nextLine();
        System.out.print("Novo valor de compra: ");
        double newPurchasePrice = scanner.nextDouble();
        System.out.print("Novo valor de venda: ");
        double newSalePrice = scanner.nextDouble();
        scanner.nextLine(); 

        store.changeItem(admin, code, newPurchasePrice, newSalePrice);
        System.out.println("Item alterado com sucesso.");
    }

    private static void changeStockItem(Admin admin) {
        System.out.print("Código do item a alterar stock: ");
        String code = scanner.nextLine();
        System.out.print("Nova quantidade: ");
        int newQuantity = scanner.nextInt();
        scanner.nextLine();

        store.changeStockItem(admin, code, newQuantity);
        System.out.println("Stock do item alterado com sucesso.");
    }

    private static void displayItemsWithLessStock() {
        System.out.print("Quantidade limite: ");
        int limit = scanner.nextInt();
        scanner.nextLine();

        List<StockItem> items = store.displayItemsWithLessStock(limit);
        if (items.isEmpty()) {
            System.out.println("Nenhum item com menos de " + limit + " unidades em stock.");
        } else {
            System.out.println("Itens com menos de " + limit + " unidades em stock:");
            for (StockItem item : items) {
                System.out.println(item);
            }
        }
    }

    private static void displayItemsSold() {
        System.out.print("Tipo (opcional, press Enter para pular): ");
        String typeStr = scanner.nextLine();
        ItemType type = typeStr.isEmpty() ? null : ItemType.valueOf(typeStr.toUpperCase());
        System.out.print("Categoria (opcional, press Enter para pular): ");
        String categoryStr = scanner.nextLine();
        Gender category = categoryStr.isEmpty() ? null : (categoryStr.equalsIgnoreCase("M") ? Gender.HOMEM : Gender.MULHER);

        List<Item> items = store.displayItemsSold(type, category);
        if (items.isEmpty()) {
            System.out.println("Nenhum item vendido correspondente aos critérios.");
        } else {
            System.out.println("Itens vendidos:");
            for (Item item : items) {
                System.out.println(item);
            }
        }
    }

    private static void exportItemsSold(Admin admin) {
        store.exportItemsSold(admin);
        System.out.println("Dados de vendas exportados com sucesso.");
    }

    private static void displayItemsWithStock() {
        System.out.print("Tipo (opcional, press Enter para pular): ");
        String typeStr = scanner.nextLine();
        ItemType type = typeStr.isEmpty() ? null : ItemType.valueOf(typeStr.toUpperCase());
        System.out.print("Categoria (opcional, press Enter para pular): ");
        String categoryStr = scanner.nextLine();
        Gender category = categoryStr.isEmpty() ? null : (categoryStr.equalsIgnoreCase("M") ? Gender.HOMEM : Gender.MULHER);

        List<StockItem> items = store.displayItemsWithStock(type, category); 
        if (items.isEmpty()) {
            System.out.println("Nenhum item disponível para venda correspondente aos critérios.");
        } else {
            System.out.println("Itens disponíveis para venda:");
            for (StockItem item : items) {
                System.out.println(item);
            }
        }
    }

    private static void addItemToCart(Client client) {
        System.out.print("Código do item a adicionar ao carrinho: ");
        String code = scanner.nextLine();
        store.addItemToCart(client, code);
        System.out.println("Item adicionado ao carrinho com sucesso.");
    }

    private static void removeItemFromCart(Client client) {
        System.out.print("Código do item a remover do carrinho: ");
        String code = scanner.nextLine();
        store.removeItemFromCart(client, code);
        System.out.println("Item removido do carrinho com sucesso.");
    }

    private static void checkout(Client client) {
        store.checkout(client);
        System.out.println("Compra finalizada com sucesso.");
    }

    private static void displayItemsBought(Client client) {
        List<Item> items = store.displayItemsBought(client);
        if (items.isEmpty()) {
            System.out.println("Nenhum item comprado.");
        } else {
            System.out.println("Histórico de compras:");
            for (Item item : items) {
                System.out.println(item);
            }
        }
    }
}