package hotelbooking.domain;

import hotelbooking.BookingApplication;
import hotelbooking.domain.Booked;
import hotelbooking.domain.Canceled;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Booking_table")
@Data
//<<< DDD / Aggregate Root
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer userId;

    private Integer bookId;

    private Integer hotelId;

    private String hotelName;

    private Date bookDt;

    private String bookStatus;

    @PostPersist
    public void onPostPersist() {
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        hotelbooking.external.Point point = new hotelbooking.external.Point();
        // mappings goes here
        BookingApplication.applicationContext
            .getBean(hotelbooking.external.PointService.class)
            .decreasePoint(point);

        Booked booked = new Booked(this);
        booked.publishAfterCommit();

        Canceled canceled = new Canceled(this);
        canceled.publishAfterCommit();
    }

    public static BookingRepository repository() {
        BookingRepository bookingRepository = BookingApplication.applicationContext.getBean(
            BookingRepository.class
        );
        return bookingRepository;
    }

    public void book() {
        //implement business logic here:

        Booked booked = new Booked(this);
        booked.publishAfterCommit();
    }

    public void cancel() {
        //implement business logic here:

        Canceled canceled = new Canceled(this);
        canceled.publishAfterCommit();
    }
}
//>>> DDD / Aggregate Root
