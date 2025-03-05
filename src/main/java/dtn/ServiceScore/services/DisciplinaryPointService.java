package dtn.ServiceScore.services;

import dtn.ServiceScore.model.DisciplinaryPoint;
import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.User;

public interface DisciplinaryPointService {
    DisciplinaryPoint Addpoint(User user, Event event);
}
