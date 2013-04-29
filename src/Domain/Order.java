package Domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Sebastian
 */
public class Order {

    private int oID;
    private int cID;
    private int ver;
    private Date fromDate;
    private Date toDate;
    private Date created;
    private boolean depositPaid;
    private ArrayList<OrderDetail> orderdetails;
    private ArrayList<Installer> installers;
    private int discount;
    private ArrayList<Vehicle> vl;

    public Order(int o, int c, String from, String to, String created, boolean depositPaid, int rabat, int ver){
        oID = o;
        cID = c;
        this.ver = ver;
        discount = rabat;
        this.depositPaid = depositPaid;
        this.created = toDate(created);
        fromDate = toDate(from);
        toDate = toDate(to);
        orderdetails = new ArrayList();
        installers = new ArrayList();
        vl = new ArrayList();
    }
    
    public Order(int o, int c, Date datoFra, Date datoTil, Date created, boolean depositPaid, int rabat, int ver){
        oID = o;
        cID = c;
        this.ver = ver;
        discount = rabat;
        this.created = created;
        this.depositPaid = depositPaid;
        fromDate = datoFra;
        toDate = datoTil;
        orderdetails = new ArrayList();
        installers = new ArrayList();
        vl = new ArrayList();
    }
    
    private Date toDate(String s){
        String[] array = s.split("-");
        Calendar c = new GregorianCalendar();
        String sMonth = array[1].replace("jan", "0").replace("feb", "1")
                .replace("mar", "2").replace("apr", "3").replace("may", "4")
                .replace("jun", "5").replace("jul", "6").replace("aug", "7")
                .replace("sep", "8").replace("oct", "9").replace("nov", "10")
                .replace("dec", "11");
        int month = Integer.parseInt(sMonth);
        c.set(Integer.parseInt(array[2]), month, Integer.parseInt(array[0]),0 ,0 ,0);
        
        return new Date(c.getTimeInMillis());
    }
    
    public int getDiscount(){
        return discount;
    }
    
    public ArrayList<Vehicle> getVehicles(){
        return vl;
    }
    
    public void removeVehicle(Vehicle v){
        if(vl.contains(v)){
            vl.remove(v);
        }
    }
    
    public void addVehicle(Vehicle v){
        if(!vl.contains(v)){
            vl.add(v);
        }
    }
    
    public void setDiscount(int i){
        discount = i;
    }
    
    public boolean removeInstaller(Installer in){
        if(installers.contains(in)){
            return installers.remove(in);
        }
        return false;
    }
    
    public boolean removeOrderDetail(OrderDetail od){
        if(orderdetails.contains(od)){
            return orderdetails.remove(od);
        }
        return false;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isDepositPaid() {
        return depositPaid;
    }

    public void setDepositPaid(boolean depositPaid) {
        this.depositPaid = depositPaid;
    }
    
    
    public ArrayList<OrderDetail> getOrderDetails(){
        return orderdetails;
    }
    
    public ArrayList<Installer> getInstallers(){
        return installers;
    }

    public int getOID() {
        return oID;
    }

    public void setOID(int ono) {
        this.oID = ono;
    }

    public int getCID() {
        return cID;
    }

    public void setCID(int cno) {
        this.cID = cno;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(String from) {
        fromDate = toDate(from);
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(String to) {
        toDate = toDate(to);
    }

    public void addDetail(OrderDetail od) {
        orderdetails.add(od);
    }

    public boolean equals(Order o) {
        return (oID == o.getOID()
                && cID == o.getCID()
                && fromDate.equals(o.getFromDate())
                && toDate.equals(o.getToDate()));
    }

    public String toString() {
        return " OID: " + oID + " CID: " + cID;
    }

    public String detailsToString() {
        String res = "";
        for (int i = 0; i < orderdetails.size(); i++) {
            res += orderdetails.get(i).toString() + "\n";
        }
        return res;
    }
    
    public String installersToString(){
        String res = "";
        for (int i = 0; i < installers.size(); i++) {
            res += "e" + installers.get(i).toString() + "\n";
        }
        return res;
    }
    
    public void addInstaller(Installer in) {
        installers.add(in);
    }

    public int getVer()
    {
        return ver;
    }
    public void incrVer(){
        ver++;
    }
}
