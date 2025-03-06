package dtn.ServiceScore.controllers;

import dtn.ServiceScore.model.User;
import dtn.ServiceScore.responses.EventRespone;
import dtn.ServiceScore.responses.UserResponse;
import dtn.ServiceScore.services.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/registrations")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/{eventId}")
    public ResponseEntity<?> getAllFiveGoodLcd(@Valid @PathVariable("eventId") Long eventId) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = user.getId();

            return ResponseEntity.ok(registrationService.register_event(eventId, userId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/checkin/{registrationId}")
    public ResponseEntity<?> checkInEvent(@PathVariable Long registrationId) {
        try {
            registrationService.checkInEvent(registrationId);
            return ResponseEntity.ok("Check in success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<UserResponse>> getUsersByEvent(@PathVariable Long eventId) {
        List<UserResponse> users = registrationService.getAllStudentByEvent(eventId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/getevents")
    public ResponseEntity<List<EventRespone>> getEventsByUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = user.getId();
        List<EventRespone> events = registrationService.getAllEventByStudent(userId);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/event/checked/{eventId}")
    public ResponseEntity<List<UserResponse>> getCheckedInStudentsByEvent(@PathVariable Long eventId) {
        List<UserResponse> users = registrationService.getCheckedInStudentsByEvent(eventId);
        return ResponseEntity.ok(users);
    }
}
