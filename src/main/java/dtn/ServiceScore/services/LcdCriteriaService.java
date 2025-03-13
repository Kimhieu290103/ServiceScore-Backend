package dtn.ServiceScore.services;

import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.User;

import java.util.List;

public interface LcdCriteriaService {
    List<User> getLcdsCompletedAllCriteria(Long semesterId);

    String Event_validation(User user, Event event);
}

