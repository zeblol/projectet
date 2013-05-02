package DataSource;

import Domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sebastian
 */
public class UserMapper {
    //Sebastian

    public boolean insertUsers(ArrayList<User> ul, Connection conn)  {
        try
        {
            int rowsinserted = 0;
            String SQLString = "insert into accounts values (?,?,?)";
            PreparedStatement statement = null;
            statement = conn.prepareStatement(SQLString);

            for (int i = 0; i < ul.size(); i++) {
                User u = ul.get(i);
                statement.setInt(1, Integer.parseInt(u.getBrugernavn()));
                statement.setString(2, u.getPassword());
                statement.setInt(3, u.getBrugerNiveau());
                rowsinserted += statement.executeUpdate();
            }
            System.out.println("insertUsers: " + (rowsinserted == ul.size()));
            return (rowsinserted == ul.size());
        } catch (SQLException ex)
        {
            System.err.println(ex);
        }
        return false;
    }

    public boolean updateUsers(ArrayList<User> ul, Connection conn) {
        int rowsUpdated = 0;
        String SQLString = "update accounts "
                + "set password = ?, brugerniveau = ? "
                + "where eid = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            for (int i = 0; i < ul.size(); i++) {
                User u = ul.get(i);
                statement.setString(1, u.getPassword());
                statement.setInt(2, u.getBrugerNiveau());
                statement.setInt(3, Integer.parseInt(u.getBrugernavn()));
                int tupleUpdated = statement.executeUpdate();
                rowsUpdated += tupleUpdated;
            }
        } catch (SQLException ex) {
            System.out.println("Fail in UserMapper - updateUsers");
            System.out.println(ex.getMessage());
        }
        System.out.println("updateUsers: " + (rowsUpdated == ul.size()));
        return (rowsUpdated == ul.size());
    }

    //Frederik
    public boolean removeUsers(ArrayList<User> ul, Connection conn){
        int rowsRemoved = 0;
        String SQLString = "delete from accounts "
                + "where eid = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            for (int i = 0; i < ul.size(); i++) {
                User u = ul.get(i);
                statement.setInt(1, Integer.parseInt(u.getBrugernavn()));
                int tupleRemoved = statement.executeUpdate();
                rowsRemoved += tupleRemoved;
            }
        } catch (SQLException ex) {
            System.out.println("Fail in UserMapper - removeUsers");
            System.out.println(ex.getMessage());
        }
        System.out.println("removeUsers: " + (rowsRemoved == ul.size()));
        return (rowsRemoved == ul.size());
    }
    
    // Frederik
    public ArrayList<User> getUsers(Connection conn){
        ArrayList<User> ul = new ArrayList();
        String SQLString = "select * "
                + "from accounts";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ul.add(new User(rs.getInt(1) + "", rs.getString(2), rs.getInt(3)));
            }
        } catch (SQLException ex) {
            System.out.println("Fail in UserMapper - getUsers");
            System.out.println(ex.getMessage());
        }
        return ul;
    }
}
