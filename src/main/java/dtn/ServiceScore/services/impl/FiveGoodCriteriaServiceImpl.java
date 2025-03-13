package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.dtos.FiveGoodCriteriaDTO;
import dtn.ServiceScore.model.FiveGoodCriteria;
import dtn.ServiceScore.repositories.FiveGoodCriteriaRepository;
import dtn.ServiceScore.repositories.StudentCriteriaRepository;
import dtn.ServiceScore.services.FiveGoodCriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FiveGoodCriteriaServiceImpl implements FiveGoodCriteriaService {
    private final FiveGoodCriteriaRepository fiveGoodCriteriaRepository;
    private final StudentCriteriaRepository studentCriteriaRepository;
    @Override
    public List<FiveGoodCriteria> getAllFiveGoodCriteria() {
        return fiveGoodCriteriaRepository.findAll();
    }


}
