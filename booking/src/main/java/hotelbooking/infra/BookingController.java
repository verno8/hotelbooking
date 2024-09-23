package hotelbooking.infra;

import hotelbooking.domain.*;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/bookings")
@Transactional
public class BookingController {

    @Autowired
    BookingRepository bookingRepository;

    // POST /bookings로 들어오는 요청 처리
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking bookingRequest) {
        // 저장할 엔티티 생성
        Booking booking = new Booking();
        booking.setUserId(bookingRequest.getUserId());
        booking.setBookId(bookingRequest.getBookId());
        booking.setHotelId(bookingRequest.getHotelId());
        booking.setHotelName(bookingRequest.getHotelName());
        booking.setBookDt(bookingRequest.getBookDt());
        booking.setBookStatus(bookingRequest.getBookStatus());

        // 데이터베이스에 저장
        Booking savedBooking = bookingRepository.save(booking);

        // 생성된 엔티티를 응답으로 반환
        return ResponseEntity.ok(savedBooking);
    }
}
//>>> Clean Arch / Inbound Adaptor
