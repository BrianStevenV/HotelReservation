package com.makaia.HotelWorkshop.Controllers;

import com.makaia.HotelWorkshop.Modules.Room;
import com.makaia.HotelWorkshop.Repositories.RoomRepository;
import com.makaia.HotelWorkshop.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @PostMapping("/room")
    public ResponseEntity<Room> register(@RequestBody Room room){
        return roomService.create(room);
    }
}
