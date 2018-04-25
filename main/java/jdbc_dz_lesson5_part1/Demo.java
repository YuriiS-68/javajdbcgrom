package jdbc_dz_lesson5_part1;

public class Demo {
    public static void main(String[] args) {
        Product product = new Product();
        product.setId(777777);
        product.setName("player");
        product.setDescription("SONY");
        product.setPrice(170);

        ProductRepository productRepository = new ProductRepository();

        //productRepository.save(product);
        //productRepository.update(product);
        //productRepository.delete(product);
    }
}
