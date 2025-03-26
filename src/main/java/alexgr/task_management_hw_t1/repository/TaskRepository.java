package alexgr.task_management_hw_t1.repository;

import alexgr.task_management_hw_t1.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {


}
