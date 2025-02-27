package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.dtos.EventDTO;
import dtn.ServiceScore.exceptions.DataNotFoundException;
import dtn.ServiceScore.exceptions.InvalidParamException;
import dtn.ServiceScore.model.*;
import dtn.ServiceScore.repositories.*;

import dtn.ServiceScore.responses.CriteriaResponse;
import dtn.ServiceScore.responses.EventCriteriaResponse;
import dtn.ServiceScore.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.SpringVersion;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.swing.plaf.DesktopIconUI;
import java.beans.DesignMode;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final SemesterRepository semesterRepository;
    private final LcdRepository lcdRepository;
    private  final EventImageRepository eventImageRepository;
    private final FiveGoodCriteriaRepository fiveGoodCriteriaRepository;
    private final EventCriteriaRepository eventCriteriaRepository;
    private final FiveGoodCriteriaLcdRepository fiveGoodCriteriaLcdRepository;
    private  final EventCriteriaLcdRepository eventCriteriaLcdRepository;
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
        Event event = eventRepository.save(newEvent);

        List<Long> criteriaIds = eventDTO.getFive_good_id();
        if (criteriaIds != null && !criteriaIds.isEmpty()) {
            List<FiveGoodCriteria> criteriaList = fiveGoodCriteriaRepository.findAllById(criteriaIds);
            List<EventCriteria> eventCriteriaList = criteriaList.stream()
                    .map(criteria -> EventCriteria.builder()
                            .event(event)
                            .criteria(criteria)
                            .build())
                    .collect(Collectors.toList());

            eventCriteriaRepository.saveAll(eventCriteriaList);
        }
        List<Long> criteriaLcd_Ids = eventDTO.getFive_good_lcd_id();
        if (criteriaLcd_Ids != null && !criteriaLcd_Ids.isEmpty()) {
            List<FiveGoodCriteriaLcd> criteriaLcd_List = fiveGoodCriteriaLcdRepository.findAllById(criteriaLcd_Ids);
            List<EventCriteriaLcd> eventCriteriaLcdList = criteriaLcd_List.stream()
                    .map(criteria1 -> EventCriteriaLcd.builder()
                            .event(event)
                            .criteria(criteria1)
                            .build())
                    .collect(Collectors.toList());

            eventCriteriaLcdRepository.saveAll(eventCriteriaLcdList);
        }

        return event;
    }

    @Override
    public Event getEventById(long id) throws Exception {
        return eventRepository.findById(id).orElseThrow(() -> new DataNotFoundException("khong tim thay"));
    }

    @Override
    public void deleteEvent(long id) throws Exception {
        Event existingEvent = getEventById(id);
        if (existingEvent != null) {
            // ✅ **Xóa các liên kết **
            eventCriteriaRepository.deleteByEventId(id);
            eventCriteriaLcdRepository.deleteByEventId(id);
            eventImageRepository.deleteByEventId(id);
            eventRepository.delete(existingEvent);
        }
    }

    @Override
    public Page<Event> getAllEvents(PageRequest pageRequest) {
        return eventRepository.findAll(pageRequest).map(event -> {
            return Event.builder()
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
            // ✅ **Xóa các liên kết cũ**
            eventCriteriaRepository.deleteByEventId(id);
            eventCriteriaLcdRepository.deleteByEventId(id);


            // ✅ **Thêm các liên kết mới**
            List<Long> criteriaIds = eventDTO.getFive_good_id();
            if (criteriaIds != null && !criteriaIds.isEmpty()) {
                List<FiveGoodCriteria> criteriaList = fiveGoodCriteriaRepository.findAllById(criteriaIds);
                List<EventCriteria> eventCriteriaList = criteriaList.stream()
                        .map(criteria -> EventCriteria.builder()
                                .event(existingEvent)
                                .criteria(criteria)
                                .build())
                        .collect(Collectors.toList());

                eventCriteriaRepository.saveAll(eventCriteriaList);
            }

            List<Long> criteriaLcdIds = eventDTO.getFive_good_lcd_id();
            if (criteriaLcdIds != null && !criteriaLcdIds.isEmpty()) {
                List<FiveGoodCriteriaLcd> criteriaLcdList = fiveGoodCriteriaLcdRepository.findAllById(criteriaLcdIds);
                List<EventCriteriaLcd> eventCriteriaLcdList = criteriaLcdList.stream()
                        .map(criteria -> EventCriteriaLcd.builder()
                                .event(existingEvent)
                                .criteria(criteria)
                                .build())
                        .collect(Collectors.toList());

                eventCriteriaLcdRepository.saveAll(eventCriteriaLcdList);
            }

            return eventRepository.save(existingEvent);

        }

        return null;
    }

    @Override
    public EventCriteriaResponse getFilteredEventCriteria(Long eventId) {
        List<EventCriteriaLcd> eventCriteriaLcdList = eventCriteriaLcdRepository.findByEventId(eventId);
        List<EventCriteria> eventCriteriaList = eventCriteriaRepository.findByEventId(eventId);

        EventCriteriaResponse response = new EventCriteriaResponse();

        // Chỉ lấy dữ liệu cần thiết từ eventCriteriaLcd
        for (EventCriteriaLcd ecl : eventCriteriaLcdList) {
            CriteriaResponse criteriaResponse = new CriteriaResponse(
                    ecl.getId(),
                    ecl.getCriteria().getName(),
                    ecl.getCriteria().getDescription()
            );
            response.getEventCriteriaLcd().add(criteriaResponse);
        }

        // Chỉ lấy dữ liệu cần thiết từ eventCriteria
        for (EventCriteria ec : eventCriteriaList) {
            CriteriaResponse criteriaResponse = new CriteriaResponse(
                    ec.getId(),
                    ec.getCriteria().getName(),
                    ec.getCriteria().getDescription()
            );
            response.getEventCriteria().add(criteriaResponse);
        }

        return response;
    }


}
