package hibernate_dz.dz_lesson4.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ROOM")
public class Room extends IdEntity {
    private Long id;
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

    @Id
    @SequenceGenerator(name = "RM_SQ", sequenceName = "ROOM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RM_SQ")
    @Column(name = "ID_R")
    public Long getId() {
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
    public int isBreakfastIncluded() {
        return breakfastIncluded;
    }

    @Column(name = "PETS_ALLOWED", nullable = false)
    public int isPetsAllowed() {
        return petsAllowed;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_AVAILABLE_FROM", nullable = false)
    public Date getDateAvailableFrom() {
        return dateAvailableFrom;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_HOTEL")
    public Hotel getHotel() {
        return hotel;
    }

    public void setId(Long id) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return numberOfGuests == room.numberOfGuests &&
                Double.compare(room.price, price) == 0 &&
                breakfastIncluded == room.breakfastIncluded &&
                petsAllowed == room.petsAllowed &&
                Objects.equals(id, room.id) &&
                Objects.equals(dateAvailableFrom, room.dateAvailableFrom) &&
                Objects.equals(hotel, room.hotel);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, numberOfGuests, price, breakfastIncluded, petsAllowed, dateAvailableFrom, hotel);
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
                ", hotel=" + hotel.getId() +
                '}';
    }
}
