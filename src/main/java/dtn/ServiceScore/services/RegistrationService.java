package dtn.ServiceScore.services;

import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.Registration;
import dtn.ServiceScore.model.User;

public interface RegistrationService {
    Registration register_event(Long eventId , Long userId) throws Exception;
}
