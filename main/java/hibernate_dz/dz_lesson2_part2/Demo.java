package hibernate_dz.dz_lesson2_part2;

public class Demo {
    public static void main(String[] args) {

        System.out.println("Find product - " + ProductDAO.findById(15));

        System.out.println("Find products - " + ProductDAO.findByName("toy"));

        System.out.println("Find products - " + ProductDAO.findByContainedName("new"));

        System.out.println("Find products - " + ProductDAO.findByPrice(100, 50));

        System.out.println("Find products - " + ProductDAO.findByNameSortedAsc());

        System.out.println("Find products - " + ProductDAO.findByNameSortedDesc());

        System.out.println("Find products - " + ProductDAO.findByPriceSortedDesc(100, 50));
    }
}
