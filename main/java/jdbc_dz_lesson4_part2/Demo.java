package jdbc_dz_lesson4_part2;

public class Demo {
    public static void main(String[] args)throws Exception {
        FileDAO fileDAO = new FileDAO();
        StorageDAO storageDAO = new StorageDAO();
        Controller controller = new Controller();

        File file1 = new File(1111, "test1", "doc", 130);
        File file2 = new File(2222, "test2", "txt", 100);
        File file3 = new File(3333, "test3", "png", 150);
        File file4 = new File(4444, "test4", "jpg", 600);
        File file5 = new File(5555, "test5", "txt", 120);
        File file6 = new File(6666, "test6", "doc", 20);

        File[] files1 = new File[5];
        File[] files2 = new File[4];

        Storage storage1 = new Storage(1111, files1, "txt, png, doc", "China", 500);
        Storage storage2 = new Storage(2222, files2, "txt, doc, jpg", "Korea", 480);



        //fileDAO.save(file1);

        //storageDAO.save(storage1);

        //storageDAO.save(storage2);

        controller.put(storage1, file6);

        //controller.delete(storage1, file1);

        //controller.transferFile(storage2, storage1, 6666);

        //System.out.println(controller.checkOnSameId(file1, file1));

        //controller.validateForTransferFile(storage1, storage2, file1, 1111);

        //controller.checkOnSameIdOnTransfer(file1, storage1, 1131);

        //controller.checkStorageSize(storage1, file4);

        //System.out.println(controller.checkFormat(storage2, file4));

        //System.out.println(controller.checkOnSameId(file1));

        //System.out.println(fileDAO.findById(1111));

        //storageDAO.save(storage1);

        //System.out.println(storageDAO.findById(1111));

    }
}
