package dtn.ServiceScore.controllers;

import dtn.ServiceScore.dtos.FiveGoodCriteriaDTO;
import dtn.ServiceScore.dtos.FiveGoodCriteriaLcdDTO;
import dtn.ServiceScore.model.FiveGoodCriteria;
import dtn.ServiceScore.model.FiveGoodCriteriaLcd;
import dtn.ServiceScore.services.FiveGoodCriteriaLcdService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    //  API Thêm mới tiêu chí
    @PostMapping
    public ResponseEntity<?> createCriteria(@Valid @RequestBody FiveGoodCriteriaLcdDTO dto) {
     FiveGoodCriteriaLcd fiveGoodCriteriaLcd = fiveGoodCriteriaLcdService.createCriteriaLcd(dto);
        return ResponseEntity.ok(fiveGoodCriteriaLcd);
    }

    // API xóa mềm tiêu chí
    @PutMapping("/delete/{id}")
    public ResponseEntity<String> softDeleteCriteria(@PathVariable Long id) {
        fiveGoodCriteriaLcdService.softDeleteCriteriaLcd(id);
        return ResponseEntity.ok("✅ Đã xóa mềm tiêu chí có ID: " + id);
    }
}
