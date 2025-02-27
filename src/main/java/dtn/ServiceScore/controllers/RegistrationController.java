package dtn.ServiceScore.controllers;

import dtn.ServiceScore.model.User;
import dtn.ServiceScore.services.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registrations")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    @PostMapping("/{eventId}")
    public ResponseEntity<?> getAllFiveGoodLcd(@Valid @PathVariable("eventId") Long eventId){
        try {
           User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
           Long userId = user.getId();

            return ResponseEntity.ok(registrationService.register_event(eventId,userId));
        } catch (Exception e ) {
            throw new RuntimeException(e);
        }

    }
}
