package hotelbooking.domain;

import hotelbooking.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class Pointincreased extends AbstractEvent {

    private Long id;
    private Integer userId;
    private Float userpoint;
    private Date updateDt;
}
