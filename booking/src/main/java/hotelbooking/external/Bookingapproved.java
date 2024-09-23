package hotelbooking.external;

import java.util.Date;
import lombok.Data;

@Data
public class Bookingapproved {
    private Integer bookId;
    private Integer hotelId;
    private String hotelName;
    private Date roomDt;
    private Integer roomQty;
    private String status;  // "Y" or "N"
    private String source;  // 메시지 발신자 정보 추가
}
