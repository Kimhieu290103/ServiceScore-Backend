package dtn.ServiceScore.controllers;

import dtn.ServiceScore.model.*;
import dtn.ServiceScore.responses.MessageResponse;
import dtn.ServiceScore.responses.PointResponse;
import dtn.ServiceScore.responses.StudentPointResponse;
import dtn.ServiceScore.services.DisciplinaryPointService;
import dtn.ServiceScore.services.EventService;
import dtn.ServiceScore.services.ExternalEventService;
import dtn.ServiceScore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
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
            if (disciplinaryPoint == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("Đã được điểm danh rồi"));
            }
            return ResponseEntity.ok(new MessageResponse("Điểm danh thành công"));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Lỗi hệ thống: " + e.getMessage()));
        }
    }
    // điểm danh tất cả sinh viên
    @PostMapping("/batchAll/{eventId}")
    public ResponseEntity<?> addPointsForAllRegisteredUsers(@PathVariable Long eventId) {
        try {
            Event event = eventService.getEventById(eventId); // Lấy sự kiện
            Map<String, Object> response = disciplinaryPointService.addPointsForAllRegisteredUsers(event);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi hệ thống: " + e.getMessage());
        }
    }

    // điểm danh cách sinh viên được chọn
    @PostMapping("/batch/{eventId}")
    public ResponseEntity<?> addPointsForMultipleUsers(@PathVariable Long eventId, @RequestBody List<Long> userIds) {
        try {
            Event event = eventService.getEventById(eventId);
            List<DisciplinaryPoint> disciplinaryPoints = new ArrayList<>();
            List<Long> skippedUsers = new ArrayList<>();

            for (Long userId : userIds) {
                try {
                    User user = userService.getUserById(userId);
                    DisciplinaryPoint disciplinaryPoint = disciplinaryPointService.Addpoint(user, event);
                    if (disciplinaryPoint != null) {
                        disciplinaryPoints.add(disciplinaryPoint);
                    } else {
                        skippedUsers.add(userId); // Ghi nhận sinh viên đã điểm danh trước đó
                    }
                } catch (Exception e) {
                    skippedUsers.add(userId); // Nếu có lỗi khác, cũng bỏ qua sinh viên đó
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("successful", disciplinaryPoints);
            response.put("skipped", skippedUsers);

            return ResponseEntity.ok(response);
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
    public ResponseEntity<?> approveExternalEvent(@PathVariable Long eventId) {
        try {
            // Tìm ExternalEvent theo ID
            ExternalEvent externalEvent = externalEventService.findById(eventId);
            if (externalEvent == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("External Event not found"));
            }

            // Tìm User từ ExternalEvent
            User user = externalEvent.getUser();
            if (user == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("User not found for this event"));
            }

            // Gọi hàm cộng điểm và cập nhật trạng thái
            disciplinaryPointService.AddPointForExternalEvent(user, externalEvent);
            return ResponseEntity.ok(new MessageResponse("External Event approved successfully and points added!"));
        }catch (IllegalStateException e) {
            // Trường hợp sự kiện đã được duyệt trước đó
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        } catch (Exception e) {
            // Xử lý lỗi không mong muốn
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("An error occurred while approving the event: " + e.getMessage()));
        }

    }

    @PutMapping("/{eventId}/reject")
    public ResponseEntity<String> rejectExternalEvent(@PathVariable Long eventId) {
        String result = disciplinaryPointService.rejectExternalEvent(eventId);
        if (result.equals("External Event not found")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    // điểm danh bằng QR
    @PostMapping("/attend-by-qr")
    public ResponseEntity<?> attendByQR(@RequestBody Map<String, Long> requestData) {
        try {
            Long userId = requestData.get("userId");
            Long eventId = requestData.get("eventId");

            User user = userService.getUserById(userId);
            Event event = eventService.getEventById(eventId);
            DisciplinaryPoint disciplinaryPoint = disciplinaryPointService.Addpoint(user, event);

            return ResponseEntity.ok("Điểm danh thành công!");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi hệ thống: " + e.getMessage());
        }
    }


    // lấy danh sách và số điểm sinh viên theo filter

    @GetMapping
    public ResponseEntity<List<StudentPointResponse>> getStudents(
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) Integer courseId,
            @RequestParam(required = false) Integer departmentId,
            @RequestParam(required = false) Long  semesterId) {

        List<StudentPointResponse> students = disciplinaryPointService.getStudentsWithTotalPoints(classId, courseId, departmentId, semesterId);
        return ResponseEntity.ok(students);
    }
}
