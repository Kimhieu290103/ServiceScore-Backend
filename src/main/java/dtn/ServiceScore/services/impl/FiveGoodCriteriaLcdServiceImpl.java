package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.model.FiveGoodCriteriaLcd;
import dtn.ServiceScore.repositories.FiveGoodCriteriaLcdRepository;
import dtn.ServiceScore.services.FiveGoodCriteriaLcdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class FiveGoodCriteriaLcdServiceImpl implements FiveGoodCriteriaLcdService {
    private final FiveGoodCriteriaLcdRepository fiveGoodCriteriaLcdRepository;
    @Override
    public List<FiveGoodCriteriaLcd> getAllFiveGoodCriteriaLcd() {
        return fiveGoodCriteriaLcdRepository.findAll();
    }
}
