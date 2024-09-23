package hotelbooking.domain;

import hotelbooking.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class Booked extends AbstractEvent {

    private Long id;
    private Integer userId;
    private Integer bookId;
    private Integer hotelId;
    private String hotelName;
    private Date bookDt;
    private String bookStatus;
}
