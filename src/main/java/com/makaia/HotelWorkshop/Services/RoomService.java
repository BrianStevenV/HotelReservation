package com.makaia.HotelWorkshop.Services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.makaia.HotelWorkshop.Modules.Reservation;
import com.makaia.HotelWorkshop.Modules.Room;
import com.makaia.HotelWorkshop.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "reservation")
    @JsonIgnoreProperties("reservation")
    private List<Reservation> reservations;
    @Autowired
    private RoomRepository roomRepository;

    public ResponseEntity<Room> create(Room room){
        if(room.getNumberRoom() != null ){
            Optional<Room> tempRoom = this.roomRepository.findById(room.getNumberRoom());
            if(tempRoom.isPresent()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room is rejected in Database.");
            }
        }
        return new ResponseEntity<>(this.roomRepository.save(room), HttpStatus.CREATED);
    }

    public List<Room> researchAll(){
        List<Room> roomsAvailables = (List<Room>) roomRepository.findAll();
        if(roomsAvailables.isEmpty()){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are Rooms available now.");
        }
        return roomsAvailables;
    }

    public Optional<Room> researchById(int id){
        Optional<Room> roomsAvailables = roomRepository.findById(id);
        if(roomsAvailables.isEmpty()){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The room: " + id + " don't is available.");
        }
        return roomsAvailables;
    }
}
