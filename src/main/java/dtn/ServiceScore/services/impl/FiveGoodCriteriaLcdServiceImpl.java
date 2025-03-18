package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.dtos.FiveGoodCriteriaLcdDTO;
import dtn.ServiceScore.model.FiveGoodCriteriaLcd;
import dtn.ServiceScore.model.Semester;
import dtn.ServiceScore.repositories.FiveGoodCriteriaLcdRepository;
import dtn.ServiceScore.repositories.SemesterRepository;
import dtn.ServiceScore.services.FiveGoodCriteriaLcdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FiveGoodCriteriaLcdServiceImpl implements FiveGoodCriteriaLcdService {
    private final FiveGoodCriteriaLcdRepository fiveGoodCriteriaLcdRepository;
    private final SemesterRepository semesterRepository;
    @Override
    public List<FiveGoodCriteriaLcd> getAllFiveGoodCriteriaLcd() {
        return fiveGoodCriteriaLcdRepository.findByIsActiveTrue();
    }

    @Override
    public FiveGoodCriteriaLcd createCriteriaLcd(FiveGoodCriteriaLcdDTO dto) {
        // Kiểm tra học kỳ hợp lệ
        Semester semester = semesterRepository.findById(dto.getSemesterId())
                .orElseThrow(() -> new IllegalArgumentException("❌ Không tìm thấy học kỳ với ID: " + dto.getSemesterId()));

        // Tạo mới tiêu chí từ DTO
        FiveGoodCriteriaLcd criteria = FiveGoodCriteriaLcd.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .semester(semester)
                .isActive(true) // Mặc định là hiển thị
                .build();

        return fiveGoodCriteriaLcdRepository.save(criteria);
    }

    @Override
    public void softDeleteCriteriaLcd(Long id) {
        fiveGoodCriteriaLcdRepository.findById(id).ifPresent(criteria -> {
            criteria.setIsActive(false);
            fiveGoodCriteriaLcdRepository.save(criteria);
        });
    }
}
