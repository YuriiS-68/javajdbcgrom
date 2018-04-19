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
            storage.setStorageSize(storage.getStorageSize() + file.getSize());
            generalDAO.delete(storage, file);
        }else {
            throw new Exception("File with id " + file.getId() + " not found in storage " + storage.getId() + ".");
        }
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id)throws Exception{
        //1. проверить есть ли файл в хранилище, в которое файл перемещаем
        //2. проверить поддерживает ли хранилище формат файла
        //3. проверить хватит ли свободного места для записываемого файла
        if (storageFrom == null || storageTo == null || id == 0)
            throw new Exception("Incoming data contains an error");

        File file = fileDAO.findById(id);

        validateForTransferFile(storageFrom, storageTo, file, id);

        if (file.getStorageId() == storageFrom.getId()){
            file.setStorageId(storageTo.getId());
            storageFrom.setStorageSize(storageFrom.getStorageSize() + file.getSize());
            storageTo.setStorageSize(storageTo.getStorageSize() - file.getSize());
            generalDAO.transfer(storageFrom, storageTo, file);
        }else {
            throw new Exception("File with id " + file.getId() + " not found in storage " + storageFrom.getId() + ".");
        }
    }

    public void transferAll(Storage storageFrom, Storage storageTo)throws Exception{
        //1. по айди хранилища получить список находящихся в нём файлов
        //2. проверить чтобы суммарный размер файлов из storageFrom не превышал размер свободного места в хранилищу storageTo
        //3. проверить чтобы файлы из storageFrom не находились в storageTo
        //4. проверить хранилище storageTo на соответствие форматов файлов из хранилища storageFrom
        if (storageFrom == null || storageTo == null)
            throw new Exception("Incoming data contains an error");

        List<File> filesFrom = fileDAO.findById(storageFrom);
        List<File> filesTo = fileDAO.findById(storageTo);

        validateTransferAll(filesFrom, filesTo, storageTo);

        if (storageFrom.getId() != storageTo.getId()){
            long sumSizeFilesFrom = 0;
            for (File element : filesFrom){
                sumSizeFilesFrom += element.getSize();
                element.setStorageId(storageTo.getId());
            }

            storageFrom.setStorageSize(storageFrom.getStorageSize() + sumSizeFilesFrom);
            storageTo.setStorageSize(storageTo.getStorageSize() - sumSizeFilesFrom);
            generalDAO.transferAll(filesFrom, storageFrom, storageTo);
        }else {
            throw new Exception("Can not transfer files from storage " + storageFrom.getId());
        }
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
        //1. проверяю наличие файла в хранилище1, из которой он переносится, и на отсутствие файла в хранилище2, в которую переносится
        //2. проверяю остаток свободного места в хранилище2
        //3. проверяю хранилище2 на поддержку формата файла
        if (storageFrom == null || storageTo == null || file == null || id == 0)
            throw new NullPointerException("Incoming data contains an error");

        if (file.getId() == 0)
            throw new Exception("The file with id " + id + " is not in the database " + storageFrom.getId());

        if (checkOnSameIdOnTransfer(file, storageFrom, storageTo))
            throw new Exception("A file with id: " + file.getId() + " with the same id already exists in the storage with id: " + storageTo.getId());

        validate(storageTo, file);
    }

    private void validateTransferAll(List<File> filesFrom, List<File> filesTo, Storage storageTo)throws Exception{
        if (filesFrom == null || filesTo == null || storageTo == null)
            throw new NullPointerException("Incoming data contains an error");

        if (!checkFreeSpaceInStorageTo(storageTo, filesFrom))
            throw new Exception("The amount of free space in the repository " + storageTo.getId() + " is less than the total size of the files for writing");

        if (!checkOnSameIdTransferAll(filesFrom, filesTo, storageTo))
            throw new Exception("One of the files already exists in the repository " + storageTo.getId());

        if (!checkFormatAll(filesFrom, storageTo))
            throw new Exception("The repository " + storageTo.getId() + " does not support all file formats");
    }


    private boolean checkFreeSpaceInStorageTo(Storage storageTo, List<File> fileList)throws Exception{
        if (storageTo == null || fileList == null)
            throw new Exception("Incoming data contains an error");

        long sumSizeFiles = 0;
        for (File file : fileList){
            sumSizeFiles += file.getSize();
        }

        return storageTo.getStorageSize() >= sumSizeFiles;
    }

    private boolean checkOnSameIdTransferAll(List<File> filesFrom, List<File> filesTo, Storage storageTo)throws Exception{
        if (filesFrom == null || filesTo == null || storageTo == null)
            throw new Exception("Incoming data contains an error");

        for (File file : filesFrom){
            for (File file1 : filesTo){
                if (file1.getId() == file.getId()){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkFormatAll(List<File> files, Storage storageTo)throws Exception{
        if (files == null || storageTo == null)
            throw new Exception("Incoming data contains an error");

        String[] formatsStorage = storageTo.getFormatSupported().split(",");

        List<String> storageFormats = new ArrayList<>();

        for (String element : formatsStorage){
            if (element != null){
                storageFormats.add(element.trim());
            }
        }

        Set<String> fileFormats = new HashSet<>();

        for (File file : files){
            fileFormats.add(file.getFormat());
        }

        return storageFormats.containsAll(fileFormats);
    }

    private boolean checkOnSameIdOnTransfer(File file, Storage storageFrom, Storage storageTo)throws Exception{
        if (file == null || storageFrom == null || storageTo == null)
            throw new Exception("Incoming data contains an error");

        if (file.getStorageId() != storageFrom.getId())
            throw new Exception("The file with id " + file.getId() + " is not in the database " + storageFrom.getId());

        return file.getStorageId() == storageTo.getId();
    }

    private boolean checkFreeSpace(Storage storage, File file)throws Exception{
        if (storage == null || file == null)
            throw new Exception("Incoming data contains an error");

        return storage.getStorageSize() < file.getSize();
    }

    private boolean checkFormat(Storage storage, File file)throws Exception {
        if (storage == null || file == null)
            throw new Exception("Incoming data contains an error");

        String[] formatsStorage = storage.getFormatSupported().split(",");

        for (String elStorage : formatsStorage){
            if (elStorage != null && file.getFormat() != null && file.getFormat().equals(elStorage.trim())){
                return true;
            }
        }
        return false;
    }
}
