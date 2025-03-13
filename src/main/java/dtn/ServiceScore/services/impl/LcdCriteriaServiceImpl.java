package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.EventCriteriaLcd;
import dtn.ServiceScore.model.LcdCriteria;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.repositories.EventCriteriaLcdRepository;
import dtn.ServiceScore.repositories.EventRepository;
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

    private final EventRepository eventRepository;
    @Override
    public List<User> getLcdsCompletedAllCriteria(Long semesterId) {
        return lcdCriteriaRepository.findLcdsCompletedAllCriteria(semesterId);
    }

    @Override
    public String Event_validation(User user, Event event) {
        List<EventCriteriaLcd> eventCriteriaLcdList = eventCriteriaLcdRepository.findByEventId(event.getId());

        if (eventCriteriaLcdList.isEmpty()) {
            return "Không có tiêu chí nào để ghi!";
        }

        try {
            for (EventCriteriaLcd eventCriteriaLcd : eventCriteriaLcdList) {
                boolean exists = lcdCriteriaRepository.existsByUserAndCriteriaAndSemester(
                        user,
                        eventCriteriaLcd.getCriteria(),
                        event.getSemester()
                );

                if (!exists) { // Chỉ lưu nếu chưa tồn tại
                    LcdCriteria lcdCriteria = LcdCriteria.builder()
                            .user(user)
                            .fiveGoodCriteriaLcd(eventCriteriaLcd.getCriteria())
                            .semester(event.getSemester())
                            .achievedAt(LocalDate.now())
                            .isCompleted(true)
                            .build();

                    lcdCriteriaRepository.save(lcdCriteria);
                }
            }
            // ✅ Cập nhật trạng thái sự kiện
            event.setStatus("COMPLETED");
            eventRepository.save(event);
            return "Xác nhận thành công!";
        } catch (Exception e) {
            return "Lỗi khi ghi dữ liệu: " + e.getMessage();
        }
    }
}
