// package hotelbooking.domain;

// import hotelbooking.domain.*;
// import org.springframework.data.repository.PagingAndSortingRepository;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// //<<< PoEAA / Repository
// @RepositoryRestResource(collectionResourceRel = "bookings", path = "bookings")
// public interface BookingRepository
//     extends PagingAndSortingRepository<Booking, Long> {}

package hotelbooking.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Booking findByBookId(Integer bookId);
}

