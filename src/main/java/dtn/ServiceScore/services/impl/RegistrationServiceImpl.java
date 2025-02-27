package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.exceptions.DataNotFoundException;
import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.Registration;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.repositories.EventRepository;
import dtn.ServiceScore.repositories.RegistrationRepository;
import dtn.ServiceScore.repositories.UserRepository;
import dtn.ServiceScore.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Registration register_event(Long eventId, Long userId) throws Exception {
        User existingUser = userRepository.findById(userId)
        .orElseThrow(() -> new DataNotFoundException("Không tìm người dùng"));

        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy event"));

        Registration newRegistration = Registration.builder()
                .event(existingEvent)
                .user(existingUser)
                .status("Registered")
                .registeredAt(LocalDate.now())
                .build();
        return registrationRepository.save(newRegistration);
    }
}
