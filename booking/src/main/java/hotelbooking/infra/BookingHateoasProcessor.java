package hotelbooking.infra;

import hotelbooking.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class BookingHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Booking>> {

    @Override
    public EntityModel<Booking> process(EntityModel<Booking> model) {
        return model;
    }
}
