package Domain;

/**
 *
 * @author Sebastian
 */
import DataSource.DBFacade;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Controller {

    private boolean processingOrder;
    private boolean processingCustomer;
    private Order currentOrder;
    private DBFacade dbFacade;
    private boolean processingProduct;
    private Product currentProduct;
    private Customer currentCustomer;

    public Controller() {
        processingOrder = false;
        processingCustomer = false;
        currentOrder = null;
        dbFacade = DBFacade.getInstance();
        processingProduct = false;
        currentProduct = null;
        currentCustomer = null;

    }

    public void addDirtyOrder(Order o) {
        currentOrder = o;
        dbFacade.startNewBusinessTransaction();
        dbFacade.addDirtyOrder(o);
    }

    public Order getOrder(int oID) {
        if (processingOrder) {
            return null;
        }
        Order o = null;
        dbFacade.startNewBusinessTransaction();
        if (oID != 0) {
//            processingOrder = true;
            o = dbFacade.getOrder(oID);
        } else {
//            processingOrder = false;
//            currentOrder = null;
        }
        return o;
    }

    // Frederik
    public ArrayList<Employee> getMontører() {
        dbFacade.startNewBusinessTransaction();
        return dbFacade.getMontører();
    }

    // Frederik
    public ArrayList<Installer> getInstallers(String fromDate, String toDate) {
        dbFacade.startNewBusinessTransaction();
        return dbFacade.getInstallers(fromDate, toDate);
    }

    public ArrayList<Order> getOrders(String fromDate, String toDate) {
        dbFacade.startNewBusinessTransaction();
        return dbFacade.getOrders(fromDate, toDate);
    }

    // Frederik
    public ArrayList<Product> getProducts() {
        dbFacade.startNewBusinessTransaction();
        return dbFacade.getProducts();
    }
    //sebastian

//    public ArrayList<Customer> getCustomers() {
//        dbFacade.startNewBusinessTransaction();
//        return dbFacade.getCustomers(); //metode mangler
//    }
    // Frederik
    public Order createOrder(int cID, String fromDate, String toDate) {
        int oID = 0;
        if (processingOrder) {
            return null;
        }
        dbFacade.startNewBusinessTransaction();

        oID = dbFacade.getNextOrderID();

        if (oID != 0) {
            processingOrder = true;
            currentOrder = new Order(oID, cID, fromDate, toDate);
            dbFacade.registerNewOrder(currentOrder);
        } else {
            processingOrder = false;
            currentOrder = null;
        }
        return currentOrder;
    }
    //Sebastian

    public Customer createCustomer(String cName, String cEmail, int cPhoneNumber, String cAddress, int cZip) {
        int cID = 0;
        if (processingCustomer) {
            return null;
        }
        dbFacade.startNewBusinessTransaction();

        cID = dbFacade.getNextCustomerID();

        if (cID != 0) {
            processingCustomer = true;
            currentCustomer = new Customer(cID, cName, cEmail, cPhoneNumber, cAddress, cZip);
            dbFacade.registerNewCustomer(currentCustomer);
        } else {
            processingCustomer = false;
            currentCustomer = null;
        }
        return currentCustomer;
    }

    public boolean addOrderDetail(int pID, int qty) {
        boolean status = false;
        if (processingOrder) {
            OrderDetail od = new OrderDetail(currentOrder.getOID(), pID, qty);
            currentOrder.addDetail(od);
            dbFacade.registerNewOrderDetail(od);
            status = true;
        }
        return status;
    }

    public Date toDate(String s){
        System.out.println("INPUT STRING: " + s);
        String[] array = s.split("-|:|\\s+");
        System.out.println("ARRAY CONTENT:");
        for (int i = 0; i < array.length; i++) {
            String string = array[i];
            System.out.println(string);
        }
        Calendar c = new GregorianCalendar();
        String sMonth = array[1].replace("jan", "0").replace("feb", "1")
                .replace("mar", "2").replace("apr", "3").replace("may", "4")
                .replace("jun", "5").replace("jul", "6").replace("aug", "7")
                .replace("sep", "8").replace("oct", "9").replace("nov", "10")
                .replace("dec", "11");
        int month = Integer.parseInt(sMonth);
        c.set(Integer.parseInt(array[2]), month, Integer.parseInt(array[0]),
                Integer.parseInt(array[3]), Integer.parseInt(array[4]) ,0);
        return new Date(c.getTimeInMillis());
    }
    
    //Frederik
    public boolean addInstaller(int eID, String fromDate, String toDate) {
        boolean status = false;
        if (processingOrder) {
            Installer in = new Installer(currentOrder.getOID(), eID, fromDate, toDate);
            currentOrder.addInstaller(in);
            dbFacade.registerNewInstaller(in);
            status = true;
        }
        return status;
    }

    // Frederik
    public void addDirtyProduct(Product p) {
        currentProduct = p;
        dbFacade.startNewBusinessTransaction();
        dbFacade.addDirtyProduct(p);
    }

    public boolean saveOrder() {
        boolean status = false;
        if (processingOrder) {
            status = dbFacade.commitBusinessTransaction();
            processingOrder = false;
            currentOrder = null;
        }
        return status;
    }

    public boolean saveCustomer() {
        boolean status = false;
        if (processingCustomer) {
            status = dbFacade.commitBusinessTransaction();
            processingCustomer = false;
            currentCustomer = null;
        }
        return status;
    }
}
