package dtn.ServiceScore.services;

import dtn.ServiceScore.dtos.ExternalEventDTO;
import dtn.ServiceScore.model.ExternalEvent;
import dtn.ServiceScore.responses.ExternalEventResponse;

import java.util.List;

public interface ExternalEventService {
    ExternalEventResponse createExternalEvent(ExternalEventDTO dto);

    List<ExternalEventResponse> getPendingEvents();

    List<ExternalEventResponse> getUserEvents(Long userId);

    ExternalEvent findById(Long id);
}
