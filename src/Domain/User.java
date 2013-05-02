package Domain;

/**
 *
 * @author Kirstine
 */
public class User {
    
    public final static int USER_LEVEL_ADMIN = 1;
    public final static int USER_LEVEL_NORMAL = 2;

    private int level;
    private String brugernavn;
    private String password;

    public User(String brugernavn, String password, int brugerniveau) {
        level = brugerniveau;
        this.brugernavn = brugernavn;
        this.password = password;
    }

    public int getBrugerNiveau() {
        return level;
    }

    public String getBrugernavn() {
        return brugernavn;
    }

    public String getPassword() {
        return password;
    }

    public void setBrugerNiveau(int i) {
        level = i;
    }

    public void setBrugernavn(String brugernavn) {
        this.brugernavn = brugernavn;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return brugernavn;
    }
}
