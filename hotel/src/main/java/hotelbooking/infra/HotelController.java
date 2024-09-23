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
        // 중복 처리를 방지하기 위해 이미 처리된 예약인지 확인
        Hotel hotel = hotelRepository.findByHotelIdAndRoomDt(reservation.getHotelId(), reservation.getRoomDt());

        // 처리 가능한 상태인지 확인 (예: 예약 가능 상태인지)
        if (hotel != null && hotel.getRoomQty() > 0) {
            hotel.setRoomQty(hotel.getRoomQty() - 1);
            hotelRepository.save(hotel);
            System.out.println("LOG>> Room reserved successfully. Remaining rooms: " + hotel.getRoomQty());

            // Kafka로 성공 메시지 전송 (예약 성공)
            sendBookingStatus(reservation.getBookId(), "Y");
        } else {
            System.out.println("LOG>> No rooms available.");

            // 이미 "N" 상태인 경우 메시지를 전송하지 않음
            if (!"N".equals(reservation.getStatus())) {
                sendBookingStatus(reservation.getBookId(), "N");
            }
        }
    }
        
    // Kafka로 Booking 상태 메시지를 보내는 메서드
    private void sendBookingStatus(Integer bookId, String status) {
        try {
            // 현재 예약 상태 확인
            Bookingapproved bookingStatus = new Bookingapproved();
            bookingStatus.setBookId(bookId);
            bookingStatus.setStatus(status);

            // 중복 전송을 방지하기 위해 필요한 경우 상태를 확인하는 로직 추가
            if ("N".equals(status) && alreadySentFailureMessage(bookId)) {
                return; // 이미 실패 메시지를 보냈다면 전송하지 않음
            }

            // Kafka로 메시지 전송
            output.send(MessageBuilder.withPayload(bookingStatus).build());
            System.out.println("LOG>> Booking status sent from HotelController: " + bookingStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 중복 실패 메시지 전송 방지를 위한 확인 메서드
    private boolean alreadySentFailureMessage(Integer bookId) {
        // 이미 해당 bookId에 대한 실패 메시지가 전송되었는지 확인하는 로직을 추가하세요.
        // 예: 데이터베이스 또는 캐시를 활용하여 실패 메시지 전송 여부를 추적할 수 있습니다.
        return false; // 기본값: 아직 전송되지 않음
    }
    
}
