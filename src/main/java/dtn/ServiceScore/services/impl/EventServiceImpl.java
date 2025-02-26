package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.dtos.EventDTO;
import dtn.ServiceScore.exceptions.DataNotFoundException;
import dtn.ServiceScore.exceptions.InvalidParamException;
import dtn.ServiceScore.model.*;
import dtn.ServiceScore.repositories.EventImageRepository;
import dtn.ServiceScore.repositories.EventRepository;

import dtn.ServiceScore.repositories.LcdRepository;
import dtn.ServiceScore.repositories.SemesterRepository;
import dtn.ServiceScore.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.swing.plaf.DesktopIconUI;
import java.beans.DesignMode;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final SemesterRepository semesterRepository;
    private final LcdRepository lcdRepository;
    private  final EventImageRepository eventImageRepository;
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
                .endDate(eventDTO.getEndDate())
                .registrationStartDate(eventDTO.getRegistrationStartDate())
                .registrationEndDate(eventDTO.getRegistrationEndDate())
                .status(eventDTO.getStatus())
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
    public void deleteEvent(long id) throws Exception {
        Event existingEvent = getEventById(id);
        if (existingEvent != null) {
            eventRepository.delete(existingEvent);
        }
    }

    @Override
    public Page<Event> getAllEvents(PageRequest pageRequest) {
        return eventRepository.findAll(pageRequest).map(event -> {
            Event event1= Event.builder()
                    .id(event.getId())
                    .name(event.getName())
                    .description(event.getDescription())
                    .date(event.getDate())
                    .endDate(event.getEndDate())
                    .registrationStartDate(event.getRegistrationStartDate())
                    .registrationEndDate(event.getRegistrationEndDate())
                    .status(event.getStatus())
                    .semester(event.getSemester())
                    .organizingCommittee(event.getOrganizingCommittee())
                    .score(event.getScore())
                    .maxRegistrations(event.getMaxRegistrations())
                    .currentRegistrations(event.getCurrentRegistrations())
                    .eventType(event.getEventType())
                    .build();

            return event1;
        });
    }

    @Override
    public EventImage createEventImage(Long eventId, EventImage eventImage) throws Exception {
        Event existingEvent = getEventById(eventId);
        EventImage newEventImage = EventImage.builder()
                .event(existingEvent)
                .imageUrl(eventImage.getImageUrl())
                .build();

        // khong cho insert qua 5 anh cho 1 san pham
        int size = eventImageRepository.findByEventId(eventId).size();
        if (size > 5) {
            throw new InvalidParamException("so anh cua san pham lon hon 5");
        }
        return eventImageRepository.save(newEventImage);

    }

    @Override
    public Event updateEvent(Long id, EventDTO eventDTO) throws Exception {
        Event existingEvent = getEventById(id);
        if (existingEvent != null) {
            Semester semester = semesterRepository.findByName(eventDTO.getSemester()).
                    orElseThrow(() -> new DataNotFoundException("Không tìm thấy học kì phù hợp"));
            Lcd lcd = lcdRepository.findByName(eventDTO.getOrganizingCommittee()).
                    orElseThrow(() -> new DataNotFoundException("Không tìm tổ chức phù hợp"));

            existingEvent.setSemester(semester);
            existingEvent.setOrganizingCommittee(lcd);
            existingEvent.setName(eventDTO.getName());
            existingEvent.setDescription(eventDTO.getDescription());
            existingEvent.setDate(eventDTO.getDate());
            existingEvent.setEndDate(eventDTO.getEndDate());
            existingEvent.setCurrentRegistrations(eventDTO.getCurrentRegistrations());
            existingEvent.setRegistrationEndDate(eventDTO.getRegistrationEndDate());
            existingEvent.setStatus(eventDTO.getStatus());
            existingEvent.setScore(eventDTO.getScore());
            existingEvent.setMaxRegistrations(eventDTO.getMaxRegistrations());
            existingEvent.setEventType(eventDTO.getEventType());
            return eventRepository.save(existingEvent);

        }

        return null;
    }
}
