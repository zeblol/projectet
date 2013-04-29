package DataSource;

import Domain.Customer;
import Domain.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class ProductMapper {
    
    // Frederik
    public int getNextProductID(Connection conn){
        int pID = 0;
        String SQLString = "select pSequence.nextval "
                + "from dual";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                pID = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Fail in ProductMapper - getNextProductID");
            System.out.println(e.getMessage());
        }
        return pID;
    }

    // Frederik
    public ArrayList<Product> getProducts(Connection conn) {
        ArrayList<Product> pl = new ArrayList();
        String SQLString = "select * "
                + "from products";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                pl.add(new Product(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4), 0));
            }

        } catch (SQLException ex) {
            System.out.println("Fail in ProductMapper - getProducts");
            System.out.println(ex.getMessage());
        }
        return pl;
    }

    public boolean insertProducts(ArrayList<Product> pl, Connection conn) throws SQLException {
        int rowsinserted = 0;
        String SQLString = "insert into products values (?,?,?,?)";
        PreparedStatement statement = null;
        statement = conn.prepareStatement(SQLString);

        for (int i = 0; i < pl.size(); i++) {
            Product p = pl.get(i);
            statement.setInt(1, p.getpID());
            statement.setString(2, p.getPnavn());
            statement.setFloat(3, p.getPris());
            statement.setInt(4, p.getAntalTotal());
            rowsinserted += statement.executeUpdate();
        }
        System.out.println("insertProducts: " + (rowsinserted == pl.size()));
        return (rowsinserted == pl.size());
    }
    
    // Frederik
    public boolean updateProducts(ArrayList<Product> pl, Connection conn) {
        int rowsUpdated = 0;
        String SQLString = "update products "
                + "set pnavn = ?, pris = ?, totalantal = ? "
                + "where pid = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            for (int i = 0; i < pl.size(); i++) {
                Product p = pl.get(i);
                statement.setString(1, p.getPnavn());
                statement.setFloat(2, p.getPris());
                statement.setInt(3, p.getAntalTotal());
                statement.setInt(4, p.getpID());
                int tupleUpdated = statement.executeUpdate();
                rowsUpdated += tupleUpdated;
            }
        } catch (SQLException ex) {
            System.out.println("Fail in ProductMapper - updateProducts");
            System.out.println(ex.getMessage());
        }
        System.out.println("updateProducts: " + (rowsUpdated == pl.size()));
        return (rowsUpdated == pl.size());
    }
}
