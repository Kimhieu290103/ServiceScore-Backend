package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.model.Course;
import dtn.ServiceScore.repositories.ClassRepository;
import dtn.ServiceScore.repositories.CourseRepository;
import dtn.ServiceScore.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    @Override
    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }
}
