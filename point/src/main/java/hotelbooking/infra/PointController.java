package hotelbooking.infra;

import hotelbooking.domain.*;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/points")
public class PointController {

    @Autowired
    PointRepository pointRepository;

    @PostMapping("/{userId}/decrease")
    @Transactional
    public void decreasePoint(@PathVariable Long userId, @RequestParam Float points) {
        Optional<Point> pointOptional = pointRepository.findByUserId(userId);
        if (pointOptional.isPresent()) {
            Point point = pointOptional.get();
            point.setUserpoint(point.getUserpoint() - points);
            pointRepository.save(point);
        }
    }
}
//>>> Clean Arch / Inbound Adaptor
