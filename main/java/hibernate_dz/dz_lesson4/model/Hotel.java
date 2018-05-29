package hibernate_dz.dz_lesson4.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "HOTEL")
public class Hotel extends IdEntity {
    private Long id;
    private String name;
    private String country;
    private String city;
    private String street;
    private List<Room> rooms;

    public Hotel() {
    }

    public Hotel(String name, String country, String city, String street) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.street = street;
    }

    public Hotel(String name, String country, String city, String street, List<Room> rooms) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.street = street;
        this.rooms = rooms;
    }

    @Id
    @SequenceGenerator(name = "HT_SQ", sequenceName = "HOTEL_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HT_SQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @Column(name = "NAME", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "COUNTRY", nullable = false)
    public String getCountry() {
        return country;
    }

    @Column(name = "CITY", nullable = false)
    public String getCity() {
        return city;
    }

    @Column(name = "STREET", nullable = false)
    public String getStreet() {
        return street;
    }

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, targetEntity = Room.class)
    public List<Room> getRooms() {
        return rooms;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(id, hotel.id) &&
                Objects.equals(name, hotel.name) &&
                Objects.equals(country, hotel.country) &&
                Objects.equals(city, hotel.city) &&
                Objects.equals(street, hotel.street);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, country, city, street);
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street +
                '}';
    }
}
