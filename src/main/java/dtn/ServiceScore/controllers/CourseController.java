package dtn.ServiceScore.controllers;



import dtn.ServiceScore.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourse());
    }
}
