package hotelbooking.infra;

import hotelbooking.domain.Booking;
import hotelbooking.domain.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/bookings")  // 컨트롤러가 "/bookings" 경로를 처리하도록 설정
@Transactional
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    // POST 요청을 처리하는 메서드
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking bookingRequest) {
        // 예약 정보를 데이터베이스에 저장
        Booking savedBooking = bookingRepository.save(bookingRequest);

        // 저장된 예약 정보 반환
        return ResponseEntity.ok(savedBooking);
    }
}
