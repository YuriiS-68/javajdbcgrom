package hibernate_dz.dz_lesson4.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ORDER_")
public class Order {

    private long id;
    private User user;
    private Room room;
    private Date dateFrom;
    private Date dateTo;
    private double moneyPaid;

    public Order() {
    }

    @Id
    @SequenceGenerator(name = "ORDER_SQ", sequenceName = "ORDER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_SQ")
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "ID_USER")
    public User getUser() {
        return user;
    }

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ROOM")
    public Room getRoom() {
        return room;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_FROM")
    public Date getDateFrom() {
        return dateFrom;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_TO")
    public Date getDateTo() {
        return dateTo;
    }

    @Column(name = "MONEY_PAID")
    public double getMoneyPaid() {
        return moneyPaid;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setMoneyPaid(double moneyPaid) {
        this.moneyPaid = moneyPaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Double.compare(order.moneyPaid, moneyPaid) == 0 &&
                Objects.equals(user, order.user) &&
                Objects.equals(room, order.room) &&
                Objects.equals(dateFrom, order.dateFrom) &&
                Objects.equals(dateTo, order.dateTo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, user, room, dateFrom, dateTo, moneyPaid);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", room=" + room +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", moneyPaid=" + moneyPaid +
                '}';
    }
}
