package dtn.ServiceScore.controllers;

import dtn.ServiceScore.services.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/class")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllFiveGood() {
        try {
            return ResponseEntity.ok(classService.getAllClass());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
