package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.dtos.EventDTO;
import dtn.ServiceScore.exceptions.DataNotFoundException;
import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.Lcd;
import dtn.ServiceScore.model.Semester;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.repositories.EventRepository;

import dtn.ServiceScore.repositories.LcdRepository;
import dtn.ServiceScore.repositories.SemesterRepository;
import dtn.ServiceScore.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final SemesterRepository semesterRepository;
    private final LcdRepository lcdRepository;
    @Override
    public Event createEvent(EventDTO eventDTO) throws Exception {
        String eventName = eventDTO.getName();
        if(eventRepository.existsByName(eventName)){
            throw  new DataIntegrityViolationException("event name existed");
        }
        Event newEvent = Event.builder()
                .name(eventDTO.getName())
                .description(eventDTO.getDescription())
                .date(eventDTO.getDate())
                .score(eventDTO.getScore())
                .maxRegistrations(eventDTO.getMaxRegistrations())
                .eventType(eventDTO.getEventType())
                .build();
        newEvent.setCurrentRegistrations(0L);
        Semester semester = semesterRepository.findByName(eventDTO.getSemester()).
            orElseThrow(() -> new DataNotFoundException("Không tìm thấy học kì phù hợp"));
        Lcd lcd = lcdRepository.findByName(eventDTO.getOrganizingCommittee()).
                orElseThrow(() -> new DataNotFoundException("Không tìm tổ chức phù hợp"));

        newEvent.setSemester(semester);
        newEvent.setOrganizingCommittee(lcd);
        return eventRepository.save(newEvent);
    }

    @Override
    public Event getEventById(long id) throws Exception {
        return eventRepository.findById(id).orElseThrow(() -> new DataNotFoundException("khong tim thay"));
    }

    @Override
    public Page<Event> getAllEvents(PageRequest pageRequest) {
        return eventRepository.findAll(pageRequest).map(event -> {
            return Event.builder()
                    .id(event.getId())
                    .name(event.getName())
                    .description(event.getDescription())
                    .date(event.getDate())
                    .semester(event.getSemester())
                    .organizingCommittee(event.getOrganizingCommittee())
                    .score(event.getScore())
                    .maxRegistrations(event.getMaxRegistrations())
                    .currentRegistrations(event.getCurrentRegistrations())
                    .eventType(event.getEventType())
                    .build();
        });
    }
}
