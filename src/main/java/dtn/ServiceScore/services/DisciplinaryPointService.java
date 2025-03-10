package dtn.ServiceScore.services;

import dtn.ServiceScore.model.DisciplinaryPoint;
import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.responses.PointResponse;

import java.util.List;
import java.util.Map;

public interface DisciplinaryPointService {
    DisciplinaryPoint Addpoint(User user, Event event);
    List<PointResponse> getDisciplinaryPointsByUserId();
    Map<String, Object> getDisciplinaryPointsWithTotal();
}
