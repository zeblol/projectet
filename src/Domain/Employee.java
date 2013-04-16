
package Domain;

/**
 *
 * @author Riboe
 */
public class Employee {
    int eID;
    String eNavn;
    String eJob;

    public Employee(int eID, String eNavn, String eJob) {
        this.eID = eID;
        this.eNavn = eNavn;
        this.eJob = eJob;
    }
    
    public int geteID() {
        return eID;
    }

    public void seteID(int eID) {
        this.eID = eID;
    }

    public String geteNavn() {
        return eNavn;
    }

    public void seteNavn(String eNavn) {
        this.eNavn = eNavn;
    }

    public String geteJob() {
        return eJob;
    }

    public void seteJob(String eJob) {
        this.eJob = eJob;
    }
    
    public String toString(){
        return eID + ": " + eNavn;
    }
}
