package hibernate_dz.dz_lesson2_part2;

public class Demo {
    public static void main(String[] args) {

        System.out.println("Find product - " + ProductDAO.findById(15));

        /*System.out.println("Find products - " + hibernate_dz.dz_lesson2_part2.ProductDAO.findByName("toy"));

        System.out.println("Find products - " + hibernate_dz.dz_lesson2_part2.ProductDAO.findByContainedName("new"));

        System.out.println("Find products - " + hibernate_dz.dz_lesson2_part2.ProductDAO.findByPrice(100, 50));

        System.out.println("Find products - " + hibernate_dz.dz_lesson2_part2.ProductDAO.findByNameSortedAsc());

        System.out.println("Find products - " + hibernate_dz.dz_lesson2_part2.ProductDAO.findByNameSortedDesc());

        System.out.println("Find products - " + hibernate_dz.dz_lesson2_part2.ProductDAO.findByPriceSortedDesc(100, 50));*/
    }
}
