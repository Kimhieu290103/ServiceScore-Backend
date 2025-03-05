package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.model.User;
import dtn.ServiceScore.repositories.StudentCriteriaRepository;
import dtn.ServiceScore.services.StudentCriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentCriteriaServiceImpl implements StudentCriteriaService {
    private final StudentCriteriaRepository studentCriteriaRepository;
    @Override
    public boolean hasCompletedAllCriteria(User user) {
        long distinctCriteriaCount = studentCriteriaRepository.countDistinctCriteriaByUser(user.getId());
        return distinctCriteriaCount == 5; // Kiểm tra đủ 5 tiêu chí khác nhau
    }

    @Override
    public List<User> getStudentsCompletedAllCriteria() {
        return studentCriteriaRepository.findStudentsCompletedAllCriteria();
    }
}
