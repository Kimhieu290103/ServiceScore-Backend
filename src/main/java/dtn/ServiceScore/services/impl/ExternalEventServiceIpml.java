package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.dtos.ExternalEventDTO;
import dtn.ServiceScore.enums.ExternalEventStatus;
import dtn.ServiceScore.model.ExternalEvent;
import dtn.ServiceScore.model.Semester;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.repositories.ExternalEventRepository;
import dtn.ServiceScore.repositories.SemesterRepository;
import dtn.ServiceScore.repositories.UserRepository;
import dtn.ServiceScore.responses.ExternalEventResponse;
import dtn.ServiceScore.services.ExternalEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExternalEventServiceIpml implements ExternalEventService {
    private final ExternalEventRepository externalEventRepository;
    private final UserRepository userRepository;
    private final SemesterRepository semesterRepository;
    @Override
    public ExternalEventResponse createExternalEvent(ExternalEventDTO dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Semester semester = semesterRepository.findById(dto.getSemesterId())
                .orElseThrow(() -> new RuntimeException("Semester not found"));

        ExternalEvent externalEvent = ExternalEvent.builder()
                .user(user)
                .name(dto.getName())
                .description(dto.getDescription())
                .date(dto.getDate())
                .proofUrl(dto.getProofUrl())
                .status(ExternalEventStatus.PENDING)
                .points(dto.getPoints())
                .semester(semester)
                .build();

        externalEvent = externalEventRepository.save(externalEvent);

        return new ExternalEventResponse(
                externalEvent.getId(),
                externalEvent.getUser().getId(),
                externalEvent.getName(),
                externalEvent.getDescription(),
                externalEvent.getDate(),
                externalEvent.getProofUrl(),
                externalEvent.getStatus().name(),
                externalEvent.getPoints(),
                externalEvent.getSemester().getName()
        );
    }

    @Override
    public List<ExternalEventResponse> getPendingEvents() {
        List<ExternalEvent> pendingEvents = externalEventRepository.findByStatus(ExternalEventStatus.PENDING);

        return pendingEvents.stream().map(event -> new ExternalEventResponse(
                event.getId(),
                event.getUser().getId(),
                event.getName(),
                event.getDescription(),
                event.getDate(),
                event.getProofUrl(),
                event.getStatus().name(),
                event.getPoints(),
                event.getSemester().getName()
        )).collect(Collectors.toList());
    }

    @Override
    public List<ExternalEventResponse> getUserEvents(Long userId) {
        List<ExternalEvent> userEvents = externalEventRepository.findByUserId(userId);

        return userEvents.stream().map(event -> new ExternalEventResponse(
                event.getId(),
                event.getUser().getId(),
                event.getName(),
                event.getDescription(),
                event.getDate(),
                event.getProofUrl(),
                event.getStatus().name(),
                event.getPoints(),
                event.getSemester().getName()
        )).collect(Collectors.toList());
    }

    @Override
    public ExternalEvent findById(Long id) {
        Optional<ExternalEvent> event = externalEventRepository.findById(id);
        return event.orElse(null);
    }


}
