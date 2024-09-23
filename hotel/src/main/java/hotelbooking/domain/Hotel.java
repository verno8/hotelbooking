package hotelbooking.domain;

import hotelbooking.HotelApplication;
import hotelbooking.domain.Bookingapproved;
import hotelbooking.domain.Bookingcanceled;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Hotel_table")
@Data
//<<< DDD / Aggregate Root
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer hotelId;

    private String hotelName;

    private Date roomDt;

    private String roomStatus;

    @PostPersist
    public void onPostPersist() {
        Bookingapproved bookingapproved = new Bookingapproved(this);
        bookingapproved.publishAfterCommit();

        Bookingcanceled bookingcanceled = new Bookingcanceled(this);
        bookingcanceled.publishAfterCommit();
    }

    public static HotelRepository repository() {
        HotelRepository hotelRepository = HotelApplication.applicationContext.getBean(
            HotelRepository.class
        );
        return hotelRepository;
    }

    //<<< Clean Arch / Port Method
    public static void approvedBooking(Pointdecreased pointdecreased) {
        //implement business logic here:

        /** Example 1:  new item 
        Hotel hotel = new Hotel();
        repository().save(hotel);

        Bookingapproved bookingapproved = new Bookingapproved(hotel);
        bookingapproved.publishAfterCommit();
        Bookingapproved bookingapproved = new Bookingapproved(hotel);
        bookingapproved.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(pointdecreased.get???()).ifPresent(hotel->{
            
            hotel // do something
            repository().save(hotel);

            Bookingapproved bookingapproved = new Bookingapproved(hotel);
            bookingapproved.publishAfterCommit();
            Bookingapproved bookingapproved = new Bookingapproved(hotel);
            bookingapproved.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void canceledBooking(Pointincreased pointincreased) {
        //implement business logic here:

        /** Example 1:  new item 
        Hotel hotel = new Hotel();
        repository().save(hotel);

        Bookingcanceled bookingcanceled = new Bookingcanceled(hotel);
        bookingcanceled.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(pointincreased.get???()).ifPresent(hotel->{
            
            hotel // do something
            repository().save(hotel);

            Bookingcanceled bookingcanceled = new Bookingcanceled(hotel);
            bookingcanceled.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
