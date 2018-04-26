package jdbc.lesson3;

public class Demo {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();

        Product product = new Product(10, "test", "test description", 99);
        Product product1 = new Product(1111, "test-1", "test description 1", 152);
        Product product2 = new Product(1122, "test-2", "test description 2", 200);
        Product product3 = new Product(1133, "test-3", "test description 3", 250);
        Product product4 = new Product(1144, "test-4", "test description 4", 270);

        productDAO.save(product4);

        //System.out.println(productDAO.getProducts());

        //productDAO.update(product1);

        //productDAO.delete(10);
    }

}
