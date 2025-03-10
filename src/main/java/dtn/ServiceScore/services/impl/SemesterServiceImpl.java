package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.model.Semester;
import dtn.ServiceScore.repositories.SemesterRepository;
import dtn.ServiceScore.services.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepository;

    public List<Semester> getAllSemesters() {
        return semesterRepository.findAll();
    }
}
