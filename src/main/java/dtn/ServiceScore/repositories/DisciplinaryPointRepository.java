package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.DisciplinaryPoint;
import dtn.ServiceScore.model.Semester;
import dtn.ServiceScore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DisciplinaryPointRepository extends JpaRepository<DisciplinaryPoint, Long> {
    DisciplinaryPoint findByUserAndSemester(User user, Semester semester);
    List<DisciplinaryPoint> findByUser_Id(Long userId);

    List<DisciplinaryPoint> findByUser(User user);


    @Query("SELECT COALESCE(SUM(dp.points), 0) FROM DisciplinaryPoint dp WHERE dp.user.id = :userId AND dp.semester.id = :semesterId")
    Long getTotalPointsByUserAndSemester(@Param("userId") Long userId, @Param("semesterId") Long semesterId);


}
