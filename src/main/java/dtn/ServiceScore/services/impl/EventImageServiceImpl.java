package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.model.EventImage;
import dtn.ServiceScore.repositories.EventImageRepository;
import dtn.ServiceScore.services.EventImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class EventImageServiceImpl implements EventImageService {
    private final EventImageRepository eventImageRepository;
    @Override
    public List<EventImage> findByEventid(Long eventId) {
        return eventImageRepository.findByEventId(eventId);
    }
}
