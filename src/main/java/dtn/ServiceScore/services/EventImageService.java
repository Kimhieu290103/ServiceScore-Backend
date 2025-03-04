package dtn.ServiceScore.services;

import dtn.ServiceScore.model.EventImage;

import java.util.List;

public interface EventImageService {
    List<EventImage> findByEventId(Long eventId);
}
