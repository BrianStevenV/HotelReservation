package com.makaia.HotelWorkshop.Modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Room")
public class Room {
    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "room")
    @JsonIgnoreProperties("room")
    private List<Reservation> reservations;
    @Id
    @Column(name = "numberRoom")
    private Integer numberRoom;
    @Column(name = "roomType")
    private String roomType;
    @Column(name = "price")
    private Double price;

    public Room(Integer numberRoom, String roomType, Double price) {
        this.numberRoom = numberRoom;
        this.roomType = roomType;
        this.price = price;
    }

    public Integer getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(Integer numberRoom) {
        this.numberRoom = numberRoom;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}