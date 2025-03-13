package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.dtos.ClassSearchRequest;
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

    @Override
    public List<Class> getClasses(ClassSearchRequest request) {
        if (request.getDepartmentId() != null && request.getCourseId() != null) {
            return classRepository.findByDepartmentIdAndCourseId(request.getDepartmentId(), request.getCourseId());
        } else if (request.getDepartmentId() != null) {
            return classRepository.findByDepartmentId(request.getDepartmentId());
        } else if (request.getCourseId() != null) {
            return classRepository.findByCourseId(request.getCourseId());
        } else {
            return classRepository.findAll(); // Trả về tất cả nếu không có điều kiện nào
        }
    }
}
