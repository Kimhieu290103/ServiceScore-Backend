package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.model.*;
import dtn.ServiceScore.repositories.EventCriteriaLcdRepository;
import dtn.ServiceScore.repositories.EventCriteriaRepository;
import dtn.ServiceScore.repositories.LcdCriteriaRepository;
import dtn.ServiceScore.services.LcdCriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class LcdCriteriaServiceImpl implements LcdCriteriaService {
    private final EventCriteriaLcdRepository eventCriteriaLcdRepository;
    private final LcdCriteriaRepository lcdCriteriaRepository;
    @Override
    public List<User> getLcdsCompletedAllCriteria() {
        return lcdCriteriaRepository.findLcdsCompletedAllCriteria();
    }

    @Override
    public String Event_validation(User user, Event event) {
        List<EventCriteriaLcd> eventCriteriaLcdList = eventCriteriaLcdRepository.findByEventId(event.getId());

        if (eventCriteriaLcdList.isEmpty()) {
            return "Không có tiêu chí nào để ghi!";
        }

        try {
            for (EventCriteriaLcd eventCriteriaLcd : eventCriteriaLcdList) {
                LcdCriteria lcdCriteria = LcdCriteria.builder()
                        .user(user)
                        .fiveGoodCriteriaLcd(eventCriteriaLcd.getCriteria()) // Liên kết với tiêu chí 5 tốt
                        .achievedAt(LocalDate.now())
                        .isCompleted(true)
                        .build();

                lcdCriteriaRepository.save(lcdCriteria);
            }
            return "Xác nhận thành công!";
        } catch (Exception e) {
            return "Lỗi khi ghi dữ liệu: " + e.getMessage();
        }
    }
}
