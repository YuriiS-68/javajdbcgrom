package jdbc_dz_lesson3_part3;

public class TestSpeed {
    private long id;
    private String someString;
    private int someNumber;

    public TestSpeed(){

    }

    public TestSpeed(long id) {
        this.id = id;
    }

    public TestSpeed(long id, String someString, int someNumber) {
        this.id = id;
        this.someString = someString;
        this.someNumber = someNumber;
    }

    public long getId() {
        return id;
    }

    public String getSomeString() {
        return someString;
    }

    public int getSomeNumber() {
        return someNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSomeString(String someString) {
        this.someString = someString;
    }

    public void setSomeNumber(int someNumber) {
        this.someNumber = someNumber;
    }

    @Override
    public String toString() {
        return "TestSpeed{" +
                "id=" + id +
                ", someString='" + someString + '\'' +
                ", someNumber=" + someNumber +
                '}';
    }
}
