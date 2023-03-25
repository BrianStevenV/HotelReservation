package com.makaia.HotelWorkshop.Services;

import com.makaia.HotelWorkshop.Modules.Customer;
import com.makaia.HotelWorkshop.Modules.Reservation;
import com.makaia.HotelWorkshop.Modules.Room;
import com.makaia.HotelWorkshop.Repositories.ReservationRepository;
import com.makaia.HotelWorkshop.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private CustomerService customerService;
    public List<Reservation> research(){
        List<Reservation> reservationsAvailables = (List<Reservation>) reservationRepository.findAll();
        if(reservationsAvailables.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are Rooms available now.");
        }
        return reservationsAvailables;
    }

    public List<Room> roomsByType(LocalDate date, String roomType){
        List<Reservation> reservations = research();
        List<Room> roomList = new ArrayList<>();

        if(roomType.equals("basic")){
            List<Reservation> reservationBasic = reservations.stream()
                    .filter(reservation -> reservation.getRoom() != null && reservation.getRoom().getRoomType().equals("basic") && reservation.getReserveDate().equals(date))
                    .collect(Collectors.toList());
            reservationBasic.stream().forEach(reservation -> {
                Optional<Room> auxRoom = roomService.researchById(reservation.getRoom().getNumberRoom());
                roomList.add(auxRoom.get());

            });
        }else if (roomType.equals("premium")) {
            List<Reservation> reservationBasic = reservations.stream()
                    .filter(reservation -> reservation.getRoom() != null && reservation.getRoom().getRoomType().equals("premium") && reservation.getReserveDate().equals(date))
                    .collect(Collectors.toList());
            reservationBasic.stream().forEach(reservation -> {
                Optional<Room> auxRoom = roomService.researchById(reservation.getRoom().getNumberRoom());
                roomList.add(auxRoom.get());

            });

        }
        return roomList;
    }


    public List<Room> roomsByDate(LocalDate date){
        List<Reservation> reservations = research();
        List<Room> roomList = new ArrayList<>();

        List<Reservation> reservationBasic = reservations.stream()
                .filter(reservation -> reservation.getRoom() != null && reservation.getReserveDate().equals(date))
                .collect(Collectors.toList());
        reservationBasic.stream().forEach(reservation -> {
            Optional<Room> auxRoom = roomService.researchById(reservation.getRoom().getNumberRoom());
            roomList.add(auxRoom.get());
        });
        return roomList;
    }

    public ResponseEntity<Reservation> create(Reservation reservation, int idCustomer){
        Optional<Customer> customer = this.customerService.researchById(idCustomer);
        List<Customer> customerList = this.customerService.researchAll();
        if(reservation.getReserveCode() != null ){
            Optional<Reservation> tempReservation = this.reservationRepository.findById(reservation.getReserveCode());
            if(tempReservation.isPresent()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room is rejected in Database.");
            }
        }

        LocalDate nowDate = LocalDate.now();
        if(reservation.getReserveDate().isAfter(nowDate) &&
                reservation.getReserveDate() != null &&
                customerList.contains(customer) &&
                reservation.getRoom() != null){
            return new ResponseEntity<>(this.reservationRepository.save(reservation), HttpStatus.CREATED);
        }   else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DNI, FirstName and LastName are required.");
        }

    }
}
