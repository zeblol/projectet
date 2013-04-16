package Test;

import Domain.Controller;
import Domain.Product;
import java.util.ArrayList;

/**
 *
 * @author Riboe
 */
public class Test {

    public static void main(String[] args) {
        Controller c = new Controller();
        
        ArrayList<Product> al = c.getProducts();
        System.out.println("ArrayList size: " + al.size());
        for (int i = 0; i < al.size(); i++) {
            Product product = al.get(i);
            System.out.println(product);
        }
    }
}
