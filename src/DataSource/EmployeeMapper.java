package DataSource;

import Domain.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Riboe
 */
public class EmployeeMapper {

    // Frederik
    public ArrayList<Employee> getMontoerer(Connection conn) {
        ArrayList<Employee> el = new ArrayList();
        String SQLString = "select * "
                + "from employees "
                + "where eJob = 'Montør'";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                el.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            System.out.println("Fail in EmployeeMapper - getMontoerer");
            System.out.println(e.getMessage());
        }
        return el;
    }
    
    //Frederik
    public boolean employeeExists(Connection conn, int ID){
        boolean b = false;
        String SQLString = "select * "
                + "from employees "
                + "where eID = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            statement.setInt(1, ID);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                b = true;
            }
        } catch (SQLException e){
            System.out.println("Fail in EmployeeMapper - employeeExists");
            System.out.println(e.getMessage());
        }
        return b;
    }
}
