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

import java.util.Comparator;
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

        return ExternalEventResponse.builder()
                .id(externalEvent.getId())
                .user_id(externalEvent.getUser().getId())
                .nameStudent(externalEvent.getUser().getFullname())
                .name(externalEvent.getName())
                .description(externalEvent.getDescription())
                .date(externalEvent.getDate())
                .proofUrl(externalEvent.getProofUrl())
                .status(externalEvent.getStatus().name())
                .points(externalEvent.getPoints())
                .semester(externalEvent.getSemester().getName())
                .studentName(externalEvent.getUser().getFullname())
                .clazz(externalEvent.getUser().getClazz() != null ? externalEvent.getUser().getClazz().getName() : "N/A")
                .created_at(externalEvent.getCreatedAt())
                .build();
    }

    @Override
    public List<ExternalEventResponse> getPendingEvents() {
        List<ExternalEvent> pendingEvents = externalEventRepository.findByStatus(ExternalEventStatus.PENDING);
        return pendingEvents.stream()
                .sorted(Comparator.comparing(event -> event.getUser().getId()))
                .map(event -> ExternalEventResponse.builder()
                        .id(event.getId())
                        .user_id(event.getUser().getId())
                        .nameStudent(event.getUser().getFullname())
                        .name(event.getName())
                        .description(event.getDescription())
                        .date(event.getDate())
                        .proofUrl(event.getProofUrl())
                        .status(event.getStatus().name())
                        .points(event.getPoints())
                        .studentName(event.getUser().getFullname())
                        .semester(event.getSemester().getName())
                        .clazz(event.getUser().getClazz() != null ? event.getUser().getClazz().getName() : "N/A")
                        .created_at(event.getCreatedAt())
                        .build()
                ).collect(Collectors.toList());
    }

    @Override
    public List<ExternalEventResponse> getUserEvents(Long userId) {
        List<ExternalEvent> userEvents = externalEventRepository.findByUserId(userId);

        return userEvents.stream()
                .map(event -> ExternalEventResponse.builder()
                        .id(event.getId())
                        .user_id(event.getUser().getId())
                        .nameStudent(event.getUser().getFullname())
                        .name(event.getName())
                        .description(event.getDescription())
                        .date(event.getDate())
                        .proofUrl(event.getProofUrl())
                        .status(event.getStatus().name())
                        .points(event.getPoints())
                        .studentName(event.getUser().getFullname())
                        .semester(event.getSemester().getName())
                        .clazz(event.getUser().getClazz() != null ? event.getUser().getClazz().getName() : "N/A")
                        .created_at(event.getCreatedAt())
                        .build()
                ).collect(Collectors.toList());
    }

    @Override
    public ExternalEvent findById(Long id) {
        Optional<ExternalEvent> event = externalEventRepository.findById(id);
        return event.orElse(null);
    }


}
