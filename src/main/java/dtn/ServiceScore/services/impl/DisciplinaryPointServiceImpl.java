package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.enums.ExternalEventStatus;
import dtn.ServiceScore.exceptions.DataNotFoundException;
import dtn.ServiceScore.model.*;
import dtn.ServiceScore.model.Class;
import dtn.ServiceScore.repositories.*;
import dtn.ServiceScore.responses.PointResponse;
import dtn.ServiceScore.responses.StudentPointResponse;
import dtn.ServiceScore.services.DisciplinaryPointService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class DisciplinaryPointServiceImpl implements DisciplinaryPointService {
    private final DisciplinaryPointRepository disciplinaryPointRepository;
    private final RegistrationRepository registrationRepository;
    private final EventCriteriaRepository eventCriteriaRepository;
    private final StudentCriteriaRepository studentCriteriaRepository;
    private final ExternalEventRepository externalEventRepository;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final SemesterRepository semesterRepository;
    @Override
    @Transactional
    public DisciplinaryPoint Addpoint(User user, Event event) {
        Registration registration = registrationRepository.findByUserAndEvent(user, event);
        // Nếu không tìm thấy bản đăng ký -> Trả về lỗi
        if (registration == null) {
            throw new IllegalStateException("Người dùng chưa đăng ký sự kiện này.");
        }

        if ( registration.isAttendances()) {
            return null;
        }

        DisciplinaryPoint disciplinaryPoint = disciplinaryPointRepository.findByUserAndSemester(user, event.getSemester())
                .orElseGet(() -> {
                    // Nếu chưa có, tạo bản ghi mới
                    DisciplinaryPoint newPoint = DisciplinaryPoint.builder()
                            .user(user)
                            .semester(event.getSemester())
                            .points(event.getScore()) // Set điểm event
                            .build();
                    return disciplinaryPointRepository.save(newPoint);
                });

        if (disciplinaryPoint.getId() != null) {
            disciplinaryPoint.setPoints(disciplinaryPoint.getPoints() + event.getScore());
        }

        List<EventCriteria> eventCriteriaList = eventCriteriaRepository.findByEventId(event.getId());
        for (EventCriteria eventCriteria : eventCriteriaList) {
            StudentCriteria studentCriteria = StudentCriteria.builder()
                    .student(user)
                    .criteria(eventCriteria.getCriteria())
                    .semester(event.getSemester())// Liên kết với tiêu chí 5 tốt
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
    @Transactional
    public DisciplinaryPoint AddPointForExternalEvent(User user, ExternalEvent externalEvent) {

        // Nếu sự kiện đã được duyệt, ném exception để Controller xử lý
        if (externalEvent.getStatus() == ExternalEventStatus.APPROVED) {
            throw new IllegalStateException("Sự kiện này đã được duyệt trước đó.");
        }
        // Kiểm tra xem đã có bản ghi trong bảng DisciplinaryPoint chưa
        DisciplinaryPoint disciplinaryPoint = disciplinaryPointRepository.findByUserAndSemester(user, externalEvent.getSemester())
                .orElse(null);


        if (disciplinaryPoint == null) {
            // Nếu chưa có, tạo mới bản ghi
            DisciplinaryPoint newPoint = DisciplinaryPoint.builder()
                    .user(user)
                    .semester(externalEvent.getSemester())
                    .points(externalEvent.getPoints()) // Lấy điểm từ ExternalEvent
                    .build();

            // Cập nhật trạng thái của ExternalEvent thành APPROVED
            externalEvent.setStatus(ExternalEventStatus.APPROVED);
            externalEventRepository.save(externalEvent);

            // Lưu vào database
            return disciplinaryPointRepository.save(newPoint);

        } else {
            // Nếu đã có bản ghi, cộng thêm điểm của externalEvent vào điểm hiện tại
            disciplinaryPoint.setPoints(disciplinaryPoint.getPoints() + externalEvent.getPoints());
        }

        // Cập nhật trạng thái của ExternalEvent thành APPROVED
        externalEvent.setStatus(ExternalEventStatus.APPROVED);
        externalEventRepository.save(externalEvent);

        return disciplinaryPointRepository.save(disciplinaryPoint);
    }

    @Override
    public String rejectExternalEvent(Long eventId) {
        ExternalEvent externalEvent = externalEventRepository.findById(eventId).orElse(null);
        if (externalEvent == null) {
            return "External Event not found";
        }

        externalEvent.setStatus(ExternalEventStatus.REJECTED);
        externalEventRepository.save(externalEvent);

        return "External Event has been rejected.";
    }

    // điêmr danh tất cả sinh viên
    @Override
    public Map<String, Object> addPointsForAllRegisteredUsers(Event event) {
        List<Registration> registrations = registrationRepository.findByEvent(event);
        List<DisciplinaryPoint> disciplinaryPoints = new ArrayList<>();
        List<Long> skippedUsers = new ArrayList<>();

        for (Registration registration : registrations) {
            User user = registration.getUser();
            DisciplinaryPoint disciplinaryPoint = Addpoint(user, event);

            if (disciplinaryPoint == null) {
                skippedUsers.add(user.getId());
            } else {
                disciplinaryPoints.add(disciplinaryPoint);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("disciplinaryPoints", disciplinaryPoints);
        response.put("skippedUsers", skippedUsers);
        return response;
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

    @Override
    public List<StudentPointResponse> getStudentsWithTotalPointsByClass(Long classId) {
        Class clazz = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        // Lấy danh sách sinh viên trong lớp
        List<User> students = userRepository.findByClazz(clazz);

        List<StudentPointResponse> studentPointsList = students.stream().map(student -> {
            // Tính tổng điểm của sinh viên
            Long totalPoints = disciplinaryPointRepository.findByUser(student)
                    .stream()
                    .mapToLong(DisciplinaryPoint::getPoints)
                    .sum();

            // Trả về đối tượng StudentPointResponse
            return StudentPointResponse.builder()
                    .studentId(student.getId())
                    .studentName(student.getFullname())
                    .className(student.getClazz().getName())
                    .email(student.getEmail())
                    .phoneNumber(student.getPhoneNumber())
                    .dateOfBirth(student.getDateOfBirth())
                    .Department(student.getClazz().getDepartment().getName())
                    .address(student.getAddress())
                    .totalPoints(totalPoints)
                    .build();
        }).toList();

        return studentPointsList;
    }

    @Override
    public List<StudentPointResponse> getStudentsWithTotalPointsByClassAndSemester(Long classId, Long semester) {
        Class clazz = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        List<User> students = userRepository.findByClazz(clazz);

        List<StudentPointResponse> studentPointsList = students.stream().map(student -> {
            Long totalPoints = Optional.ofNullable(
                    disciplinaryPointRepository.getTotalPointsByUserAndSemester(student.getId(), semester)
            ).orElse(0L);

            return StudentPointResponse.builder()
                    .studentId(student.getId())
                    .studentName(student.getFullname())
                    .className(student.getClazz().getName())
                    .email(student.getEmail())
                    .phoneNumber(student.getPhoneNumber())
                    .dateOfBirth(student.getDateOfBirth())
                    .Department(student.getClazz().getDepartment().getName())
                    .address(student.getAddress())
                    .totalPoints(totalPoints)
                    .build();
        }).toList();

        return studentPointsList;
    }



}
