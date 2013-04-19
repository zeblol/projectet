package Domain;

/**
 *
 * @author Sebastian
 */
import DataSource.DBFacade;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Controller {

    private boolean processingOrder;
    private boolean processingCustomer;
    private Order currentOrder;
    private DBFacade dbFacade;
    private boolean processingOrderDetail;
    private boolean processingProduct;
    private Product currentProduct;
    private Customer currentCustomer;
    private OrderDetail currentOrderDetail;
    private Installer currentInstaller;

    public Controller() {
        processingOrder = false;
        processingCustomer = false;
        currentOrder = null;
        dbFacade = DBFacade.getInstance();
        processingProduct = false;
        currentProduct = null;
        currentCustomer = null;
        currentOrderDetail = null;
        currentInstaller = null;
        processingOrderDetail = false;
    }

    public void registerDirtyOrder(Order o) {
        currentOrder = o;
        processingOrder = true;
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
            o = dbFacade.getOrder(oID);
        }
        return o;
    }

    // Frederik
    public String getCity(int zip) {
        String s = null;
        dbFacade.startNewBusinessTransaction();
        if (zip != 0) {
            s = dbFacade.getCity(zip);
        }
        return s;
    }

    // Sebastian
    public Customer getCustomer(int cID) {
        if (processingCustomer) {
            return null;
        }
        Customer c = null;
        dbFacade.startNewBusinessTransaction();
        if (cID != 0) {
            c = dbFacade.getCustomer(cID);
        }
        return c;
    }


    // Frederik
    public ArrayList<Employee> getMontoerer() {
        dbFacade.startNewBusinessTransaction();
        return dbFacade.getMontoerer();
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

    // Frederik
    public Order createOrder(int cID, String fromDate, String toDate, int rabat) {
        int oID = 0;
        if (processingOrder) {
            return null;
        }
        dbFacade.startNewBusinessTransaction();
        oID = dbFacade.getNextOrderID();
        if (oID != 0) {
            processingOrder = true;
            String created = new Date(System.currentTimeMillis()).toString();
            currentOrder = new Order(oID, cID, fromDate, toDate, created, false, rabat, 0);
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
            OrderDetail od = new OrderDetail(currentOrder.getOID(), pID, qty, 0);
            currentOrder.addDetail(od);
            dbFacade.registerNewOrderDetail(od);
            status = true;
        }
        return status;
    }

    public Date toDate(String s) {
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
                Integer.parseInt(array[3]), Integer.parseInt(array[4]), 0);
        return new Date(c.getTimeInMillis());
    }

    //Frederik
    public boolean addInstaller(int eID, String fromDate, String toDate) {
        boolean status = false;
        if (processingOrder) {
            Installer in = new Installer(currentOrder.getOID(), eID, fromDate, toDate ,0);
            currentOrder.addInstaller(in);
            dbFacade.registerNewInstaller(in);
            status = true;
        }
        return status;
    }

    // Frederik
    public boolean saveUpdatedOrderDetails() {
        boolean status = false;
        if (processingOrderDetail) {
            status = dbFacade.commitBusinessTransaction();
            processingOrderDetail = false;
            currentOrderDetail = null;
        }
        return status;
    }

    // Frederik
    public void registerDirtyProduct(Product p) {
        currentProduct = p;
        dbFacade.startNewBusinessTransaction();
        dbFacade.addDirtyProduct(p);
    }

    // Kirstine
    public void registerDirtyOrderDetail(OrderDetail od) {
        currentOrderDetail = od;
        processingOrderDetail = true;
        System.out.println(currentOrderDetail);
        dbFacade.startNewBusinessTransaction();
        dbFacade.registerDirtyOrderDetail(od);
    }

    // Charlotte
    public void registerRemovedOrderDetail(OrderDetail od) {
        currentOrderDetail = od;
        dbFacade.startNewBusinessTransaction();
        dbFacade.addRemovedOrderDetail(od);
    }

    // Sebastian
    public void registerRemovedInstaller(Installer in) {
        currentInstaller = in;
        dbFacade.startNewBusinessTransaction();
        dbFacade.addRemovedInstaller(in);
    }

    public boolean saveDeletedInstallers() {
        boolean status = false;
        if (currentInstaller != null) {
            status = dbFacade.commitBusinessTransaction();
            currentInstaller = null;
        }
        return status;
    }

    public boolean saveDeletedOrderDetails() {
        boolean status = false;
        if (currentOrderDetail != null) {
            status = dbFacade.commitBusinessTransaction();
            currentOrderDetail = null;
        }
        return status;
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
    
    public BigDecimal calcDeposit(Order o, int deposit) {
        return calcTotal(o, 100 - deposit);
    }

    //Sebastian & Frederik
    public BigDecimal calcTotal(Order o, int discount){
        ArrayList<OrderDetail> list = o.getOrderDetails();
        ArrayList<Product> plist = getProducts();
        boolean b = false;
        double totalPris = 0;
        for (int i = 0; i < list.size(); i++) {
            int j = 0;
            while (!b && j < plist.size()) {
                if (list.get(i).getpID() == plist.get(j).getpID()) {
                    b = true;
                    totalPris = totalPris + (plist.get(j).getPris() * list.get(i).getQuantity());
                }
                j++;
            }
            b = false;
        }
        BigDecimal result = new BigDecimal(totalPris).setScale(2, BigDecimal.ROUND_HALF_UP);
        if(discount == 0){
            return result;
        }
        discount = 100 - discount;
        totalPris = (totalPris / 100) * discount;
        result = new BigDecimal(totalPris).setScale(2, BigDecimal.ROUND_HALF_UP);
        return result;
    }
    
    public BigDecimal calcTotal(Order o) {
        return calcTotal(o, o.getDiscount());
    }

    public double calcDiscount(int discount, double totalpris) {
        double d = 1/(double)discount;
        d = 1 - d;
        double totalPrisCalc = totalpris * d;
        return totalPrisCalc;
    }
}
