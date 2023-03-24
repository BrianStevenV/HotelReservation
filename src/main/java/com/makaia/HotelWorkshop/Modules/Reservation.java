package com.makaia.HotelWorkshop.Modules;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Reservation")
public class Reservation {
    @Id
    @Column(name = "reserveCode")
    private Integer reserveCode;
    @Column(name = "reserveDate")
    private Date reserveDate;
    @Column(name = "totalValue")
    private Double totalValue;
    @ManyToOne
    @JoinColumn(name = "dni")
    @Column(name = "dni")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "numberRoom")
    @Column(name = "roomNumber")
    private Room room;

    public Reservation(Integer reserveCode, Date reserveDate, Double totalValue, Customer customer, Room room) {
        this.reserveCode = reserveCode;
        this.reserveDate = reserveDate;
        this.totalValue = totalValue;
        this.customer = customer;
        this.room = room;
    }
    private String generarCodigoReserva() {
        return UUID.randomUUID().toString();
    }

    public Integer getReserveCode() {
        return reserveCode;
    }

    public void setReserveCode(Integer reserveCode) {
        this.reserveCode = reserveCode;
    }

    public Date getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
