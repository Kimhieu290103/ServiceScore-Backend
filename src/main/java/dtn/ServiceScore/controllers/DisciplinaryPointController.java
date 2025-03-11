package dtn.ServiceScore.controllers;

import dtn.ServiceScore.model.DisciplinaryPoint;
import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.ExternalEvent;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.responses.PointResponse;
import dtn.ServiceScore.services.DisciplinaryPointService;
import dtn.ServiceScore.services.EventService;
import dtn.ServiceScore.services.ExternalEventService;
import dtn.ServiceScore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/points")
@RequiredArgsConstructor
public class DisciplinaryPointController {
    private final DisciplinaryPointService disciplinaryPointService;

    private final UserService userService;

    private final EventService eventService;

    private final ExternalEventService externalEventService;

    // điêmr danh sự kiện trong trường
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

    // Điêmr danh các sinh viên được chợn
    @PostMapping("/batch/{eventId}")
    public ResponseEntity<?> addPointsForMultipleUsers(@PathVariable Long eventId, @RequestBody List<Long> userIds) {
        try {
            Event event = eventService.getEventById(eventId);
            List<DisciplinaryPoint> disciplinaryPoints = new ArrayList<>();

            for (Long userId : userIds) {
                User user = userService.getUserById(userId);
                DisciplinaryPoint disciplinaryPoint = disciplinaryPointService.Addpoint(user, event);
                disciplinaryPoints.add(disciplinaryPoint);
            }

            return ResponseEntity.ok(disciplinaryPoints);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi hệ thống: " + e.getMessage());
        }
    }
    // lấy số điêmr của bản thân
    @GetMapping("/by-user")
    public ResponseEntity<?> getDisciplinaryPointsByUserId() {
        Map<String, Object> result = disciplinaryPointService.getDisciplinaryPointsWithTotal();
        return ResponseEntity.ok(result);
    }

    // Xét duyệt một ExternalEvent (Chuyển thành APPROVED và cộng điểm)
    @PutMapping("/{eventId}/approve")
    public ResponseEntity<String> approveExternalEvent(@PathVariable Long eventId) {
        // Tìm ExternalEvent theo ID
        ExternalEvent externalEvent = externalEventService.findById(eventId);
        if (externalEvent == null) {
            return ResponseEntity.badRequest().body("External Event not found");
        }

        // Tìm User từ ExternalEvent
        User user = externalEvent.getUser();
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found for this event");
        }

        // Gọi hàm cộng điểm và cập nhật trạng thái
        disciplinaryPointService.AddPointForExternalEvent(user, externalEvent);

        return ResponseEntity.ok("External Event approved successfully and points added!");
    }

    @PutMapping("/{eventId}/reject")
    public ResponseEntity<String> rejectExternalEvent(@PathVariable Long eventId) {
        String result = disciplinaryPointService.rejectExternalEvent(eventId);
        if (result.equals("External Event not found")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
