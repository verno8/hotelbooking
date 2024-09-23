package hotelbooking.external;

import java.util.Date;
import lombok.Data;

@Data
public class Point {

    private Long id;
    private Integer userId;
    private Float userpoint;
    private Date updateDt;
}
