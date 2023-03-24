package com.makaia.HotelWorkshop.Services;

import com.makaia.HotelWorkshop.Modules.Reservation;
import com.makaia.HotelWorkshop.Modules.Room;
import com.makaia.HotelWorkshop.Repositories.ReservationRepository;
import com.makaia.HotelWorkshop.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomService roomService;

    public List<Reservation> research(){
        List<Reservation> reservationsAvailables = (List<Reservation>) reservationRepository.findAll();
        if(reservationsAvailables.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are Rooms available now.");
        }
        return reservationsAvailables;
    }

    public List<Room> researchAvailable(int dni, int numberRoom, Date date, String roomType){
        List<Reservation> reservations = research();
        List<Room> roomList = new ArrayList<>();
        List<Reservation> reservationBasic = reservations.stream()
                .filter(reservation -> reservation.getRoom() != null && reservation.getRoom().getRoomType().equals("basic") && reservation.getReserveDate().equals(date))
                .collect(Collectors.toList());
        reservationBasic.stream().forEach(reservation -> {
            Optional<Room> auxRoom = roomService.researchById(reservation.getRoom().getNumberRoom());
            if (auxRoom.isPresent()) {
                roomList.add(auxRoom.get());
            }
        });
        return roomList;
    }
    
}
