package hotelbooking.domain;

import hotelbooking.domain.*;
import hotelbooking.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;
import lombok.Data;

//<<< DDD / Domain Event
@Data
@ToString
public class Bookingapproved extends AbstractEvent {

    private Long id;
    private Integer bookId;
    private Integer hotelId;
    private String hotelName;
    private Date roomDt;
    private Integer roomQty;
    private String status;  // "Y" or "N"
    private String source;  // "Y" or "N"

    public Bookingapproved(Hotel aggregate) {
        super(aggregate);
    }

    public Bookingapproved() {
        super();
    }
}
//>>> DDD / Domain Event
