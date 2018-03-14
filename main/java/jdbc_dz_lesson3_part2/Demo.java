package jdbc_dz_lesson3_part2;

public class Demo {
    public static void main(String[] args)throws Exception {

        ProductDAO productDAO = new ProductDAO();

        System.out.println(productDAO.findProductsByPrice(200, 50));

        //Product product = new Product(1122, "test-1", "test description 1", 200);

        //System.out.println(productDAO.findProductsByName("toy"));

        //System.out.println(productDAO.findProductsWithEmptyDescription());
    }
}
