package jdbc_dz_lesson4_part2;

import java.util.Arrays;
import java.util.Objects;

public class Storage {
    private long id;
    private File[] files;
    private String formatSupported;
    private String storageCountry;
    private long storageSize;

    public Storage(){

    }

    public Storage(long id){
        this.id = id;
    }

    public Storage(long id, File[] files, String formatSupported, String storageCountry, long storageSize) {
        this.id = id;
        this.files = files;
        this.formatSupported = formatSupported;
        this.storageCountry = storageCountry;
        this.storageSize = storageSize;
    }

    public Storage(long id, String formatSupported, String storageCountry, long storageSize) {
        this.id = id;
        this.formatSupported = formatSupported;
        this.storageCountry = storageCountry;
        this.storageSize = storageSize;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public String getFormatSupported() {
        return formatSupported;
    }

    public void setFormatSupported(String formatSupported) {
        this.formatSupported = formatSupported;
    }

    public String getStorageCountry() {
        return storageCountry;
    }

    public void setStorageCountry(String storageCountry) {
        this.storageCountry = storageCountry;
    }

    public long getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(long storageSize) {
        this.storageSize = storageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Storage storage = (Storage) o;
        return id == storage.id &&
                storageSize == storage.storageSize &&
                Arrays.equals(files, storage.files) &&
                Objects.equals(formatSupported, storage.formatSupported) &&
                Objects.equals(storageCountry, storage.storageCountry);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, formatSupported, storageCountry, storageSize);
        result = 31 * result + Arrays.hashCode(files);
        return result;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", files=" + Arrays.toString(files) +
                ", formatSupported=" + formatSupported +
                ", storageCountry='" + storageCountry + '\'' +
                ", storageSize=" + storageSize +
                '}';
    }
}
