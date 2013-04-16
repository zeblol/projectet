package Domain;

/**
 *
 * @author Kirstine
 */
public class Product
{
    private int pID;
    private String pNavn;
    private float pris;
    private int antalTotal;
    private int antalUdlejet;
    
    public Product(int pID, String pNavn, float pris, int antalTotal, int antalUdlejet)
    {
        this.pID = pID;
        this.pNavn = pNavn;
        this.pris = pris;
        this.antalTotal = antalTotal;
        this.antalUdlejet = antalUdlejet;
    }
    
    public int getAntalUdlejet(){
        return antalUdlejet;
    }
    
    public void setAntalUdlejet(int i){
        antalUdlejet -= i;
    }

    public int getpID()
    {
        return pID;
    }

    public String getPnavn()
    {
        return pNavn;
    }

    public float getPris()
    {
        return pris;
    }

    public int getAntalTotal()
    {
        return antalTotal;
    }

    public void setpID(int pID)
    {
        this.pID = pID;
    }

    public void setPnavn(String Pnavn)
    {
        this.pNavn = Pnavn;
    }

    public void setPris(float pris)
    {
        this.pris = pris;
    }

    public void setAntal(int antal)
    {
        this.antalTotal = antal;
    }
    
    public String toString()
    {
        return pNavn;
    }
}
