package dtn.ServiceScore.controllers;

import dtn.ServiceScore.model.Semester;
import dtn.ServiceScore.services.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/semesters")
@RequiredArgsConstructor
public class SemesterController {
    private final SemesterService semesterService;

    @GetMapping
    public List<Semester> getAllSemesters() {
        return semesterService.getAllSemesters();
    }
}
