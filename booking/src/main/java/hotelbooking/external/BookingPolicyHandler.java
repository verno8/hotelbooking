package hotelbooking.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import hotelbooking.domain.Booking;
import hotelbooking.domain.BookingRepository;
import hotelbooking.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
@EnableBinding(KafkaProcessor.class) // KafkaProcessor 인터페이스를 사용하여 메시지 채널을 바인딩
public class BookingPolicyHandler {

    @Autowired
    BookingRepository bookingRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookingStatusUpdate(@Payload String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Bookingapproved reservation = objectMapper.readValue(message, Bookingapproved.class);

            // BookId로 Booking 엔티티를 조회
            Booking booking = bookingRepository.findByBookId(reservation.getBookId());
            
            if (booking != null) {
                // Hotel 서비스에서 온 메시지에 따라 bookStatus 업데이트
                booking.setBookStatus(reservation.getStatus().equals("Y") ? "ㅛ" : "FAILED");
                bookingRepository.save(booking);
                
                System.out.println("Booking status updated for BookId: " + reservation.getBookId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
