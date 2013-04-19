package Domain;

import java.sql.Date;

/**
 *
 * @author Sebastian
 */
public class Installer {

    private int oID;
    private int eID;
    private int ver;
    private String from;
    private String to;

    public Installer(int oID, int eID, String from, String to, int ver) {
        this.oID = oID;
        this.eID = eID;
        this.ver = ver;
        this.from = from;
        this.to = to;
    }

    public int getVer()
    {
        return ver;
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
    
    public void incrVer(){
        ver++;
    }

    public String toString(){
        return "ID:  " + eID;
    }
}
