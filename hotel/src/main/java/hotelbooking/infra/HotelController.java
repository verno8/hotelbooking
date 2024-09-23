package hotelbooking.infra;

import hotelbooking.domain.Hotel;
import hotelbooking.domain.HotelRepository;
import hotelbooking.domain.Bookingapproved;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.beans.factory.annotation.Qualifier;


@RestController
public class HotelController {

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    @Qualifier("event-out") // event-out 채널 지정
    private MessageChannel output; // Kafka event-out 채널


    @PostMapping("/hotels/checkAvailability")
    public void checkAvailability(@RequestBody Bookingapproved reservation) {
        // 예약 가능 여부를 확인하고 로직 처리
        Hotel hotel = hotelRepository.findByHotelIdAndRoomDt(reservation.getHotelId(), reservation.getRoomDt());

        if (hotel != null && hotel.getRoomQty() > 0) {
            hotel.setRoomQty(hotel.getRoomQty() - 1);
            hotelRepository.save(hotel);
            System.out.println("LOG>> Room reserved successfully. Remaining rooms: " + hotel.getRoomQty());

            // Kafka로 성공 메시지 전송
            sendBookingStatus(reservation.getBookId(), "Y");


        } else {
            System.out.println("LOG>> No rooms available.");

            // Kafka로 실패 메시지 전송, 단 한번만 전송
            if (!"N".equals(reservation.getStatus())) {
                sendBookingStatus(reservation.getBookId(), "N");
            }
        }
    }

    // Kafka로 Booking 상태 메시지를 보내는 메서드
    private void sendBookingStatus(Integer bookId, String status) {
        try {
            Bookingapproved bookingStatus = new Bookingapproved();
            bookingStatus.setBookId(bookId);
            bookingStatus.setStatus(status);

            // Kafka로 메시지 전송
            output.send(MessageBuilder.withPayload(bookingStatus).build());
            System.out.println("LOG>> Booking status sent from HotelController: " + bookingStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
