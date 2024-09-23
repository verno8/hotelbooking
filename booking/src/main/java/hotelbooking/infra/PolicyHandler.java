package hotelbooking.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import hotelbooking.config.kafka.KafkaProcessor;
import hotelbooking.domain.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import hotelbooking.external.Bookingapproved;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    BookingRepository bookingRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void handleBookingResponse(@Payload String eventString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Bookingapproved bookingResponse = objectMapper.readValue(eventString, Bookingapproved.class);

            // bookId로 예약을 조회
            Booking booking = bookingRepository.findById(bookingResponse.getBookId()).orElse(null);
            
            if (booking != null) {
                // 응답에 따라 예약 상태 업데이트
                booking.setStatus(bookingResponse.getStatus());
                bookingRepository.save(booking);
                System.out.println("Booking status updated for bookId: " + bookingResponse.getBookId() + " with status: " + bookingResponse.getStatus());
            } else {
                System.out.println("Booking not found with bookId: " + bookingResponse.getBookId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//>>> Clean Arch / Inbound Adaptor
