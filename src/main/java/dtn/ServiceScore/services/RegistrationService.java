package dtn.ServiceScore.services;

import dtn.ServiceScore.model.Registration;
import dtn.ServiceScore.responses.EventRespone;
import dtn.ServiceScore.responses.UserResponse;

import java.util.List;

public interface RegistrationService {
    Registration register_event(Long eventId , Long userId) throws Exception;
    Registration checkInEvent(Long registrationId) throws Exception;
    Registration checkInEvent(Long eventId, Long userId) throws Exception;
    boolean isUserRegistered(Long eventId, Long userId) throws Exception;
    List<UserResponse> getAllStudentByEvent(Long eventId);
    List<EventRespone> getAllEventByStudent(Long sudentId);

}
