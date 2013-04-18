package DataSource;

import Domain.*;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class UnitOfWork {

    private ArrayList<Product> dirtyProducts;
    private ArrayList<Order> newOrders;
    private ArrayList<Order> dirtyOrders;
    private ArrayList<OrderDetail> newOrderDetails;
    private ArrayList<OrderDetail> dirtyOrderDetails;
    private ArrayList<OrderDetail> removedOrderDetails;
    private ArrayList<Customer> newCustomers;
    private ArrayList<Customer> dirtyCustomers;
    private ArrayList<Installer> newInstallers;
    private ArrayList<Installer> removedInstallers;

    public UnitOfWork() {
        dirtyProducts = new ArrayList();
        newOrders = new ArrayList();
        dirtyOrders = new ArrayList();
        newOrderDetails = new ArrayList();
        dirtyOrderDetails = new ArrayList();
        removedOrderDetails = new ArrayList();
        newCustomers = new ArrayList();
        dirtyCustomers = new ArrayList();
        newInstallers = new ArrayList();
        removedInstallers = new ArrayList();
    }

    public void addDirtyOrder(Order o) {
        if (!newOrders.contains(o) && !dirtyOrders.contains(o)) {
            dirtyOrders.add(o);
        }
    }

    // Kirstine
    public void addDirtyOrderDetail(OrderDetail od) {
        if (!newOrderDetails.contains(od) && !dirtyOrderDetails.contains(od)) {
            dirtyOrderDetails.add(od);
        }
    }

    // Charlotte
    public void addRemovedOrderDetail(OrderDetail od) {
        if (!newOrderDetails.contains(od) && !dirtyOrderDetails.contains(od)) {
            removedOrderDetails.add(od);
        }
    }

    // Frederik
    public ArrayList<Product> getProducts(Connection conn) {
        ArrayList<Product> al = null;
        try {
            al = new ProductMapper().getProducts(conn);
        } catch (Exception e) {
            System.out.println("fail in UnitOfWork - getProducts()");
            System.err.println(e);
        }
        return al;
    }

    //sebastian
    public void registerNewCustomer(Customer c) {
        if (!newCustomers.contains(c) && !dirtyCustomers.contains(c)) {
            newCustomers.add(c);
        }
    }

    //sebastian
    public void registerDirtyCustomer(Customer c) {
        if (!dirtyCustomers.contains(c)) {
            dirtyCustomers.add(c);
        }
    }

    public void addDirtyProduct(Product p) {
        if (!dirtyProducts.contains(p)) {
            dirtyProducts.add(p);
        }
    }

    public void registerNewOrder(Order o) {
        if (!newOrders.contains(o) && !dirtyOrders.contains(o)) {
            newOrders.add(o);
        }
    }

    public void registerNewOrderDetail(OrderDetail od) {
        if (!newOrderDetails.contains(od) && !dirtyOrderDetails.contains(od)) {
            newOrderDetails.add(od);
        }
    }

    // Frederik
    public void registerDirtyProduct(Product p) {
        if (!dirtyProducts.contains(p)) {
            dirtyProducts.add(p);
        }
    }

    // Frederik
    public void registerNewInstaller(Installer in) {
        if (!newInstallers.contains(in)) {
            newInstallers.add(in);
        }
    }

    // Sebastian
    public void addRemovedInstaller(Installer in) {
        if (!newInstallers.contains(in) && !removedInstallers.contains(in)) {
            removedInstallers.add(in);
        }
    }

    // Frederik
    public boolean commit(Connection conn) throws SQLException {
        boolean status = true;
        try {
            //=== system transaction - start
            conn.setAutoCommit(false);
            OrderMapper om = new OrderMapper();
            ProductMapper pm = new ProductMapper();
            CustomerMapper cm = new CustomerMapper(); //sebastian

            status = status && om.insertOrders(newOrders, conn);
            status = status && om.insertOrderDetails(newOrderDetails, conn);
            status = status && pm.updateProducts(dirtyProducts, conn);
            status = status && cm.insertCustomers(newCustomers, conn); //sebastian
            status = status && om.insertInstallers(newInstallers, conn);
            status = status && om.removeOrderDetails(removedOrderDetails, conn);
            status = status && om.removeInstallers(removedInstallers, conn);
            status = status && om.updateOrderDetails(dirtyOrderDetails, conn);
            status = status && om.updateOrders(dirtyOrders, conn);
            if (!status) {
                throw new Exception("Business Transaction aborted");
            }
            //=== system transaction - end with success
            conn.commit();
        } catch (Exception e) {
            System.out.println("fail in UnitOfWork - commit()");
            System.err.println(e);
            //=== system transaction - end with roll back
            conn.rollback();
            status = false;// rettelse
        }
        return status;
    }
}
