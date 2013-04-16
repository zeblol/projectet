package Domain;

/**
 *
 * @author Kirstine
 */
public class OrderDetail {

    private int oID;
    private int pID;
    private int amount;

    public OrderDetail(int oID, int pID, int amount) {
        this.oID = oID;
        this.pID = pID;
        this.amount = amount;
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
}