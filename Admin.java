
/**
 * Write a description of class d here.
 *
 * @author Rodrigo Carneiro | Marco Carregal
 * @version 1
 */
public class Admin extends AbstractUser {
    private boolean isGlobalAdmin;

    public Admin(String email, String password, boolean isGlobalAdmin) {
        super(email, password);
        this.isGlobalAdmin = isGlobalAdmin;
    }

    public boolean isGlobalAdmin() {
        return isGlobalAdmin;
    }

        
}

