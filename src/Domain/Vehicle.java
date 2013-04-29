
package Domain;

/**
 *
 * @author Riboe
 */
public class Vehicle {
    
    private int vID;
    private String vNavn;
    private int oID;
    private String toDate;
    private String fromDate;
    
    public Vehicle(int vID, String vNavn, String from, String to) {
        this.vID = vID;
        this.vNavn = vNavn;
        oID = -1;
        fromDate = from;
        toDate = to;
    }
    
    public Vehicle(int vID, String vNavn){
        this.vID = vID;
        this.vNavn = vNavn;
        oID = -1;
        toDate = null;
        fromDate = null;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }
    
    public int getvID() {
        return vID;
    }

    public String getvNavn() {
        return vNavn;
    }
    
    public void setoID(int i){
        oID = i;
    }
    
    public String toString(){
        return vID + ": " + vNavn;
    }

    public int getoID() {
        return oID;
    }
}
