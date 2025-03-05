package dtn.ServiceScore.controllers;

import dtn.ServiceScore.model.User;
import dtn.ServiceScore.responses.MessageResponse;
import dtn.ServiceScore.services.StudentCriteriaService;
import dtn.ServiceScore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<User>> getStudentsCompletedAllCriteria() {
        List<User> students = studentCriteriaService.getStudentsCompletedAllCriteria();
        return ResponseEntity.ok(students);
    }
}
