package DataSource;

import Domain.Employee;
import Domain.Order;
import Domain.OrderDetail;
import Domain.Installer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Riboe
 */
public class OrderMapper {

    // Frederik
    public ArrayList<Installer> getInstallers(Connection conn, String datoFra, String datoTil) {
        ArrayList il = new ArrayList();
        String SQLString = "select * "
                + "from installers "
                + "where datofra between to_date(?, 'dd-mon-yyyy hh24:mi') "
                + "and to_date(?, 'dd-mon-yyyy hh24:mi')";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            statement.setString(1, datoFra);
            statement.setString(2, datoTil);
            ResultSet rs = statement.executeQuery();
            Calendar c = new GregorianCalendar();
            while (rs.next()) {
                c.setTimeInMillis(rs.getDate(3).getTime());
                String from = c.get(c.DAY_OF_MONTH) + "-" + c.get(c.MONTH) + "-" + c.get(c.YEAR) + " "
                        + c.get(c.HOUR_OF_DAY) + ":" + c.get(c.MINUTE);
                c.setTimeInMillis(rs.getDate(4).getTime());
                String to = c.get(c.DAY_OF_MONTH) + "-" + c.get(c.MONTH) + "-" + c.get(c.YEAR) + " "
                        + c.get(c.HOUR_OF_DAY) + ":" + c.get(c.MINUTE);
                il.add(new Installer(rs.getInt(1), rs.getInt(2), from, to));
            }
        } catch (SQLException e) {
            System.out.println("Fail in OrderMapper - getInstallers");
            System.out.println(e.getMessage());
        }
        return il;
    }

    // Kirstine, Charlotte og Frederik
    public ArrayList<Order> getOrders(Connection conn, String datoFra, String datoTil) {
        Order currentO = null;
        ArrayList<Order> ol = new ArrayList();
        String SQLString = "select * "
                + "from orders "
                + "where datofra between to_date(?,'dd-mon-yyyy') "
                + "and to_date(?,'dd-mon-yyyy')";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            statement.setString(1, datoFra);
            statement.setString(2, datoTil);
            ResultSet rs = statement.executeQuery();
            SQLString = "select * "
                    + "from orderdetails "
                    + "where oID = ?";
            statement = conn.prepareStatement(SQLString);
            ResultSet rs2;
            String SQLString2 = "select * "
                    + "from installers "
                    + "where oID = ?";
            PreparedStatement statement2 = conn.prepareStatement(SQLString2);
            ResultSet rs3;
            while (rs.next()) {
                boolean depositPaid = false;
                if("Y".equals(rs.getString(6))){
                    depositPaid = true;
                }
                currentO = new Order(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getDate(4), rs.getDate(5), depositPaid);
                statement.setInt(1, currentO.getOID());
                rs2 = statement.executeQuery();
                while (rs2.next()) {
                    currentO.addDetail(new OrderDetail(rs2.getInt(1), rs2.getInt(2), rs2.getInt(3)));
                }
                statement2.setInt(1, currentO.getOID());
                rs3 = statement2.executeQuery();
                Calendar c = new GregorianCalendar();
                while (rs3.next()) {
                    c.setTimeInMillis(rs3.getDate(3).getTime());
                    String from = c.get(c.DAY_OF_MONTH) + "-" + c.get(c.MONTH) + "-" + c.get(c.YEAR) + " "
                            + c.get(c.HOUR_OF_DAY) + ":" + c.get(c.MINUTE);
                    c.setTimeInMillis(rs3.getDate(4).getTime());
                    String to = c.get(c.DAY_OF_MONTH) + "-" + c.get(c.MONTH) + "-" + c.get(c.YEAR) + " "
                            + c.get(c.HOUR_OF_DAY) + ":" + c.get(c.MINUTE);
                    currentO.addInstaller(new Installer(rs3.getInt(1), rs3.getInt(2), from, to));
                }
                ol.add(currentO);

            }
        } catch (SQLException ex) {
            System.out.println("Fail in OrderMapper - getOrders");
            System.out.println(ex.getMessage());
        }
        return ol;
    }

    //Frederik
    public Order getOrder(Connection conn, int oID) {
        Order o = null;
        String SQLString = "select * "
                + "from orders "
                + "where oid = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            statement.setInt(1, oID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                boolean depositPaid = false;
                if("Y".equals(rs.getString(6))){
                    depositPaid = true;
                }
                o = new Order(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getDate(4), rs.getDate(5), depositPaid);
                SQLString = "select * "
                        + "from orderdetails "
                        + "where oid = ?";
                statement = conn.prepareStatement(SQLString);
                statement.setInt(1, oID);
                rs = statement.executeQuery();
                while (rs.next()) {
                    o.addDetail(new OrderDetail(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
                }
                SQLString = "select * "
                        + "from installers "
                        + "where oid = ?";
                statement = conn.prepareStatement(SQLString);
                statement.setInt(1, oID);
                rs = statement.executeQuery();
                while(rs.next()){
                    o.addInstaller(new Installer(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Fail in OrderMapper - getOrder");
            System.out.println(ex.getMessage());
        }
        return o;
    }

    // Frederik
    public int getNextOrderID(Connection conn) {
        int nextOID = 0;
        String SQLString = "select oSequence.nextval "
                + "from dual";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                nextOID = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Fail in OrderMapper - getNextOrderID");
            System.out.println(e.getMessage());
        }
        return nextOID;
    }

    // Sebastian
    public boolean insertInstallers(ArrayList<Installer> il, Connection conn) throws SQLException {
        int rowsInserted = 0;
        String SQLString = "insert into installers values (?,?,to_date(?,'dd-mon-yyyy hh24:mi'),to_date(?,'dd-mon-yyyy hh24:mi'))";
        PreparedStatement statement = null;
        statement = conn.prepareStatement(SQLString);
        for (int i = 0; i < il.size(); i++) {
            statement.setInt(1, il.get(i).getoID());
            statement.setInt(2, il.get(i).geteID());
            statement.setString(3, il.get(i).getFrom());
            statement.setString(4, il.get(i).getTo());
            rowsInserted += statement.executeUpdate();
        }
        System.out.println("insertInstallers: " + rowsInserted);
        return (rowsInserted == il.size());
    }

    // Frederik
    public boolean insertOrders(ArrayList<Order> ol, Connection conn) throws SQLException {
        int rowsInserted = 0;
        String SQLString = "insert into orders values (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        statement = conn.prepareStatement(SQLString);
        for (int i = 0; i < ol.size(); i++) {
            Order o = ol.get(i);
            statement.setInt(1, o.getOID());
            statement.setInt(2, o.getCID());
            statement.setDate(3, o.getFromDate());
            statement.setDate(4, o.getToDate());
            statement.setDate(5, o.getCreated());
            String depositPaid = "N";
            if(o.isDepositPaid()){
                depositPaid = "Y";
            }
            statement.setString(6, depositPaid);
            rowsInserted += statement.executeUpdate();
        }
        System.out.println("insertOrders: " + (rowsInserted == ol.size()));
        return (rowsInserted == ol.size());
    }

    // Frederik
    public boolean insertOrderDetails(ArrayList<OrderDetail> odl, Connection conn) throws SQLException {
        String SQLString = "insert into orderdetails values (?,?,?)";
        PreparedStatement statement = null;

        int rowsInserted = 0;
        if (0 < odl.size()) {
            statement = conn.prepareStatement(SQLString);
            for (int i = 0; i < odl.size(); i++) {
                statement.setInt(1, odl.get(i).getoID());
                statement.setInt(2, odl.get(i).getpID());
                statement.setInt(3, odl.get(i).getQuantity());
                rowsInserted += statement.executeUpdate();
            }
        }
        System.out.println("insertOrderDetails: " + (rowsInserted == odl.size()));
        return rowsInserted == odl.size();
    }
}
