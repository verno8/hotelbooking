// package: hotelbooking.infra
package hotelbooking.infra;

import hotelbooking.domain.Point;
import hotelbooking.domain.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/points")
public class PointController {

    @Autowired
    PointRepository pointRepository;

    @PostMapping("/{userId}/decrease")
    @Transactional
    public void decreasePoint(@PathVariable Integer userId, @RequestParam Float points) {
        Optional<Point> pointOptional = pointRepository.findByUserId(userId);
        if (pointOptional.isPresent()) {
            Point point = pointOptional.get();
            if (point.getUserpoint() >= points) {
                point.setUserpoint(point.getUserpoint() - points);
                pointRepository.save(point);
            } else {
                throw new IllegalStateException("Not enough points for user ID: " + userId);
            }
        } else {
            throw new IllegalStateException("User ID " + userId + " not found in the point table.");
        }
    }
}
