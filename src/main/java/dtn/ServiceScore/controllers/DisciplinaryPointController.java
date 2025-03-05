package dtn.ServiceScore.controllers;

import dtn.ServiceScore.model.DisciplinaryPoint;
import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.services.DisciplinaryPointService;
import dtn.ServiceScore.services.EventService;
import dtn.ServiceScore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/points")
@RequiredArgsConstructor
public class DisciplinaryPointController {
    private final DisciplinaryPointService disciplinaryPointService;

    private final UserService userService;

    private final EventService eventService;

    @PostMapping("/{userId}/{eventId}")
    public ResponseEntity<?> addPoint(@PathVariable Long userId, @PathVariable Long eventId) {
        try {
            User user = userService.getUserById(userId);
            Event event = eventService.getEventById(eventId);
            DisciplinaryPoint disciplinaryPoint = disciplinaryPointService.Addpoint(user, event);
            return ResponseEntity.ok(disciplinaryPoint);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi hệ thống: " + e.getMessage());
        }
    }
}
