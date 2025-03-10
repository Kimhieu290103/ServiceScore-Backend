package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.model.*;
import dtn.ServiceScore.repositories.DisciplinaryPointRepository;
import dtn.ServiceScore.repositories.EventCriteriaRepository;
import dtn.ServiceScore.repositories.RegistrationRepository;
import dtn.ServiceScore.repositories.StudentCriteriaRepository;
import dtn.ServiceScore.responses.PointResponse;
import dtn.ServiceScore.services.DisciplinaryPointService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class DisciplinaryPointServiceImpl implements DisciplinaryPointService {
    private final DisciplinaryPointRepository disciplinaryPointRepository;
    private final RegistrationRepository registrationRepository;
    private final EventCriteriaRepository eventCriteriaRepository;
    private final StudentCriteriaRepository studentCriteriaRepository;

    @Override
    public DisciplinaryPoint Addpoint(User user, Event event) {
        Registration registration = registrationRepository.findByUserAndEvent(user, event);
        if (registration != null && registration.isAttendances()) {
            throw new IllegalStateException("Đã được điểm danh rồi");
        }

        // Kiểm tra xem đã có bản ghi trong bảng DisciplinaryPoint hay chưa
        DisciplinaryPoint disciplinaryPoint = disciplinaryPointRepository.findByUserAndSemester(user, event.getSemester());

        if (disciplinaryPoint == null) {
            // Nếu chưa có, tạo mới bản ghi
            DisciplinaryPoint newPoint = DisciplinaryPoint.builder()
                    .user(user)
                    .semester(event.getSemester())
                    .points(event.getScore()) // Set điểm mặc định là 0, có thể sửa lại theo yêu cầu
                    .build();

            // Lưu vào database
            return disciplinaryPointRepository.save(newPoint);

        } else {
            // Nếu đã có bản ghi, cộng thêm điểm của event vào điểm hiện tại
            disciplinaryPoint.setPoints(disciplinaryPoint.getPoints() + event.getScore());
        }
        List<EventCriteria> eventCriteriaList = eventCriteriaRepository.findByEventId(event.getId());
        for (EventCriteria eventCriteria : eventCriteriaList) {
            StudentCriteria studentCriteria = StudentCriteria.builder()
                    .student(user)
                    .criteria(eventCriteria.getCriteria()) // Liên kết với tiêu chí 5 tốt
                    .achievedAt(LocalDate.now())
                    .isCompleted(true)
                    .build();

            studentCriteriaRepository.save(studentCriteria);
        }

        // Cập nhật trạng thái đã điểm danh
        registration.setAttendances(true);
        registrationRepository.save(registration);
        return disciplinaryPointRepository.save(disciplinaryPoint);
    }

    @Override
    public List<PointResponse> getDisciplinaryPointsByUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = user.getId();
        List<DisciplinaryPoint> disciplinaryPoints= disciplinaryPointRepository.findByUser_Id(userId);
        // Tạo danh sách kết quả
        List<PointResponse> responseList = disciplinaryPoints.stream()
                .map(dp -> PointResponse.builder()
                        .id(dp.getId())
                        .semester(dp.getSemester().getName())
                        .points(dp.getPoints())
                        .build())
                .toList();

        return responseList;
    }

    @Override
    public Map<String, Object> getDisciplinaryPointsWithTotal() {
        List<PointResponse> pointsList = getDisciplinaryPointsByUserId();

        Long totalPoints = pointsList.stream()
                .mapToLong(PointResponse::getPoints)
                .sum();

        return Map.of(
                "disciplinaryPoints", pointsList,
                "totalPoints", totalPoints
        );
    }


}
