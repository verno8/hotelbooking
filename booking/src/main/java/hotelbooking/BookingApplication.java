package hotelbooking;

import hotelbooking.config.kafka.KafkaProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.ApplicationContext;
import hotelbooking.domain.Booking;
import hotelbooking.external.HotelService;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableBinding(KafkaProcessor.class)
@EnableFeignClients
@EnableAsync
public class BookingApplication {

    public static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(BookingApplication.class, args);
        Booking.setPointService(applicationContext.getBean(hotelbooking.external.PointService.class));
        Booking.setHotelService(applicationContext.getBean(HotelService.class)); // HotelService 연결

        System.out.println("LOG>> booking main");
    }
}
