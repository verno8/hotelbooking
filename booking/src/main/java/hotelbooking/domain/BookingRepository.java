package hotelbooking.domain;

import hotelbooking.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "bookings", path = "bookings")
public interface BookingRepository
    extends PagingAndSortingRepository<Booking, Long> {}
