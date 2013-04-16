package Domain;

import java.sql.Date;

/**
 *
 * @author Sebastian
 */
public class Installer {

    int oID;
    int eID;
    String from;
    String to;

    public Installer(int oID, int eID, String from, String to) {
        this.oID = oID;
        this.eID = eID;
        this.from = from;
        this.to = to;
    }

    public int getoID() {
        return oID;
    }

    public void setoID(int oID) {
        this.oID = oID;
    }

    public int geteID() {
        return eID;
    }

    public void seteID(int eID) {
        this.eID = eID;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String toString(){
        return "ID:  " + eID;
    }
}
