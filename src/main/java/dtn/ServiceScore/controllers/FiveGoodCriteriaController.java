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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> softDeleteCriteria(@PathVariable Long id) {
        fiveGoodCriteriaService.deleteCriteria(id);
        return ResponseEntity.ok("✅ Đã xóa mềm tiêu chí có ID: " + id);
    }

    // Lấy danh sách tiêu chí theo kỳ học
    @GetMapping("/{semesterId}")
    public  ResponseEntity<?> getCriteriaBySemester(@PathVariable Long semesterId) {
        return ResponseEntity.ok(fiveGoodCriteriaService.getCriteriaBySemester(semesterId));
    }
    @PutMapping("/{id}")
    public ResponseEntity<FiveGoodCriteria> updateCriteria(
            @PathVariable Long id,
            @RequestBody FiveGoodCriteriaDTO dto) {
        FiveGoodCriteria updatedCriteria = fiveGoodCriteriaService.updateCriteria(id, dto);
        return ResponseEntity.ok(updatedCriteria);
    }

}
