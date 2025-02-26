package dtn.ServiceScore.services;

import dtn.ServiceScore.model.EventImage;
import dtn.ServiceScore.responses.EventImageRespone;

import java.util.List;

public interface EventImageService {
    List<EventImage> findByEventid(Long eventId);
}
