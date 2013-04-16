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
