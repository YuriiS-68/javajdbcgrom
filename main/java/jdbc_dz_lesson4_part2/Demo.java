package jdbc_dz_lesson4_part2;

public class Demo {
    public static void main(String[] args)throws Exception {
        FileDAO fileDAO = new FileDAO();
        StorageDAO storageDAO = new StorageDAO();
        Controller controller = new Controller();

        File file1 = new File(1111, 2222,  "test1", "jpg", 130);
        File file2 = new File(2222, 2222,  "test2", "jpg", 100);
        File file3 = new File(3333, 1111,  "test3", "png", 150);
        File file4 = new File(4444, 2222,  "test4", "txt", 250);
        File file5 = new File(5555, "test5", "jpg", 120);
        File file6 = new File(6666, "test6", "txt", 20);

        File[] files1 = {file3};
        File[] files2 = {file1, file2, file4};

        Storage storage1 = new Storage(1111, files1, "png, doc, jpg", "China", 550);
        Storage storage2 = new Storage(2222, files2, "txt, jpg, png", "Korea", 700);

        //fileDAO.save(file4);

        //storageDAO.save(storage2);

        //controller.put(storage1, file4);

        //controller.delete(storage1, file2);

        //controller.transferFile(storage1, storage2, 3333);

        controller.transferAll(storage1, storage2);

        //System.out.println(storageDAO.findById(2222));

    }
}
