package hotelbooking.domain;

import hotelbooking.BookingApplication;
import hotelbooking.domain.Booked;
import hotelbooking.domain.Canceled;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import hotelbooking.external.PointService;

@Entity
@Table(name = "booking")
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

    @Transient
    private static PointService pointService;

    @PostPersist
    public void onPostPersist() {
        // Following code causes dependency to external APIs
        // It is NOT A GOOD PRACTICE. Instead, Event-Policy mapping is recommended.

        // 포인트 1점 차감 - FeignClient를 통한 동기 호출
        if (pointService != null) {
            pointService.decreasePoint(this.userId, 1.0f);
        } else {
            System.out.println("PointService is not initialized.");
        }

        // 예약 이벤트 발행
        Booked booked = new Booked(this);
        booked.publishAfterCommit();

        // 예약 취소 이벤트 발행 (예약 시에 취소 이벤트를 발행하는 것은 의도된 로직인지 확인 필요)
        // 일반적으로 예약 시에는 '취소' 이벤트를 발행하지 않기 때문에 아래 코드는 제거할 수 있습니다.
        // Canceled canceled = new Canceled(this);
        // canceled.publishAfterCommit();
    }

    public static BookingRepository repository() {
        return BookingApplication.applicationContext.getBean(BookingRepository.class);
    }

    public void book() {
        // Implement business logic here
        Booked booked = new Booked(this);
        booked.publishAfterCommit();
    }

    public void cancel() {
        // Implement business logic here
        Canceled canceled = new Canceled(this);
        canceled.publishAfterCommit();
    }

    public static void setPointService(PointService pointService) {
        Booking.pointService = pointService;
    }
}
//>>> DDD / Aggregate Root
