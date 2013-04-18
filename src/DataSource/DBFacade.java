package DataSource;

import Domain.Customer;
import Domain.Employee;
import Domain.Installer;
import Domain.Order;
import Domain.OrderDetail;
import Domain.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class DBFacade {

    private UnitOfWork uow;
    private static DBFacade instance;

    private DBFacade() {
    }

    public static DBFacade getInstance() {
        if (instance == null) {
            instance = new DBFacade();
        }
        return instance;
    }

    // Frederik
    public ArrayList<Product> getProducts() {
        Connection conn = null;
        ArrayList<Product> pl = null;
        try {
            conn = getConnection();
            pl = new ProductMapper().getProducts(conn);
        } finally {
            releaseConnection(conn);
        }
        return pl;
    }

    // Frederik
    public Order getOrder(int oID) {
        Connection conn = null;
        Order o = null;
        try {
            conn = getConnection();
            o = new OrderMapper().getOrder(conn, oID);
        } finally {
            releaseConnection(conn);
        }
        return o;
    }

    //Sebastian
    public Customer getCustomer(int cID) {
        Connection conn = null;
        Customer c = null;
        try {
            conn = getConnection();
            c = new CustomerMapper().getCustomer(conn, cID);
        } finally {
            releaseConnection(conn);
        }
        return c;
    }

    // Frederik
    public String getCity(int zip) {
        Connection conn = null;
        String s = null;
        try {
            conn = getConnection();
            s = new Mapper().getCity(conn, zip);
        } finally {
            releaseConnection(conn);
        }
        return s;
    }

    public void addDirtyOrder(Order o) {
        if (uow != null) {
            uow.addDirtyOrder(o);
        }
    }

    // Kirstine
    public void registerDirtyOrderDetail(OrderDetail od) {
        if (uow != null) {
            uow.addDirtyOrderDetail(od);
            System.out.println(od);
        }
    }

    // Charlotte
    public void addRemovedOrderDetail(OrderDetail od) {
        if (uow != null) {
            uow.addRemovedOrderDetail(od);
        }
    }

    // Sebastian
    public void addRemovedInstaller(Installer in) {
        if (uow != null) {
            uow.addRemovedInstaller(in);
        }
    }

    public void startNewBusinessTransaction() {
        uow = new UnitOfWork();
    }

    public ArrayList<Employee> getMontoerer() {
        Connection conn = null;
        ArrayList<Employee> el = null;
        try {
            conn = getConnection();
            el = new EmployeeMapper().getMontoerer(conn);
        } finally {
            releaseConnection(conn);
        }
        return el;
    }

    public ArrayList<Installer> getInstallers(String fromDate, String toDate) {
        Connection conn = null;
        ArrayList<Installer> il = null;
        try {
            conn = getConnection();
            il = new OrderMapper().getInstallers(conn, fromDate, toDate);
        } finally {
            releaseConnection(conn);
        }
        return il;
    }

    public ArrayList<Order> getOrders(String fromDate, String toDate) {
        Connection conn = null;
        ArrayList<Order> al = null;
        try {
            conn = getConnection();
            al = new OrderMapper().getOrders(conn, fromDate, toDate);
        } finally {
            releaseConnection(conn);
        }
        return al;
    }

    public void addDirtyProduct(Product p) {
        if (uow != null) {
            uow.addDirtyProduct(p);
        }
    }

    // Frederik
    public int getNextOrderID() {
        Connection conn = null;
        int nextOID = 0;
        try {
            conn = getConnection();
            nextOID = new OrderMapper().getNextOrderID(conn);
        } finally {
            releaseConnection(conn);
        }
        return nextOID;
    }

    // Frederik
    public int getNextCustomerID() {
        Connection conn = null;
        int nextCID = 0;
        try {
            conn = getConnection();
            nextCID = new CustomerMapper().getNextCustomerID(conn);
        } finally {
            releaseConnection(conn);
        }
        return nextCID;
    }

    public void registerNewOrder(Order o) {
        if (uow != null) {
            uow.registerNewOrder(o);
        }
    }

    public void registerNewCustomer(Customer c) {
        if (uow != null) {
            uow.registerNewCustomer(c);
        }
    }

    public void registerNewInstaller(Installer in) {
        if (uow != null) {
            uow.registerNewInstaller(in);
        }
    }

    public void registerNewOrderDetail(OrderDetail od) {
        if (uow != null) {
            uow.registerNewOrderDetail(od);
        }
    }

    private Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@delfi.lyngbyes.dk:1521:KNORD", "CLDATB12E5", "CLDATB12E5");
        } catch (Exception e) {
            System.out.println("fejl i DBFacade.getConnection()");
            System.out.println(e);
        }
        return conn;
    }

    private void releaseConnection(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public boolean commitBusinessTransaction() {
        boolean status = false;
        if (uow != null) {
            Connection conn = null;
            try {
                conn = getConnection();
                status = uow.commit(conn);
            } catch (Exception e) {
                System.out.println("Fail in DBFacade - commitBusinessTransaction");
                System.err.println(e);
            } finally {
                releaseConnection(conn);
            }
            uow = null;
        }
        return status;
    }
}