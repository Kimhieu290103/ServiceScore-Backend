package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.dtos.FiveGoodCriteriaLcdDTO;
import dtn.ServiceScore.model.FiveGoodCriteriaLcd;
import dtn.ServiceScore.model.Semester;
import dtn.ServiceScore.repositories.EventCriteriaLcdRepository;
import dtn.ServiceScore.repositories.FiveGoodCriteriaLcdRepository;
import dtn.ServiceScore.repositories.LcdCriteriaRepository;
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
    private final EventCriteriaLcdRepository eventCriteriaLcdRepository;
    private final LcdCriteriaRepository lcdCriteriaRepository;
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
    public void deleteCriteriaLcd(Long id) {
        // Xóa tất cả bản ghi liên quan trong event_criteria_lcd
        eventCriteriaLcdRepository.deleteByCriteriaId(id);

        // Xóa tất cả bản ghi liên quan trong lcd_criteria
        lcdCriteriaRepository.deleteByCriteriaId(id);

        // Xóa tiêu chí trong five_good_criteria_lcd
        fiveGoodCriteriaLcdRepository.deleteById(id);
    }

    @Override
    public List<FiveGoodCriteriaLcd> getCriteriaLcdBySemester(Long semesterId) {
        return fiveGoodCriteriaLcdRepository.findBySemesterId(semesterId);
    }

    @Override
    public FiveGoodCriteriaLcd updateCriteriaLcd(Long id, FiveGoodCriteriaLcdDTO dto) {
        // Kiểm tra tiêu chí có tồn tại không
        FiveGoodCriteriaLcd criteria = fiveGoodCriteriaLcdRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("❌ Không tìm thấy tiêu chí với ID: " + id));

        // Cập nhật thông tin tiêu chí
        criteria.setName(dto.getName());
        criteria.setDescription(dto.getDescription());
        // Lưu lại và trả về tiêu chí đã cập nhật
        return fiveGoodCriteriaLcdRepository.save(criteria);

    }
}
