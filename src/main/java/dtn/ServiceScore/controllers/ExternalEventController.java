package dtn.ServiceScore.controllers;

import dtn.ServiceScore.dtos.ExternalEventDTO;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.responses.ExternalEventResponse;
import dtn.ServiceScore.services.ExternalEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/external-events")
@RequiredArgsConstructor
public class ExternalEventController {
    private final ExternalEventService externalEventService;

    @PostMapping
    public ResponseEntity<ExternalEventResponse> createExternalEvent(@RequestBody ExternalEventDTO externalEventDTO) {
        ExternalEventResponse response = externalEventService.createExternalEvent(externalEventDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ExternalEventResponse>> getPendingEvents() {
        return ResponseEntity.ok(externalEventService.getPendingEvents());
    }

    @GetMapping("/my-events")
    public ResponseEntity<List<ExternalEventResponse>> getUserEvents() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = user.getId();
        return ResponseEntity.ok(externalEventService.getUserEvents(userId));
    }
}
