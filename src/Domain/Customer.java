package Domain;

/**
 *
 * @author Sebastian
 */
public class Customer
{
    private int cID;
    private String cName;
    private String cEmail;
    private int cPhoneNumber;
    private String cAddress;
    private int cZip;

    public Customer(int cID, String cName, String cEmail, int cPhoneNumber, String cAddress, int cZip)
    {
        this.cID = cID;
        this.cName = cName;
        this.cEmail = cEmail;
        this.cPhoneNumber = cPhoneNumber;
        this.cAddress = cAddress;
        this.cZip = cZip;
    }

    public int getcID()
    {
        return cID;
    }

    public String getcName()
    {
        return cName;
    }

    public String getcEmail()
    {
        return cEmail;
    }

    public int getcPhoneNumber()
    {
        return cPhoneNumber;
    }

    public String getcAddress()
    {
        return cAddress;
    }

    public int getcZip()
    {
        return cZip;
    }
}
