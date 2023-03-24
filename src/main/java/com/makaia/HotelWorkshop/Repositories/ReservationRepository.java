package com.makaia.HotelWorkshop.Repositories;

import com.makaia.HotelWorkshop.Modules.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
}
