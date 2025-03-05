package dtn.ServiceScore.services;

import dtn.ServiceScore.model.User;

import java.util.List;

public interface StudentCriteriaService {
    boolean hasCompletedAllCriteria(User user);
    public List<User> getStudentsCompletedAllCriteria();
}
