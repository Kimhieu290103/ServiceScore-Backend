package dtn.ServiceScore.services;

import dtn.ServiceScore.dtos.EventDTO;
import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.EventImage;
import dtn.ServiceScore.responses.EventCriteriaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface EventService {
    Event createEvent(EventDTO eventDTO) throws Exception;

    Event getEventById(long id) throws Exception;

    void deleteEvent(long id) throws Exception;

    Page<Event> getAllEvents(PageRequest pageRequest);

    Page<Event> getEventsByEventType(Long eventTypeId, Pageable pageable);

    EventImage createEventImage(Long eventId, EventImage eventImage) throws Exception;

    Event updateEvent(Long id, EventDTO eventDTO) throws Exception;

    EventCriteriaResponse getFilteredEventCriteria(Long eventId);

    List<Event> getEventByUser();


}
