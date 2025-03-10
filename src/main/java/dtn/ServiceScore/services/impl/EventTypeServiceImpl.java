package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.model.EventType;
import dtn.ServiceScore.repositories.EventTypeRepository;
import dtn.ServiceScore.services.EventTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class EventTypeServiceImpl implements EventTypeService{
    private  final EventTypeRepository eventTypeRepository;
    @Override
    public List<EventType> getAllEventTypes() {
        return eventTypeRepository.findAll();
    }
}
