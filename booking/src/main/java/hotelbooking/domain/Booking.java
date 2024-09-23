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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import java.util.concurrent.CompletableFuture;
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
    private String source;

    @Transient
    @Autowired
    private static PointService pointService;

    @Transient
    @Autowired
    private static HotelService hotelService; // HotelService 추가

    @PostPersist
    public void onPostPersist() {
        // // Point 차감 로직 (동기 호출)
        // hotelbooking.external.Point point = new hotelbooking.external.Point();
        // BookingApplication.applicationContext.getBean(hotelbooking.external.PointService.class)
        //     .decreasePoint(userId, 1.0f);


        // // Hotel 서비스에 예약 요청 (비동기 호출)
        // Bookingapproved reservation = new Bookingapproved();
        // reservation.setBookId(bookId); // bookId를 사용하도록 수정
        // reservation.setHotelId(hotelId);
        // reservation.setHotelName(hotelName);
        // reservation.setRoomDt(bookDt);
        // reservation.setRoomQty(1);  // 예약하려는 방 수량 설정
        // hotelService.checkAvailability(reservation);

        // 동기 호출: 포인트 차감
        pointService.decreasePoint(userId, 1.0f);

        // 예약 이벤트 발행
        Booked booked = new Booked(this);
        booked.publishAfterCommit();

        // 비동기 호출: 호텔 예약 확인
        checkHotelAvailabilityAsync();
    }

    @Async
    public CompletableFuture<Void> checkHotelAvailabilityAsync() {
        try {
            Bookingapproved reservation = new Bookingapproved();
            reservation.setBookId(bookId);
            reservation.setHotelId(hotelId);
            reservation.setHotelName(hotelName);
            reservation.setRoomDt(bookDt);
            reservation.setRoomQty(1);

            hotelService.checkAvailability(reservation);
            System.out.println("Hotel availability checked successfully.");
        } catch (Exception ex) {
            ex.printStackTrace(); // 예외 로그 처리
        }
        return CompletableFuture.completedFuture(null);
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

    // bookId의 Getter/Setter
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    // status의 Getter/Setter
    public String getStatus() {
        return bookStatus;
    }

    public void setStatus(String Status) {
        this.bookStatus = Status;
    }

}
