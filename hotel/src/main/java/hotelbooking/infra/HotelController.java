package hotelbooking.infra;

import hotelbooking.domain.Hotel;
import hotelbooking.domain.HotelRepository;
import hotelbooking.domain.Bookingapproved;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HotelController {

    @Autowired
    private HotelRepository hotelRepository;

    @PostMapping("/hotels/checkAvailability")
    public void checkAvailability(@RequestBody Bookingapproved reservation) {
        // 예약 가능 여부를 확인하고 로직 처리
        Hotel hotel = hotelRepository.findByHotelIdAndRoomDt(reservation.getHotelId(), reservation.getRoomDt());

        if (hotel != null && hotel.getRoomQty() > 0) {
            hotel.setRoomQty(hotel.getRoomQty() - 1);
            hotelRepository.save(hotel);
            System.out.println("Room reserved successfully. Remaining rooms: " + hotel.getRoomQty());
        } else {
            System.out.println("No rooms available.");
        }
    }
}
