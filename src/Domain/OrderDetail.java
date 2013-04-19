package Domain;

/**
 *
 * @author Kirstine
 */
public class OrderDetail {

    private int oID;
    private int pID;
    private int ver;
    private int amount;

    public OrderDetail(int oID, int pID, int amount, int ver) {
        this.oID = oID;
        this.pID = pID;
        this.ver = ver;
        this.amount = amount;
    }

    public int getVer()
    {
        return ver;
    }

    public void setAmount(int i){
        amount = i;
    }
    
    public int getoID() {
        return oID;
    }

    public int getpID() {
        return pID;
    }

    public int getQuantity() {
        return amount;
    }

    public void setoID(int oID) {
        this.oID = oID;
    }

    public void setpID(int pID) {
        this.pID = pID;
    }

    public String toString() {
        return "ID: " + pID + "   " + amount;
    }
    public void incrVer(){
        ver++;
    }
}
