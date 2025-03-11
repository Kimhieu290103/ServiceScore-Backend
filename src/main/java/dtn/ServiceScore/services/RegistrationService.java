package dtn.ServiceScore.services;

import dtn.ServiceScore.model.Registration;
import dtn.ServiceScore.responses.EventRegistrationResponse;
import dtn.ServiceScore.responses.EventRespone;
import dtn.ServiceScore.responses.UserResponse;

import java.util.List;

public interface RegistrationService {
    Registration register_event(Long eventId, Long userId) throws RuntimeException;

    void checkInEvent(Long registrationId) throws RuntimeException;

    void checkInEvent(Long eventId, Long userId) throws RuntimeException;

    void multiCheckInEvent(List<Long> registrationIds) throws RuntimeException;

    void multiCheckInEvent(Long eventId, List<Long> userIds) throws RuntimeException;

    void allCheckInEvent(Long eventId) throws RuntimeException;

    boolean isUserRegistered(Long eventId, Long userId) throws RuntimeException;

    // danh sách sinh viên đăng kí sự kiện
    EventRegistrationResponse getAllStudentByEvent(Long eventId);

    List<EventRespone> getAllEventByStudent(Long sudentId);

    List<UserResponse> getCheckedInStudentsByEvent(Long eventId);

}
