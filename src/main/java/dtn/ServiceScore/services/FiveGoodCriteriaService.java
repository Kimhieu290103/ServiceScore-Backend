package dtn.ServiceScore.services;

import dtn.ServiceScore.dtos.FiveGoodCriteriaDTO;
import dtn.ServiceScore.model.FiveGoodCriteria;

import java.util.List;

public interface FiveGoodCriteriaService {
    List<dtn.ServiceScore.model.FiveGoodCriteria> getAllFiveGoodCriteria();

    FiveGoodCriteria createCriteria(FiveGoodCriteriaDTO dto);

    void deleteCriteria(Long id);
    List<FiveGoodCriteria> getCriteriaBySemester(Long semesterId);

    FiveGoodCriteria updateCriteria(Long id, FiveGoodCriteriaDTO dto);
}
