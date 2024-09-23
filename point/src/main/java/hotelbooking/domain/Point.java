package hotelbooking.domain;

import hotelbooking.PointApplication;
import hotelbooking.domain.Pointdecreased;
import hotelbooking.domain.Pointincreased;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "point")
@Data
//<<< DDD / Aggregate Root
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer userId;

    private Float userpoint;

    private Date updateDt;

    @PostPersist
    public void onPostPersist() {
        Pointdecreased pointdecreased = new Pointdecreased(this);
        pointdecreased.publishAfterCommit();

        Pointincreased pointincreased = new Pointincreased(this);
        pointincreased.publishAfterCommit();
    }

    public static PointRepository repository() {
        PointRepository pointRepository = PointApplication.applicationContext.getBean(
            PointRepository.class
        );
        return pointRepository;
    }

    public void decreasePoint() {
        //implement business logic here:

        Pointdecreased pointdecreased = new Pointdecreased(this);
        pointdecreased.publishAfterCommit();
    }

    //<<< Clean Arch / Port Method
    public static void increasePoint(Canceled canceled) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        Pointdecreased pointdecreased = new Pointdecreased(point);
        pointdecreased.publishAfterCommit();
        Pointincreased pointincreased = new Pointincreased(point);
        pointincreased.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(canceled.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);

            Pointdecreased pointdecreased = new Pointdecreased(point);
            pointdecreased.publishAfterCommit();
            Pointincreased pointincreased = new Pointincreased(point);
            pointincreased.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
