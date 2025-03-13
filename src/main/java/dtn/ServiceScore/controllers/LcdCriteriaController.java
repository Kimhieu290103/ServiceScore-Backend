package dtn.ServiceScore.controllers;

import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.responses.MessageResponse;
import dtn.ServiceScore.services.EventService;
import dtn.ServiceScore.services.LcdCriteriaService;
import dtn.ServiceScore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lcd_criteria")
@RequiredArgsConstructor
public class LcdCriteriaController {
    private final LcdCriteriaService lcdCriteriaService;
    private final UserService userService;
    private final EventService eventService;

    @GetMapping("/completed")
    public ResponseEntity<List<User>> getLcdsCompletedAllCriteria(@RequestParam Long semesterId) {
        List<User> lcds = lcdCriteriaService.getLcdsCompletedAllCriteria(semesterId);
        return ResponseEntity.ok(lcds);
    }

//    @PostMapping("/confirm/{eventId}")
//    public ResponseEntity<?> confirmCompletedEvent(@PathVariable("eventId") Long eventId) {
//        try {
//
//            Event event = eventService.getEventById(eventId);
//            User user = userService.getUserById(event.getUser().getId());
//            if (user == null || event == null) {
//                return ResponseEntity.badRequest().body(new MessageResponse("User hoặc Event không tồn tại!"));
//            }
//
//            String result = lcdCriteriaService.Event_validation(user, event);
//            return ResponseEntity.ok(result);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Lỗi khi lấy Event: " + e.getMessage());
//        }
//    }
}