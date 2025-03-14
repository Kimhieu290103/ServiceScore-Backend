package dtn.ServiceScore.controllers;

import dtn.ServiceScore.dtos.FiveGoodCriteriaDTO;
import dtn.ServiceScore.model.FiveGoodCriteria;
import dtn.ServiceScore.services.FiveGoodCriteriaService;
import jakarta.validation.Valid;
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


    //  API Thêm mới tiêu chí
    @PostMapping
    public ResponseEntity<FiveGoodCriteria> createCriteria(@Valid @RequestBody FiveGoodCriteriaDTO dto) {
        FiveGoodCriteria criteria = fiveGoodCriteriaService.createCriteria(dto);
        return ResponseEntity.ok(criteria);
    }

    // API xóa mềm tiêu chí
    @PutMapping("/delete/{id}")
    public ResponseEntity<String> softDeleteCriteria(@PathVariable Long id) {
        fiveGoodCriteriaService.softDeleteCriteria(id);
        return ResponseEntity.ok("✅ Đã xóa mềm tiêu chí có ID: " + id);
    }

}
