package dtn.ServiceScore.services;

import dtn.ServiceScore.dtos.ClassSearchRequest;
import dtn.ServiceScore.model.Class;

import java.util.List;

public interface ClassService {
    List<Class> getAllClass();

    List<Class> getClasses(ClassSearchRequest request);

}
