package hotelbooking.domain;

import hotelbooking.domain.*;
import hotelbooking.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Bookingapproved extends AbstractEvent {

    private Long id;
    private Integer hotelId;
    private String hotelName;
    private Date roomDt;
    private Integer roomQty;

    public Bookingapproved(Hotel aggregate) {
        super(aggregate);
    }

    public Bookingapproved() {
        super();
    }
}
//>>> DDD / Domain Event
