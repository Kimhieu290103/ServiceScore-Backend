package dtn.ServiceScore.controllers;

import dtn.ServiceScore.dtos.FiveGoodCriteriaDTO;
import dtn.ServiceScore.model.FiveGoodCriteria;
import dtn.ServiceScore.services.FiveGoodCriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/five_good")
@RequiredArgsConstructor
public class FiveGoodCriteriaController {
    private final FiveGoodCriteriaService fiveGoodCriteriaService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllFiveGood() {
        try {
            return ResponseEntity.ok(fiveGoodCriteriaService.getAllFiveGoodCriteria());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
