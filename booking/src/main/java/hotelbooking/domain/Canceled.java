package hotelbooking.domain;

import hotelbooking.domain.*;
import hotelbooking.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Canceled extends AbstractEvent {

    private Long id;
    private Integer userId;
    private Integer bookId;
    private Integer hotelId;
    private String hotelName;
    private Date bookDt;
    private String bookStatus;

    public Canceled(Booking aggregate) {
        super(aggregate);
    }

    public Canceled() {
        super();
    }
}
//>>> DDD / Domain Event
