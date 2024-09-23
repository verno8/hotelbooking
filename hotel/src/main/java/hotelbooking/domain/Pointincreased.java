package hotelbooking.domain;

import hotelbooking.domain.*;
import hotelbooking.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class Pointincreased extends AbstractEvent {

    private Long id;
    private Integer userId;
    private Float userpoint;
    private Date updateDt;
}
