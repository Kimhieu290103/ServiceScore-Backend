package dtn.ServiceScore.controllers;

import dtn.ServiceScore.services.FiveGoodCriteriaLcdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/five_good_lcd")
@RequiredArgsConstructor
public class FiveGoodCriteriaLcdController {
    private final FiveGoodCriteriaLcdService fiveGoodCriteriaLcdService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllFiveGoodLcd() {
        try {
            return ResponseEntity.ok(fiveGoodCriteriaLcdService.getAllFiveGoodCriteriaLcd());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
