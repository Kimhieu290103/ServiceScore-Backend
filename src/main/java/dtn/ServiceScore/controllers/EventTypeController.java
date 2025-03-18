package dtn.ServiceScore.controllers;

import dtn.ServiceScore.model.EventType;
import dtn.ServiceScore.services.EventTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event_types")
@RequiredArgsConstructor
public class EventTypeController {
    private final EventTypeService eventTypeService;

    @GetMapping
    public ResponseEntity<List<EventType>> getAllEventTypes() {
        return ResponseEntity.ok(eventTypeService.getAllEventTypes());
    }
}
