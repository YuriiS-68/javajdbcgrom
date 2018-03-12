package lesson3;

public class Demo {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();

        Product product = new Product(10, "test", "test description", 99);
        Product product1 = new Product(10, "testNew", "test descriptionNew", 152);

        //productDAO.save(product);

        //System.out.println(productDAO.getProducts());

        //productDAO.update(product1);

        productDAO.delete(10);
    }

}
