package dtn.ServiceScore.controllers;

import dtn.ServiceScore.services.FiveGoodCriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/five_good")
@RequiredArgsConstructor
public class FiveGoodCriteriaController {
    private final FiveGoodCriteriaService fiveGoodCriteriaService;
    @GetMapping("/all")
    public ResponseEntity<?> getAllFiveGood(){
        try {
            return ResponseEntity.ok(fiveGoodCriteriaService.getAllFiveGoodCriteria());
        } catch (Exception e ) {
            throw new RuntimeException(e);
        }
    }
}
