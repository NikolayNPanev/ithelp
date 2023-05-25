package bg.tu_varna.sit.task_manager.repository;

import bg.tu_varna.sit.task_manager.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByTaskId(Long taskId);

    List<Report> findByTaskIdAndHoursWorkedBetween(Long taskId, double min, double max);

    @Query("SELECT r FROM Report r WHERE r.task.id = :taskId AND r.hoursWorked = (SELECT max(r.hoursWorked) FROM Report r)")
    Report findLongestReport(@Param("taskId") Long taskId);

    @Query("SELECT sum(r.hoursWorked) FROM Report r WHERE r.task.id = :taskId")
    double getAllReportsLength(@Param("taskId") Long taskId);
}
