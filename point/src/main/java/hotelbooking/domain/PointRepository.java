// package hotelbooking.domain;

// import hotelbooking.domain.*;
// import org.springframework.data.repository.PagingAndSortingRepository;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// //<<< PoEAA / Repository
// @RepositoryRestResource(collectionResourceRel = "points", path = "points")
// public interface PointRepository
//     extends PagingAndSortingRepository<Point, Long> {}

// package: hotelbooking.domain
package hotelbooking.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface PointRepository extends CrudRepository<Point, Integer> {
    Optional<Point> findByUserId(Integer userId);
}
