package jdbc_dz.jdbc_dz_lesson4_part1;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args){

        TransactionDemo transactionDemo = new TransactionDemo();
        Product product1 = new Product(55, "!!!", "!!!", 777);
        Product product2 = new Product(66, "!!!", "!!!", 777);
        Product product3 = new Product(66, "!!!", "!!!", 777);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);

        transactionDemo.save(products);
    }

}
