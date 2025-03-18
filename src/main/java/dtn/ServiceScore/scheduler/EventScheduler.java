package dtn.ServiceScore.scheduler;

import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.services.EventService;
import dtn.ServiceScore.services.LcdCriteriaService;
import dtn.ServiceScore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventScheduler {
    private final EventService eventService;
    private final LcdCriteriaService lcdCriteriaService;
    private final UserService userService;

    @Scheduled(fixedRate = 60000) // Chạy mỗi 60 giây (1 phút)
    public void checkAndProcessCompletedEvents() {
        LocalDateTime now = LocalDateTime.now();
        List<Event> expiredEvents = eventService.getExpiredEvents(now); // Lấy danh sách sự kiện đã kết thúc

        for (Event event : expiredEvents) {
            if (!event.getStatus().equals("COMPLETED")) { // Kiểm tra nếu sự kiện chưa xử lý
                processCompletedEvent(event);
            }
        }
    }

    private void processCompletedEvent(Event event) {
        try {
            // Lấy người tổ chức sự kiện
            Long userId = event.getUser().getId();
            var user = userService.getUserById(userId);

            // Gọi service xử lý sự kiện kết thúc
            String result = lcdCriteriaService.Event_validation(user, event);


            System.out.println("Đã xử lý sự kiện: " + event.getName() + " | Kết quả: " + result);
        } catch (Exception e) {
            System.err.println("Lỗi khi xử lý sự kiện " + event.getId() + ": " + e.getMessage());
        }
    }
}
