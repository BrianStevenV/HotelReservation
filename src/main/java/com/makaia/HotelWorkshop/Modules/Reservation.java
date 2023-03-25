package com.makaia.HotelWorkshop.Modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reserveCode;
    @Column(name = "reserveDate")
    private LocalDate reserveDate;
    @Column(name = "totalValue")
    private Double totalValue;
    @ManyToOne
    @JoinColumn(name = "dni")
    @JsonIgnoreProperties("reservations")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "numberRoom")
    @JsonIgnoreProperties("reservations")
    private Room room;


    private String generarCodigoReserva() {
        return UUID.randomUUID().toString();
    }

    public Integer getReserveCode() {
        return reserveCode;
    }

    public void setReserveCode(Integer reserveCode) {
        this.reserveCode = reserveCode;
    }

    public LocalDate getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(LocalDate reserveDate) {
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
