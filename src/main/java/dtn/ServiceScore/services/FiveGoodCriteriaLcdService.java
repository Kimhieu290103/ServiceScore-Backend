package dtn.ServiceScore.services;


import dtn.ServiceScore.dtos.FiveGoodCriteriaLcdDTO;
import dtn.ServiceScore.model.FiveGoodCriteria;
import dtn.ServiceScore.model.FiveGoodCriteriaLcd;

import java.util.List;

public interface FiveGoodCriteriaLcdService {
    List<FiveGoodCriteriaLcd> getAllFiveGoodCriteriaLcd();

    FiveGoodCriteriaLcd createCriteriaLcd(FiveGoodCriteriaLcdDTO dto);

    void deleteCriteriaLcd(Long id);
    List<FiveGoodCriteriaLcd> getCriteriaLcdBySemester(Long semesterId);


    FiveGoodCriteriaLcd updateCriteriaLcd(Long id, FiveGoodCriteriaLcdDTO dto);

}
