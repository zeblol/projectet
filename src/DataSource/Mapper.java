package DataSource;

import Domain.Installer;
import Domain.Order;
import Domain.OrderDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Riboe
 */
public class Mapper {

    //Frederik
    public String getCity(Connection conn, int zip) {
        String s = null;
        String SQLString = "select * "
                + "from zipcodes "
                + "where postnr = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            statement.setInt(1, zip);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                s = rs.getString(2);
            }
        } catch (SQLException ex) {
            System.out.println("Fail in Mapper - getCity");
            System.out.println(ex.getMessage());
        }
        return s;
    }
}
