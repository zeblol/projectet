package DataSource;

import Domain.Controller;
import Domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Sebastian
 */
public class UserMapperTest
{
    private Connection conn;
    public UserMapperTest()
    {
        
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
        conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@delfi.lyngbyes.dk:1521:KNORD", "cphfo12", "cphfo12");
        } catch (Exception e) {
            System.out.println("fejl i DBFacade.getConnection()");
            System.out.println(e);
        }

    }
    
    @After
    public void tearDown()
    {
        try
        {
            conn.rollback();
        } catch (SQLException ex)
        {
            Logger.getLogger(UserMapperTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        conn = null;
        
    }

    /**
     * Test of insertUsers method, of class UserMapper.
     */
    @Test
    public void testInsertUsers()
    {   
       //sebastian
        System.out.println("insertUsers");
        ArrayList<User> ul = new ArrayList();
        User u = new User("1000","123abc",1);
        ul.add(u);
        UserMapper instance = new UserMapper();
        boolean expResult = true;
        boolean result = instance.insertUsers(ul, conn);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of updateUsers method, of class UserMapper.
     */
    @Test //sebastian  
    public void testUpdateUsers()
    {
        System.out.println("updateUsers");
        ArrayList<User> ul = new ArrayList();
        User u = new User("1000","123abc",1);
        UserMapper instance = new UserMapper();
        boolean expResult = true;
        boolean result = instance.updateUsers(ul, conn);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of removeUsers method, of class UserMapper.
     */
    @Test //sebastian
    public void testRemoveUsers()
    {
        System.out.println("removeUsers");
        ArrayList<User> ul = new ArrayList();
        User u = new User("1001","abc123",2);
        ul.add(u);
        UserMapper instance = new UserMapper();
        boolean expResult = true;
        boolean result = instance.removeUsers(ul, conn);
        assertEquals(expResult, result);
    }

    /**
     * Test of getUsers method, of class UserMapper.
     */
    @Test //sebastian
    public void testGetUsers()
    {
        System.out.println("getUsers");
        UserMapper instance = new UserMapper();
        ArrayList result = instance.getUsers(conn);
        assertNotNull(result);
    }
}
