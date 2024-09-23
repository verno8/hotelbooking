package hotelbooking.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import hotelbooking.config.kafka.KafkaProcessor;
import hotelbooking.domain.*;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    HotelRepository hotelRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='Pointdecreased'"
    )
    public void wheneverPointdecreased_ApprovedBooking(
        @Payload Pointdecreased pointdecreased
    ) {
        Pointdecreased event = pointdecreased;
        System.out.println(
            "\n\n##### listener ApprovedBooking : " + pointdecreased + "\n\n"
        );

        // Sample Logic //
        Hotel.approvedBooking(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='Pointincreased'"
    )
    public void wheneverPointincreased_CanceledBooking(
        @Payload Pointincreased pointincreased
    ) {
        Pointincreased event = pointincreased;
        System.out.println(
            "\n\n##### listener CanceledBooking : " + pointincreased + "\n\n"
        );

        // Sample Logic //
        Hotel.canceledBooking(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
