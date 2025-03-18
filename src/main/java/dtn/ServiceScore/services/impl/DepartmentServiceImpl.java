package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.model.Department;
import dtn.ServiceScore.repositories.DepartmentRepository;
import dtn.ServiceScore.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
