package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.exceptions.DataNotFoundException;
import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.Registration;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.repositories.EventRepository;
import dtn.ServiceScore.repositories.RegistrationRepository;
import dtn.ServiceScore.repositories.UserRepository;
import dtn.ServiceScore.responses.EventRespone;
import dtn.ServiceScore.responses.UserResponse;
import dtn.ServiceScore.services.RegistrationService;
import dtn.ServiceScore.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Registration register_event(Long eventId, Long userId) throws RuntimeException {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm người dùng"));

        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy event"));
        if (isUserRegistered(eventId, userId)) {
            throw new IllegalStateException("Người dùng đã đăng ký sự kiện này rồi!");
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

    @Override
    public List<UserResponse> getAllStudentByEvent(Long eventID) {
        Event existingEvent = eventRepository.findById(eventID)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy event"));
        List<Registration> registrations = registrationRepository.findByEvent(existingEvent);
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
                        .eventType(reg.getEvent().getEventType())

                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getCheckedInStudentsByEvent(Long eventID) {
        Event existingEvent = eventRepository.findById(eventID)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy event"));

        List<Registration> registrations = registrationRepository.findByEventAndAttendancesTrue(existingEvent);

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
                        .build())
                .collect(Collectors.toList());
    }

}
