package jdbc_dz_lesson4_part2;

public class Controller {

    private FileDAO fileDAO = new FileDAO();
    private StorageDAO storageDAO = new StorageDAO();
    //Ограничения:
    //Storage может хранить файлы только поддерживаемого формата Учитывайте макс размер хранилища
    //В одном хранилище не могут хранится файлы с одинаковым айди, но могут хранится файлы с одинаковыми именами
    //Имя файла не может быть больше 10 символов, то есть файл с таким именем не может быть создан

    //1. проверить файл на null +
    //2. проверить файл и хранилище на совпадение форматов
    //3. проверить хранилище на вместимость

    public File put(Storage storage, File file)throws Exception {
        if (file == null || storage == null){
            return null;
        }

        Storage storageFromDB = storageDAO.findById(storage.getId());
        File fileFromDB = fileDAO.findById(file.getId());

        if (storageFromDB == null)
            throw new NullPointerException("There is no storage " + storage.getId() + " in the database");

        if (fileFromDB == null)
            throw new NullPointerException("The file " + file.getId() + " is not in the database");

        if (storageFromDB.getId() == 0)
            throw new Exception("Storage with id : " + storage.getId() + " in the database not found.");

        if (checkOnSameId(fileFromDB, file))
            throw new Exception("A file with id: " + file.getId() + " with the same id already exists in the storage with id: " + storage.getId());

        if (checkFreeSpace(storageFromDB, file))
            throw new Exception("For storage with id: " + storage.getId() + " The file with id: " + file.getId() + " - is too large");

        if (!checkFormat(storageFromDB, file))
            throw new Exception("The storage with id: " + storageFromDB.getId() + " does not support the file : " + file.getId());

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

        if (storageFromDB == null)
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

    private boolean checkOnSameId(File fileInDB, File file){

        return fileInDB.getId() == file.getId();

    }

    private boolean checkFreeSpace(Storage storage, File file){

        return file != null && storage != null && storage.getStorageSize() < file.getSize();
    }

    private boolean checkFormat(Storage storage, File file) {
        if (storage == null || file == null)
            return false;

        String[] formatsStorage = storage.getFormatSupported().split(",");

        for (String elStorage : formatsStorage){
            if (elStorage != null && file.getFormat().equals(elStorage.trim())){
                return true;
            }
        }
        return false;
    }
}
