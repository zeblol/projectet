
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
    public ArrayList<Employee> getMontører(Connection conn){
        ArrayList<Employee> el = new ArrayList();
        String SQLString = "select * "
                + "from employees "
                + "where eJob = 'Montør'";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                el.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e){
            System.out.println("Fail in OrderMapper - getEmployees");
            System.out.println(e.getMessage());
        }
        return el;
    }
}
