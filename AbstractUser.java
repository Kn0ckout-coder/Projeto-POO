
/**
 * Write a description of class d here.
 *
 * @author Rodrigo Carneiro | Marco Carregal
 * @version 1
 */
public abstract class AbstractUser implements User {
    protected String email;
    protected String password;

    public AbstractUser(String email, String password) {
        if (!email.contains("@")) {
            throw new InvalidEmailException("Email invalido");
        }
        this.email = email;
        this.password = password;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setEmail(String email) {
        if (!email.contains("@")) {
            throw new InvalidEmailException("Email invalido");
        }
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void login(String email, String password) {
        if (this.email.equals(email) && this.password.equals(password)) {
            System.out.println("Login efetuado");
        } else {
            throw new SecurityException("Email ou password inválida");
        }
    }

    @Override
    public void logout() {
        System.out.println("Logout efetuado");
    }
}

