package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.dtos.FiveGoodCriteriaDTO;
import dtn.ServiceScore.model.FiveGoodCriteria;
import dtn.ServiceScore.model.Semester;
import dtn.ServiceScore.repositories.EventCriteriaRepository;
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
    private final EventCriteriaRepository eventCriteriaRepository;
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
    public void deleteCriteria(Long id) {
        // Xóa tất cả bản ghi liên quan trong event_criteria
        eventCriteriaRepository.deleteByCriteriaId(id);

        // Xóa tất cả bản ghi liên quan trong student_criteria
        studentCriteriaRepository.deleteByCriteriaId(id);

        // Xóa tiêu chí trong five_good_criteria
        fiveGoodCriteriaRepository.deleteById(id);
    }

    // lấy danh sách các tiêu chí theo kì
    @Override
    public List<FiveGoodCriteria> getCriteriaBySemester(Long semesterId) {
        return fiveGoodCriteriaRepository.findBySemesterId(semesterId);
    }

    @Override
    public FiveGoodCriteria updateCriteria(Long id, FiveGoodCriteriaDTO dto) {
        // Kiểm tra xem tiêu chí có tồn tại không
        FiveGoodCriteria existingCriteria = fiveGoodCriteriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Không tìm thấy tiêu chí với ID: " + id));

        // Cập nhật thông tin tiêu chí
        existingCriteria.setName(dto.getName());
        existingCriteria.setDescription(dto.getDescription());

        return fiveGoodCriteriaRepository.save(existingCriteria);
    }


}
