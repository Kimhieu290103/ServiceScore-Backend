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
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
    @Transactional
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
    public void cancelRegistration(Long eventId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = user.getId();
        Registration registration = registrationRepository
                .findByUser_IdAndEvent_Id(userId, eventId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đăng ký của người dùng cho sự kiện này"));
        registrationRepository.delete(registration);
    }


//    @Override
//    public void checkInEvent(Long registrationId) throws RuntimeException {
//        Registration existingRegistration = registrationRepository.findById(registrationId)
//                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy registration"));
//        existingRegistration.setAttendances(true);
//        existingRegistration.setStatus(Enums.RegistrationStatus.CHECKED_IN.toString());
//        registrationRepository.save(existingRegistration);
//    }

//    @Override
//    public void checkInEvent(Long eventId, Long userId) throws RuntimeException {
//        List<Registration> registrations = registrationRepository.findByUserIdAndEventId(userId, eventId);
//        if (registrations.isEmpty()) {
//            throw new DataNotFoundException("Không tìm thấy đăng ký");
//        }
//        Registration registration = registrations.get(0);
//        registration.setAttendances(true);
//        registration.setStatus(Enums.RegistrationStatus.CHECKED_IN.toString());
//        registrationRepository.save(registration);
//    }
//
//    public void multiCheckInEvent(List<Long> registrationIds) throws RuntimeException {
//        for (Long registrationId : registrationIds) {
//            checkInEvent(registrationId);
//        }
//    }
//
//    public void multiCheckInEvent(Long eventId, List<Long> userIds) throws RuntimeException {
//        for (Long userId : userIds) {
//            checkInEvent(eventId, userId);
//        }
//    }
//
//    public void allCheckInEvent(Long eventId) throws RuntimeException {
//        List<Registration> registrations = registrationRepository.findByEventId(eventId);
//        for (Registration registration : registrations) {
//            registration.setAttendances(true);
//            registration.setStatus(Enums.RegistrationStatus.CHECKED_IN.toString());
//            registrationRepository.save(registration);
//        }
//    }

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
                        .semester(SemesterRespone.builder()
                                .id(reg.getEvent().getSemester().getId())
                                .name(reg.getEvent().getSemester().getName())
                                .build())
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
                .semester(SemesterRespone.builder()
                        .id(event.getSemester().getId())
                        .name(event.getSemester().getName())
                        .build())  // Lấy tên kỳ học
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


    public ResponseEntity<Resource> exportEventRegistrationsToExcel(Long eventID) {
        EventRegistrationResponse response = getAllStudentByEvent(eventID);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Event Registrations");

            // Tạo font cho tiêu đề
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // Tạo hàng tiêu đề
            String[] columns = {"ID", "Tên", "Username", "Email", "Số điện thoại", "Mã sinh viên", "Địa chỉ",
                    "Ngày sinh", "Lớp", "Khoa", "Có điểm danh không?"};

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Điền dữ liệu sinh viên
            List<UserResponse> users = response.getUsers();
            int rowNum = 1;
            for (UserResponse user : users) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getFullname());
                row.createCell(2).setCellValue(user.getUsername());
                row.createCell(3).setCellValue(user.getEmail());
                row.createCell(4).setCellValue(user.getPhoneNumber());
                row.createCell(5).setCellValue(user.getStudentId());
                row.createCell(6).setCellValue(user.getAddress());
                row.createCell(7).setCellValue(user.getDateOfBirth().toString()); // Convert Date to String
                row.createCell(8).setCellValue(user.getClazz());
                row.createCell(9).setCellValue(user.getDepartment());
                row.createCell(10).setCellValue(user.isAttendances() ? "Có" : "Không");
            }

            // Tự động điều chỉnh độ rộng cột
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Ghi vào file
            workbook.write(out);
            ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=event_registrations.xlsx");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Xuất danh sách sự kiện đã tham gia của một user ra file Excel
    public ResponseEntity<Resource> exportAttendedEventsToExcel(Long userId, Long semesterId) {
        List<EventRespone> events = getAttendedEvents(userId, semesterId);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Attended Events");

            // Tạo font cho tiêu đề
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // Tạo hàng tiêu đề
            String[] columns = {"ID", "Tên sự kiện", "Mô tả", "Ngày bắt đầu", "Ngày kết thúc", "Điểm số", "Loại sự kiện", "Địa điểm"};

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Điền dữ liệu sự kiện
            int rowNum = 1;
            for (EventRespone event : events) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(event.getId());
                row.createCell(1).setCellValue(event.getName());
                row.createCell(2).setCellValue(event.getDescription());
                row.createCell(3).setCellValue(event.getDate().toString());
                row.createCell(4).setCellValue(event.getEndDate().toString());
                row.createCell(5).setCellValue(event.getScore());
                row.createCell(6).setCellValue(event.getEventType());
                row.createCell(7).setCellValue(event.getLocation());
            }

            // Tự động điều chỉnh độ rộng cột
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Ghi vào file
            workbook.write(out);
            ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attended_events.xlsx");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
