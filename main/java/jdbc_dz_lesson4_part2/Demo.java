package jdbc_dz_lesson4_part2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Demo {
    public static void main(String[] args)throws Exception {
        FileDAO fileDAO = new FileDAO();
        StorageDAO storageDAO = new StorageDAO();
        Controller controller = new Controller();

        File file1 = new File(1111, "test1", "jpg", 130);
        File file2 = new File(2222, "test2", "jpg", 100);
        File file3 = new File(3333, "test3", "png", 150);
        File file4 = new File(4444, "test4", "doc", 600);
        File file5 = new File(5555, "test5", "jpg", 120);
        File file6 = new File(6666, "test6", "txt", 20);

        File[] files1 = new File[5];
        File[] files2 = new File[4];

        Storage storage1 = new Storage(1111, files1, "png, doc, jpg", "China", 270);
        Storage storage2 = new Storage(2222, files2, "txt, jpg, png", "Korea", 480);

        //fileDAO.save(file1);

        //storageDAO.save(storage2);

        //controller.put(storage1, file3);

        //controller.delete(storage1, file3);

        //controller.transferFile(storage2, storage1, 3333);

        controller.transferAll(storage1, storage2);

    }
}
