package DataSource;

import Domain.Customer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class CustomerMapper {

    //Sebastian
    public boolean insertCustomers(ArrayList<Customer> cl, Connection conn) throws SQLException {
        int rowsinserted = 0;
        String SQLString = "insert into customers values (?,?,?,?,?,?)";
        PreparedStatement statement = null;
        statement = conn.prepareStatement(SQLString);

        for (int i = 0; i < cl.size(); i++) {
            Customer c = cl.get(i);
            statement.setInt(1, c.getcID());
            statement.setString(2, c.getcName());
            statement.setString(3, c.getcEmail());
            statement.setInt(4, c.getcPhoneNumber());
            statement.setString(5, c.getcAddress());
            statement.setInt(6, c.getcZip());
            rowsinserted += statement.executeUpdate();
        }
        System.out.println("insertCustomers: " + (rowsinserted == cl.size()));
        return (rowsinserted == cl.size());
    }
    //sebastian

    public Customer getCustomer(Connection conn, int cID) {
        Customer c = null;
        String SQLString = "select * "
                + "from customers "
                + "where cid = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            statement.setInt(1, cID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                c = new Customer(cID, rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6));
            }
        } catch (SQLException ex) {
            System.out.println("Fail in CustomerMapper - getCustomer");
            System.out.println(ex.getMessage());
        }
        return c;
    }
    
    //Sebastian
    public Customer getCustomerByPhone(Connection conn, int phone) {
        Customer c = null;
        String SQLString  = "select * "
                + "from customers "
                + "where tlfnr = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            statement.setInt(1, phone);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
            {
                c = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), phone, rs.getString(5), rs.getInt(6));
            }
        } catch (SQLException ex) {
            System.out.println("Fail in CustomerMapper - getCustomerByPhone");
            System.out.println(ex.getMessage());
        }
        return c;
    }
    
    // Kirstine og Charlotte
    public Customer getCustomerByEmail(Connection conn, String email){
        Customer c = null;
        String SQLString = "select * "
                + "from customers "
                + "where upper (email)  = upper (?)";
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(SQLString);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                c = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6));
            }
        } catch (SQLException ex){
            System.out.println("Fail in CustomerMapper - getCustomerByEmail");
            System.out.println(ex.getMessage());
        }
        return c;
    }

    //sebastian
    public int getNextCustomerID(Connection conn) {
        int nextCID = 0;
        String SQLString = "select cSequence.nextVal from dual";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                nextCID = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Fail in CustomerMapper - getNextCustomerID");
            System.out.println(e.getMessage());
        }
        return nextCID;
    }
}
