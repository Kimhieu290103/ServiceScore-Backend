package dtn.ServiceScore.controllers;

import dtn.ServiceScore.dtos.EventDTO;
import dtn.ServiceScore.dtos.UserDTO;
import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.responses.EventListResponse;
import dtn.ServiceScore.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/events")
@RequiredArgsConstructor
public class EventController {
    public final EventService eventService;
    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@Valid @RequestBody EventDTO eventDTO, BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errors = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }


            eventService.createEvent(eventDTO);
            return  ResponseEntity.ok("dang  ki thanh cong") ;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long eventId){
        try {
            Event existingEvent = eventService.getEventById(eventId);
            return ResponseEntity.ok(existingEvent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @GetMapping("")
    public ResponseEntity<EventListResponse> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        PageRequest pageRequest= PageRequest.of(page,
                limit);
        Page<Event> eventPages = eventService.getAllEvents(pageRequest);
        int totalPages = eventPages.getTotalPages();
        List<Event> events = eventPages.getContent();
        return ResponseEntity.ok(EventListResponse.builder()
                .events(events)
                .totalPage(totalPages)
                .build());
    }
}
