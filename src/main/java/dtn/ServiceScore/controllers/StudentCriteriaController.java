package dtn.ServiceScore.controllers;

import dtn.ServiceScore.model.User;
import dtn.ServiceScore.responses.MessageResponse;
import dtn.ServiceScore.responses.UserResponse;
import dtn.ServiceScore.services.StudentCriteriaService;
import dtn.ServiceScore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/student_criteria")
@RequiredArgsConstructor
public class StudentCriteriaController {
    private final StudentCriteriaService studentCriteriaService;
    private final UserService userService;


    @GetMapping("/check/{userId}")
    public ResponseEntity<?> checkUserCriteria(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        boolean isCompleted = studentCriteriaService.hasCompletedAllCriteria(user);

        if (isCompleted) {

            return ResponseEntity.ok(new MessageResponse("User đã hoàn thành 5 tiêu chí 5 tốt!"));
        } else {
            return ResponseEntity.ok(new MessageResponse("User chưa hoàn thành đủ 5 tiêu chí."));
        }
    }

    @GetMapping("/completed")
    public ResponseEntity<List<User>> getStudentsCompletedAllCriteria(@RequestParam Long semesterId) {
        List<User> students = studentCriteriaService.getStudentsCompletedAllCriteria(semesterId);
        return ResponseEntity.ok(students);

    }
}
