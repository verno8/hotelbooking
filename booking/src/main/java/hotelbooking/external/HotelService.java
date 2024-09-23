package hotelbooking.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hotel", url = "${api.url.hotel}") // 실제 환경에 맞게 URL 변경 필요
public interface HotelService {
    @PostMapping("/hotels/checkAvailability")
    void checkAvailability(@RequestBody Bookingapproved reservation);
}
