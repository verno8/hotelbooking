package hotelbooking.external;

import java.util.Date;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "point", url = "${api.url.point}") // application.yml의 api.url.point 값을 사용
public interface PointService {

    @RequestMapping(method = RequestMethod.POST, value = "/points/{userId}/decrease", consumes = "application/json")
    void decreasePoint(@PathVariable("userId") Integer userId, @RequestParam("points") Float points);
}
