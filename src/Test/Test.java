package Test;

import Domain.Customer;
import Domain.Employee;
import Domain.Order;
import Domain.PDFGenerator;

/**
 *
 * @author Riboe
 */
public class Test {

    public static void main(String[] args) {
        Domain.Controller c = new Domain.Controller();
        Customer cu = c.getCustomer(1002);
        Order o = c.getOrder(1201);
        Employee e = new Employee(1001, "Anna", "Kontor");
        
        double depositum = c.calcDeposit(o, 33).doubleValue();
        double total = c.calcTotal(o).doubleValue();
        
        PDFGenerator pdfWriter = new PDFGenerator(cu, o, e, depositum, total);
        
        pdfWriter.create();
    }
}
