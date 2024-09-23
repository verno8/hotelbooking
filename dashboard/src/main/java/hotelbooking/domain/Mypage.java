package hotelbooking.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "Mypage_table")
@Data
public class Mypage {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Integer userId;
    private Integer bookId;
    private Integer hotelId;
    private String hotelName;
    private Date bookDt;
    private String bookStaus;
    private Float userpoint;
}
