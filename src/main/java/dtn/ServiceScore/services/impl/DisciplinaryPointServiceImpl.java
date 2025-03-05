package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.model.DisciplinaryPoint;
import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.Registration;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.repositories.DisciplinaryPointRepository;
import dtn.ServiceScore.repositories.RegistrationRepository;
import dtn.ServiceScore.services.DisciplinaryPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisciplinaryPointServiceImpl implements DisciplinaryPointService {
    private final DisciplinaryPointRepository disciplinaryPointRepository;
    private final RegistrationRepository registrationRepository;

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

        // Cập nhật trạng thái đã điểm danh
        registration.setAttendances(true);
        registrationRepository.save(registration);
        return disciplinaryPointRepository.save(disciplinaryPoint);
    }


}
