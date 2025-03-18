package dtn.ServiceScore.services;

import dtn.ServiceScore.model.DisciplinaryPoint;
import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.ExternalEvent;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.responses.PointResponse;
import dtn.ServiceScore.responses.StudentPointResponse;

import java.util.List;
import java.util.Map;

public interface DisciplinaryPointService {
    // điểm danh cộng điểm và tiêu chí cho sinh viên
    DisciplinaryPoint Addpoint(User user, Event event);

    // Cộng điểm cho hoạt động ở ngoài của sinh viên
    DisciplinaryPoint AddPointForExternalEvent(User user, ExternalEvent externalEvent);

    // Từ chối hoạt động mà sinh viên đã gửi
    String rejectExternalEvent(Long eventId);

    // điểm danh tất cả sinh viên
    Map<String, Object> addPointsForAllRegisteredUsers(Event event);

    // Danh sách điểm theo các kì
    List<PointResponse> getDisciplinaryPointsByUserId();

    Map<String, Object> getDisciplinaryPointsWithTotal();


    // danh sách điêmr sinh viên
    List<StudentPointResponse> getStudentsWithTotalPoints(
            Long classId, Integer courseId, Integer departmentId, Long semesterId);
}
