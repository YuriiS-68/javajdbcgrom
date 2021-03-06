package hibernate_dz.dz_lesson3;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ROOM")
public class Room {

    private long id;
    private int numberOfGuests;
    private double price;
    private int breakfastIncluded;
    private int petsAllowed;
    private Date dateAvailableFrom;
    private Hotel hotel;

    public Room() {
    }

    public Room(int numberOfGuests, double price, int breakfastIncluded, int petsAllowed, Date dateAvailableFrom, Hotel hotel) {
        this.numberOfGuests = numberOfGuests;
        this.price = price;
        this.breakfastIncluded = breakfastIncluded;
        this.petsAllowed = petsAllowed;
        this.dateAvailableFrom = dateAvailableFrom;
        this.hotel = hotel;
    }

    public Room(long id, int numberOfGuests, double price, int breakfastIncluded, int petsAllowed, Date dateAvailableFrom, Hotel hotel) {
        this.id = id;
        this.numberOfGuests = numberOfGuests;
        this.price = price;
        this.breakfastIncluded = breakfastIncluded;
        this.petsAllowed = petsAllowed;
        this.dateAvailableFrom = dateAvailableFrom;
        this.hotel = hotel;
    }

    @Id
    @SequenceGenerator(name = "R_SEQ", sequenceName = "ROOM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "R_SEQ")
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    @Column(name = "NUMBER_OF_GUESTS", nullable = false)
    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    @Column(name = "PRICE", nullable = false)
    public double getPrice() {
        return price;
    }

    @Column(name = "BREAKFAST_INCLUDED", nullable = false)
    public int getBreakfastIncluded() {
        return breakfastIncluded;
    }

    @Column(name = "PETS_ALLOWED", nullable = false)
    public int getPetsAllowed() {
        return petsAllowed;
    }

    @Column(name = "DATE_AVAILABLE_FROM", nullable = false)
    public Date getDateAvailableFrom() {
        return dateAvailableFrom;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_HOTEL")
    public Hotel getHotel() {
        return hotel;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBreakfastIncluded(int breakfastIncluded) {
        this.breakfastIncluded = breakfastIncluded;
    }

    public void setPetsAllowed(int petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public void setDateAvailableFrom(Date dateAvailableFrom) {
        this.dateAvailableFrom = dateAvailableFrom;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", numberOfGuests=" + numberOfGuests +
                ", price=" + price +
                ", breakfastIncluded=" + breakfastIncluded +
                ", petsAllowed=" + petsAllowed +
                ", dateAvailableFrom=" + dateAvailableFrom +
                ", hotel=" + hotel +
                '}';
    }
}
