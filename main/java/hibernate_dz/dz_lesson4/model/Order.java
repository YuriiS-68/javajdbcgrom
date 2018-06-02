package hibernate_dz.dz_lesson4.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ORDER_DZ4")
public class Order extends IdEntity {

    private Long id;
    private User user;
    private Room room;
    private Date dateFrom;
    private Date dateTo;
    private double moneyPaid;

    public Order() {
    }

    public Order(User user, Room room, Date dateFrom, Date dateTo, double moneyPaid) {
        this.user = user;
        this.room = room;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.moneyPaid = moneyPaid;
    }

    public Order(double moneyPaid) {
        this.moneyPaid = moneyPaid;
    }

    @Id
    @SequenceGenerator(name = "ORDER_SQ", sequenceName = "ORDER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_SQ")
    @Column(name = "ID_O")
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER")
    public User getUser() {
        return user;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ROOM")
    public Room getRoom() {
        return room;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_FROM", nullable = false)
    public Date getDateFrom() {
        return dateFrom;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_TO", nullable = false)
    public Date getDateTo() {
        return dateTo;
    }

    @Column(name = "MONEY_PAID", nullable = false)
    public double getMoneyPaid() {
        return moneyPaid;
    }

    public void setId(Long id) {
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
        return Double.compare(order.moneyPaid, moneyPaid) == 0 &&
                Objects.equals(id, order.id) &&
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
