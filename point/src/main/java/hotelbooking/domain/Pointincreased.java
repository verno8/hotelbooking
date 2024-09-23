package hotelbooking.domain;

import hotelbooking.domain.*;
import hotelbooking.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Pointincreased extends AbstractEvent {

    private Long id;
    private Integer userId;
    private Float userpoint;
    private Date updateDt;

    public Pointincreased(Point aggregate) {
        super(aggregate);
    }

    public Pointincreased() {
        super();
    }
}
//>>> DDD / Domain Event
