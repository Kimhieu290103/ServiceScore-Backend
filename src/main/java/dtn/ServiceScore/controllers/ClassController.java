package dtn.ServiceScore.controllers;

import dtn.ServiceScore.dtos.ClassSearchRequest;
import dtn.ServiceScore.services.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/search")
    public ResponseEntity<?> searchClasses(@RequestBody ClassSearchRequest request) {
        List<dtn.ServiceScore.model.Class> classes = classService.getClasses(request);
        return ResponseEntity.ok(classes);
    }
}
