package bg.tu_varna.sit.task_manager.repository;

import bg.tu_varna.sit.task_manager.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
