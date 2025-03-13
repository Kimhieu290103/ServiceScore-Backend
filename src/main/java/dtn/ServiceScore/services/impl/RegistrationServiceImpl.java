package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.exceptions.DataNotFoundException;
import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.EventImage;
import dtn.ServiceScore.model.Registration;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.repositories.EventRepository;
import dtn.ServiceScore.repositories.RegistrationRepository;
import dtn.ServiceScore.repositories.UserRepository;
import dtn.ServiceScore.responses.*;
import dtn.ServiceScore.services.EventImageService;
import dtn.ServiceScore.services.EventService;
import dtn.ServiceScore.services.RegistrationService;
import dtn.ServiceScore.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    public final EventImageService eventImageService;
    public final EventService eventService;
    // đăng kí sự kiện
    @Override
    public Registration register_event(Long eventId, Long userId) throws RuntimeException {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm người dùng"));

        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy event"));
        if (isUserRegistered(eventId, userId)) {
            throw new IllegalStateException("Bạn đã đăng kí sự kiện");
        }
        if (Objects.equals(existingEvent.getCurrentRegistrations(), existingEvent.getMaxRegistrations())) {
            throw new IllegalStateException("Sự kiện đã đủ số lượng người đăng kí");
        }
        Registration newRegistration = Registration.builder()
                .event(existingEvent)
                .user(existingUser)
                .status(Enums.RegistrationStatus.REGISTERED.toString())
                .registeredAt(LocalDate.now())
                .build();

        existingEvent.setCurrentRegistrations(existingEvent.getCurrentRegistrations() + 1);
        eventRepository.save(existingEvent);
        return registrationRepository.save(newRegistration);
    }

    @Override
    public void checkInEvent(Long registrationId) throws RuntimeException {
        Registration existingRegistration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy registration"));
        existingRegistration.setAttendances(true);
        existingRegistration.setStatus(Enums.RegistrationStatus.CHECKED_IN.toString());
        registrationRepository.save(existingRegistration);
    }

    @Override
    public void checkInEvent(Long eventId, Long userId) throws RuntimeException {
        List<Registration> registrations = registrationRepository.findByUserIdAndEventId(userId, eventId);
        if (registrations.isEmpty()) {
            throw new DataNotFoundException("Không tìm thấy đăng ký");
        }
        Registration registration = registrations.get(0);
        registration.setAttendances(true);
        registration.setStatus(Enums.RegistrationStatus.CHECKED_IN.toString());
        registrationRepository.save(registration);
    }

    public void multiCheckInEvent(List<Long> registrationIds) throws RuntimeException {
        for (Long registrationId : registrationIds) {
            checkInEvent(registrationId);
        }
    }

    public void multiCheckInEvent(Long eventId, List<Long> userIds) throws RuntimeException {
        for (Long userId : userIds) {
            checkInEvent(eventId, userId);
        }
    }

    public void allCheckInEvent(Long eventId) throws RuntimeException {
        List<Registration> registrations = registrationRepository.findByEventId(eventId);
        for (Registration registration : registrations) {
            registration.setAttendances(true);
            registration.setStatus(Enums.RegistrationStatus.CHECKED_IN.toString());
            registrationRepository.save(registration);
        }
    }

    @Override
    public boolean isUserRegistered(Long eventId, Long userId) {
        List<Registration> registrations = registrationRepository.findByUserIdAndEventId(userId, eventId);
        return !registrations.isEmpty();
    }

    // danh sách sinh viên đăng kí sự kiện
    @Override
    public EventRegistrationResponse  getAllStudentByEvent(Long eventID) {
        Event existingEvent = eventRepository.findById(eventID)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy event"));
        List<Registration> registrations = registrationRepository.findByEvent(existingEvent);

        // Danh sách sinh viên đăng ký
        List<UserResponse> users = getUserResponses(registrations);


        // Tổng số sinh viên đăng ký
        int totalRegistrations = registrations.size();

        // Lấy số lượng mã đăng ký tối đa của sự kiện
        int maxRegistrations = Math.toIntExact(existingEvent.getMaxRegistrations());

        // Khởi tạo danh sách khóa học với giá trị mặc định là 0
        Map<String, Long> studentByCourse = new HashMap<>();
        studentByCourse.put("Course_20", 0L);
        studentByCourse.put("Course_21", 0L);
        studentByCourse.put("Course_22", 0L);
        studentByCourse.put("Course_23", 0L);
        studentByCourse.put("Course_24", 0L);
        studentByCourse.put("Other", 0L);

        // Đếm số sinh viên theo khóa học từ bảng Class
        Map<String, Long> studentCounts = registrations.stream()
                .map(reg -> reg.getUser().getClazz()) // Lấy thông tin lớp từ User
                .map(studentClass -> (studentClass != null) ? studentClass.getName() : "Other") // Nếu lớp bị null, gán "Other"
                .map(className -> (className != null && className.length() >= 2) ? className.substring(0, 2) : "Other") // Lấy 2 ký tự đầu
                .collect(Collectors.groupingBy(course -> {
                    switch (course) {
                        case "20": return "Course_20";
                        case "21": return "Course_21";
                        case "22": return "Course_22";
                        case "23": return "Course_23";
                        case "24": return "Course_24";
                        default: return "Other";
                    }
                }, Collectors.counting()));

        // Cập nhật số lượng thực tế vào studentByCourse
        studentCounts.forEach(studentByCourse::put);

        return EventRegistrationResponse.builder()
                .users(users)
                .totalRegistrations(totalRegistrations)
                .maxRegistrations(maxRegistrations)
                .studentByCourse(studentByCourse)
                .build();
    }

    private List<UserResponse> getUserResponses(List<Registration> registrations) {
        return registrations.stream()
                .map(reg -> UserResponse.builder()
                        .id(reg.getUser().getId())
                        .username(reg.getUser().getUsername())
                        .email(reg.getUser().getEmail())
                        .fullname(reg.getUser().getFullname())
                        .phoneNumber(reg.getUser().getPhoneNumber())
                        .studentId(reg.getUser().getStudentId())
                        .address(reg.getUser().getAddress())
                        .dateOfBirth(reg.getUser().getDateOfBirth())
                        .clazz(reg.getUser().getClazz() != null ? reg.getUser().getClazz().getName() : null) // Kiểm tra null
                        .Department((reg.getUser().getClazz() != null && reg.getUser().getClazz().getDepartment() != null)
                                ? reg.getUser().getClazz().getDepartment().getName()
                                : null) // Kiểm tra null
                        .attendances(reg.isAttendances())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<EventRespone> getAllEventByStudent(Long sudentId) {
        User existingUser = userRepository.findById(sudentId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm người dùng"));
        List<Registration> registrations = registrationRepository.findByUser(existingUser);
        return registrations.stream()
                .map(reg -> EventRespone.builder()
                        .id(reg.getEvent().getId())
                        .name(reg.getEvent().getName())
                        .description(reg.getEvent().getDescription())
                        .date(reg.getEvent().getDate())
                        .endDate(reg.getEvent().getEndDate())
                        .registrationStartDate(reg.getEvent().getRegistrationStartDate())
                        .registrationEndDate(reg.getEvent().getRegistrationEndDate())
                        .semester(reg.getEvent().getSemester().getName())
                        .score(reg.getEvent().getScore())
                        .maxRegistrations(reg.getEvent().getMaxRegistrations())
                        .currentRegistrations(reg.getEvent().getCurrentRegistrations())
                        .location(reg.getEvent().getLocation())
                        .additionalInfo(reg.getEvent().getAdditionalInfo())
                        .eventType(reg.getEvent().getEventType().getName())

                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getCheckedInStudentsByEvent(Long eventID) {
        Event existingEvent = eventRepository.findById(eventID)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy event"));

        List<Registration> registrations = registrationRepository.findByEventAndAttendancesTrue(existingEvent);

        return getUserResponses(registrations);
    }

    @Override
    public List<EventRespone> getAttendedEvents(Long userId, Long semesterId ) {
        System.out.println("Received semesterId: " + semesterId);
        List<Registration> registrations = registrationRepository.findByUserIdAndAttendancesTrue(userId);

        return registrations.stream()
                .filter(registration ->semesterId == null || registration.getEvent().getSemester().getId().equals(semesterId)) // Lọc theo kỳ học
                .map(registration -> mapToDTO(registration.getEvent()))
                .collect(Collectors.toList());
    }



    private EventRespone mapToDTO(Event event) {

        // Lấy danh sách hình ảnh
        List<EventImage> eventImages = eventImageService.findByEventId(event.getId());

        // Lấy danh sách tiêu chí sự kiện
        EventCriteriaResponse eventCriteriaResponse = eventService.getFilteredEventCriteria(event.getId());

        List<EventImageRespone> eventImageResponses = eventImages.stream()
                .map(image -> EventImageRespone.builder()
                        .id(image.getId())
                        .eventID(image.getEvent().getId())
                        .imageUrl(image.getImageUrl())
                        .build())
                .toList();

        return EventRespone.builder()
                .id(event.getId())
                .name(event.getName())
                .user_id(event.getUser().getId())
                .description(event.getDescription())
                .date(event.getDate())
                .endDate(event.getEndDate())
                .registrationStartDate(event.getRegistrationStartDate())
                .registrationEndDate(event.getRegistrationEndDate())
                .semester(event.getSemester().getName())  // Lấy tên kỳ học
                .score(event.getScore())
                .maxRegistrations(event.getMaxRegistrations())
                .currentRegistrations(event.getCurrentRegistrations())
                .location(event.getLocation())
                .additionalInfo(event.getAdditionalInfo())
                .eventType(event.getEventType().getName())  // Lấy tên loại sự kiện
                .eventCriteria(eventCriteriaResponse)  // Map tiêu chí sự kiện
                .eventImage(eventImageResponses)  // Map danh sách ảnh sự kiện
                .build();
    }


}
