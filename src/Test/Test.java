package Test;

import Domain.Customer;

/**
 *
 * @author Riboe
 */
public class Test {

    public static void main(String[] args) {
        Domain.Controller c = new Domain.Controller();
        Customer cu = c.getCustomer(1001);

        System.out.println(cu.getcName());
        System.out.println(cu.getcAddress());
        System.out.println(cu.getcEmail());
        System.out.println(cu.getcID());
        System.out.println(cu.getcPhoneNumber());
        System.out.println(cu.getcZip());
        
        System.out.println(c.getCity(cu.getcZip()));
    }
}
