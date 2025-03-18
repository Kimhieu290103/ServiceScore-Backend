package dtn.ServiceScore.services;


import dtn.ServiceScore.dtos.FiveGoodCriteriaLcdDTO;
import dtn.ServiceScore.model.FiveGoodCriteriaLcd;

import java.util.List;

public interface FiveGoodCriteriaLcdService {
    List<FiveGoodCriteriaLcd> getAllFiveGoodCriteriaLcd();

    FiveGoodCriteriaLcd createCriteriaLcd(FiveGoodCriteriaLcdDTO dto);

    void softDeleteCriteriaLcd(Long id);
}
