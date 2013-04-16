package DataSource;

import Domain.*;
import java.util.ArrayList;
import java.sql.*;

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
    private ArrayList<Customer> newCustomers;
    private ArrayList<Customer> dirtyCustomers;
    private ArrayList<Installer> newInstallers;
    
    public UnitOfWork() {
        dirtyProducts = new ArrayList();
        newOrders = new ArrayList();
        dirtyOrders = new ArrayList();
        newOrderDetails = new ArrayList();
        dirtyOrderDetails = new ArrayList();
        newCustomers = new ArrayList();
        dirtyCustomers = new ArrayList();
        newInstallers = new ArrayList();
        
    }
    
    public void addDirtyOrder(Order o){
        if(!newOrders.contains(o) && !dirtyOrders.contains(o)){
            dirtyOrders.add(o);
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
    public void registerNewCustomer(Customer c){
        if (!newCustomers.contains(c) && !dirtyCustomers.contains(c)) {
            newCustomers.add(c);
        }
    }
    //sebastian
    public void registerDirtyCustomer(Customer c){
        if(!dirtyCustomers.contains(c)) {
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
    public void registerNewInstaller(Installer in){
        if(!newInstallers.contains(in)){
            newInstallers.add(in);
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
            
            System.out.println("1");
            status = status && om.insertOrders(newOrders, conn);
            System.out.println("2");
            status = status && om.insertOrderDetails(newOrderDetails, conn);
            System.out.println("3");
            status = status && pm.updateProducts(dirtyProducts, conn);
            System.out.println("4");
            status = status && cm.insertCustomers(newCustomers, conn); //sebastian
            System.out.println("5"); //sebastian
            status = status && om.insertInstallers(newInstallers, conn);
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
