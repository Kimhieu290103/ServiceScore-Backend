package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.model.Class;
import dtn.ServiceScore.repositories.ClassRepository;
import dtn.ServiceScore.services.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepository;

    @Override
    public List<Class> getAllClass() {
        return classRepository.findAll();
    }
}
