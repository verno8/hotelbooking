package hotelbooking.domain;

import hotelbooking.domain.*;
import hotelbooking.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Bookingcanceled extends AbstractEvent {

    private Long id;
    private Integer hotelId;
    private String hotelName;
    private Date roomDt;
    private Integer roomQty;

    public Bookingcanceled(Hotel aggregate) {
        super(aggregate);
    }

    public Bookingcanceled() {
        super();
    }
}
//>>> DDD / Domain Event
