package DataSource;

import Domain.Installer;
import Domain.Order;
import Domain.OrderDetail;
import Domain.Vehicle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riboe
 */
public class OrderMapper {

    // Kirstine
    public boolean updateOrders(ArrayList<Order> ol, Connection conn) {
        int rowsUpdated = 0;
        String SQLString = "update orders "
                + "set datofra = ?, datotil = ?, depositpaid = ?, discount = ?, ver = ? "
                + "where oid = ? and ver = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            for (int i = 0; i < ol.size(); i++) {
                Order o = ol.get(i);
                statement.setDate(1, o.getFromDate());
                statement.setDate(2, o.getToDate());
                String s = "N";
                if (o.isDepositPaid()) {
                    s = "Y";
                }
                statement.setString(3, s);
                statement.setInt(4, o.getDiscount());
                statement.setInt(5, o.getVer() + 1); //new ver
                statement.setInt(6, o.getOID());
                statement.setInt(7, o.getVer()); //old ver
                int tupleUpdated = statement.executeUpdate();
                if (tupleUpdated == 1) {
                    o.incrVer();
                }
                rowsUpdated += tupleUpdated;
            }
        } catch (SQLException e) {
            System.out.println("Fail in Order - updateOrders");
            System.out.println(e.getMessage());
        }
        return (rowsUpdated == ol.size());
    }

    // Frederik
    public boolean updateOrderDetails(ArrayList<OrderDetail> odl, Connection conn) {
        int rowsUpdated = 0;
        String SQLString = "update orderdetails "
                + "set antal = ?, ver = ? "
                + "where oid = ? and pid = ? and ver = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            for (int i = 0; i < odl.size(); i++) {
                OrderDetail od = odl.get(i);
                statement.setInt(1, od.getAmount());
                statement.setInt(2, od.getVer() + 1);
                statement.setInt(3, od.getoID());
                statement.setInt(4, od.getpID());
                statement.setInt(5, od.getVer());
                int tupleUpdated = statement.executeUpdate();
                if (tupleUpdated == 1) {
                    od.incrVer();
                }
                rowsUpdated += tupleUpdated;
            }
        } catch (SQLException e) {
            System.out.println("Fail in OrderMapper - updateOrderDetails");
            System.out.println(e.getMessage());
        }
        return (rowsUpdated == odl.size());
    }

    // Frederik
    public boolean removeInstallers(ArrayList<Installer> il, Connection conn) {
        int rowsRemoved = 0;
        String SQLString = "delete from installers "
                + "where oid = ? and eid = ? and ver = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            for (int i = 0; i < il.size(); i++) {
                Installer in = il.get(i);
                statement.setInt(1, in.getoID());
                statement.setInt(2, in.geteID());
                statement.setInt(3, in.getVer());
                int tupleRemoved = statement.executeUpdate();
                rowsRemoved += tupleRemoved;
            }
        } catch (SQLException ex) {
            System.out.println("Fail in OrderMapper - RemoveInstallers");
            System.out.println(ex.getMessage());
        }
        System.out.println("removeInstallers: " + (rowsRemoved == il.size()));
        return (rowsRemoved == il.size());
    }

    // Frederik
    public boolean removeOrderDetails(ArrayList<OrderDetail> il, Connection conn) {
        int rowsRemoved = 0;
        String SQLString = "delete from orderdetails "
                + "where oid = ? and pid = ? and ver = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            for (int i = 0; i < il.size(); i++) {
                OrderDetail od = il.get(i);
                statement.setInt(1, od.getoID());
                statement.setInt(2, od.getpID());
                statement.setInt(3, od.getVer());
                int tupleRemoved = statement.executeUpdate();
                rowsRemoved += tupleRemoved;
            }
        } catch (SQLException ex) {
            System.out.println("Fail in OrderMapper - removeOrderDetails");
            System.out.println(ex.getMessage());
        }
        System.out.println("removeOrderDetails: " + (rowsRemoved == il.size()));
        return (rowsRemoved == il.size());
    }

    // Frederik
    public ArrayList<Installer> getInstallers(Connection conn, String datoFra, String datoTil) {
        ArrayList il = new ArrayList();
        String SQLString = "select * "
                + "from installers "
                + "where datofra between to_date(?, 'dd-mon-yyyy hh24:mi') "
                + "and to_date(?, 'dd-mon-yyyy hh24:mi') "
                + "and datotil between to_date(?, 'dd-mon-yyyy hh24:mi') "
                + "and to_date(?, 'dd-mon-yyyy hh24:mi')";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            statement.setString(1, datoFra);
            statement.setString(2, datoTil);
            statement.setString(3, datoFra);
            statement.setString(4, datoTil);
            ResultSet rs = statement.executeQuery();
            Calendar c = new GregorianCalendar();
            while (rs.next()) {
                c.setTimeInMillis(rs.getDate(3).getTime());
                String from = c.get(c.DAY_OF_MONTH) + "-" + c.get(c.MONTH) + "-" + c.get(c.YEAR) + " "
                        + c.get(c.HOUR_OF_DAY) + ":" + c.get(c.MINUTE);
                c.setTimeInMillis(rs.getDate(4).getTime());
                String to = c.get(c.DAY_OF_MONTH) + "-" + c.get(c.MONTH) + "-" + c.get(c.YEAR) + " "
                        + c.get(c.HOUR_OF_DAY) + ":" + c.get(c.MINUTE);
                il.add(new Installer(rs.getInt(1), rs.getInt(2), from, to, rs.getInt(5)));
            }
        } catch (SQLException e) {
            System.out.println("Fail in OrderMapper - getInstallers");
            System.out.println(e.getMessage());
        }
        return il;
    }
    
    // Frederik
    public ArrayList<Vehicle> getVehicles(Connection conn, String from, String to){
        ArrayList<Vehicle> vl = new ArrayList();
        ArrayList<Vehicle> bvl = new ArrayList();
        String SQLString = "select * "
                + "from booked_vehicles "
                + "where datofra between to_date(?, 'dd-mon-yyyy hh24:mi') "
                + "and to_date(?, 'dd-mon-yyyy hh24:mi') "
                + "and datotil between to_date(?, 'dd-mon-yyyy hh24:mi') "
                + "and to_date(?, 'dd-mon-yyyy hh24:mi')";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            statement.setString(1, from);
            statement.setString(2, to);
            statement.setString(3, from);
            statement.setString(4, to);
            ResultSet rs  = statement.executeQuery();
            while(rs.next()){
                bvl.add(new Vehicle(rs.getInt(1), rs.getString(2), from, to));
            }
            SQLString = "select * from vehicles";
            statement = conn.prepareStatement(SQLString);
            rs = statement.executeQuery();
            while(rs.next()){
                vl.add(new Vehicle(rs.getInt(1), rs.getString(2)));
            }
            for(int i = 0; i < bvl.size(); i++){
                for (int j = 0; j < vl.size(); j++) {
                    Vehicle v = vl.get(j);
                    if(v.getvID() == bvl.get(i).getvID()){
                        vl.remove(v);
                    }
                }
            }
        }
        catch (SQLException ex) {
            System.out.println("Fail in OrderMapper - getVehicles");
            System.out.println(ex.getMessage());
        }        
        return vl;
    }
    
    // Kirstine, Charlotte og Frederik
    public ArrayList<Order> getOrders(Connection conn, String datoFra, String datoTil) {
            Order currentO = null;
            ArrayList<Order> ol = new ArrayList();
            String SQLString = "select * "
                    + "from orders "
                    + "where datofra between to_date(?,'dd-mon-yyyy') "
                    + "and to_date(?,'dd-mon-yyyy') "
                    + "and datotil between to_date(?,'dd-mon-yyyy') "
                    + "and to_date(?,'dd-mon-yyyy')";
            PreparedStatement statement = null;
        try {
    //        try {
                statement = conn.prepareStatement(SQLString);
                statement.setString(1, datoFra);
                statement.setString(2, datoTil);
                statement.setString(3, datoFra);
                statement.setString(4, datoTil);
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
                String SQLString3 = "select * "
                        + "from booked_vehicles "
                        + "where oID = ?";
                PreparedStatement statementVehicles = conn.prepareStatement(SQLString3);
                ResultSet rsVehicles;
                while (rs.next()) {
                    boolean depositPaid = false;
                    if ("Y".equals(rs.getString(6))) {
                        depositPaid = true;
                    }
                    currentO = new Order(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getDate(4), rs.getDate(5), depositPaid, rs.getInt(7), rs.getInt(8));
                    statement.setInt(1, currentO.getOID());
                    rs2 = statement.executeQuery();
                    while (rs2.next()) {
                        currentO.addDetail(new OrderDetail(rs2.getInt(1), rs2.getInt(2), rs2.getInt(3), rs2.getInt(4)));
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
                        currentO.addInstaller(new Installer(rs3.getInt(1), rs3.getInt(2), from, to, rs3.getInt(5)));
                    }
                    statementVehicles.setInt(1, currentO.getOID());
                    rsVehicles = statementVehicles.executeQuery();
                    while (rsVehicles.next()) {
                        c.setTimeInMillis(rsVehicles.getDate(3).getTime());
                        String from = c.get(c.DAY_OF_MONTH) + "-" + c.get(c.MONTH) + "-" + c.get(c.YEAR) + " "
                                + c.get(c.HOUR_OF_DAY) + ":" + c.get(c.MINUTE);
                        c.setTimeInMillis(rsVehicles.getDate(4).getTime());
                        String to = c.get(c.DAY_OF_MONTH) + "-" + c.get(c.MONTH) + "-" + c.get(c.YEAR) + " "
                                + c.get(c.HOUR_OF_DAY) + ":" + c.get(c.MINUTE);
                        Vehicle v = getVehicle(conn, rsVehicles.getInt(2));
                        v.setoID(currentO.getOID());
                        v.setToDate(to);
                        v.setFromDate(from);
                        currentO.addVehicle(v);
                    }
                    ol.add(currentO);
                }
    //        } catch (SQLException ex) {
    //            System.out.println("Fail in OrderMapper - getOrders");
    //            System.out.println(ex.getMessage());
    //        }
        } catch (SQLException ex) {
            Logger.getLogger(OrderMapper.class.getName()).log(Level.SEVERE, null, ex);
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
                if ("Y".equals(rs.getString(6))) {
                    depositPaid = true;
                }
                o = new Order(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getDate(4), rs.getDate(5), depositPaid, rs.getInt(7), rs.getInt(8));
                SQLString = "select * "
                        + "from orderdetails "
                        + "where oid = ?";
                statement = conn.prepareStatement(SQLString);
                statement.setInt(1, oID);
                rs = statement.executeQuery();
                while (rs.next()) {
                    o.addDetail(new OrderDetail(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4)));
                }
                SQLString = "select * "
                        + "from installers "
                        + "where oid = ?";
                statement = conn.prepareStatement(SQLString);
                statement.setInt(1, oID);
                rs = statement.executeQuery();
                while (rs.next()) {
                    o.addInstaller(new Installer(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
                }
                SQLString = "select * "
                        + "from booked_vehicles "
                        + "where oid = ?";
                statement = conn.prepareStatement(SQLString);
                statement.setInt(1, oID);
                rs = statement.executeQuery();
                Calendar c = new GregorianCalendar();
                while (rs.next()) {
                    c.setTimeInMillis(rs.getDate(3).getTime());
                    String from = c.get(c.DAY_OF_MONTH) + "-" + c.get(c.MONTH) + "-" + c.get(c.YEAR) + " "
                            + c.get(c.HOUR_OF_DAY) + ":" + c.get(c.MINUTE);
                    c.setTimeInMillis(rs.getDate(4).getTime());
                    String to = c.get(c.DAY_OF_MONTH) + "-" + c.get(c.MONTH) + "-" + c.get(c.YEAR) + " "
                            + c.get(c.HOUR_OF_DAY) + ":" + c.get(c.MINUTE);
                    Vehicle v = getVehicle(conn, rs.getInt(2));
                    v.setoID(o.getOID());
                    v.setFromDate(from);
                    v.setToDate(to);
                    o.addVehicle(v);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Fail in OrderMapper - getOrder");
            System.out.println(ex.getMessage());
        }
        return o;
    }

    public Vehicle getVehicle(Connection conn, int vID) {
        Vehicle vehicle = null;
        try {
            String SQLString = "select * "
                    + "from vehicles "
                    + "where vid = ?";
            PreparedStatement statement = conn.prepareStatement(SQLString);
            statement.setInt(1, vID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                vehicle = new Vehicle(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vehicle;
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

    // Sebastian, Charlotte
    public boolean insertInstallers(ArrayList<Installer> il, Connection conn) throws SQLException {
        int rowsInserted = 0;
        String SQLString = "insert into installers values (?,?,to_date(?,'dd-mon-yyyy hh24:mi'),to_date(?,'dd-mon-yyyy hh24:mi'), ?)";
        PreparedStatement statement = null;
        statement = conn.prepareStatement(SQLString);
        for (int i = 0; i < il.size(); i++) {
            statement.setInt(1, il.get(i).getoID());
            statement.setInt(2, il.get(i).geteID());
            statement.setString(3, il.get(i).getFrom());
            statement.setString(4, il.get(i).getTo());
            statement.setInt(5, il.get(i).getVer());
            rowsInserted += statement.executeUpdate();
        }
        System.out.println("insertInstallers: " + (rowsInserted == il.size()));
        return (rowsInserted == il.size());
    }

    // Frederik
    public boolean insertOrders(ArrayList<Order> ol, Connection conn) throws SQLException {
        int rowsInserted = 0;
        String SQLString = "insert into orders values (?, ?, ?, ?, ?, ?, ?, ?)";
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
            if (o.isDepositPaid()) {
                depositPaid = "Y";
            }
            statement.setString(6, depositPaid);
            statement.setInt(7, o.getDiscount());
            statement.setInt(8, o.getVer());
            rowsInserted += statement.executeUpdate();
        }
        System.out.println("insertOrders: " + (rowsInserted == ol.size()));
        return (rowsInserted == ol.size());
    }

    // Frederik
    public boolean insertOrderDetails(ArrayList<OrderDetail> odl, Connection conn) throws SQLException {
        String SQLString = "insert into orderdetails values (?,?,?,?)";
        PreparedStatement statement = null;

        int rowsInserted = 0;
        if (0 < odl.size()) {
            statement = conn.prepareStatement(SQLString);
            for (int i = 0; i < odl.size(); i++) {
                statement.setInt(1, odl.get(i).getoID());
                statement.setInt(2, odl.get(i).getpID());
                statement.setInt(3, odl.get(i).getAmount());
                statement.setInt(4, odl.get(i).getVer());
                rowsInserted += statement.executeUpdate();
            }
        }
        System.out.println("insertOrderDetails: " + (rowsInserted == odl.size()));
        return rowsInserted == odl.size();
    }
    
    public boolean insertBookedVehicles(ArrayList<Vehicle> vl, Connection conn) throws SQLException {
        String SQLString = "insert into booked_vehicles values (?, ?, to_date(?,'dd-mon-yyyy hh24:mi'),to_date(?,'dd-mon-yyyy hh24:mi'))";
        PreparedStatement statement = null;
        int rowsInserted = 0;
        if(0 < vl.size()){
            statement = conn.prepareStatement(SQLString);
            for (int i = 0; i < vl.size(); i++) {
                Vehicle v = vl.get(i);
                statement.setInt(1, v.getoID());
                statement.setInt(2, v.getvID());
                statement.setString(3, v.getFromDate());
                statement.setString(4, v.getToDate());
                rowsInserted += statement.executeUpdate();
            }
        }
        System.out.println("insertBookedVehicles: " + (rowsInserted == vl.size()));
        return rowsInserted == vl.size();
    }
    
    public boolean removeBookedVehicles(ArrayList<Vehicle> vl, Connection conn) throws SQLException {
        String SQLString = "delete from booked_vehicles where oid = ? and vid = ?";
        int rowsRemoved = 0;
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(SQLString);
            for (int i = 0; i < vl.size(); i++) {
                Vehicle v = vl.get(i);
                statement.setInt(1, v.getoID());
                statement.setInt(2, v.getvID());
                int tupleRemoved = statement.executeUpdate();
                rowsRemoved += tupleRemoved;
            }
        } catch (SQLException ex) {
            System.out.println("Fail in OrderMapper - RemoveBookedVehicles");
            System.out.println(ex.getMessage());
        }
        System.out.println("removeBookedVehicles: " + (rowsRemoved == vl.size()));
        return (rowsRemoved == vl.size());
    }
}
