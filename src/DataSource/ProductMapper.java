package DataSource;

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
    public ArrayList<Product> getProducts(Connection conn) {
        ArrayList<Product> pl = new ArrayList();
        String SQLString = "select * "
                + "from products";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                pl.add(new Product(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getInt(5)));
            }

        } catch (SQLException ex) {
            System.out.println("Fail in ProductMapper - getProducts");
            System.out.println(ex.getMessage());
        }
        return pl;
    }
    
    // Frederik
    public boolean updateProducts(ArrayList<Product> pl, Connection conn) throws SQLException {
        int rowsUpdated = 0;
        String SQLString = "update products "
                + "set pnavn = ?, pris = ?, antalTotal = ?, antalUdlejet = ? "
                + "where pid = ?";
        PreparedStatement statement = null;
        statement = conn.prepareStatement(SQLString);
        for(int i = 0; i < pl.size(); i++){
            Product p = pl.get(i);
            statement.setString(1, p.getPnavn());
            statement.setFloat(2, p.getPris());
            statement.setInt(3, p.getAntalTotal());
            statement.setInt(4, p.getAntalUdlejet());
            statement.setInt(5, p.getpID());
            int tupleUpdated = statement.executeUpdate();
            rowsUpdated += tupleUpdated;
        }
        System.out.println("updateProducts: " + (rowsUpdated == pl.size()));
        return (rowsUpdated == pl.size());
    }
}
