package jdbc_dz_lesson4_part2;

import java.util.*;

public class Controller {

    private FileDAO fileDAO = new FileDAO();
    private StorageDAO storageDAO = new StorageDAO();
    private GeneralDAO generalDAO = new GeneralDAO();
    //Ограничения:
    //Storage может хранить файлы только поддерживаемого формата Учитывайте макс размер хранилища
    //В одном хранилище не могут хранится файлы с одинаковым айди, но могут хранится файлы с одинаковыми именами
    //Имя файла не может быть больше 10 символов, то есть файл с таким именем не может быть создан

    public File put(Storage storage, File file)throws Exception {
        if (file == null || storage == null)
            throw new Exception("Incoming data contains an error");

        if (file.getStorageId() != 0)
            return null;

        validate(storage, file);

        file.setStorageId(storage.getId());
        storage.setStorageSize(storage.getStorageSize() - file.getSize());

        generalDAO.update(storage, file);

        return file;
    }

    public void delete(Storage storage, File file)throws Exception{
        if (file == null || storage == null)
            throw new Exception("Incoming data contains an error");

        if (file.getStorageId() == storage.getId()){
            fileDAO.delete(file.getId());
            storage.setStorageSize(storage.getStorageSize() + file.getSize());
            storageDAO.update(storage);
        }else {
            throw new Exception("File with id " + file.getId() + " not found in storage " + storage.getId() + ".");
        }
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id)throws Exception{
        //1. получить объекты со всеми полями хранилищ и файла из базы данных
        //2. проверить есть ли файл в хранилище, в которое файл перемещаем
        //3. проверить поддерживает ли хранилище формат файла
        //4. проверить хватит ли свободного места для записываемого файла
        if (storageFrom == null || storageTo == null || id == 0)
            throw new Exception("Incoming data contains an error");

        Storage storageFromDB = storageDAO.findById(storageFrom.getId());
        Storage storageToDB = storageDAO.findById(storageTo.getId());
        File fileFromDB = fileDAO.findById(id);

        validateForTransferFile(storageFromDB, storageToDB, fileFromDB, id);

        if (fileFromDB.getStorageId() == storageFromDB.getId()){
            fileFromDB.setStorageId(storageToDB.getId());
            fileDAO.update(fileFromDB);
            storageFromDB.setStorageSize(storageFromDB.getStorageSize() + fileFromDB.getSize());
            storageDAO.update(storageFromDB);
            storageToDB.setStorageSize(storageToDB.getStorageSize() - fileFromDB.getSize());
            storageDAO.update(storageToDB);
        }else {
            throw new Exception("File with id " + fileFromDB.getId() + " not found in storage " + storageFromDB.getId() + ".");
        }
    }

    public void transferAll(Storage storageFrom, Storage storageTo)throws Exception{
        //1. получить объекты хранилища
        //2. по айди хранилища получить список находящихся в нём файлов
        //3. проверить чтобы суммарный размер файлов из storageFrom не превышал размер хранилища storageTo
        //4. проверить чтобы файлы из storageFrom не находились в storageTo
        //5. проверить хранилище storageTo на соответствие форматов файлов из хранилища storageFrom
        if (storageFrom == null || storageTo == null)
            throw new Exception("Incoming data contains an error");

        Storage storageFromDB = storageDAO.findById(storageFrom.getId());
        Storage storageToDB = storageDAO.findById(storageTo.getId());

        List<File> filesFrom = fileDAO.findById(storageFromDB);
        List<File> filesTo = fileDAO.findById(storageToDB);


    }

    private boolean checkFreeSpaceInStorageTo(Storage storageTo, List<File> fileList){
        if (storageTo == null || fileList == null)
            return false;

        long sumSizeFiles = 0;
        for (File file : fileList){
            sumSizeFiles += file.getSize();
        }

        return storageTo.getStorageSize() >= sumSizeFiles;
    }

    private boolean checkOnSameIdTransferAll(List<File> filesFrom, List<File> filesTo){
        if (filesFrom == null || filesTo == null)
            return false;

        System.out.println("Files from -" + filesFrom);
        System.out.println("Files to - " + filesTo);

        for (File file : filesFrom){
            for (File file1 : filesTo){
                if (file1.getId() == file.getId()){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkFormatAll(List<File> filesFrom, Storage storageTo)throws Exception{
        if (filesFrom == null || storageTo == null)
            throw new Exception("Incoming data contains an error");

        String[] formatsStorage = storageTo.getFormatSupported().split(",");

        Set<String> strings = new HashSet<>();

        for (File file : filesFrom){
            strings.add(file.getFormat());
        }

        return strings.containsAll(filesFrom);
    }

    private boolean checkStrings(String string, String[] storage){
        if (string == null)
            return false;
        String element = "";
        for (String elStorage : storage){
            if (elStorage != null && string.equals(elStorage.trim())){
                return true;
            }
        }
        return false;
    }

    private void validate(Storage storage, File file)throws Exception{
        //1. проверить файл и хранилище на совпадение форматов
        //2. проверить остаток свободного места в хранилище
        //3. проверить что такого файла нет в хранилище
        if (storage == null || file == null)
            throw new Exception("Incoming data contains an error");

        if (checkFreeSpace(storage, file))
            throw new Exception("For storage with id: " + storage.getId() + " The file with id: " + file.getId() + " - is too large");

        if (!checkFormat(storage, file))
            throw new Exception("The storage with id: " + storage.getId() + " does not support the file : " + file.getId());
    }

    private void validateForTransferFile(Storage storageFrom, Storage storageTo, File file, long id)throws Exception{
        if (storageFrom == null || storageTo == null || file == null || id == 0)
            throw new NullPointerException("Incoming data contains an error");

        if (checkOnSameIdOnTransfer(file, storageFrom, storageTo, id))
            throw new Exception("A file with id: " + file.getId() + " with the same id already exists in the storage with id: " + storageTo.getId());

        if (checkFreeSpace(storageTo, file))
            throw new Exception("For storage with id: " + storageTo.getId() + " The file with id: " + file.getId() + " - is too large");

        if (!checkFormat(storageTo, file))
            throw new Exception("The storage with id: " + storageTo.getId() + " does not support the file : " + file.getId());
    }

    private boolean checkOnSameIdOnTransfer(File file, Storage storageFrom, Storage storageTo, long id)throws Exception{
        if (file.getId() == 0)
            throw new Exception("The file with id " + id + " is not in the database " + storageFrom.getId());

        return (file.getStorageId() == storageTo.getId() && file.getId() == id);
    }

    private boolean checkFreeSpace(Storage storage, File file){

        return file != null && storage != null && storage.getStorageSize() < file.getSize();
    }

    private boolean checkFormat(Storage storage, File file) {
        if (storage == null || file == null)
            return false;

        String[] formatsStorage = storage.getFormatSupported().split(",");

        for (String elStorage : formatsStorage){
            if (elStorage != null && file.getFormat() != null && file.getFormat().equals(elStorage.trim())){
                return true;
            }
        }
        return false;
    }
}
