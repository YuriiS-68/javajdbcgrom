package hibernate_dz.dz_lesson2_part1;

import hibernate.lesson1.Product;

import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        Product product = new Product();
        product.setName("table new!");
        product.setDescription("grey & blue");
        product.setPrice(370);

        //ProductDAO.save(product);

        Product product1 = new Product();
        product1.setName("laptop1");
        product1.setDescription("SONY");
        product1.setPrice(1000);

        Product product2 = new Product();
        product2.setName("laptop2");
        product2.setDescription("TOSHIBA");
        product2.setPrice(500);

        Product product3 = new Product();
        product3.setName("laptop3");
        product3.setDescription("ASUS");
        product3.setPrice(850);

        List<Product> products = Arrays.asList(product1, product2, product3);

        //ProductDAO.saveAll(products);

        /*product1.setId(41);
        product1.setName("TV Box1");
        product1.setDescription("black");
        product1.setPrice(100);

        product2.setId(43);
        product2.setName("TV Box2");
        product2.setDescription("silver");
        product2.setPrice(300);

        product3.setId(45);
        product3.setName("TV Box3");
        product3.setDescription("red");
        product3.setPrice(500);*/

        //System.out.println(product4.getId());
        //ProductDAO.update(product4);

        //product2.setId(13);
        //System.out.println(product2.getId());

        //ProductDAO.delete(product2);

        //ProductDAO.saveAll(products);

        //ProductDAO.updateAll(products);

        int idProduct = 41;

        for (Product element : products){
            element.setId(idProduct);
            idProduct +=2;
            System.out.println(element.getId());
        }

        ProductDAO.deleteAll(products);
    }
}
