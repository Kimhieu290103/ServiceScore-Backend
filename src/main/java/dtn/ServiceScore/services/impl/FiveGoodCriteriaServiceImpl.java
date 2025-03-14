package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.dtos.FiveGoodCriteriaDTO;
import dtn.ServiceScore.model.FiveGoodCriteria;
import dtn.ServiceScore.model.Semester;
import dtn.ServiceScore.repositories.FiveGoodCriteriaRepository;
import dtn.ServiceScore.repositories.SemesterRepository;
import dtn.ServiceScore.repositories.StudentCriteriaRepository;
import dtn.ServiceScore.services.FiveGoodCriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FiveGoodCriteriaServiceImpl implements FiveGoodCriteriaService {
    private final FiveGoodCriteriaRepository fiveGoodCriteriaRepository;
    private final StudentCriteriaRepository studentCriteriaRepository;
    private final SemesterRepository semesterRepository;
    @Override
    public List<FiveGoodCriteria> getAllFiveGoodCriteria() {
        return fiveGoodCriteriaRepository.findByIsActiveTrue();
    }

    @Override
    public FiveGoodCriteria createCriteria(FiveGoodCriteriaDTO dto) {
        // Kiểm tra học kỳ hợp lệ
        Semester semester = semesterRepository.findById(dto.getSemesterId())
                .orElseThrow(() -> new IllegalArgumentException("❌ Không tìm thấy học kỳ với ID: " + dto.getSemesterId()));

        // Tạo mới tiêu chí từ DTO
        FiveGoodCriteria criteria = FiveGoodCriteria.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .semester(semester)
                .isActive(true) // Mặc định là hiển thị
                .build();

        return fiveGoodCriteriaRepository.save(criteria);
    }

    @Override
    public void softDeleteCriteria(Long id) {
        fiveGoodCriteriaRepository.findById(id).ifPresent(criteria -> {
            criteria.setIsActive(false);
            fiveGoodCriteriaRepository.save(criteria);
        });
    }


}
