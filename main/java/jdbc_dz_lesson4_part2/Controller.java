package jdbc_dz_lesson4_part2;

public class Controller {

    private FileDAO fileDAO = new FileDAO();
    private StorageDAO storageDAO = new StorageDAO();
    //Ограничения:
    //Storage может хранить файлы только поддерживаемого формата Учитывайте макс размер хранилища
    //В одном хранилище не могут хранится файлы с одинаковым айди, но могут хранится файлы с одинаковыми именами
    //Имя файла не может быть больше 10 символов, то есть файл с таким именем не может быть создан

    //1. проверить файл на null +
    //2. проверить файл и хранилище на совпадение форматов +
    //3. проверить хранилище на вместимость +

    public File put(Storage storage, File file)throws Exception {
        if (file == null || storage == null){
            return null;
        }

        Storage storageFromDB = storageDAO.findById(storage.getId());
        File fileFromDB = fileDAO.findById(file.getId());

        validate(storageFromDB, fileFromDB, file);

        file.setStorageId(storage.getId());
        fileDAO.save(file);
        storage.setStorageSize(storageFromDB.getStorageSize() - file.getSize());
        storageDAO.update(storage);

        return file;
    }

    public void delete(Storage storage, File file)throws Exception{
        if (file == null || storage == null)
            throw new Exception("Incoming data contains an error");

        Storage storageFromDB = storageDAO.findById(storage.getId());
        File fileFromDB = fileDAO.findById(file.getId());

        if (storageFromDB == null || storageFromDB.getId() == 0)
            throw new NullPointerException("There is no storage " + storage.getId() + " in the database");

        if (fileFromDB == null)
            throw new NullPointerException("The file " + file.getId() + " is not in the database");

        if (fileFromDB.getStorageId() == storageFromDB.getId()){
            fileDAO.delete(file.getId());
            storage.setStorageSize(storageFromDB.getStorageSize() + fileFromDB.getSize());
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

    private void validate(Storage storage, File fileIn, File file)throws Exception{
        if (storage == null || fileIn == null || file == null)
            throw new Exception("Incoming data contains an error");

        if (storage.getId() == 0)
            throw new NullPointerException("There is no storage " + storage.getId() + " in the database");

        if (checkOnSameId(fileIn, file))
            throw new Exception("A file with id: " + file.getId() + " already exists in the storage with id: " + storage.getId());

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

    private boolean checkOnSameId(File fileInDB, File file){

        return fileInDB.getId() == file.getId();
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
