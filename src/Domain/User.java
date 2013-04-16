package Domain;

/**
 *
 * @author Kirstine
 */
public class User
{
    int eID;
    String brugernavn;
    String password;
    
    public User(int eID, String brugernavn, String password)
    {
        eID = this.eID;
        brugernavn = this.brugernavn;
        password = this.password;
    }

    public int geteID()
    {
        return eID;
    }

    public String getBrugernavn()
    {
        return brugernavn;
    }

    public String getPassword()
    {
        return password;
    }

    public void seteID(int eID)
    {
        this.eID = eID;
    }

    public void setBrugernavn(String brugernavn)
    {
        this.brugernavn = brugernavn;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String toString()
    {
        return eID + ", " + brugernavn + ", " + password;
    }
}
