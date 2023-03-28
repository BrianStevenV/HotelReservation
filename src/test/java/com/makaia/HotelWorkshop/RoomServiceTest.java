package com.makaia.HotelWorkshop;

import com.makaia.HotelWorkshop.Modules.Customer;
import com.makaia.HotelWorkshop.Modules.Room;
import com.makaia.HotelWorkshop.Repositories.RoomRepository;
import com.makaia.HotelWorkshop.Services.RoomService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RoomServiceTest {
    RoomRepository roomRepository;
    RoomService roomService;

    @Before
    public void setUp(){
        this.roomRepository = mock(RoomRepository.class);
        this.roomService = new RoomService(roomRepository);
    };

    @Test(expected = ResponseStatusException.class)
    public void validationRoom(){
        //Arrange
        Room room = new Room(null,"Premium",500.00);
        ResponseEntity<Room> response = this.roomService.create(room);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response);
    }
    @Test
    public void testResearchByIdSuccessRoom(){
        // Arrange
        int numberRoom = 75;
        Room room = new Room(numberRoom,"Basic",300.00);
        when(roomRepository.findById(numberRoom)).thenReturn(Optional.of(room));

        // Act
        Optional<Room> result = roomService.researchById(numberRoom);
        // Assert
        assertTrue(result.isPresent());
        assertEquals(numberRoom, result.get().getNumberRoom().intValue());
        assertEquals(room.getRoomType(), result.get().getRoomType());
        assertEquals(room.getPrice(), result.get().getPrice());
    }

    @Test
    public void testResearchAllSuccess() {
        // Arrange
        Room room1 = new Room(3,"Premium",100.50);
        Room room2 = new Room(7,"Basic",25.50);
        List<Room> roomList = Arrays.asList(room1, room2);
        when(roomRepository.findAll()).thenReturn(roomList);

        // Act
        List<Room> result = roomService.researchAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(room1, result.get(0));
        assertEquals(room2, result.get(1));
    }

    @Test
    public void testResearchAllWithRoomsAvailable() {
        // Arrange
        List<Room> roomList = Arrays.asList(
                new Room(1, "Basic", 300.00),
                new Room(2, "Standard", 400.00),
                new Room(3, "Deluxe", 500.00)
        );
        when(roomRepository.findAll()).thenReturn(roomList);

        // Act
        List<Room> result = roomService.researchAll();

        // Assert
        assertEquals(roomList, result);
    }

    @Test
    public void testResearchAllWithoutRoomsAvailable() {
        // Arrange
        List<Room> roomList = Collections.emptyList();
        when(roomRepository.findAll()).thenReturn(roomList);

        // Act and Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            roomService.researchAll();
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("There are Rooms available now.", exception.getReason());
    }

    @Test
    public void testResearchByIdWithRoomAvailable() {
        // Arrange
        int roomId = 1;
        Room room = new Room(roomId, "Basic", 300.00);
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        // Act
        Optional<Room> result = roomService.researchById(roomId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(room, result.get());
    }

    @Test
    public void testResearchByIdWithoutRoomAvailable() {
        // Arrange
        int roomId = 1;
        Optional<Room> room = Optional.empty();
        when(roomRepository.findById(roomId)).thenReturn(room);

        // Act and Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            roomService.researchById(roomId);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("The room: " + roomId + " don't is available.", exception.getReason());
    }
}
