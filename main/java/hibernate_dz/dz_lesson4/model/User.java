package hibernate_dz.dz_lesson4.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USER_DZ4")
public class User {
    private long id;
    private String userName;
    private String password;
    private String country;
    private String userType;
    private List<Order> orders;

    public User() {
    }

    public User(String userName, String password, String country, String userType) {
        this.userName = userName;
        this.password = password;
        this.country = country;
        this.userType = userType;
    }

    public User(String userName, String password, String country, String userType, List<Order> orders) {
        this.userName = userName;
        this.password = password;
        this.country = country;
        this.userType = userType;
        this.orders = orders;
    }

    @Id
    @SequenceGenerator(name = "US_SQ", sequenceName = "USER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "US_SQ")
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    @Column(name = "USER_NAME", nullable = false)
    public String getUserName() {
        return userName;
    }

    @Column(name = "PASSWORD", nullable = false)
    public String getPassword() {
        return password;
    }

    @Column(name = "COUNTRY", nullable = false)
    public String getCountry() {
        return country;
    }

    @Column(name = "USER_TYPE", nullable = false)
    public String getUserType() {
        return userType;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, targetEntity = Order.class)
    public List<Order> getOrders() {
        return orders;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(country, user.country) &&
                Objects.equals(userType, user.userType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userName, password, country, userType);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", country='" + country + '\'' +
                ", userType='" + userType +
                '}';
    }
}
