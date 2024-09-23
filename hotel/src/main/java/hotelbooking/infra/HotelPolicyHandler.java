package hotelbooking.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import hotelbooking.config.kafka.KafkaProcessor;
import hotelbooking.domain.Hotel;
import hotelbooking.domain.HotelRepository;
import hotelbooking.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
@Transactional
@EnableBinding(KafkaProcessor.class) // KafkaProcessor 인터페이스를 사용하여 메시지 채널을 바인딩
public class HotelPolicyHandler {

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    @Qualifier("event-out") // 정확한 Bean을 지정
    private MessageChannel output; // 메시지 전송을 위한 채널

    @StreamListener(KafkaProcessor.INPUT) // Kafka에서 들어오는 메시지를 처리하는 리스너
    public void wheneverBooked_CheckAvailability(@Payload String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Bookingapproved reservation = objectMapper.readValue(message, Bookingapproved.class);

            // // 발신자 정보가 "hotel-service"인 경우 무시
            // if ("hotel-service".equals(reservation.getSource())) {
            //     System.out.println("LOG>> Ignoring self-sent message for bookId: " + reservation.getBookId());
            //     return;
            // }
            
            // hotelId 및 reservationDate에 따른 방 예약 가능 여부 확인
            Hotel hotel = hotelRepository.findByHotelIdAndRoomDt(reservation.getHotelId(), reservation.getRoomDt());

            if (hotel != null) {
                if (hotel.getRoomQty() > 0) {
                    hotel.setRoomQty(hotel.getRoomQty() - 1); // 객실 1개 감소
                    hotelRepository.save(hotel);
                    // sendSuccessResponse(reservation.getBookId()); // bookId 전달
                } else {
                    // sendFailureResponse(reservation.getBookId()); // bookId 전달
                }
            } else {
                // sendFailureResponse(reservation.getBookId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendSuccessResponse(Integer bookId) {
        try {
            // 성공 응답 메시지 생성
            Bookingapproved approvedMessage = new Bookingapproved();
            approvedMessage.setBookId(bookId);
            approvedMessage.setStatus("Y"); // 예약 성공
            approvedMessage.setSource("hotel-service"); // 발신자 정보 추가
    
            // Kafka로 메시지 전송
            output.send(MessageBuilder.withPayload(approvedMessage).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void sendFailureResponse(Integer bookId) {
        try {
            // 실패 응답 메시지 생성
            Bookingapproved failedMessage = new Bookingapproved();
            failedMessage.setBookId(bookId);
            failedMessage.setStatus("N"); // 예약 실패
            failedMessage.setSource("hotel-service"); // 발신자 정보 추가
    
            // Kafka로 메시지 전송
            output.send(MessageBuilder.withPayload(failedMessage).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
