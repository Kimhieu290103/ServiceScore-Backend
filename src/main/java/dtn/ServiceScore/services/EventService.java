package dtn.ServiceScore.services;

import dtn.ServiceScore.dtos.EventDTO;
import dtn.ServiceScore.dtos.UserDTO;
import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.responses.LoginRespone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface EventService {
    Event createEvent(EventDTO eventDTO) throws Exception;
    Event getEventById(long id) throws Exception;

    Page<Event> getAllEvents (PageRequest pageRequest);

}
