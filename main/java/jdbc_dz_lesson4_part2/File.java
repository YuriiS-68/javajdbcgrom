package jdbc_dz_lesson4_part2;

import java.util.Comparator;
import java.util.Objects;

public class File {
    private long id;
    private long storageId;
    private String name;
    private String format;
    private long size;

    public File(){

    }

    public File(long id, long storageId, String name, String format, long size) throws Exception{
        if (!checkOnLengthNameFile(name))
            throw new Exception("File name can't be more 10 chars. File with this name can't be created");
        this.id = id;
        this.storageId = storageId;
        this.name = name;
        this.format = format;
        this.size = size;
    }

    public File(long id, String name, String format, long size) throws Exception{
        if (!checkOnLengthNameFile(name))
            throw new Exception("File name can't be more 10 chars. File with this name can't be created");
        this.id = id;
        this.name = name;
        this.format = format;
        this.size = size;
    }

    private static boolean checkOnLengthNameFile(String name){
        if (name.isEmpty() || name.length() > 10) {
            return false;
        }
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStorageId() {
        return storageId;
    }

    public void setStorageId(long storageId) {
        this.storageId = storageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return id == file.id &&
                Objects.equals(format, file.format);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, format);
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", format='" + format + '\'' +
                ", size=" + size +
                '}';
    }
}
