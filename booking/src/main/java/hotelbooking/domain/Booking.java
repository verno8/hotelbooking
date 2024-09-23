package hotelbooking.domain;

import hotelbooking.BookingApplication;
import hotelbooking.domain.Booked;
import hotelbooking.domain.Canceled;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import hotelbooking.external.PointService;
import hotelbooking.external.HotelService;
import hotelbooking.external.Bookingapproved;
import lombok.*;

@Entity
@Table(name = "booking")
@Data
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

    @Transient
    private static PointService pointService;

    @Transient
    private static HotelService hotelService; // HotelService 추가

    @PostPersist
    public void onPostPersist() {
        // Point 차감 로직 (동기 호출)
        hotelbooking.external.Point point = new hotelbooking.external.Point();
        BookingApplication.applicationContext.getBean(hotelbooking.external.PointService.class)
            .decreasePoint(userId, 1.0f);

        // 예약 이벤트 발행
        Booked booked = new Booked(this);
        booked.publishAfterCommit();

        // Hotel 서비스에 예약 요청 (비동기 호출)
        Bookingapproved reservation = new Bookingapproved();
        reservation.setBookId(bookId); // bookId를 사용하도록 수정
        reservation.setHotelId(hotelId);
        reservation.setHotelName(hotelName);
        reservation.setRoomDt(bookDt);
        reservation.setRoomQty(1);  // 예약하려는 방 수량 설정
        hotelService.checkAvailability(reservation);
    }

    public static BookingRepository repository() {
        BookingRepository bookingRepository = BookingApplication.applicationContext.getBean(
            BookingRepository.class
        );
        return bookingRepository;
    }

    public void book() {
        // implement business logic here:

        Booked booked = new Booked(this);
        booked.publishAfterCommit();
    }

    public void cancel() {
        // implement business logic here:

        Canceled canceled = new Canceled(this);
        canceled.publishAfterCommit();
    }

    public static void setPointService(PointService pointService) {
        Booking.pointService = pointService;
    }

    public static void setHotelService(HotelService hotelService) {
        Booking.hotelService = hotelService;
    }
}
