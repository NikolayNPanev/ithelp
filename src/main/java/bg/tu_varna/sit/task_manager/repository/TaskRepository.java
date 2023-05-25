package bg.tu_varna.sit.task_manager.repository;

import bg.tu_varna.sit.task_manager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
